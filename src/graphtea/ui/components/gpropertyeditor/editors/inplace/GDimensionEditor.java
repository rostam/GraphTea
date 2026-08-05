// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.components.gpropertyeditor.editors.inplace;

import graphtea.ui.components.gpropertyeditor.editors.GStringEditor;

import java.awt.*;

public class GDimensionEditor extends GStringEditor {
    public Component getEditorComponent(Object value) {
        if (value instanceof Dimension) {
            Dimension d = (Dimension) value;
            return super.getEditorComponent(d.width + "*" + d.height);
        }
        return null;
    }

    public Object getEditorValue() {
        String s = super.getEditorValue() + "";
        if (s != null) {
            try {
                return new Dimension(Integer.parseInt(s.substring(0, s.indexOf('*'))),
                        Integer.parseInt(s.substring(s.indexOf('*') + 1)));
            } catch (Exception e) {
                return this.initVal;
            }
        }
        return null;
    }

}
