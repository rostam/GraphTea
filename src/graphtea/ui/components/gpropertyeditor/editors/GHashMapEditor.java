// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor.editors;

import graphtea.ui.PortableNotifiableAttributeSetImpl;
import graphtea.ui.components.gpropertyeditor.GPropertyEditor;

import javax.swing.*;
import java.util.HashMap;

/**
 * @author azin azadi
 */
//todo: complete it!
public class GHashMapEditor extends GDialogEditor<HashMap> {
    GPropertyEditor ged = new GPropertyEditor();

    public JComponent getComponent(HashMap initialValue) {
        return null;
    }

    public HashMap getEditorValue() {
        return null;
    }

    public void setEditorValue(HashMap value) {

    }

    private PortableNotifiableAttributeSetImpl hashmap2NotifiableAttributeSetx(HashMap h) {
        PortableNotifiableAttributeSetImpl a = new PortableNotifiableAttributeSetImpl();
        for (Object o : h.keySet()) {
//            a.putAtr(o, h.get(o));
        }
        return null;
    }
}
