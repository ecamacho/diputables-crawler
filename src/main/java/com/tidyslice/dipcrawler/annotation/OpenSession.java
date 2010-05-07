/**
 * 
 */
package com.tidyslice.dipcrawler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author erick
 * Custom annotation to indicate that a given method needs a 
 * Hibernate Session. 
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface OpenSession {

}
