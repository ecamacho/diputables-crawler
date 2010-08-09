/**
 * 
 */
package com.tidyslice.dipcrawler.services.parser.dom;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tidyslice.dipcrawler.data.ComisionIniciativaDao;
import com.tidyslice.dipcrawler.data.IniciativaDao;
import com.tidyslice.dipcrawler.domain.ComisionIniciativa;
import com.tidyslice.dipcrawler.domain.EstatusIniciativa;
import com.tidyslice.dipcrawler.domain.Iniciativa;
import com.tidyslice.dipcrawler.domain.RolIniciativa;
import com.tidyslice.dipcrawler.services.parser.DipParser;
import com.tidyslice.dipcrawler.util.ParserUtil;

/**
 * @author erick
 *
 */
public class IniciativasParser implements DipParser<List<Iniciativa>> {

	private static final Logger logger = Logger.getLogger( IniciativasParser.class );
	
	private IniciativaDao iniciativaDao;
	
	private ComisionIniciativaDao comisionIniciativaDao;
	
	private static final String UNIDAS = "Unidas";
	
	private static final String CON_OPINION = "Con Opini—n de";
	
	private static final String SEPARADOR_COMISIONES = "- ";
	
	public void setIniciativaDao(IniciativaDao iniciativaDao) {
		this.iniciativaDao = iniciativaDao;
	}

	@Override
	public List<Iniciativa> parseObject(Document doc, Object... args) {
		List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();		
		Assert.notNull( doc );
		Assert.notNull( args[0] );
		Assert.notNull( args[1] );
		String dip = (String) args[0];
		Integer periodo = (Integer) args[1];
		logger.debug( "parsing iniciativas for a given period " );
		NodeList rows = ( (Element) getIniciativasTable( doc ) ).getElementsByTagName("TR");
		if( rows.getLength() > 1 ) {
			Iniciativa iniciativa;
			for( int i = 1; i < rows.getLength(); i++ ) {
				NodeList columns = ((Element) rows.item( i )).getElementsByTagName("TD");
				iniciativa = new Iniciativa();
				iniciativa.setId( dip + '-' + periodo + "-" + i );
				iniciativa.setTitulo( ParserUtil.trimInitialDigits(columns.item( 0 ).getTextContent() ).trim());
				iniciativa.setRolIniciativa( parseRol( iniciativa.getTitulo() ) );
				iniciativa.setFecha( parseFecha( columns.item( 1 ).getTextContent() ) );
				iniciativa.setSinopsis( columns.item( 2 ).getTextContent() );
				iniciativa.setTramite( parseEstatus( columns.item( 3 ).getTextContent() ));
				Element tramiteElement = (Element) columns.item(3);
				Element link = (Element) tramiteElement.getElementsByTagName("A").item(0);
				List<ComisionIniciativa> comisiones = parseComisionesElement( (Element) columns.item( 1 ), iniciativa.getId() );
				//Algunas no tienen fecha y muestran la cadena --
				if( !" --".equals( link.getTextContent() ) ) { 
					iniciativa.setFechaPublicacion( parseStringToDate( link.getTextContent() ));
				}
				iniciativa.setLinkGaceta( link.getAttribute("href") );
				if( iniciativa.getTramite() == EstatusIniciativa.APROBADA ) {
					iniciativa.setFechaAprobacion( parseFechaAprobacion( columns.item(3).getTextContent() ) );
				}
				
				iniciativa.setDiputadoUiid( dip );
				
				iniciativaDao.persist( iniciativa );
				for( ComisionIniciativa comision : comisiones ) {
					comisionIniciativaDao.persist( comision );
				}
				iniciativas.add( iniciativa );
			}
		}
		return iniciativas;
	}
	
	private NodeList getIniciativasTable( Document doc )
	{
		NodeList list = doc.getElementsByTagName("table");
		
		Node tableNode = list.item( 1 );
		Node inciativasTable = null;
		
		for(int i = 0; i < tableNode.getChildNodes().getLength(); i++ )
		{					
			if( tableNode.getChildNodes().item( i ).getNodeName().equals( "TBODY" ) )
			{
				inciativasTable = tableNode.getChildNodes().item( i );	
				
			}
		}
		return inciativasTable.getChildNodes();
	}
	
	private RolIniciativa parseRol( String titulo ) {
		RolIniciativa rol = null;
		if( titulo.contains( convertToRolLabel( RolIniciativa.ADHERENTE ) ) ) {
			rol = RolIniciativa.ADHERENTE;
		} else if (titulo.contains( convertToRolLabel( RolIniciativa.PROPONENTE ) ) ) {
			rol = RolIniciativa.PROPONENTE;
		} else if (titulo.contains( convertToRolLabel( RolIniciativa.SUSCRIBE ) ) ) {
			rol = RolIniciativa.SUSCRIBE;
		}
		
		return rol;
	}

	@SuppressWarnings("unchecked")
	private String convertToRolLabel( Enum rol ) {
		String rolLabel = rol.toString().toLowerCase();
		rolLabel = rolLabel.substring(0,1).toUpperCase() + rolLabel.substring(1);
		return rolLabel;
	}
	
