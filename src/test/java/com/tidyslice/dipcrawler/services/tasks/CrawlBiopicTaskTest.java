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
	private DipCrawler task;
	
	@Before
	public void setup()
	{
		Diputado dip = new Diputado();
		//dip.setUuid( "1e95ec448c45ea17f19a7306352c858716042b8bdf66bfb84dc4a55e422982" );
		dip.setUuid( "6f9171bc11bbfcd185fa66c26143a2d19292c06bf662a7c5f973efbcabfa91e0" );
		//dip.setNombre( "Aguirre Herrera çngel" );
		dip.setNombre( "Aguilar G—ngora Efra’n Ernesto" );
		
		//dip.setBiopicUrl( "http://sitl.diputados.gob.mx/LXI_leg/curricula.php?dipt=98" );
		dip.setBiopicUrl( "http://sitl.diputados.gob.mx/LXI_leg/curricula.php?dipt=794" );
		//[asistenciasUrl=null, biopicUrl=http://sitl.diputados.gob.mx/LXI_leg/curricula.php?dipt=794, curul=null, distrito=Dtto.  3, email=null, entidad=Yucat‡n, fechaNacimiento=null, foto=null, iniciativasUrl=null, nombre=Aguilar G—ngora Efra’n Ernesto, partido=null, proposicionesUrl=null, tipoMayoria=null, uuid=6f9171bc11bbfcd185fa66c26143a2d19292c06bf662a7c5f973efbcabfa91e0, votacionesUrl=null]
		task.setDiputado(dip);
		
		
	}
	
	@Test
	public void testParseBiopic( )
	{
		//System.setProperty("http.proxyHost", "myProxyServer.com");
		//System.setProperty("http.proxyPort", "80");
		logger.debug("initializing test");
		task.run();
		
	}



}
