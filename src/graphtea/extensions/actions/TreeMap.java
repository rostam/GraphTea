package graphtea.extensions.actions;

import graphtea.extensions.algorithms.Cluster;
import graphtea.extensions.algorithms.LloydKMeans;
import graphtea.extensions.gui.TMSettingContainer;
import graphtea.extensions.gui.TreeMapDialog;
import graphtea.extensions.io.MapFileReader;
import graphtea.graph.graph.*;
import graphtea.library.BaseVertex;
import graphtea.plugins.algorithmanimator.core.atoms.NewGraph;
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

		/*
		 * I DONT KNOW
		 */
		Vertex.addGlobalUserDefinedAttribute(CURVE_WIDTH, 1);

		// Lesen des Graphen aus XML
		MapFileReader mapFileReader = new MapFileReader("./maps/"
				+ tmSettingContainer.getMap() + "/map.xml");
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

					newGraph.addEdge(new Edge(points[i], points[i + 1]));
				}
			newGraph.addEdge(new Edge(edge.source, points[0]));
			newGraph.addEdge(new Edge(points[points.length - 1], edge.target));

		}

		// rearrange Vertexes
		{
			ArrayList<GraphPoint> p = new ArrayList<GraphPoint>();

			for (Vertex v : newGraph.getVertexArray()) {
				if (v.getLabel().equals("noch zu Relokalisieren"))
					p.add(v.getLocation());
			}
			Cluster[] c = LloydKMeans.cluster(
					p.toArray(new GraphPoint[p.size()]),
					tmSettingContainer.getK());
			for (Cluster cluster : c) {
				for (GraphPoint pointC : cluster.getMembers()) {
					for (int i = 0; i < newGraph.getVerticesCount(); i++) {
						if (newGraph.getVertex(i).getLocation().equals(pointC)
								& newGraph.getVertex(i).getLabel()
										.equals("noch zu Relokalisieren")) {

							newGraph.getVertex(i).setLabel("");
							newGraph.getVertex(i).setLocation(
									cluster.getCentroid());
							break;
						}
					}
				}
			}
		}

        // Erste Knot aendern
        Vertex zero=newGraph.getVertex(0);
        Vertex neu = new Vertex();
        GraphPoint gp = zero.getLocation();
        neu.setLocation(GraphPoint.add(gp,new GraphPoint(5,5)));
        newGraph.addVertex(neu);

        Vector<Integer> vv = new Vector<Integer>();

        for(Vertex nv : newGraph.getNeighbors(zero)) {
            if(vv.contains(nv.getId())) break;
            vv.add(nv.getId());
        }
        for(Integer nv : vv) {
            newGraph.addEdge(new Edge(newGraph.getVertex(nv),neu));
            System.out.println("after add edge" + nv);
            newGraph.removeEdge(newGraph.getEdge(zero,newGraph.getVertex(nv)));
        }

        newGraph.addEdge(new Edge(zero,neu));

		// Zeichnen des Graphen
        graphData.core.showGraph(newGraph);
        TreeMapPainter p = new TreeMapPainter(graphData);
		AbstractGraphRenderer gr = AbstractGraphRenderer
				.getCurrentGraphRenderer(graphData.getBlackboard());
		// Setzen des Hintergrundbilds
		File file = new File("./maps/" + tmSettingContainer.getMap()
				+ "/background.png");
		newGraph.setBackgroundImageFile(file);

        for(Edge e:newGraph.edges()) {
            e.setColor(Color.white.getRGB());
        }
		gr.addPostPaintHandler(p);
		gr.repaint();

	}

	private Vertex[] splitLine(Vertex source, Vertex target, int i) {

		GraphPoint supportVector = source.getLocation();// as p
		GraphPoint directionVector = GraphPoint.sub(target.getLocation(),
				source.getLocation());// as u
		// x = p+t*u | 0<t<1
		// x = p*(t/i)*u | 0<t<i (Gerade durch 2 Punkte)
		Vertex[] points = new Vertex[i - 1];
		for (int t = 1; t < i; t++) {
			Vertex v = new Vertex();
			v.setLabel("noch zu Relokalisieren");
			v.setLocation(GraphPoint.add(supportVector,
					GraphPoint.mul(directionVector, ((double) t) / i)));
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
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		final int n = G.getVerticesCount();
		if (n == 0)
			return;

		AbstractGraphRenderer.getCurrentGraphRenderer(gd.getBlackboard())
				.ignoreRepaints(new Runnable() {
					@SuppressWarnings({ "unchecked" })
					public void run() {
						// boolean[] marks = new boolean[n];
						Vertex V[] = G.getVertexArray();
						final Vertex parent[] = new Vertex[n];
						// consider the hole structure as a tree
						AlgorithmUtils.BFSrun(G, V[0],
								new AlgorithmUtils.BFSListener() {
									@Override
									public void visit(BaseVertex v, BaseVertex p) {
										parent[v.getId()] = (Vertex) p;
									}
								});
						final int numChild[] = new int[n];
						for (int nc = 0; nc < numChild.length; nc++)
							numChild[nc] = 0;

						for (Vertex v : G) {
							if (G.getDegree(v) != 1)
								continue;
							Vertex par = v;
							do {
								int numN = G.getDegree(par) - 1;
								par = parent[par.getId()];
								if (par == null)
									break;
								numChild[par.getId()] += numN
										+ G.getDegree(par);
							} while (par != null);
						}

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

								GraphPoint m1 = AlgorithmUtils.getMiddlePoint(
										p1, p2);
								GraphPoint m2 = AlgorithmUtils.getMiddlePoint(
										p2, p3);
								GraphPoint cp = p2;

								// Integer w1 = numChild[v.getId()]/2;
								Integer w1 = (Integer) v.getUserDefinedAttribute(WineGraph.CURVE_WIDTH);
								// Integer w2 = numChild[v1.getId()]/2;
								Integer w2 = (Integer) v1
										.getUserDefinedAttribute(WineGraph.CURVE_WIDTH);
								// Integer w3 = numChild[v2.getId()]/2;
								Integer w3 = (Integer) v2
										.getUserDefinedAttribute(WineGraph.CURVE_WIDTH);

								int startWidth = (w1 + w2) / 2;
								int endWidth = (w3 + w2) / 2;
								int middleWidth = w2;

								double teta1 = AlgorithmUtils.getAngle(p1, p2);
								double teta2 = AlgorithmUtils.getAngle(p1, p3);
								double teta3 = AlgorithmUtils.getAngle(p2, p3);

								// generate boundary curves
								java.awt.geom.QuadCurve2D c1 = new QuadCurve2D.Double(
										m1.x - startWidth * Math.sin(teta1),
										m1.y + startWidth * Math.cos(teta1),
										cp.x - middleWidth * Math.sin(teta2),
										cp.y + middleWidth * Math.cos(teta2),
										m2.x - endWidth * Math.sin(teta3), m2.y
												+ endWidth * Math.cos(teta3));

								java.awt.geom.QuadCurve2D c2 = new QuadCurve2D.Double(
										m2.x + endWidth * Math.sin(teta3), m2.y
												- endWidth * Math.cos(teta3),
										cp.x + middleWidth * Math.sin(teta2),
										cp.y - middleWidth * Math.cos(teta2),
										m1.x + startWidth * Math.sin(teta1),
										m1.y - startWidth * Math.cos(teta1));

								// mix them
								GeneralPath gp = new GeneralPath(c1);
								gp.append(c2, true);
								gp.closePath();
								gr.setColor(new Color(0, 0, 0));

								// fill the curve
								gr.fill(gp);

								// double c11 = m1.x - startWidth *
								// Math.sin(teta1);
								// double c22 = m1.y + startWidth *
								// Math.cos(teta1);

								// if(G.getInDegree(v) + G.getOutDegree(v) == 2)
								// {
								// gr.setColor(Color.gray);
								// gr.fillOval((int)c11-15,(int)c22-15,30,30);
								// }

							}
						}
					}
				}, false /* dont repaint after */);
	}
}
