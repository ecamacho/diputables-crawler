/**
 * 
 */
package com.tidyslice.dipcrawler.services.parser.dom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tidyslice.dipcrawler.domain.Diputado;
import com.tidyslice.dipcrawler.services.parser.DipParser;
import com.tidyslice.dipcrawler.util.Constants;

/**
 * @author erick
 *
 */
public class DiputadoBiopicDomParser implements DipParser<Diputado>{

	private static final Logger logger = Logger.getLogger( DiputadoBiopicDomParser.class );
	
	private Diputado diputado;
	
	@Value("#{ crawlerProperties['biopic.base.diputados.url'] }")
	private String baseBiopicUrl;
	
	
	
	
	@Override
	public Diputado parseObject(Document doc, Object... args) {
		Assert.notNull( args );
		diputado = (Diputado) args[0];		
		NodeList rows = getInfoTable(doc);
		
		logger.info("[Crawling diputado] " + diputado);
		int rowCounter = 0;		
		for(int i = 0; i < rows.getLength(); i++ )
		{
			Node node = rows.item( i );
			if( node.getNodeName().equals("TR") )
			{
				rowCounter++;
				Element element = (Element) node;
				switch( rowCounter )
				{
					case 1 : diputado = parseFirstRow(element); break;
					case 2 : diputado.setTipoMayoria( parseItem("Tipo de elecci—n:\n", node.getTextContent() ) ); break;
					case 6 : diputado.setCurul( parseItem("Curul:\n", node.getTextContent() ) ); break;
					case 8 : diputado.setFechaNacimiento( parseDate(node.getTextContent() ) ); break;
					case 9 : diputado.setEmail( parseItem("Correo electr—nico:", node.getTextContent() ) );break;
					default: break;
				}
				
				
			}
			
		}
		
		return diputado;
	}

	
	private NodeList getInfoTable( Document doc )
	{
		NodeList list = doc.getElementsByTagName("table");
		diputado = parseLinkTable( list.item( 1 ) );
		Node tableNode = list.item( 2 );
		Node dipTable = null;
		for(int i = 0; i < tableNode.getChildNodes().getLength(); i++ )
		{					
			if( tableNode.getChildNodes().item( i ).getNodeName().equals( "TBODY" ) )
			{
				dipTable = tableNode.getChildNodes().item( i );												
			}
		}
		return dipTable.getChildNodes();
	}
	
	private Diputado parseLinkTable( Node table )
	{
		
		NodeList rows = ( (Element) table ).getElementsByTagName("TBODY").item(0).getChildNodes();
		
		NodeList columns = ((Element)rows.item(0)).getElementsByTagName("TD");	
		
		String iniciativasUrl = baseBiopicUrl + ((Element)columns.item( 0 )).getElementsByTagName("A").
								item(0).getAttributes().getNamedItem("href").getNodeValue();
		
		
		String proposicionesUrl = baseBiopicUrl + ((Element)columns.item( 1 )).getElementsByTagName("A").
		item(0).getAttributes().getNamedItem("href").getNodeValue();
		
		String asistenciasUrl = baseBiopicUrl + ((Element)columns.item( 2 )).getElementsByTagName("A").
		item(0).getAttributes().getNamedItem("href").getNodeValue();
		
		String votacionesUrl = baseBiopicUrl + ((Element)columns.item( 3 )).getElementsByTagName("A").
		item(0).getAttributes().getNamedItem("href").getNodeValue();
		
		diputado.setAsistenciasUrl(asistenciasUrl);
		diputado.setIniciativasUrl(iniciativasUrl);
		diputado.setProposicionesUrl(proposicionesUrl);
		diputado.setVotacionesUrl(votacionesUrl);
		
		
		
		return diputado;
	}
	
	private Diputado parseFirstRow( Element row )
	{
		
		NodeList images = row.getElementsByTagName("IMG");
		diputado.setFoto( baseBiopicUrl + images.item(0).getAttributes().getNamedItem("src").getNodeValue() );
		
		//independientes no tienen partido ni logo
		if( images.getLength() > 1 )
		{
			diputado.setPartido( parsePartido( images.item(1).getAttributes().getNamedItem("src").getNodeValue() ) );
			
		} else {
			diputado.setPartido( "Independiente" );
		}
		
		logger.debug( diputado.getFoto());
		
		return diputado;
	}
	
	private String parsePartido( final String imgSrc )
	{
		String partido = "Independiente";
		if( Constants.PRI_IMG.equals( imgSrc ) )
		{
			partido = "PRI";
		} else if( Constants.PAN_IMG.equals( imgSrc ) )
		{
			partido = "PAN";
		} else if( Constants.PRD_IMG.equals( imgSrc ) )
		{
			partido = "PRD";
		} else if( Constants.CONVERGENCIA_IMG.equals( imgSrc ) )
		{
			partido = "Convergencia";
		} else if( Constants.PANAL_IMG.equals( imgSrc ) )
		{
			partido = "Alianza";
		} else if( Constants.PSD_IMG.equals( imgSrc ) )
		{
			partido = "PSD";
		} else if( Constants.PT_IMG.equals( imgSrc ) )
		{
			partido = "PT";
		} else if( Constants.PV_IMG.equals( imgSrc ) )
		{
			partido = "Partido Verde";
		}
		
			
		return partido;
	}
	
	
	
	private String parseItem( final String prefix, final String rawText )
	{
		return rawText.replace( prefix, "").trim();
	}
	
	private Date parseDate( final String rawDate )
	{
		String parsedDate = parseItem("Fecha de Nacimiento:\n", rawDate);
		parsedDate = parsedDate.replace("Fecha de Nacimiento:", "");
		parsedDate = parsedDate.replace("\n", "");
		parsedDate = parsedDate.trim();
		
		SimpleDateFormat parser = new SimpleDateFormat("dd-MMM-yyyy", new Locale("es"));
		Date date = null;
		
		try {
			date = parser.parse( parsedDate );
		} catch (ParseException e) {
			logger.error("Error parsing fecha de Nacimiento:" + parsedDate + " para " + diputado);
			
			date = addFechaToDiputado();
		}
		return date;
	}
	
	private Date addFechaToDiputado( ) {
		SimpleDateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
		Date date 				= null;
		String fecha 			= null;
		if( diputado.getUuid().equals("u9ba045a0a2fd5084b1ba74663b225374dfcce99c36d918733349471310231754")) {
			fecha = "11-06-1974"; 
		} else if ( "u6f2ed667fd4a91e913ebbe5d48ff3234ca9142495e3c46c33167198b8478".equals(diputado.getUuid()) || 
					"u54c442841e6ec28a24c91b2acd1c3f1f115e5ba86039e5c2707922d0c86de23b".equals( diputado.getUuid() )) {
			fecha = "16-12-1972";
		}
		if( fecha != null ) {
			try {
				date = parser.parse( fecha );
			} catch (ParseException e) {
				logger.error("Error parsing fecha de Nacimiento:" + fecha + " para " + diputado);						
			}
		}
		return date;
	}
}
