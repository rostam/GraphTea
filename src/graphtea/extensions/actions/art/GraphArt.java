package graphtea.extensions.actions.art;

import graphtea.graph.graph.*;
import graphtea.graph.old.GStroke;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.QuadCurve2D;

/**
 * @author M. Ali Rostami
 */
public class GraphArt implements GraphActionExtension {
    public static final String CURVE_WIDTH = "Curve Width";
    @Override
    public String getName() {
        return "GraphTea Art";
    }

    @Override
    public String getDescription() {
        return "GraphTea Art";
    }

    @Override
    public void action(GraphData graphData) {
        Vertex.addGlobalUserDefinedAttribute(CURVE_WIDTH,1);

        GraphModel g1 = graphData.getGraph();
        GraphModel g2 = g1.getCopy();
        g2.setFont(new Font(g2.getFont().getName(),g2.getFont().getStyle(), 0));
        g2.setLabel("TreeG0");

        for(Vertex v : g2)
            v.setSize(new GPoint(15,15));
        for(Edge e : g2.getEdges()) {
            e.setStroke(GStroke.dashed_dotted);
            e.setColor(8);
        }

        graphData.core.showGraph(g2);
        AbstractGraphRenderer gr = AbstractGraphRenderer.getCurrentGraphRenderer(graphData.getBlackboard());
        gr.addPostPaintHandler(new Painter(graphData));
        gr.repaint();
    }

    @Override
    public String getCategory() {
        return "Graph-based Visualization";
    }
}


class Painter implements PaintHandler {
    GraphData gd;
    GraphModel G;
    public Painter(GraphData gd) {
        this.gd = gd;
        this.G  = gd.getGraph();
    }

    public void paint(Graphics gr1d, Object destinationComponent, Boolean b) {
        final Graphics2D gr = (Graphics2D) gr1d;
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final int n = G.getVerticesCount();
        if (n == 0) return;

        AbstractGraphRenderer.getCurrentGraphRenderer(gd.getBlackboard()).ignoreRepaints(() -> {
            Vertex V[] = G.getVertexArray();
            final Vertex parent[] = new Vertex[n];
            //consider the hole structure as a tree
            AlgorithmUtils.BFSrun(G, V[0], (v, p) -> parent[v.getId()] = p);
            final int numChild[] = new int[n];
            for(int nc = 0;nc < numChild.length;nc++) numChild[nc]=0;


            for(Vertex v:G) {
                if(G.getDegree(v) != 1) continue;
                Vertex par = v;
                do {
                    int numN=G.getDegree(par)-1;
                    par = parent[par.getId()];
                    if(par == null) break;
                    numChild[par.getId()]
                            += numN  + G.getDegree(par);
                } while(true);
            }


            for (Vertex v : G) {
                if (v.getId() == 0) continue;
                if (v.getColor() == 0) {
                    Vertex v1 = parent[v.getId()];
                    if (v1 == null || v1.getColor() != 0) continue;

                    Vertex v2 = parent[v1.getId()];
                    if (v2 == null || v2.getColor() != 0) continue;

                    //generate the curve between v1, v2 and v3
                    GPoint p1 = v.getLocation();
                    GPoint p2 = v1.getLocation();
                    GPoint p3 = v2.getLocation();

                    GPoint m1 = AlgorithmUtils.getMiddlePoint(p1, p2);
                    GPoint m2 = AlgorithmUtils.getMiddlePoint(p2, p3);
                    GPoint cp = p2;

//                        Integer w1 = numChild[v.getId()]/2;
                    Integer w1 = v.getUserDefinedAttribute(GraphArt.CURVE_WIDTH);
//                        Integer w2 = numChild[v1.getId()]/2;
                    Integer w2 = v1.getUserDefinedAttribute(GraphArt.CURVE_WIDTH);
//                        Integer w3 = numChild[v2.getId()]/2;
                    Integer w3 = v2.getUserDefinedAttribute(GraphArt.CURVE_WIDTH);

                    int startWidth = (w1 + w2) / 2;
                    int endWidth = (w3 + w2) / 2;
                    int middleWidth = w2;

                    double teta1 = AlgorithmUtils.getAngle(p1, p2);
                    double teta2 = AlgorithmUtils.getAngle(p1, p3);
                    double teta3 = AlgorithmUtils.getAngle(p2, p3);

                    //generate boundary curves
                    QuadCurve2D c1 = new QuadCurve2D.Double(
                            m1.x - startWidth * Math.sin(teta1), m1.y + startWidth * Math.cos(teta1),
                            cp.x - middleWidth * Math.sin(teta2), cp.y + middleWidth * Math.cos(teta2),
                            m2.x - endWidth * Math.sin(teta3), m2.y + endWidth * Math.cos(teta3));

                    QuadCurve2D c2 = new QuadCurve2D.Double(
                            m2.x + endWidth * Math.sin(teta3), m2.y - endWidth * Math.cos(teta3),
                            cp.x + middleWidth * Math.sin(teta2), cp.y - middleWidth * Math.cos(teta2),
                            m1.x + startWidth * Math.sin(teta1), m1.y - startWidth * Math.cos(teta1));

                    //mix them
                    GeneralPath gp = new GeneralPath(c1);
                    gp.append(c2, true);
                    gp.closePath();
                    gr.setColor(new Color(0,0,0));

                    //fill the curve
                    gr.fill(gp);

//                        double c11 =  m1.x - startWidth * Math.sin(teta1);
//                        double c22 =  m1.y + startWidth * Math.cos(teta1);

//                        if(G.getInDegree(v) + G.getOutDegree(v) == 2) {
//                         gr.setColor(Color.gray);
//                         gr.fillOval((int)c11-15,(int)c22-15,30,30);
//                        }


                }
            }
        }, false /* dont repaint after*/);
    }
}