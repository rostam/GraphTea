// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor.editors;

import graphtea.ui.components.gpropertyeditor.EditingFinishedListener;
import graphtea.ui.components.gpropertyeditor.GBasicCellEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author azin azadi
 */
public class GStringEditor implements GBasicCellEditor, ActionListener {
    EditingFinishedListener listener;
    protected String initVal;

    public void setEditingFinishedListener(EditingFinishedListener listener) {
        this.listener = listener;
    }

    protected JTextField jt;

    public Component getEditorComponent(Object value) {
        initVal = value + "";
        jt = new JTextField(initVal);
        jt.setBorder(null);
        jt.setBackground(Color.yellow);
        return jt;
    }

    public void cancelEditing() {
    }

    public Object getEditorValue() {
        return jt.getText();
    }

    protected void finishEdit() {
        listener.editingFinished(jt.getText());
    }

    public void actionPerformed(ActionEvent e) {
        finishEdit();
    }
}