// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gpropertyeditor;

import graphlab.platform.attribute.AtomAttribute;

import java.awt.*;

/**
 * @author azin azadi
 */
public class OAtrRenderer implements GBasicCellRenderer<AtomAttribute> {
    public Component getRendererComponent(AtomAttribute value) {
        return (GCellRenderer.getRendererFor(value.getValue()));
    }
}
