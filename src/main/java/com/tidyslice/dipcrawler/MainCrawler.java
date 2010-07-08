/**
 * 
 */
package com.tidyslice.dipcrawler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tidyslice.dipcrawler.services.CrawlerService;

/**
 * @author erick
 *
 */
public class MainCrawler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext(  
				new String[]{ "classpath:/spring/data-infrastructure-application-context.xml",
							  "classpath:/spring/application-context.xml"} 
				);
		
		CrawlerService service = context.getBean( CrawlerService.class );
		service.crawlBiopics();
		
		
	}

}
