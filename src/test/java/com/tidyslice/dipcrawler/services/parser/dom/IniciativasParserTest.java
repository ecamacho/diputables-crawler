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
	
	private final String casoSimple = "<TD align=\"center\" class=\"Estilo69\" valign=\"top\" width=\"100\"><SPAN class=\"Estilo71\">Fecha de presentaci�n:<B>"+ "9-Febrero-2010</B><BR/></SPAN><BR/>"+
	"			    			    				 �				<B>- Hacienda y Cr�dito P�blico</B><BR/>"+
	""+			    
	"                </TD>";
	
	
	private final String casoUnidas = "<TD align=\"center\" class=\"Estilo69\" valign=\"top\" width=\"100\"><SPAN class=\"Estilo71\">Fecha de presentaci�n:<B>" + "1-Diciembre-2009</B><BR/></SPAN><BR/>"+
	"			    			    				Unidas�				<B>- Justicia</B><BR/>"+
	"			    				<B>- Seguridad P�blica</B><BR/>"+	
	""+			    
	"                </TD>";
	
	private final String casoUnidasYOpiniones = "<TD align=\"center\" class=\"Estilo69\" valign=\"top\" width=\"100\"><SPAN class=\"Estilo71\">Fecha de presentaci�n:<B>" + "1-Diciembre-2009</B><BR/></SPAN><BR/>"+
	"			    			    				Unidas�				<B>- Justicia</B><BR/>"+
	"			    				<B>- Seguridad P�blica</B><BR/>"+
	"			    				Con Opini�n de�				<B>- Atenci�n a Grupos Vulnerables</B><BR/>"+
	"			    				<B>- Presupuesto y Cuenta P�blica</B><BR/>"+
	""+			    
	"                </TD>";
	
	private final String casoOpiniones = "<TD align=\"center\" class=\"Estilo69\" valign=\"top\" width=\"100\"><SPAN class=\"Estilo71\">Fecha de presentaci�n:<B> 15-Octubre-2009</B><BR/></SPAN><BR/>"+
	"			    			    				Con Opini�n de�				<B>- Educaci�n P�blica y Servicios Educativos</B><BR/>"+
	 "�				<B>- Puntos Constitucionales</B><BR/>"+
""+
"                </TD>";
	
	private final String casoUnidasYOpinion = "<TD align=\"center\" class=\"Estilo69\" valign=\"top\" width=\"100\"><SPAN class=\"Estilo71\">Fecha de presentaci�n:<B>"+ "13-Abril-2010</B><BR/></SPAN><BR/>"+
	"			    			    				Unidas�				<B>- Puntos Constitucionales</B><BR/>"+
	"			    				<B>- Gobernaci�n</B><BR/>"+
	"			    				Con Opini�n de�				<B>- Presupuesto y Cuenta P�blica</B><BR/>"+
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
		Assert.assertEquals("Hacienda y Cr�dito P�blico", comisiones.get(0).getComision());
	}
	
	@Test
	public void testCasoUnidas( ) {
		
		List<ComisionIniciativa> comisiones = iniciativasParser.parseComisiones( casoUnidas, "id" );
		System.out.println("testCasoUnidas " + comisiones);
		Assert.assertEquals( 2, comisiones.size());
		Assert.assertEquals("Justicia", comisiones.get(0).getComision());
		Assert.assertEquals("Seguridad P�blica", comisiones.get(1).getComision());
	}
	
	@Test
	public void testCasoUnidasYOpiniones( ) {
		System.out.println("testCasoUnidasYOpiniones");
		List<ComisionIniciativa> comisiones = iniciativasParser.parseComisiones( casoUnidasYOpiniones, "id" );
		Assert.assertEquals( 4, comisiones.size());
		Assert.assertEquals("Justicia", comisiones.get(0).getComision());
		Assert.assertEquals(1, comisiones.get(0).getTipo());
		Assert.assertEquals("Seguridad P�blica", comisiones.get(1).getComision());
		Assert.assertEquals(1, comisiones.get(1).getTipo());
		Assert.assertEquals("Atenci�n a Grupos Vulnerables", comisiones.get(2).getComision());
		Assert.assertEquals(2, comisiones.get(2).getTipo());
		Assert.assertEquals("Presupuesto y Cuenta P�blica", comisiones.get(3).getComision());
		Assert.assertEquals(2, comisiones.get(3).getTipo());
	}
	
	@Test
	public void testCasoUnidasYOpinion( ) {
		System.out.println("testCasoUnidasYOpinion");
		List<ComisionIniciativa> comisiones = iniciativasParser.parseComisiones( casoUnidasYOpinion, "id" );
		Assert.assertEquals( 3, comisiones.size());
		Assert.assertEquals("Puntos Constitucionales", comisiones.get(0).getComision());
		Assert.assertEquals(1, comisiones.get(0).getTipo());
		Assert.assertEquals("Gobernaci�n", comisiones.get(1).getComision());
		Assert.assertEquals(1, comisiones.get(1).getTipo());
		Assert.assertEquals("Presupuesto y Cuenta P�blica", comisiones.get(2).getComision());
		Assert.assertEquals(2, comisiones.get(2).getTipo());
		
	}
	
	@Test
	public void testCasoOpiniones( ) {
		
		List<ComisionIniciativa> comisiones = iniciativasParser.parseComisiones( casoOpiniones, "id" );
		System.out.println("testCasoOpiniones " + comisiones);
		Assert.assertEquals( 2, comisiones.size());
		Assert.assertEquals("Educaci�n P�blica y Servicios Educativos", comisiones.get(0).getComision());
		Assert.assertEquals(2, comisiones.get(0).getTipo());
		Assert.assertEquals("Puntos Constitucionales", comisiones.get(1).getComision());
		Assert.assertEquals(2, comisiones.get(1).getTipo());
		
		
	}
	
	

}
