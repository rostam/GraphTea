// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gpropertyeditor.editors.inplace;

import graphlab.platform.lang.ArrayX;

import java.awt.*;

/**
 * @author Azin Azadi
 */
public class ArrayXEditor extends GComboEditor {
    ArrayX x;

    public ArrayXEditor(ArrayX x) {
        this.x = x;
    }

    public Component getEditorComponent(Object value) {
        x = (ArrayX) value;
        return super.getEditorComponent(value);
    }

    public Object getSelectedItem() {
        Object o = super.getSelectedItem();
        x.setValue(o);
        return x;
    }

    public Object[] getValues() {
        return x.getValidValues();
    }
}
