package graphtea.extensions.actions;

import graphtea.extensions.algorithms.Cluster;
import graphtea.extensions.algorithms.LloydKMeans;
import graphtea.extensions.gui.TMSettingContainer;
import graphtea.extensions.gui.TreeMapDialog;
import graphtea.extensions.io.MapFileReader;
import graphtea.graph.graph.*;
import graphtea.library.BaseVertex;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

/**
 * This Action relocate Vertexes to a geographic location.
 * 
 * @author Sebastian Glass
 * @since 03.05.2015
 * 
 */
public class TreeMap implements GraphActionExtension {

	static final String CURVE_WIDTH = "Curve Width";
	private Iterator<Edge> it;

	@Override
	public String getName() {
		return "TreeMap";
	}

	@Override
	public String getDescription() {
		return "TreeMap";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void action(GraphData graphData) {
		// Abfrage der Einstellungen ueber ein TreeMapDialog(syncroner Aufruf)
		TMSettingContainer tmSettingContainer = TreeMapDialog.showDialog();
		if (tmSettingContainer == null)
			return; // TreeMapDialog wurde abgebrochen; Action wird abgebrochen
		// Lesen des Graphen aus XML
		MapFileReader mapFileReader = new MapFileReader("./maps/" + tmSettingContainer.getMap() + "/map.xml");
		GraphModel newGraph = mapFileReader.getGraph();

		Edge[] edges = new Edge[newGraph.getEdgesCount()];
		it = newGraph.getEdges().iterator();
		for (int i = 0; i < edges.length; i++) {
			edges[i] = it.next();
		}

		for (Edge edge : edges) {
			newGraph.removeEdge(edge);
			Vertex[] points = splitLine(edge.source, edge.target, 2);
			for (Vertex graphPoint : points) {

				newGraph.addVertex(graphPoint);
			}
			if (points.length > 1)
				for (int i = 0; i < points.length; i++) {
					Edge newEdgeK = new Edge(points[i], points[i + 1]);
					newEdgeK.setWeight(edge.getWeight());
					newGraph.addEdge(newEdgeK);
				}
			Edge newEdge1 = new Edge(edge.source, points[0]);
			Edge newEdgeN = new Edge(points[points.length - 1], edge.target);
			newEdge1.setWeight(edge.getWeight());
			newEdgeN.setWeight(edge.getWeight());
			newGraph.addEdge(newEdge1);
			newGraph.addEdge(newEdgeN);

		}

		// rearrange Vertexes

		newGraph = groupHelpVertexes(newGraph, tmSettingContainer.getK());

		normalise(newGraph);
		// Zeichnen des Graphen
		graphData.core.showGraph(newGraph);
		TreeMapPainter p = new TreeMapPainter(graphData);
		AbstractGraphRenderer gr = AbstractGraphRenderer.getCurrentGraphRenderer(graphData.getBlackboard());
		// Setzen des Hintergrundbilds
		File file = new File("./maps/" + tmSettingContainer.getMap() + "/background.png");
		newGraph.setBackgroundImageFile(file);

		for (Edge e : newGraph.edges()) {
			e.setColor(Color.green.getRGB());
		}
		gr.addPostPaintHandler(p);
		gr.repaint();

	}

	private void normalise(GraphModel newGraph) {
		//Veraendere die Gewichte so, dass sie schoen anzeigbar sind
	}

	private GraphModel groupHelpVertexes(GraphModel newGraph, int K) {

		ArrayList<GraphPoint> p = new ArrayList<GraphPoint>();
		for (Vertex v : newGraph.getVertexArray()) {
			// Alle Punkte, die 'noch zu Relokalisieren' sind (Hilfspunkte)
			// werden gesammelt...
			if (v.getLabel().equals("noch zu Relokalisieren"))
				p.add(v.getLocation());
		}
		GraphPoint[] pArray = p.toArray(new GraphPoint[p.size()]);
		// ... und an den KMeans uebergeben....
		Cluster[] c = LloydKMeans.cluster(pArray, K);
		// ... um die Clustermenge zu erhalten.

		for (Cluster cluster : c) {
			// Fuer jeden Cluster wird ein Mittelpunktvertex angelegt...
			Vertex centerVertex = new Vertex();
			centerVertex.setLabel("");
			centerVertex.setLocation(cluster.getCenter());
			newGraph.addVertex(centerVertex);

			for (GraphPoint pointC : cluster.getMembers()) {
				// ... und alle Hilfspunkte des Clusters auf diesen umgelegt...
				for (int i = 0; i < newGraph.getVerticesCount(); i++) {
					if (newGraph.getVertex(i).getLocation().equals(pointC)) {
						// ... indem sie im Graph gefunden werden,...
						Vertex actualVertex = newGraph.getVertex(i);
						Iterator<Vertex> list = newGraph.getNeighbors(actualVertex).iterator();
						Vertex a = list.next();
						Vertex b = list.next();
						// ...ihre Verbindungen mit ihren Nachbarn fuer den
						// Mittelpunktvertex uebernommen werden...
						Edge edge = new Edge(centerVertex, b);
						edge.setWeight(newGraph.getEdge(a, actualVertex).getWeight());

						if (newGraph.isEdge(edge.source, edge.target)) {
							// Sollte eine Kante bereits im Graph vorkommen,
							// werden nur die gewichte erhoeht.
							newGraph.getEdge(edge.source, edge.target).setWeight(
									newGraph.getEdge(edge.source, edge.target).getWeight() + edge.getWeight());
						} else {
							newGraph.addEdge(edge);
						}

						Edge baseEdge = new Edge(a, centerVertex);
						baseEdge.setWeight(newGraph.getEdge(actualVertex, b).getWeight());

						if (newGraph.isEdge(baseEdge.source, baseEdge.target)) {
							// Sollte eine Kante bereits im Graph vorkommen,
							// werden nur die gewichte erhoeht.
							newGraph.getEdge(baseEdge.source, baseEdge.target)
									.setWeight(newGraph.getEdge(baseEdge.source, baseEdge.target).getWeight()
											+ baseEdge.getWeight());
						} else {
							newGraph.addEdge(baseEdge);
						}

						// ... und der alte Vertex mit seinen Kanten entfernt
						// wird.
						newGraph.removeEdge(newGraph.getEdge(a, actualVertex));
						newGraph.removeEdge(newGraph.getEdge(actualVertex, b));
						newGraph.removeVertex(actualVertex);

						i--;// Die Vertexmenge in der for-Schleife wird
							// reduziert.
							// Somit wuerde nicht Vertex_(i+1) sondern
							// Vertex_(i+2)
						// als naechster Vertex betrachtet werden. Um
						// gegenzusteuern i--.
						break;
					}
				}
			}
		}
		return newGraph;
	}

	private Vertex[] splitLine(Vertex source, Vertex target, int i) {

		GraphPoint supportVector = source.getLocation();// as p
		GraphPoint directionVector = GraphPoint.sub(target.getLocation(), source.getLocation());// as
																								// u
		// x = p+t*u | 0<t<1
		// x = p*(t/i)*u | 0<t<i (Gerade durch 2 Punkte)
		Vertex[] points = new Vertex[i - 1];
		for (int t = 1; t < i; t++) {
			Vertex v = new Vertex();
			v.setLabel("noch zu Relokalisieren");
			v.setLocation(GraphPoint.add(supportVector, GraphPoint.mul(directionVector, ((double) t) / i)));
			points[t - 1] = v;
		}

		return points;
	}

}

@SuppressWarnings("rawtypes")
class TreeMapPainter implements PaintHandler {

