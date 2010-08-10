/**
 * 
 */
package com.tidyslice.dipcrawler.services.tasks;

import java.util.concurrent.ConcurrentHashMap;

import com.tidyslice.dipcrawler.domain.Diputado;
import com.tidyslice.dipcrawler.domain.Partido;

/**
 * @author erick
 *
 */
public interface DipCrawler extends Runnable{

	void setDiputado( Diputado diputado );
	
	void setPartidosMap( ConcurrentHashMap<String, Partido> partidos );
	
	void crawlBiopic();
	
}
