/**
 * 
 */
package com.tidyslice.dipcrawler.services.parser.dom;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tidyslice.dipcrawler.domain.Diputado;
import com.tidyslice.dipcrawler.services.parser.DipParser;
import com.tidyslice.dipcrawler.util.ParserUtil;

/**
 * @author erick
 * 
 */
public class DiputadosDomParser implements DipParser<List<Diputado>> {

	private static final Logger logger = Logger
			.getLogger(DiputadosDomParser.class);

	@Value("#{ crawlerProperties['biopic.base.diputados.url'] }")
	private String baseBiopicUrl;

	@Override
	public List<Diputado> parseObject(Document doc, Object... args) {
		logger.debug( "parsing diputados" );
		List<Diputado> diputados = new ArrayList<Diputado>();
		

		if (doc != null) {
			NodeList rows = getDiputadosTable(doc);
			int rowCounter = 0;
			for (int i = 0; i < rows.getLength(); i++) {
				Node node = rows.item(i);
				if (node.getNodeName().equals("TR")) {
					rowCounter++;
					if (rowCounter >= 3) {
						NodeList cols = node.getChildNodes();
						int colCounter = 0;
						Diputado diputado = new Diputado();
						for (int j = 0; j < cols.getLength(); j++) {
							if ("TD".equals(cols.item(j).getNodeName())) {
								colCounter++;
								String text = cols.item(j).getTextContent();
								switch (colCounter) {
								case 1:
									diputado.setNombre(ParserUtil.trimInitialDigits(text));
									diputado.setBiopicUrl(getUrl(cols.item(j)
											.getChildNodes()));
									break;
								case 2:
									diputado.setEntidad(text);
									break;
								case 3:
									diputado.setDistrito(text);
									break;

								}
							}
						}

						if (diputado.getEntidad() != null
								&& !"Entidad".equals(diputado.getEntidad())) {

							diputados.add(diputado);

						}
					}
				}

			}

		}

		return diputados;
	}

	

	private NodeList getDiputadosTable(Document doc) {
		NodeList list = doc.getElementsByTagName("table");
		Node tableNode = list.item(2);
		Node dipTable = null;
		for (int i = 0; i < tableNode.getChildNodes().getLength(); i++) {
			if (tableNode.getChildNodes().item(i).getNodeName().equals("TBODY")) {
				dipTable = tableNode.getChildNodes().item(i);
			}
		}
		return dipTable.getChildNodes();

	}

	

	private String getUrl(final NodeList nodes) {
		String url = null;
		for (int i = 0; i < nodes.getLength(); i++) {
			if ("A".equals(nodes.item(i).getNodeName())) {
				NamedNodeMap attrs = nodes.item(i).getAttributes();
				url = baseBiopicUrl + attrs.getNamedItem("href").getNodeValue();
			}
		}
		return url;
	}

}
