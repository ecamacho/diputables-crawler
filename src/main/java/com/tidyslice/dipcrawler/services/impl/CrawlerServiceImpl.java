/**
 * 
 */
package com.tidyslice.dipcrawler.services.impl;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.log4j.Logger;
import org.cyberneko.html.parsers.DOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.tidyslice.dipcrawler.domain.Diputado;
import com.tidyslice.dipcrawler.services.CrawlerService;
import com.tidyslice.dipcrawler.services.parser.DipParser;
import com.tidyslice.dipcrawler.services.tasks.DipCrawler;

/**
 * @author erick
 *
 */
@Service("crawlerService")
public class CrawlerServiceImpl implements CrawlerService {

	private static final Logger logger = Logger.getLogger( CrawlerService.class );
	
	@Value( "#{ crawlerProperties['main.diputados.url'] }" )
	private String mainUrl;
		

	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	@Qualifier("diputadosParser")
	private DipParser<List<Diputado>> parser;
	
	@Autowired
	private ApplicationContext context;
	
	@Override
	public String getMainUrl() {

		return mainUrl;
	}

	@Override
	public List<Diputado> crawlBiopics() {
		List<Diputado> diputados = null;
		DOMParser domParser = new DOMParser();
		try {
			domParser.parse( mainUrl );
			Document doc = domParser.getDocument();
			diputados = parser.parseObject(doc);
			for( Diputado diputado : diputados )
			{
				diputado.setUuid( digestSha256( diputado.getNombre() ) );
				DipCrawler task =  context.getBean( DipCrawler.class );
				task.setDiputado( diputado );
				taskExecutor.execute( task );
				
			}
		} catch (SAXException e) {
			logger.error( "[Error leyendo lista de diputados] ", e);
			throw new RuntimeException( e );
		} catch (IOException e) {
			logger.error( "[Error leyendo lista de diputados] ", e);
			throw new RuntimeException( e );
		}
		
		return diputados;
	}
	

	@Override
	public String digestSha256(String mensaje) {
		//Concatenamos una letra arbitraria al inicio para que appengine la inserte bien como un key_name
		StringBuilder uiid = new StringBuilder("u");
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update( mensaje.getBytes() );
			byte[] digest = md.digest();
			for(byte b:digest)
			{
				uiid.append( Integer.toHexString( 0xFF & b ) ); 
			}
						
		} catch (NoSuchAlgorithmException e) {
			logger.error( "[Error creating sha digest] ", e);
			throw new RuntimeException(e); 		
		}
		return uiid.toString();
	}
}
