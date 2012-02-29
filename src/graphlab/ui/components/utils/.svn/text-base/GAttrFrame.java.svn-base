// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.utils;

import graphlab.platform.attribute.NotifiableAttributeSet;
import graphlab.platform.extension.Extension;
import graphlab.ui.components.gpropertyeditor.GPropertyEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GAttrFrame extends javax.swing.JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 722213016758300314L;
    private NotifiableAttributeSet atr;

    /**
     * Creates new form GAttrFrame
     */
    public GAttrFrame(java.awt.Frame parent, NotifiableAttributeSet atr, boolean modal) {
        super(parent, modal);
        this.atr = atr;
        initComponents();
    }

    /**
     * @return the return status of this dialog - true-> the ok presses, false-> cancelled by user
     */
    public boolean getReturnStatus() {
        return status;
    }

    GPropertyEditor table;

    private void initComponents() {
        setAlwaysOnTop(true);
        JPanel buttonPanel = new JPanel();
        JButton cancelButton = new JButton("cancel");
        table = new GPropertyEditor();
        table.connect(atr);
        JButton okButton = new JButton("ok");
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog();
            }
        });


        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                finished(true);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                finished(false);
            }
        });

        okButton.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    finished(true);
                }
            }
        });
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, java.awt.BorderLayout.SOUTH);
        add(table, java.awt.BorderLayout.CENTER);
        okButton.setSelected(true);
        validate();
        okButton.setSelected(true);
        pack();
        okButton.grabFocus();
    }

    private boolean status = false;
    private boolean finished = false;

    private void closeDialog() {
        finished = true;
    }

    private void finished(boolean status) {
        this.status = status;
        if (status)
            if (table.getTable().isEditing()) {
                table.getTable().getCellEditor().stopCellEditing();
            }
        closeDialog();
        dispose();
    }

    public static GAttrFrame showEditDialog(NotifiableAttributeSet input) {
        return showEditDialog(input, true);
    }

    /**
     * Shows a Property editor to edit the attributes in the input.
     * the modal is like the modal in JDialog
     */
    public static GAttrFrame showEditDialog(NotifiableAttributeSet input, boolean modal) {
        String title = "Edit Attributes";
        if (input instanceof Extension) {
            title = ((Extension) input).getName();
        }
        GAttrFrame gAttrFrame = new GAttrFrame(new JFrame(), input, modal);
        gAttrFrame.setTitle(title);
        gAttrFrame.setVisible(true);
        return gAttrFrame;
    }

    /**
     * return the GProertyEditor which is the main editor of NotifiableAttributeSet
     */
    public GPropertyEditor getPropertyEditor() {
        return table;
    }
}