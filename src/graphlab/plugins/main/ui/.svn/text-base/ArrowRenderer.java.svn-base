// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.ui;

import graphlab.graph.old.Arrow;
import graphlab.ui.components.gpropertyeditor.GBasicCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * @author Azin Azadi
 */
public class ArrowRenderer implements GBasicCellRenderer<Arrow> {

    public Component getRendererComponent(final Arrow arrow) {
        JPanel p = new JPanel() {
            /**
             *
             */
            private static final long serialVersionUID = -5698778092356962429L;

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D gg = ((Graphics2D) g);
                int h = getHeight();
                gg.translate(h, h / 2);
                arrow.paintArrow(gg, h, h);
                gg.drawString(arrow.getName(), 3, 5);
            }
        };
        p.setPreferredSize(new Dimension(100, Math.max(p.getHeight(), 20)));
        p.setBackground(Color.white);
        return p;

    }
}
