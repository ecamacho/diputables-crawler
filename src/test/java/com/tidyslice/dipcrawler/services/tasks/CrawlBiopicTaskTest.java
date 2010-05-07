/**
 * 
 */
package com.tidyslice.dipcrawler.services.tasks;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tidyslice.dipcrawler.domain.Diputado;

/**
 * @author erick
 *
 */
@ContextConfiguration("/spring/test/test-application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CrawlBiopicTaskTest {
	
	private static final Logger logger = Logger.getLogger( CrawlBiopicTaskTest.class );
			
	@Autowired
	private CrawlBiopicTask task;
	
	@Before
	public void setup()
	{
		Diputado dip = new Diputado();
		dip.setUuid( "1e95ec448c45ea17f19a7306352c858716042b8bdf66bfb84dc4a55e422982" );
		dip.setNombre( "Aguirre Herrera çngel" );
		dip.setBiopicUrl( "http://sitl.diputados.gob.mx/LXI_leg/curricula.php?dipt=98" );
		task.setDiputado(dip);
	}
	
	@Test
	public void testParseBiopic( )
	{
		task.run();
		
	}

}
