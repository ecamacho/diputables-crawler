/**
 * 
 */
package com.tidyslice.dipcrawler.services.parser.dom;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.tidyslice.dipcrawler.domain.ComisionIniciativa;

/**
 * @author erick
 *
 */
public class IniciativasParserTest {
	
	
	private IniciativasParser iniciativasParser;
	
	private final String casoSimple = "<TD align=\"center\" class=\"Estilo69\" valign=\"top\" width=\"100\"><SPAN class=\"Estilo71\">Fecha de presentación:<B>"+ "9-Febrero-2010</B><BR/></SPAN><BR/>"+
	"			    			    				  				<B>- Hacienda y Crédito Público</B><BR/>"+
	""+			    
	"                </TD>";
	
	
	private final String casoUnidas = "<TD align=\"center\" class=\"Estilo69\" valign=\"top\" width=\"100\"><SPAN class=\"Estilo71\">Fecha de presentación:<B>" + "1-Diciembre-2009</B><BR/></SPAN><BR/>"+
	"			    			    				Unidas 				<B>- Justicia</B><BR/>"+
	"			    				<B>- Seguridad Pública</B><BR/>"+	
	""+			    
	"                </TD>";
	
	private final String casoUnidasYOpiniones = "<TD align=\"center\" class=\"Estilo69\" valign=\"top\" width=\"100\"><SPAN class=\"Estilo71\">Fecha de presentación:<B>" + "1-Diciembre-2009</B><BR/></SPAN><BR/>"+
	"			    			    				Unidas 				<B>- Justicia</B><BR/>"+
	"			    				<B>- Seguridad Pública</B><BR/>"+
	"			    				Con Opinión de 				<B>- Atención a Grupos Vulnerables</B><BR/>"+
	"			    				<B>- Presupuesto y Cuenta Pública</B><BR/>"+
	""+			    
	"                </TD>";
	
	private final String casoOpiniones = "<TD align=\"center\" class=\"Estilo69\" valign=\"top\" width=\"100\"><SPAN class=\"Estilo71\">Fecha de presentación:<B> 15-Octubre-2009</B><BR/></SPAN><BR/>"+
	"			    			    				Con Opinión de 				<B>- Educación Pública y Servicios Educativos</B><BR/>"+
	 " 				<B>- Puntos Constitucionales</B><BR/>"+
""+
"                </TD>";
	
	private final String casoUnidasYOpinion = "<TD align=\"center\" class=\"Estilo69\" valign=\"top\" width=\"100\"><SPAN class=\"Estilo71\">Fecha de presentación:<B>"+ "13-Abril-2010</B><BR/></SPAN><BR/>"+
	"			    			    				Unidas 				<B>- Puntos Constitucionales</B><BR/>"+
	"			    				<B>- Gobernación</B><BR/>"+
	"			    				Con Opinión de 				<B>- Presupuesto y Cuenta Pública</B><BR/>"+
	"			    "+
	"                </TD>";
	@Before
	public void setup() {
		iniciativasParser = new IniciativasParser();
	}
	
	@Test
	public void testParseCasoSimple( ) {
		List<ComisionIniciativa> comisiones = iniciativasParser.parseComisiones( casoSimple, "id" );
		Assert.assertEquals( 1, comisiones.size());
		Assert.assertEquals("Hacienda y Crédito Público", comisiones.get(0).getComision());
	}
	
	@Test
	public void testCasoUnidas( ) {
		
		List<ComisionIniciativa> comisiones = iniciativasParser.parseComisiones( casoUnidas, "id" );
		System.out.println("testCasoUnidas " + comisiones);
		Assert.assertEquals( 2, comisiones.size());
		Assert.assertEquals("Justicia", comisiones.get(0).getComision());
		Assert.assertEquals("Seguridad Pública", comisiones.get(1).getComision());
	}
	
	@Test
	public void testCasoUnidasYOpiniones( ) {
		System.out.println("testCasoUnidasYOpiniones");
		List<ComisionIniciativa> comisiones = iniciativasParser.parseComisiones( casoUnidasYOpiniones, "id" );
		Assert.assertEquals( 4, comisiones.size());
		Assert.assertEquals("Justicia", comisiones.get(0).getComision());
		Assert.assertEquals(1, comisiones.get(0).getTipo());
		Assert.assertEquals("Seguridad Pública", comisiones.get(1).getComision());
		Assert.assertEquals(1, comisiones.get(1).getTipo());
		Assert.assertEquals("Atención a Grupos Vulnerables", comisiones.get(2).getComision());
		Assert.assertEquals(2, comisiones.get(2).getTipo());
		Assert.assertEquals("Presupuesto y Cuenta Pública", comisiones.get(3).getComision());
		Assert.assertEquals(2, comisiones.get(3).getTipo());
	}
	
	@Test
	public void testCasoUnidasYOpinion( ) {
		System.out.println("testCasoUnidasYOpinion");
		List<ComisionIniciativa> comisiones = iniciativasParser.parseComisiones( casoUnidasYOpinion, "id" );
		Assert.assertEquals( 3, comisiones.size());
		Assert.assertEquals("Puntos Constitucionales", comisiones.get(0).getComision());
		Assert.assertEquals(1, comisiones.get(0).getTipo());
		Assert.assertEquals("Gobernación", comisiones.get(1).getComision());
		Assert.assertEquals(1, comisiones.get(1).getTipo());
		Assert.assertEquals("Presupuesto y Cuenta Pública", comisiones.get(2).getComision());
		Assert.assertEquals(2, comisiones.get(2).getTipo());
		
	}
	
	@Test
	public void testCasoOpiniones( ) {
		
		List<ComisionIniciativa> comisiones = iniciativasParser.parseComisiones( casoOpiniones, "id" );
		System.out.println("testCasoOpiniones " + comisiones);
		Assert.assertEquals( 2, comisiones.size());
		Assert.assertEquals("Educación Pública y Servicios Educativos", comisiones.get(0).getComision());
		Assert.assertEquals(2, comisiones.get(0).getTipo());
		Assert.assertEquals("Puntos Constitucionales", comisiones.get(1).getComision());
		Assert.assertEquals(2, comisiones.get(1).getTipo());
		
		
	}
	
	

}
