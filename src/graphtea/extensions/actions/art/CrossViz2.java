package graphtea.extensions.actions.art;

import graphtea.graph.graph.AbstractGraphRenderer;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.PaintHandler;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;

/**
 * @author M. Ali Rostami
 */
public class CrossViz2 implements GraphActionExtension {
    public static final String CURVE_WIDTH = "Curve Width";
    @Override
    public String getName() {
        return "Cross Viz 2";
    }

    @Override
    public String getDescription() {
        return "Cross Viz 2";
    }

    @Override
    public void action(GraphData graphData) {
        Vertex.addGlobalUserDefinedAttribute(CURVE_WIDTH,1);

        GraphModel g1 = graphData.getGraph();
        GraphModel g2 = g1.getCopy();
        g2.setFont(new Font(g2.getFont().getName(),g2.getFont().getStyle(), 0));
        g2.setLabel("TreeG0");

        graphData.core.showGraph(g2);
        AbstractGraphRenderer gr = AbstractGraphRenderer.getCurrentGraphRenderer(graphData.getBlackboard());
        gr.addPostPaintHandler(new CrossVizPainter2(graphData));
        gr.repaint();
    }

    @Override
    public String getCategory() {
        return "Graph-based Visualization";
    }
}


class CrossVizPainter2 implements PaintHandler {
    GraphData gd;
    GraphModel G;
    public CrossVizPainter2(GraphData gd) {
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
            Iterator<Edge> ie = G.edgeIterator();
            int i = 0;
            while (ie.hasNext()) {
                Edge e = ie.next();
                int x1 = (int) e.source.getLocation().x;
                int y1 = (int) e.source.getLocation().y;
                int x2 = (int) e.target.getLocation().x;
                int y2 = (int) e.target.getLocation().y;
                Color color = new Color(i * 25, i * 25, i * 25);
                i++;
                gr.setColor(Color.WHITE);
                gr.setStroke(new BasicStroke(10));
                gr.drawLine(x1, y1, x2, y2);
                gr.setColor(color);
                gr.setStroke(new BasicStroke(2));
                gr.drawLine(x1, y1, x2, y2);
            }

            for (Vertex v : G) {
                int x = (int) v.getLocation().x;
                int y = (int) v.getLocation().y;
                gr.fillOval(x - 50, y - 50, 100, 100);
            }
        }, false /* dont repaint after*/);
    }
}