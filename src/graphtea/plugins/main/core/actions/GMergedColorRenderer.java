// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions;

import graphtea.graph.graph.FastRenderer;
import graphtea.graph.graph.GraphModel;
import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * the renderer for rendering int colors of vertices/edges in GraphTea
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