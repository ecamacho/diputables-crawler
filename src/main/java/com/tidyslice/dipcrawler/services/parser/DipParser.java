/**
 * 
 */
package com.tidyslice.dipcrawler.services.parser;

import org.w3c.dom.Document;

/**
 * @author erick
 *
 */
public interface DipParser<T> {
	
	
	T parseObject( Document doc, Object... args  );

}
