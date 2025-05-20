// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.ui;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphColoring;
import graphtea.graph.graph.Vertex;
import graphtea.platform.Application;
import graphtea.plugins.main.GraphData;
import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Azin Azadi
 */
public class GraphColoringRenderer implements GBasicCellRenderer<GraphColoring> {
    public Component getRendererComponent(GraphColoring coloring) {
        final GraphColoring myColoring = new GraphColoring(coloring.graph);
        myColoring.vertexColors = new HashMap<>(coloring.vertexColors);
        myColoring.edgeColors = new HashMap<>(coloring.edgeColors);
        myColoring.label = coloring.label;
        String txt = getHTMLFromColoring(myColoring);

        JLabel l = new JLabel(txt){
            @Override
            public void setForeground(Color fg) {
                super.setForeground(fg);
                if (fg== GCellRenderer.SELECTED_COLOR)
                    showOnGraph(myColoring);
            }
        };
        l.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showOnGraph(myColoring);
            }
        });
        return l;
    }

    private static String getHTMLFromColoring(GraphColoring myColoring) {
        StringBuilder txt = new StringBuilder();
        txt = new StringBuilder("<HTML><BODY>");
        if (myColoring.label != null && !myColoring.label.isEmpty()) {
            txt.append("<B>").append(myColoring.label).append(":  </B>");
        }
        if (myColoring.vertexColors != null && !myColoring.vertexColors.isEmpty()) {
            txt.append("<B>Vertex colors: </B> ");
            for (Map.Entry<Vertex, Integer> p : myColoring.vertexColors.entrySet()) {
                txt.append(p.getKey().getLabel()).append(":").append(p.getValue()).append(" , ");
            }
        }
        if (myColoring.edgeColors != null && !myColoring.edgeColors.isEmpty()) {
            txt.append("<br/><B>Edge colors: </B> ");
            for (Map.Entry<Edge, Integer> p : myColoring.edgeColors.entrySet()) {
                txt.append(p.getKey().getLabel()).append(":").append(p.getValue()).append(" , ");
            }
        }
        txt.append("</BODY></HTML>");
        return txt.toString();
    }

    private void showOnGraph(GraphColoring myColoring) {
        GraphData gd = new GraphData(Application.getBlackBoard());
        if (gd.getGraph() == null && myColoring.graph == null)
            return;
        if (myColoring.graph == null) {
            myColoring.applyColoring();
            return;
        }
        if (gd.getGraph() == null) {
            gd.core.showGraph(myColoring.graph);
            myColoring.applyColoring();
            return;
        }
        if (myColoring.graph.equals(gd.getGraph())) {
            myColoring.applyColoring();
        } else {
            gd.core.showGraph(myColoring.graph);
            myColoring.applyColoring();
        }
    }
}
