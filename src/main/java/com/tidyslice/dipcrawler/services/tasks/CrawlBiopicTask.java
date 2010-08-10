/**
 * 
 */
package com.tidyslice.dipcrawler.services.tasks;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.cyberneko.html.parsers.DOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.xml.sax.SAXException;

import com.tidyslice.dipcrawler.annotation.CloseSession;
import com.tidyslice.dipcrawler.annotation.OpenSession;
import com.tidyslice.dipcrawler.data.DiputadoDao;
import com.tidyslice.dipcrawler.domain.Diputado;
import com.tidyslice.dipcrawler.domain.EstatusIniciativa;
import com.tidyslice.dipcrawler.domain.Iniciativa;
import com.tidyslice.dipcrawler.domain.Partido;
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
	
	private ConcurrentHashMap<String, Partido> partidos;
	
	public void setPartidosMap( ConcurrentHashMap<String, Partido> partidos ) {
		this.partidos = partidos;
	}
	
	@Autowired
	private ActivitiyCrawler<List<Iniciativa>, Diputado> iniciativasCrawler;
	
	private DipParser<Diputado> parser;
	
	public CrawlBiopicTask( DiputadoDao diputadoDao, DipParser<Diputado> parser )
	{		
		this.diputadoDao = diputadoDao;
		this.parser 	 = parser;
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
	
	@CloseSession
	public void crawlBiopic()
	{
		DOMParser domParser = new DOMParser();
		try {			
			domParser.parse( diputado.getBiopicUrl() );
			if( domParser.getDocument() != null )
			{
				diputado = parser.parseObject(domParser.getDocument(), diputado);						
				List<Iniciativa> iniciativas = iniciativasCrawler.crawl( diputado );				
				addDataToPartido( iniciativas );
			
			}
		} catch (SAXException e) {
			logger.error( "[Error obteniendo el biopic del diputado] " + diputado, e );
			throw new RuntimeException(e); 
		} catch (IOException e) {
			logger.error( "[Error obteniendo el biopic del diputado] " + diputado, e );
			throw new RuntimeException(e);
		}
	}

	private void addDataToPartido( List<Iniciativa> iniciativas ) {
		Partido partido = partidos.get( diputado.getPartido() );
		if( partido == null ) {
			Partido newPartido = new Partido( );
			newPartido.setNombre( diputado.getPartido() );
			partido = partidos.putIfAbsent( newPartido.getNombre(), newPartido );
			if( partido == null ) {
				partido = newPartido;
			}
		}
		partido.setNumeroDiputados( partido.getNumeroDiputados() + 1 );
		this.addIniciativaData(partido, iniciativas);
		partidos.put( partido.getNombre(), partido);
	}
	
	
	private void addIniciativaData( Partido partido,  List<Iniciativa> iniciativas ) {
		partido.setNumeroIniciativas( partido.getNumeroIniciativas() + iniciativas.size() );
		int pendientes = 0;
		int aprobadas  = 0;
		int desechadas = 0;
		
		for( Iniciativa init : iniciativas ) {
			if( init.getTramite() == EstatusIniciativa.APROBADA ) {
				aprobadas++;				
			}
			else if( init.getTramite() == EstatusIniciativa.PENDIENTE ) {
				pendientes++;				
			}
			else if( init.getTramite() == EstatusIniciativa.DESECHADA ) {
				desechadas++;				
			}
		}	
		partido.setNumeroIniciativasAprobadas( partido.getNumeroIniciativasAprobadas() + aprobadas );
		partido.setNumeroIniciativasPendientes( partido.getNumeroIniciativasPendientes() + pendientes );
		partido.setNumeroIniciativasDesechadas( partido.getNumeroIniciativasDesechadas() + desechadas );
	}

	public Diputado getDiputado() {
		return diputado;
	}


	public void setDiputado(Diputado diputado) {
		this.diputado = diputado;
	}
	
	
	
	
	
	
}
