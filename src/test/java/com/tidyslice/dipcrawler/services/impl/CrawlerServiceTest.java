package com.tidyslice.dipcrawler.services.impl;

import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tidyslice.dipcrawler.domain.Diputado;
import com.tidyslice.dipcrawler.services.CrawlerService;

@ContextConfiguration("/spring/test/test-application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CrawlerServiceTest {
	
	private static final Logger logger = Logger.getLogger( CrawlerServiceTest.class );
	
	@Autowired
	private CrawlerService crawlerService;
	
	@Test
	public void testConfiguration()
	{
		
		Assert.assertNotNull( crawlerService.getMainUrl() );
		logger.debug( crawlerService.getMainUrl() );
	}
	
	@Test
	public void testBiopicCrawler( )
	{
		List<Diputado> diputados = crawlerService.crawlBiopics();
		Assert.assertEquals( 500, diputados.size() );
	}
	
	@Test
	public void testDigestSha256( )
	{
		String expected = "d6286f4ff31235e4a397b323789ddffa58c1d22a44d778cd2718af40eeeec0";
						  
		String mensaje = "AysaBernatJoseAntonio";
		Assert.assertEquals( expected, crawlerService.digestSha256( mensaje ) );
	}
}
