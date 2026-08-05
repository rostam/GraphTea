// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.components.gpropertyeditor.renderers;

import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;

import javax.swing.*;
import java.awt.*;

public class GDimensionRenderer implements GBasicCellRenderer<Dimension> {
    public Component getRendererComponent(Dimension value) {
        return new JLabel(value.width + "*" + value.height);


    }
}
