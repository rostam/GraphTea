// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.ui;

import graphtea.graph.old.GStroke;
import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * @author : Azin Azadi
 */
public class GStrokeRenderer extends JPanel implements GBasicCellRenderer {
    /**
     *
     */
    private static final long serialVersionUID = 5000660721620223990L;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D) g).setStroke(s.stroke);
        int h = getHeight();
        int w = getWidth();
//        g.setColor(lineColor);
        g.drawLine(0, h / 2, w, h / 2);
    }

//    Color lineColor = Color.darkGray;
//    Color bgColor = Color.white;

    public GStrokeRenderer() {
//        setBackground(bgColor);
    }

    public GStrokeRenderer(GStroke s) {
        this.s = s;
        setPreferredSize(new Dimension(50, 15));
//        setBackground(bgColor);
    }

    GStroke s;

    public Component getRendererComponent(Object value) {
        s = (GStroke) value;
        if (s.equals(GStroke.empty)) {
            return new JLabel("Empty");
        }
        return new GStrokeRenderer(s);
    }
}