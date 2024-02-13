// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor.editors;

import graphtea.ui.components.gpropertyeditor.EditingFinishedListener;
import graphtea.ui.components.gpropertyeditor.GBasicCellEditor;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.HORIZONTAL;


/**
 * User: root
 */
public abstract class GDialogEditor<t> implements GBasicCellEditor, ActionListener {
    EditingFinishedListener listener;


    public void setEditingFinishedListener(EditingFinishedListener listener) {
        this.listener = listener;
    }

    JDialog d;
    JButton ok, reset, cancel;
    JPanel body;
    t _value;

    public Component getEditorComponent(Object value) {
        _value = (t) value;
        d = new JDialog();
        d.setAlwaysOnTop(true);
        d.setModal(true);
        //btm is the bottom of the editor where the ok/reset/cancel buttons placed there
        JPanel btm = new JPanel();
        //body is the body of editor which will be filled by a component that will be given from abstract method (JComponent getEditorComponent)
        body = new JPanel();
        //the buttons which will be put on btm
        ok = new JButton("ok");
        reset = new JButton("reset");
        cancel = new JButton("cancel");

        ok.addActionListener(this);
        reset.addActionListener(this);
        cancel.addActionListener(this);

        btm.add(ok);
        btm.add(reset);
        btm.add(cancel);

        //filling the body
        body.setLayout(new BorderLayout(2, 2));
        body.add(getComponent(_value));
        body.setBorder(new SoftBevelBorder(0));

        d.setLayout(new GridBagLayout());

        GridBagConstraints gbcc = new GridBagConstraints(0, 0, 2, 1, 1, 1, CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
        d.getContentPane().add(body, gbcc);

        GridBagConstraints gbc = new GridBagConstraints(0, 1, 2, 1, 1, 0, CENTER, HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
        d.getContentPane().add(btm, gbc);


        d.validate();
        d.pack();
        new Thread(() -> d.setVisible(true)).start();
        return GCellRenderer.getRendererFor(value);
    }

    public void cancelEditing() {
        d.setVisible(false);
    }

    abstract public JComponent getComponent(t initialValue);

    abstract public t getEditorValue();

    abstract public void setEditorValue(t value);

    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src == ok) {
            listener.editingFinished(getEditorValue());
            d.setVisible(false);
        }
        if (src == cancel) {
            listener.editingFinished(_value);
            d.setVisible(false);
        }
        if (src == reset) {
            setEditorValue(_value);
        }
    }
}