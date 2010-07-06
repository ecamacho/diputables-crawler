/**
 * 
 */
package com.tidyslice.dipcrawler.services.crawlers;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.cyberneko.html.parsers.DOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.xml.sax.SAXException;

import com.tidyslice.dipcrawler.domain.Diputado;
import com.tidyslice.dipcrawler.domain.Iniciativa;
import com.tidyslice.dipcrawler.services.parser.DipParser;

/**
 * @author erick
 *
 */
public class IniciativasCrawler  implements ActivitiyCrawler<List<Iniciativa>, Diputado> {
	
	private static final Logger logger = Logger.getLogger( IniciativasCrawler.class );

	@Autowired
	@Qualifier("iniciativasLinkParser")
	private DipParser<List<String>> linkParser;
	
	@Autowired
	@Qualifier("iniciativasParser")
	private DipParser<List<Iniciativa>> iniciativaParser;

	public List<Iniciativa> crawl( Diputado diputado ) {
		Assert.notNull( diputado );
		Assert.notNull( diputado.getIniciativasUrl() );
		
		DOMParser domParser = new DOMParser();
		try {
			logger.debug( "[crawling iniciativas ]" + diputado.getIniciativasUrl() );
			domParser.parse( diputado.getIniciativasUrl() );
			if( domParser.getDocument() != null )
			{
				List<String> urls = linkParser.parseObject( domParser.getDocument() );
				for( String url : urls ) {
					logger.debug( url );
					domParser.parse( url );
					if( domParser.getDocument() != null )
					{
						iniciativaParser.parseObject( domParser.getDocument() );
					}
				}
			}
		} catch (SAXException e) {
			logger.error( "[Error obteniendo el biopic del diputado] " + diputado, e );
			throw new RuntimeException(e); 
		} catch (IOException e) {
			logger.error( "[Error obteniendo el biopic del diputado] " + diputado, e );
			throw new RuntimeException(e);
		}
		return null;
	}
	
}
