/**
 * 
 */
package com.tidyslice.dipcrawler.services.parser.dom;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tidyslice.dipcrawler.domain.Diputado;
import com.tidyslice.dipcrawler.services.parser.DipParser;

/**
 * @author erick
 *
 */
public class IniciativasLinkParser implements DipParser<List<String>> {

	private static final Logger logger = Logger.getLogger( IniciativasLinkParser.class );
	
	private Diputado diputado;
	
	@Value( "#{ crawlerProperties['biopic.base.diputados.url'] }" )
	private String mainUrl;
	
	@Override
	public List<String> parseObject(Document doc, Object... args) {
		logger.debug("parsing iniciativas doc");
		List<String> urls = new ArrayList<String>();
		Assert.notNull( doc );
		diputado = (Diputado) args[0];
		NodeList iniciativas = getIniciativasTable( doc );
		if( iniciativas != null ) {
			NodeList links = ( (Element) iniciativas ).getElementsByTagName("A");
		
		
		
			if( links.getLength() > 1 ) {
				for( int i = 1; i < links.getLength(); i++ ) {
					Element link = (Element) links.item( i );
					urls.add( mainUrl + link.getAttribute( "href" ) );
					
				}
				
			}
		
		}
		
		return urls;
	}
	
	private NodeList getIniciativasTable( Document doc )
	{
		NodeList list = doc.getElementsByTagName("table");
		
		
		Node tableNode = list.item( 2 );
		Node iniciativasTable = null;
		
		for(int i = 0; i < tableNode.getChildNodes().getLength(); i++ )
		{
			logger.debug( tableNode.getChildNodes().item( i ).getNodeName() );
			if( tableNode.getChildNodes().item( i ).getNodeName().equals( "TBODY" ) )
			{
				iniciativasTable = tableNode.getChildNodes().item( i );												
			}
		}
		
		return iniciativasTable != null ? iniciativasTable.getChildNodes():null;
	}

}
