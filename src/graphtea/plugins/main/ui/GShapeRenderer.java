// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.ui;

import graphtea.graph.old.GShape;
import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;
import graphtea.ui.components.gpropertyeditor.editors.inplace.ValueSet;

import javax.swing.*;
import java.awt.*;

/**
 * @author Azin Azadi
 */
public class GShapeRenderer implements ValueSet, GBasicCellRenderer<GShape> {
    public Component getRendererComponent(final GShape value) {
        //Vertex v=new Vertex();
        JPanel p = new JPanel() {

            /**
             *
             */
            private static final long serialVersionUID = 880282301386500240L;

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                int h = getHeight();
                g.drawString(value.name + "", h + 4, (h - 2) / 2 + 8);
                g.setColor(Color.lightGray);
                GShape.fillShape(value.name, g, h - 1, h - 1);
                g.setColor(Color.darkGray);
                GShape.drawShape(value.name, g, h - 1, h - 1);
//                g.setColor(Color.black);
            }
        };
        p.setPreferredSize(new Dimension(50, 30));
        p.setBackground(Color.white);
        return p;
    }

    public Object[] getValues() {
        return new GShape[]{
                GShape.OVAL,
                GShape.RECTANGLE,
                GShape.ROUNDRECT,
                GShape.STAR,
                GShape.SIXPOINTSTAR,
                GShape.SEVENPOINTSTAR,
                GShape.EIGHTPOINTSTAR,
                GShape.NINEPOINTSTAR,
                GShape.TENPOINTSTAR,
                GShape.LEFTWARDTTRIANGLE,
                GShape.RIGHTWARDTRIANGLE,
                GShape.UPWARDTRIANGLE,
                GShape.DOWNWARDTRIANGLE,
                GShape.REGULARHEXAGON,
                GShape.REGULARPENTAGON,
                GShape.DOWNWARDTRAPEZOID,
                GShape.UPWARDTRAPEZOID,
                GShape.RIGHTWARDTRAPEZOID,
                GShape.LEFTWARDTRAPEZOID,
                GShape.DOWNWARDPARALLELOGRAM,
                GShape.UPWARDPARALLELOGRAM,
                GShape.NICESTAR,
                GShape.NICESIXPOINTSTAR,
                GShape.NICESEVENPOINTSTAR,
                GShape.NICEEIGHTPOINTSTAR,
                GShape.NICENINEPOINTSTAR,
                GShape.NICETENPOINTSTAR,


        };
    }


}
