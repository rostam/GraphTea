// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gpropertyeditor.renderers;

import graphlab.ui.components.gpropertyeditor.GBasicCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * @author azin azadi
 */
public class GFontRenderer implements GBasicCellRenderer<Font> {
    public Component getRendererComponent(Font value) {
        JLabel l = new JLabel(value.getFontName()) {
            /**
             *
             */
            private static final long serialVersionUID = -1315161861822432705L;

            public void paint(Graphics g) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                super.paint(g);
            }
        };
        l.setFont(value);
        l.repaint();
        return l;
    }
}
