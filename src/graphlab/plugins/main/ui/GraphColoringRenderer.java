// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.ui;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphColoring;
import graphlab.graph.graph.Vertex;
import graphlab.platform.Application;
import graphlab.plugins.main.GraphData;
import graphlab.ui.components.gpropertyeditor.GBasicCellRenderer;
import graphlab.ui.components.gpropertyeditor.GCellRenderer;

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
        myColoring.vertexColors = new HashMap<Vertex, Integer>(coloring.vertexColors);
        myColoring.edgeColors = new HashMap<Edge, Integer>(coloring.edgeColors);
        myColoring.label = coloring.label;
        String txt = "";
        txt = "<HTML><BODY>";
        if (myColoring.label != null && !myColoring.label.equals("")) {
            txt = txt + "<B>" + myColoring.label + ":  </B>";
        }
        if (myColoring.vertexColors != null && myColoring.vertexColors.size() > 0) {
            txt = txt + "<B>Vertex colors: </B> ";
            for (Map.Entry<Vertex, Integer> p : myColoring.vertexColors.entrySet()) {
                txt = txt + p.getKey().getLabel() + ":" + p.getValue() + " , ";
            }
        }
        if (myColoring.edgeColors != null && myColoring.edgeColors.size() > 0) {
            txt = txt + "<br/><B>Edge colors: </B> ";
            for (Map.Entry<Edge, Integer> p : myColoring.edgeColors.entrySet()) {
                txt = txt + p.getKey().getLabel() + ":" + p.getValue() + " , ";
            }
        }
        txt = txt + "</BODY></HTML>";
        
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
