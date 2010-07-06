/**
 * 
 */
package com.tidyslice.dipcrawler.util;

/**
 * @author erick
 *
 */
public class ParserUtil {
	
	public static String trimInitialDigits(final String name) {
		StringBuilder parsedName = new StringBuilder();
		if (name != null) {

			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);
				if (!Character.isDigit(c)) {
					parsedName.append(c);
				}
			}
		}
		return parsedName.toString().trim();
	}

}