	private EstatusIniciativa parseEstatus( String estatusUnparsed ) {
		EstatusIniciativa estatus = null;
		if( estatusUnparsed.contains( convertToRolLabel( EstatusIniciativa.PENDIENTE ) ) ) {
			estatus = EstatusIniciativa.PENDIENTE;
		} else if( estatusUnparsed.contains( convertToRolLabel( EstatusIniciativa.APROBADA ) ) ) {
			estatus = EstatusIniciativa.APROBADA;
		}
		
		return estatus;
	}
	
	private Date parseFecha( String fechaUnparsed ) {
		Assert.notNull( fechaUnparsed );
		Date date = null;
		if( fechaUnparsed.trim().length() >0 ) {
			String fecha = fechaUnparsed.substring( "Fecha de presentaci—n: ".length());
			date = parseStringToDate( fecha );
		}
		return date;
	}
	
	private Date parseStringToDate( String fechaUnparsed ) {
		
		Date date = null;
		
		SimpleDateFormat parser = new SimpleDateFormat("dd-MMM-yyyy", new Locale("es"));
		
		
		try {
			date = parser.parse( fechaUnparsed );
		} catch (ParseException e) {
			logger.error("Error parsing fecha" + fechaUnparsed);
			throw new RuntimeException(e);
		}
		return date;
	}
	
	
	public List<ComisionIniciativa> parseComisionesElement( Element column, String iniciativaId ) {
		List<ComisionIniciativa> comisiones = new ArrayList<ComisionIniciativa>();
		
		try {
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer;
			
				transformer = transFactory.newTransformer();
			
			StringWriter buffer = new StringWriter();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(column),
			      new StreamResult(buffer));
			
			comisiones = parseComisiones( buffer.toString(), iniciativaId);
			
			
		} catch (TransformerConfigurationException e) {
			logger.error( e );
		} catch (TransformerException e) {
			logger.error( e );
		}
		return comisiones;
	}
	
	private Date parseFechaAprobacion( String fechaUnparsed ) {
		Assert.notNull( fechaUnparsed );
		
		Date date = null;
		if( fechaUnparsed.trim().length() > 0 ) {
			fechaUnparsed = fechaUnparsed.replace('\n', ' ');

			String fecha = fechaUnparsed.trim().substring( "Aprobada  			  			      con fecha  ".length(), 
				fechaUnparsed.indexOf("Publicaci—n")).trim();
			date = parseStringToDate( fecha );
		}
		return date;
	}
	
	public List<ComisionIniciativa> parseComisiones( String unparsedText, String iniciativaId ) {
		List<ComisionIniciativa> comisiones = new ArrayList<ComisionIniciativa>();
		Assert.notNull( unparsedText );
		//Primer caso, solo una comision
		if( !unparsedText.contains( UNIDAS ) && !unparsedText.contains( CON_OPINION ) ) {
			if( unparsedText.contains( SEPARADOR_COMISIONES ) ) {
				String com = unparsedText.substring( unparsedText.indexOf( SEPARADOR_COMISIONES ) + SEPARADOR_COMISIONES.length()  );				
				com = com.substring( 0, com.indexOf("</B>") );
				ComisionIniciativa comision = new ComisionIniciativa();
				comision.setComision( com.replace("\n", "") );
				comision.setIniciativaId("");
				comision.setTipo(1);
				comision.setIniciativaId(iniciativaId);
				comisiones.add( comision );
			}
		}
		//Segundo caso, hay comisiones unidas
		if( unparsedText.contains( UNIDAS ) ) {
			String comisionesUnidas = unparsedText.substring( unparsedText.indexOf( UNIDAS ) + UNIDAS.length(), 
									  unparsedText.contains( CON_OPINION )? 
											  unparsedText.indexOf( CON_OPINION ) : 
												  unparsedText.lastIndexOf("<BR/>") + 5 );						
			comisiones.addAll( parseList( comisionesUnidas, 1, iniciativaId ) );
		}
		
		//Tercer caso, hay comisiones que dieron su opinion
		if( unparsedText.contains( CON_OPINION ) ) {
			String comisionesOpinion = unparsedText.substring( unparsedText.indexOf( CON_OPINION ) + CON_OPINION.length(), 
									   unparsedText.lastIndexOf("<BR/>") + 5 );						
			comisiones.addAll( parseList( comisionesOpinion, 2, iniciativaId ) );
		}
		
		return comisiones;
	}
	
	
	private List<ComisionIniciativa> parseList( String list, int tipo, String iniciativaId ) {
		Assert.notNull( list );
		List<ComisionIniciativa> comisiones = new ArrayList<ComisionIniciativa>();
		list = list.replace( "<BR/>", "");
		list = list.replace( "</B>", "");
		String[] comisionesStr = list.split("<B>");
		for( String comisionStr : comisionesStr ) {
			if( comisionStr.trim().length() > 1) {
			
				comisionStr = comisionStr.trim().replace( SEPARADOR_COMISIONES, "");
				ComisionIniciativa comision = new ComisionIniciativa();
				char space = 160;
				comisionStr = comisionStr.replace(space + "", "");						
				comision.setIniciativaId(iniciativaId);
				comision.setComision( comisionStr.trim() );
				comision.setTipo( tipo );
				comisiones.add( comision );
			}
		}
		return comisiones;
	}

	public void setComisionIniciativaDao(ComisionIniciativaDao comisionIniciativaDao) {
		this.comisionIniciativaDao = comisionIniciativaDao;
	}
}
