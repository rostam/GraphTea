// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.prefeditor;

import graphlab.ui.components.gpropertyeditor.GPropertyEditor;
import graphlab.ui.components.utils.GFrameLocationProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Rouzbeh Ebrahimi
 */
public class GTabbedAttributeFrame extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 2001771646679881523L;
    //    private NotifiableAttributeSet atr;
    public GTabbedAttributePane tabbedPane;

    /**
     * Creates new form GAttrFrame
     */
    public GTabbedAttributeFrame(java.awt.Frame parent, GTabbedAttributePane tabbedPane, boolean modal) {
        super(parent, modal);

        this.tabbedPane = tabbedPane;

        tabbedPane.setVisible(true);
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
        add(tabbedPane);
        JPanel buttonPanel = new JPanel();
        JButton cancelButton = new JButton("cancel");
//        table = new gpropertyeditor();
//        table.connect(tabbedPane);

        JButton okButton = new JButton("Ok");
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
//        buttonPanel.add(applyButton);
        add(buttonPanel, java.awt.BorderLayout.SOUTH);
//        add(table,java.awt.BorderLayout.CENTER);
//        applyButton.setSelected(true);
        setSize(GFrameLocationProvider.getPopUpSize());
        setLocation(GFrameLocationProvider.getPopUpLocation());
        validate();
        okButton.setSelected(true);
        pack();
//        applyButton.grabFocus();
    }

    private boolean status = false;
    private boolean finished = false;

    private void closeDialog() {
        finished = true;
    }

    private void finished(boolean status) {
        this.status = status;
        closeDialog();
        dispose();
    }

    public static GTabbedAttributeFrame showEditDialog(GTabbedAttributePane tabs) {
        return showEditDialog(tabs, true);
    }

    /**
     * Shows a Property editor to edit the attributes in the input.
     * the modal is like the modal in JDialog
     */
    public static GTabbedAttributeFrame showEditDialog(GTabbedAttributePane tabs, boolean modal) {
        GTabbedAttributeFrame gAttrFrame = new GTabbedAttributeFrame(new JFrame(), tabs, modal);
        gAttrFrame.setVisible(true);
        return gAttrFrame;
    }

    /**
     * return the GProertyEditor which is the main editor of notifiableAttributeSet
     */
    public GPropertyEditor getPropertyEditor() {
        return table;
    }
}
