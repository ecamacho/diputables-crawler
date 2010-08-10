/**
 * 
 */
package com.tidyslice.dipcrawler.services;

import java.util.concurrent.ConcurrentHashMap;

import com.tidyslice.dipcrawler.domain.Partido;

/**
 * @author erick
 *
 */
public interface PartidosService {

	void persistPartidos(ConcurrentHashMap<String, Partido> partidos);

}
