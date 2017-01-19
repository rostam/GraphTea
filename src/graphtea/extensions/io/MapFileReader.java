package graphtea.extensions.io;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @author Sebastian Glass
 * @since 03.05.2015
 *
 */

public class MapFileReader {

	private GraphModel graph;
	private String background;

	public MapFileReader(String path){
		 try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			    DocumentBuilder builder = factory.newDocumentBuilder();
			    Document document = builder.parse( new File(path.replace(".data", ".xml")) );
			    NodeList nList = document.getElementsByTagName("map");
			    background =  ((Element)nList.item(0)).getElementsByTagName("File").item(0).getTextContent();
			    nList = document.getElementsByTagName("vertex");
			    GraphModel g2 = new GraphModel(false);
			    g2.setAllowLoops(true);
			    Vertex root = new Vertex();
			    root.setLocation(new GPoint(0,0));
			    g2.addVertex(root);
			    for (int temp = 0; temp < nList.getLength(); temp++) {
			    	 
					Node nNode = nList.item(temp);			
					
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						boolean isRoot= eElement.getAttribute("type").equals("root");
						String id = eElement.getAttribute("id");
						int x =  Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent());
						int y =  Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent());
						int value;
						Vertex newVertex = new Vertex();
						newVertex.setLocation(new GPoint(x, y));
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

	public String getBackground() {
		return background;
	}

	
}
