/**
 * 
 */
package com.tidyslice.dipcrawler.services.parser.dom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tidyslice.dipcrawler.data.IniciativaDao;
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
				
				iniciativa.setTitulo( ParserUtil.trimInitialDigits(columns.item( 0 ).getTextContent() ).trim());
				iniciativa.setRolIniciativa( parseRol( iniciativa.getTitulo() ) );
				iniciativa.setFecha( parseFecha( columns.item( 1 ).getTextContent() ) );
				iniciativa.setSinopsis( columns.item( 2 ).getTextContent() );
				iniciativa.setTramite( parseEstatus( columns.item( 3 ).getTextContent() ));
				Element tramiteElement = (Element) columns.item(3);
				Element link = (Element) tramiteElement.getElementsByTagName("A").item(0);
				logger.debug( "contenido " + columns.item(3).getTextContent() );
				logger.debug( "contenido " + link.getTextContent() );
				if( !" --".equals( link.getTextContent() ) ) { 
					iniciativa.setFechaPublicacion( parseStringToDate( link.getTextContent() ));
				}
				iniciativa.setLinkGaceta( link.getAttribute("href") );
				if( iniciativa.getTramite() == EstatusIniciativa.APROBADA ) {
					iniciativa.setFechaAprobacion( parseFechaAprobacion( columns.item(3).getTextContent() ) );
				}
				iniciativa.setId( dip + '-' + periodo + "-" + i );
				iniciativa.setDiputadoUiid( dip );
				logger.debug( iniciativa );
				iniciativaDao.persist( iniciativa );
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
	
}
