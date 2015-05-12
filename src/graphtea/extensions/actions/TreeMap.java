package graphtea.extensions.actions;

import graphtea.extensions.gui.TMSettingContainer;
import graphtea.extensions.gui.TreeMapDialog;
import graphtea.extensions.io.LoadSimpleGraph;
import graphtea.extensions.io.MapFileReader;
import graphtea.extensions.io.SaveSimpleGraph;
import graphtea.graph.graph.*;
import graphtea.graph.old.GStroke;
import graphtea.library.BaseVertex;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.main.extension.GraphActionExtension;
import graphtea.plugins.main.saveload.core.GraphIOException;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This Action relocate Vertexes to a geographic location. 
 * @author Sebastian Glass
 * @since 03.05.2015
 * 
 */
public class TreeMap implements GraphActionExtension {

	static final String CURVE_WIDTH = "Curve Width";

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
		TMSettingContainer sc = TreeMapDialog.showDialog();
		if (sc == null) {
			// Cancel of TreeMapDialog
			return;
		}
		Vertex.addGlobalUserDefinedAttribute(CURVE_WIDTH, 1);
		GraphModel g1 = graphData.getGraph();

		GraphModel g2;
		try {
			g2 = relocate(g1, sc);
		} catch (MappingException e) {
			e.printStackTrace();
			return;
		}
		graphData.core.showGraph(g2);
		TreeMapPainter p = new TreeMapPainter(graphData);
		AbstractGraphRenderer gr = AbstractGraphRenderer
				.getCurrentGraphRenderer(graphData.getBlackboard());
		File file = new File("./maps/" + sc.getMap() + "/background.png");
		try {
			gr.setBackgroundImage(ImageIO.read(file));
		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("MapImage not found at File '"
					+ file.getAbsolutePath() + "'.");
			return;
		}

		gr.addPostPaintHandler(p);
		gr.repaint();

	}

	private GraphModel relocate(GraphModel g1, TMSettingContainer sc)
			throws MappingException {
		GraphModel g2 = null;
		SaveSimpleGraph ssg = new SaveSimpleGraph();
		LoadSimpleGraph lsg = new LoadSimpleGraph();
		try {
			ssg.write(new File("./tmpg1"), g1);
			g2 = lsg.read(new File("./tmpg1"));
		} catch (GraphIOException e) {
			e.printStackTrace();
		}

		g2.setFont(new Font(g2.getFont().getName(), g2.getFont().getStyle(), 0));
		g2.setLabel("TreeMapG0");

		MapFileReader mfr = new MapFileReader("./maps/" + sc.getMap() + "/map.data");
		for (Vertex v : g2) {
			// Relocate Edge by 'map.data'
			GraphPoint p = null;
			switch (sc.getMappingCode()) {
			case 1:
				p = mfr.getPositionByLabel(v.getLabel());
				if (p == null)
					throw new MappingException("Label not found: "
							+ v.getLabel());

				break;
			case 2:
				p = mfr.getPositionByID(v.getId());
				if (p == null)
					throw new MappingException("ID not found: " + v.getId());

				break;
			default:
				break;
			}

			v.setLocation(p);
			v.setSize(new GraphPoint(15, 15));
		}

		for (Edge e : g2.getEdges()) {

			e.setStroke(GStroke.dashed_dotted);
			e.setColor(8);
		}
		return g2;
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
					@SuppressWarnings({ "unchecked"})
					public void run() {
						//boolean[] marks = new boolean[n];
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
