// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor.editors.inplace;

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
