package graphtea.extensions.io;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author Sebastian Glaﬂ
 * @since 03.05.2015
 *
 */

public class MapFileReader {

	private GraphModel graph;

	public MapFileReader(String path){
		 try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			    DocumentBuilder builder = factory.newDocumentBuilder();
			    Document document = builder.parse( new File(path.replace(".data", ".xml")) );
			    NodeList nList = document.getElementsByTagName("vertex");
			    GraphModel g2 = new GraphModel(false);
			    g2.setAllowLoops(true);
			    Vertex root = new Vertex();
			    root.setLocation(new GraphPoint(0,0));
			    g2.addVertex(root);
			    for (int temp = 0; temp < nList.getLength(); temp++) {
			    	 
					Node nNode = nList.item(temp);			
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						boolean isRoot= eElement.getAttribute("type").equals("root");
						String id = eElement.getAttribute("id");
						int x =  Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent());
						int y =  Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent());
						int value=0;
						
						
						Vertex newVertex = new Vertex();
						newVertex.setLocation(new GraphPoint(x, y));
						newVertex.setLabel(id);
						
						if(!isRoot){
							g2.addVertex(newVertex);
							value = Integer.parseInt(eElement.getElementsByTagName("value").item(0).getTextContent());
							Edge e = new Edge(newVertex, root);
							e.setWeight(value);
							g2.addEdge(e);
						}else{
							root.setLocation(newVertex.getLocation());
							root.setLabel(newVertex.getLabel());
						//	root =  newVertex;
						}

					}
			    }
				this.setGraph(g2);

		} catch (DOMException | ParserConfigurationException | SAXException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public GraphModel getGraph() {
		return graph;
	}

	public void setGraph(GraphModel graph) {
		this.graph = graph;
	}

	
}
