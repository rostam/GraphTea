// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions;

import graphlab.graph.graph.FastRenderer;
import graphlab.graph.graph.GraphModel;
import graphlab.ui.components.gpropertyeditor.GBasicCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * the renderer for rendering int colors of vertices/edges in GraphLab
 * author: Azin Azadi
 * Email: aazadi@gmail.com
 */
public class GMergedColorRenderer implements GBasicCellRenderer {
    public GMergedColorRenderer(boolean isvertex) {
        this.isvertex = isvertex;
        general = false;
    }

    public GMergedColorRenderer() {
        general = true;
    }

    boolean isvertex;
    boolean general = true;

    public Component getRendererComponent(Object value) {
        if (general) {
            Integer i = (Integer) value;
            JLabel l = new JLabel(i + "");
            l.setOpaque(true);
            Color c = GraphModel.getColor(i);
            l.setBackground(c);
            l.setHorizontalAlignment(JLabel.CENTER);
            return l;
        } else
            return getRendererComponent(value, isvertex);
    }

    public static Component getRendererComponent(Object value, boolean isVertex) {
        Integer i = (Integer) value;
        JLabel l = new JLabel(i + "");
        l.setOpaque(true);
        Color c = GraphModel.getColor(i);
        if (i == 0) {
            if (isVertex) {
                c = FastRenderer.defaultVertexColor;
            } else
                c = FastRenderer.defaultEdgeColor;
        }
        l.setBackground(c);
        l.setHorizontalAlignment(JLabel.CENTER);
        return l;
    }
}