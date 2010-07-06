/**
 * 
 */
package com.tidyslice.dipcrawler.services.tasks;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.cyberneko.html.parsers.DOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.xml.sax.SAXException;

import com.tidyslice.dipcrawler.annotation.OpenSession;
import com.tidyslice.dipcrawler.data.DiputadoDao;
import com.tidyslice.dipcrawler.domain.Diputado;
import com.tidyslice.dipcrawler.domain.Iniciativa;
import com.tidyslice.dipcrawler.services.crawlers.ActivitiyCrawler;
import com.tidyslice.dipcrawler.services.parser.DipParser;

/**
 * @author erick
 * Task that parses the biopic of a single Diputado
 */
public class CrawlBiopicTask implements DipCrawler {

	private static final Logger logger = Logger.getLogger( CrawlBiopicTask.class );
	
	private DiputadoDao diputadoDao;
	
	private Diputado diputado;
	
	@Autowired
	private ActivitiyCrawler<List<Iniciativa>, Diputado> iniciativasCrawler;
	
	private DipParser<Diputado> parser;
	
	public CrawlBiopicTask( DiputadoDao diputadoDao, DipParser<Diputado> parser )
	{		
		this.diputadoDao = diputadoDao;
		this.parser = parser;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	@OpenSession
	@Transactional
	public void run() {
		Assert.notNull( diputado );
		logger.debug("[crawling biopic] " + diputado.getNombre());
		crawlBiopic();
		diputadoDao.persist( diputado );

	}
	
	private void crawlBiopic()
	{
		DOMParser domParser = new DOMParser();
		try {
			logger.debug( "[crawling biopic for ]" + diputado );
			domParser.parse( diputado.getBiopicUrl() );
			if( domParser.getDocument() != null )
			{
				diputado = parser.parseObject(domParser.getDocument(), diputado);
				
				iniciativasCrawler.crawl( diputado );
			}
		} catch (SAXException e) {
			logger.error( "[Error obteniendo el biopic del diputado] " + diputado, e );
			throw new RuntimeException(e); 
		} catch (IOException e) {
			logger.error( "[Error obteniendo el biopic del diputado] " + diputado, e );
			throw new RuntimeException(e);
		}
	}


	public Diputado getDiputado() {
		return diputado;
	}


	public void setDiputado(Diputado diputado) {
		this.diputado = diputado;
	}
	
	
	
	
	
	
}
