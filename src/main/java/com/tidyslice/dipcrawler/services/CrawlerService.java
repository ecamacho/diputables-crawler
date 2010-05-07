package com.tidyslice.dipcrawler.services;

import java.util.List;

import com.tidyslice.dipcrawler.domain.Diputado;

public interface CrawlerService {
	
	String getMainUrl( );
	
	List<Diputado> crawlBiopics();
	
	String digestSha256( String mensaje );
	
}
