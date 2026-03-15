// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.ui;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.Application;
import graphtea.plugins.main.GraphData;
import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

/**
 * @author Azin Azadi
 */
public class SubGraphRenderer implements GBasicCellRenderer<SubGraph> {
    public static SubGraph sgbck = new SubGraph();
    public Component getRendererComponent(SubGraph sd) {
        sgbck=sd;
        final SubGraph mysd = new SubGraph(sd.graph);
        mysd.vertices = new HashSet<>(sd.vertices);
        mysd.edges = new HashSet<>(sd.edges);
        mysd.label = sd.label;
        StringBuilder sb = new StringBuilder("<HTML><BODY>");
        if (mysd.label != null && !mysd.label.isEmpty()) {
            sb.append("<B>").append(mysd.label).append(": </B><BR>");
        }
        if (mysd.vertices != null && mysd.vertices.size() > 0) {
            sb.append("<B>V: </B> {");
            for (Vertex v : mysd.vertices) {
                sb.append(v.getLabel()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()).append("}");
        }
        if (mysd.edges != null && mysd.edges.size() > 0) {
            sb.append("<BR><B>E: </B> {");
            for (Edge e : mysd.edges) {
                sb.append(e.source.getLabel()).append("-").append(e.target.getLabel()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()).append("}");
        }
        sb.append("</BODY></HTML>");
        String txt = sb.toString();
        JLabel l = new JLabel(txt) {
            @Override
            public void setForeground(Color fg) {
                super.setForeground(fg);
                if (fg== GCellRenderer.SELECTED_COLOR)
                    showOnGraph(mysd);
            }
        };
        l.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showOnGraph(mysd);
            }
        }

        );
        return l;
    }

    private void showOnGraph(SubGraph mysd) {
        GraphData gd = new GraphData(Application.getBlackBoard());
        if (gd.getGraph() == null && mysd.graph == null)
            return;
        if (mysd.graph == null) {
            gd.select.setSelected(mysd);
            return;
        }
        if (gd.getGraph() == null) {
            gd.core.showGraph(mysd.graph);
            gd.select.setSelected(mysd);
            return;
        }
        if (mysd.graph.equals(gd.getGraph())) {
            gd.select.setSelected(mysd);
        } else {
            gd.core.showGraph(mysd.graph);
            gd.select.setSelected(mysd);
        }
    }
}
