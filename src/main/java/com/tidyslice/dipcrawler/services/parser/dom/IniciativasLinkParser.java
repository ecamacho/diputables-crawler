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

import com.tidyslice.dipcrawler.services.parser.DipParser;

/**
 * @author erick
 *
 */
public class IniciativasLinkParser implements DipParser<List<String>> {

	private static final Logger logger = Logger.getLogger( IniciativasLinkParser.class );
	
	@Value( "#{ crawlerProperties['biopic.base.diputados.url'] }" )
	private String mainUrl;
	
	@Override
	public List<String> parseObject(Document doc, Object... args) {
		logger.debug("parsing iniciativas doc");
		List<String> urls = new ArrayList<String>();
		Assert.notNull( doc );
		
		NodeList links = ( (Element) getIniciativasTable( doc ) ).getElementsByTagName("A");
		
		//Existen periodos con iniciativas
		if( links.getLength() > 1 ) {
			for( int i = 1; i < links.getLength(); i++ ) {
				Element link = (Element) links.item( i );
				urls.add( mainUrl + link.getAttribute( "href" ) );
				
			}
			
		}
		
		return urls;
	}
	
	private NodeList getIniciativasTable( Document doc )
	{
		NodeList list = doc.getElementsByTagName("table");
		
		Node tableNode = list.item( 2 );
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

}
