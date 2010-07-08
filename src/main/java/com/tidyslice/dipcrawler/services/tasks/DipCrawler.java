/**
 * 
 */
package com.tidyslice.dipcrawler.services.tasks;

import com.tidyslice.dipcrawler.domain.Diputado;

/**
 * @author erick
 *
 */
public interface DipCrawler extends Runnable{

	void setDiputado( Diputado diputado );
	
	void crawlBiopic();
	
}
