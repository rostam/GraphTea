// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor.editors.inplace;

import graphtea.ui.components.gpropertyeditor.EditingFinishedListener;
import graphtea.ui.components.gpropertyeditor.GBasicCellEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: root
 */
public class GBooleanEditor implements GBasicCellEditor, ActionListener {
    private EditingFinishedListener listener;
    private JCheckBox j;

    public Object[] getValues() {
        return new Boolean[]{true, false};
    }

    public void setEditingFinishedListener(EditingFinishedListener listener) {
        this.listener = listener;
    }

    public Component getEditorComponent(Object value) {
        Boolean b = (Boolean) value;
        j = new JCheckBox("", b);
        j.setOpaque(false);
        j.addActionListener(this);
        j.setHorizontalAlignment(JCheckBox.CENTER);
        return j;
    }

    public void cancelEditing() {
        listener.editingFinished(getEditorValue());
    }

    public Object getEditorValue() {
        return j.isSelected();
    }

    public void actionPerformed(ActionEvent e) {
        listener.editingFinished(getEditorValue());
    }
}
