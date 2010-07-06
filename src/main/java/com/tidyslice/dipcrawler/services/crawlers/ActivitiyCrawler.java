/**
 * 
 */
package com.tidyslice.dipcrawler.services.crawlers;

/**
 * @author erick
 *
 */
public interface ActivitiyCrawler<T,E> {
	
	T crawl( E entity );

}