	private GraphData gd;
	private GraphModel G;

	public TreeMapPainter(GraphData gd) {
		this.gd = gd;
		this.G = gd.getGraph();

	}

	public void paint(Graphics gr1d, Object destinationComponent, Boolean b) {

		final Graphics2D gr = (Graphics2D) gr1d;
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		final int n = G.getVerticesCount();
		if (n == 0)
			return;

	

				AbstractGraphRenderer.getCurrentGraphRenderer(gd.getBlackboard()).ignoreRepaints(new Runnable() {
					@SuppressWarnings({ "unchecked" })
					public void run() {

						// boolean[] marks = new boolean[n];
						Vertex V[] = G.getVertexArray();
						final Vertex parent[] = new Vertex[n];

						// consider the hole structure as a tree
						AlgorithmUtils.BFSrun(G, V[0], new AlgorithmUtils.BFSListener() {
							@Override
							public void visit(BaseVertex v, BaseVertex p) {
								parent[v.getId()] = (Vertex) p;
							}
						});


						for (Vertex v : G) {
							if (v.getId() == 0)
								continue;
							if (v.getColor() == 0) {
								Vertex v1 = parent[v.getId()];
								if (v1 == null || v1.getColor() != 0)
									continue;

								Vertex v2 = parent[v1.getId()];
								if (v2 == null || v2.getColor() != 0)
									continue;

								// generate the curve between v1, v2 and v3
								GraphPoint p1 = v.getLocation();
								GraphPoint p2 = v1.getLocation();
								GraphPoint p3 = v2.getLocation();

								// Integer w1 = numChild[v.getId()]/2;
								Integer w1 = G.getEdge(v, v1).getWeight();// (Integer)
																					// v.getUserDefinedAttribute(WineGraph.CURVE_WIDTH);

								// Integer w2 = numChild[v1.getId()]/2;

								Integer w3 = G.getEdge(v1, v2).getWeight();// (Integer)
																					// v2.getUserDefinedAttribute(WineGraph.CURVE_WIDTH);

								Integer w2 = (w1 + w3) / 2;

								int startWidth = w1;
								int endWidth = w3 ;
								int middleWidth = w2;

								double teta1 = AlgorithmUtils.getAngle(p1, p2);
								double teta2 = AlgorithmUtils.getAngle(p1, p3);
								double teta3 = AlgorithmUtils.getAngle(p2, p3);

								java.awt.geom.QuadCurve2D c1 = new QuadCurve2D.Double(
										p1.x - startWidth * Math.sin(teta1), p1.y + startWidth * Math.cos(teta1),
										p2.x - middleWidth * Math.sin(teta2), p2.y + middleWidth * Math.cos(teta2),
										p3.x - endWidth * Math.sin(teta3), p3.y + endWidth * Math.cos(teta3));

								java.awt.geom.QuadCurve2D c2 = new QuadCurve2D.Double(p3.x + endWidth * Math.sin(teta3),
										p3.y - endWidth * Math.cos(teta3), p2.x + middleWidth * Math.sin(teta2),
										p2.y - middleWidth * Math.cos(teta2), p1.x + startWidth * Math.sin(teta1),
										p1.y - startWidth * Math.cos(teta1));
								GeneralPath gp = new GeneralPath(c1);
								gp.append(c2, true);
								gp.closePath();
								gr.setColor(new Color(0, 0, 0));

								// fill the curve
								gr.fill(gp);
							}
						}
					}
			
		}, false /* dont repaint after */);
	}
}
