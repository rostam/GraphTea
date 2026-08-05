// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor.renderers;

import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * User: root
 */
public class GBooleanRenderer implements GBasicCellRenderer {

    public Component getRendererComponent(Object value) {
        Boolean b = (Boolean) value;
        if (b)
            return new JLabel("Yes");
        else
            return new JLabel("No");
//        JCheckBox j = new JCheckBox("", b);
//        j.setBorderPaintedFlat(true);
//        j.setOpaque(false);
//        j.setHorizontalAlignment(JCheckBox.CENTER);
//        return j;
//        String file;
//        if ((Boolean) value == true)
//            file = "true.gif";
//        else
//            file = "false.gif";
//        JLabel l = new JLabel(new ImageIcon(getClass().getResource(file)));
//        l.setLayout(new BorderLayout(2,2));
//        return l;
    }
}
