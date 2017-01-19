// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor;

import graphtea.platform.attribute.AttributeListener;
import graphtea.platform.attribute.NotifiableAttributeSet;
import graphtea.ui.AttributeSetView;
import graphtea.ui.NotifiableAttributeSetView;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.HashMap;

/**
 * Author: azin azadi
 */
public class ProEditor2NotifiableAttributeSetConnector implements AttributeListener, TableModelListener {
    private GPropertyTableModel model;
    GPropertyEditor editor;
    private NotifiableAttributeSet atr;

    public ProEditor2NotifiableAttributeSetConnector(GPropertyEditor editor) {
        this.model = editor.model;
        this.editor = editor;
        model.addTableModelListener(this);
    }

    public void connect(NotifiableAttributeSet atr) {
//        if (!atr.equals(this.atr)) {
        this.atr = atr;
        load();
//        }
    }

    private void load() {
        if (this.atr != null)
            this.atr.removeAttributeListener(this);
        this.load(atr);
        atr.addAttributeListener(this);
        iChangedTheAtr = false;

    }

    public void attributeUpdated(String name, Object oldVal, Object newVal) {
        //System.out.println(newVal);
        if (newVal != null && !newVal.equals(oldVal)) {
            iChangedTheAtr = true;
            model.setValue(name, newVal);
            editor.updateUI();
        }
    }

    boolean iChangedTheAtr = false;

    public void tableChanged(TableModelEvent e) {
        if (atr != null && !iChangedTheAtr)
            if (e.getType() == TableModelEvent.UPDATE) {
                int i = e.getFirstRow();
                if (i != -1) {
                    String name = keyByRow.get(i);
                    Object value = model.getValueAt(i, 1);
                    atr.put(name, value);
                }
            }
        iChangedTheAtr = false;
    }

    HashMap<Integer, String> keyByRow = new HashMap<>();

    public void load(NotifiableAttributeSet x) {
        if (x instanceof NotifiableAttributeSetView)
            editor.target = (NotifiableAttributeSetView) x;
        else {
            editor.def.getView().setAttribute(x);
            editor.target = editor.def;
        }
        reset();
        AttributeSetView attributes = editor.target.getView();
        editor.editor.setAtrView(attributes);
        editor.renderer.setAtrView(attributes);
        load(attributes);
        editor.validate();
        editor.updateUI();
    }

    private void reset() {
        editor.model.clear();
        editor.editor.cancelCellEditing();
        keyByRow.clear();
    }

    private void load(AttributeSetView xatr) {
        String[] names = xatr.getNames();
        int i = 0;
        for (String key : names) {
            if (xatr.isVisible(key)) {
                iChangedTheAtr = true;
                keyByRow.put(i++, key);
                // hooman: kaht be khat reporta add mishan
                editor.model.addData(xatr.getDisplayName(key), xatr.getAttribute().get(key));
                //xatr.getCategory(name)
                //hooman: inja baiad aval sort koni, ezafe koni, be ezaie har category ham ie satr ezafe koni
                
            }

        }
    }
}