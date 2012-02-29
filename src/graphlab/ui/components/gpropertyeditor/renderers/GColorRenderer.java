// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gpropertyeditor.renderers;

import graphlab.ui.components.gpropertyeditor.GBasicCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * author: Azin Azadi
 * EMail:
 */
public class GColorRenderer implements GBasicCellRenderer {
    public Component getRendererComponent(final Object value) {
        Color c = (Color) value;
        JLabel l = new JLabel(String.valueOf(c.getRGB()));
        l.setOpaque(true);
        l.setBackground(c);
        l.setHorizontalAlignment(JLabel.CENTER);
        return l;
//        JPanel x = new JPanel() {
//            /**
//             *
//             */
//            private static final long serialVersionUID = 5615648107336029419L;
//
//            public void paint(Graphics g) {
//                super.paint(g);
//                Color c = (Color) value;
//                g.drawString(String.valueOf(c.getRGB()), 0, 12);
////                value.getClass();
//            }
//        };
//        x.setBackground((Color) value);
//        x.setPreferredSize(new Dimension(50, 150));
//        return x;
    }
}