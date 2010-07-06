/**
 * 
 */
package com.tidyslice.dipcrawler.services.parser.dom;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tidyslice.dipcrawler.domain.Iniciativa;
import com.tidyslice.dipcrawler.domain.RolIniciativa;
import com.tidyslice.dipcrawler.services.parser.DipParser;
import com.tidyslice.dipcrawler.util.ParserUtil;

/**
 * @author erick
 *
 */
public class IniciativasParser implements DipParser<List<Iniciativa>> {

	private static final Logger logger = Logger.getLogger( IniciativasParser.class );
	
	@Override
	public List<Iniciativa> parseObject(Document doc, Object... args) {
		
		Assert.notNull( doc );
		logger.debug( "parsing iniciativas for a given period " );
		NodeList rows = ( (Element) getIniciativasTable( doc ) ).getElementsByTagName("TR");
		if( rows.getLength() > 1 ) {
			Iniciativa iniciativa;
			for( int i = 1; i < rows.getLength(); i++ ) {
				NodeList columns = ((Element) rows.item( i )).getElementsByTagName("TD");
				iniciativa = new Iniciativa();
				iniciativa.setTitulo( ParserUtil.trimInitialDigits(columns.item( 0 ).getTextContent() ).trim());
				iniciativa.setRolIniciativa( parseRol( iniciativa.getTitulo() ) );
				logger.debug( iniciativa );
			}
		}
		return null;
	}
	
	private NodeList getIniciativasTable( Document doc )
	{
		NodeList list = doc.getElementsByTagName("table");
		
		Node tableNode = list.item( 1 );
		Node inciativasTable = null;
		
		for(int i = 0; i < tableNode.getChildNodes().getLength(); i++ )
		{					
			if( tableNode.getChildNodes().item( i ).getNodeName().equals( "TBODY" ) )
			{
				inciativasTable = tableNode.getChildNodes().item( i );	
				
			}
		}
		return inciativasTable.getChildNodes();
	}
	
	private RolIniciativa parseRol( String titulo ) {
		RolIniciativa rol = RolIniciativa.ADHERENTE;
		if( titulo.contains( convertToRolLabel( RolIniciativa.ADHERENTE ) ) ) {
			rol = RolIniciativa.ADHERENTE;
		} else if (titulo.contains( convertToRolLabel( RolIniciativa.PROPONENTE ) ) ) {
			rol = RolIniciativa.PROPONENTE;
		} else if (titulo.contains( convertToRolLabel( RolIniciativa.SUSCRIBE ) ) ) {
			rol = RolIniciativa.SUSCRIBE;
		}
		
		return rol;
	}

	private String convertToRolLabel( RolIniciativa rol ) {
		String rolLabel = rol.toString().toLowerCase();
		rolLabel = rolLabel.substring(0,1).toUpperCase() + rolLabel.substring(1);
		return rolLabel;
	}
	
}
