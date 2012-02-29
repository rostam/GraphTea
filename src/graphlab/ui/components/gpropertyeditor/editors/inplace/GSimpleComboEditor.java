// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gpropertyeditor.editors.inplace;

/**
 * a simple combo editor, with specific values
 */
public class GSimpleComboEditor extends GComboEditor {
    ValueSet x;

    public GSimpleComboEditor(ValueSet x) {
        this.x = x;
    }

    public Object[] getValues() {
        return x.getValues();
    }
}
