// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor.editors.inplace;

import graphtea.platform.attribute.AtomAttribute;
import graphtea.ui.components.gpropertyeditor.EditingFinishedListener;
import graphtea.ui.components.gpropertyeditor.GBasicCellEditor;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: root
 */
public abstract class GComboEditor implements GBasicCellEditor, ActionListener {
    EditingFinishedListener listener;

    public void setEditingFinishedListener(EditingFinishedListener listener) {
        this.listener = listener;
    }

    JComboBox cbox;
    Object initValue;

    public Component getEditorComponent(Object value) {
        initValue = value;
        cbox = new JComboBox(getValues());
        cbox.addActionListener(this);
        if (value instanceof AtomAttribute) {
            AtomAttribute v = (AtomAttribute) value;
            cbox.setSelectedItem(v.getValue());
        } else {
            cbox.setSelectedItem(value);
        }
        cbox.setRenderer(new ListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                return GCellRenderer.getRendererFor(value);
            }
        });
        cbox.setBackground(Color.white);
        return cbox;
    }

    public abstract Object[] getValues();

    public void cancelEditing() {
        //nothing to do :D
    }

    public Object getEditorValue() {
        return getSelectedItem();
    }

    /**
     * occurs when one item of combo list selected
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        listener.editingFinished(getSelectedItem());
    }

    /**
     * returns the selected item in combo list
     */
    public Object getSelectedItem() {
        if (initValue instanceof AtomAttribute) {
            ((AtomAttribute) initValue).setValue(cbox.getSelectedItem());
            return initValue;
        } else
            return cbox.getSelectedItem();
    }
}
