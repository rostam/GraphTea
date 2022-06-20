package graphtea.extensions.actions.art;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.*;
import graphtea.graph.old.GStroke;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.util.Iterator;

/**
 * @author M. Ali Rostami
 */
public class CrossViz implements GraphActionExtension {
    public static final String CURVE_WIDTH = "Curve Width";
    @Override
    public String getName() {
        return "Cross Viz";
    }

    @Override
    public String getDescription() {
        return "Cross Viz";
    }

    @Override
    public void action(GraphData graphData) {
        Vertex.addGlobalUserDefinedAttribute(CURVE_WIDTH,1);

        GraphModel g1 = graphData.getGraph();
        GraphModel g2 = g1.getCopy();
        g2.setFont(new Font(g2.getFont().getName(),g2.getFont().getStyle(), 0));
        g2.setLabel("TreeG0");

//        for(Vertex v : g2)
//            v.setSize(new GPoint(15,15));

        int i = 0;
        for(Edge e : g2.getEdges()) {
//            e.setStroke(new BasicStroke());
//            e.setColor(8);
        }

        graphData.core.showGraph(g2);
        AbstractGraphRenderer gr = AbstractGraphRenderer.getCurrentGraphRenderer(graphData.getBlackboard());
        gr.addPostPaintHandler(new CrossVizPainter(graphData));
        gr.repaint();
    }

    @Override
    public String getCategory() {
        return "Graph-based Visualization";
    }
}


class CrossVizPainter implements PaintHandler {
    GraphData gd;
    GraphModel G;
    public CrossVizPainter(GraphData gd) {
        this.gd = gd;
        this.G  = gd.getGraph();
    }

