// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.ui;

import graphlab.graph.old.GShape;
import graphlab.ui.components.gpropertyeditor.editors.inplace.GComboEditor;

/**
 * User: root
 */
public class GShapeEditor extends GComboEditor {
    public Object[] getValues() {
        return new GShape[]{
                GShape.OVAL,
                GShape.RECTANGLE,
                GShape.ROUNDRECT
        };
    }
}