    public void paint(Graphics gr1d, Object destinationComponent, Boolean b) {
        final Graphics2D gr = (Graphics2D) gr1d;
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final int n = G.getVerticesCount();
        if (n == 0) return;

//        System.out.println();
//        for(Component c : AbstractGraphRenderer.getCurrentGraphRenderer(gd.getBlackboard()).getComponents()) {
//            System.out.println(c.getClass().getName());
//        }
        AbstractGraphRenderer.getCurrentGraphRenderer(gd.getBlackboard()).ignoreRepaints(() -> {
            Vertex[] V = G.getVertexArray();
            Iterator<Edge> ie = G.edgeIterator();

                int i = 0;
                while (ie.hasNext()) {
                    Edge e = ie.next();
                    int x1 = (int) e.source.getLocation().x;
                    int y1 = (int) e.source.getLocation().y;
                    int x2 = (int) e.target.getLocation().x;
                    int y2 = (int) e.target.getLocation().y;
                    Color color = new Color( i * 25,i*25,i*25);
                    i++;
                    gr.setColor(color);
                    gr.drawLine(x1,y1,x2,y2);
//                    Edge e = ie.next();
//                    System.out.println(e);
//                    paint((Graphics2D) gg, e, getGraph(), drawExtras);
                }

            for(Vertex v : G) {
                int x = (int) v.getLocation().x;
                int y = (int) v.getLocation().y;
                gr.fillOval(x-50,y-50, 100, 100);
            }
//            int[][] E = G.getEdgeArray();
//            for(int i=0;i<E.length;i++) {
//                for(int j=0;j<E[i].length;j++) {
//                    if(i > j && E[i][j] != 0) {
//                        Vertex v = G.getVertex(i);
//                        Vertex u = G.getVertex(i);
//                        GPoint gp1 = v.getLocation();
//                        GPoint gp2 = u.getLocation();
//                        Line2D l = new Line2D.Double(gp1.x, gp1.y, gp2.x, gp2.y );
//
//                    }
//                }
//            }
//            for(Vertex v : G) {
//                for(Vertex u : G) {
//                    if(v.getId() > u.getId()) {
//                        if(G.isEdge(G.getVertex(v.getId()), G.getVertex(u.getId()))) {
//
//                            Line2D l1 = new Line2D.Double();
//                            Polygon p = new Polygon();
//                            p.addPoint((int) (gp1.x - 5), (int) (gp1.y - 5));
//                            p.addPoint((int) (gp1.x + 5), (int) (gp1.y + 5));
//                            p.addPoint((int) (gp2.x - 5), (int) (gp2.y - 5));
//                            p.addPoint((int) (gp2.x + 5), (int) (gp2.y + 5));
////                            gr.setStroke(new Bas);
//                            gr.drawPolygon(p);
////                            gr.drawRect((int) gp1.x, (int) gp1.y, (int) gp2.x, (int) gp2.y);
//                        }
//                    }
//                }
//            }
//
//            for (Vertex v : G) {
//                if (v.getId() == 0) continue;
//                if (v.getColor() == 0) {
//                    Vertex v1 = parent[v.getId()];
//                    if (v1 == null || v1.getColor() != 0) continue;
//
//                    Vertex v2 = parent[v1.getId()];
//                    if (v2 == null || v2.getColor() != 0) continue;
//
//                    //generate the curve between v1, v2 and v3
//                    GPoint p1 = v.getLocation();
//                    GPoint p2 = v1.getLocation();
//                    GPoint p3 = v2.getLocation();
//
//                    GPoint m1 = AlgorithmUtils.getMiddlePoint(p1, p2);
//                    GPoint m2 = AlgorithmUtils.getMiddlePoint(p2, p3);
//
////                        Integer w1 = numChild[v.getId()]/2;
//                    Integer w1 = v.getUserDefinedAttribute(GraphArt.CURVE_WIDTH);
////                        Integer w2 = numChild[v1.getId()]/2;
//                    Integer w2 = v1.getUserDefinedAttribute(GraphArt.CURVE_WIDTH);
////                        Integer w3 = numChild[v2.getId()]/2;
//                    Integer w3 = v2.getUserDefinedAttribute(GraphArt.CURVE_WIDTH);
//
//                    int startWidth = (w1 + w2) / 2;
//                    int endWidth = (w3 + w2) / 2;
//                    int middleWidth = w2;
//
//                    double teta1 = AlgorithmUtils.getAngle(p1, p2);
//                    double teta2 = AlgorithmUtils.getAngle(p1, p3);
//                    double teta3 = AlgorithmUtils.getAngle(p2, p3);
//
//                    //generate boundary curves
//                    QuadCurve2D c1 = new QuadCurve2D.Double(
//                            m1.x - startWidth * Math.sin(teta1), m1.y + startWidth * Math.cos(teta1),
//                            p2.x - middleWidth * Math.sin(teta2), p2.y + middleWidth * Math.cos(teta2),
//                            m2.x - endWidth * Math.sin(teta3), m2.y + endWidth * Math.cos(teta3));
//
//                    QuadCurve2D c2 = new QuadCurve2D.Double(
//                            m2.x + endWidth * Math.sin(teta3), m2.y - endWidth * Math.cos(teta3),
//                            p2.x + middleWidth * Math.sin(teta2), p2.y - middleWidth * Math.cos(teta2),
//                            m1.x + startWidth * Math.sin(teta1), m1.y - startWidth * Math.cos(teta1));
//
//                    //mix them
//                    GeneralPath gp = new GeneralPath(c1);
//                    gp.append(c2, true);
//                    gp.closePath();
//                    gr.setColor(new Color(0,0,0));
//
//                    //fill the curve
//                    gr.fill(gp);
//
//
//
////                        double c11 =  m1.x - startWidth * Math.sin(teta1);
////                        double c22 =  m1.y + startWidth * Math.cos(teta1);
//
////                        if(G.getInDegree(v) + G.getOutDegree(v) == 2) {
////                         gr.setColor(Color.gray);
////                         gr.fillOval((int)c11-15,(int)c22-15,30,30);
////                        }
//
//
//                }
//            }
        }, false /* dont repaint after*/);
    }
}