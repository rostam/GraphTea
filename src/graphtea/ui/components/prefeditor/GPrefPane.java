// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.components.prefeditor;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.preferences.AbstractPreference;
import graphtea.ui.components.GFrame;
import graphtea.ui.components.gpropertyeditor.GPropertyEditor;
import graphtea.ui.components.utils.GFrameLocationProvider;

import javax.swing.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * @author Rouzbeh Ebrahimi
 * Email: ruzbehus@gmail.com
 */

public class GPrefPane extends GFrame {
    // Variables declaration - do not modify
    public HashMap<String, AbstractPreference> tabs;
    private javax.swing.JList list;
    private javax.swing.JScrollPane scrollPane;
    private GPropertyEditor tabPane;
    private JButton ok;
    private JButton cancel;
    private JButton apply;
    private JLabel label;
// End of variables declaration

    /**
     * Creates new form prefForm
     */
    public GPrefPane(BlackBoard bb, HashMap<String, AbstractPreference> items) {
        super(bb);
        java.awt.GridBagConstraints gridBagConstraints;
        tabs = items;
        tabPane = new GPropertyEditor();
        scrollPane = new javax.swing.JScrollPane();
        list = new JList();
        ok = new JButton("Ok");
        ok.addActionListener(e -> finished(true));

        cancel = new JButton("Cancel");
        cancel.addActionListener(e -> closeDialog());
        apply = new JButton("Apply");
        label = new JLabel("Preferences:");

        setLayout(new java.awt.GridBagLayout());

//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTab();
        Vector<String> refined = items.keySet().stream().filter(s -> !Objects.equals(s, "Only Storable")).collect(Collectors.toCollection(Vector::new));
        final String[] strs = refined.toArray(new String[refined.size()]);

        list.setModel(new javax.swing.AbstractListModel() {
            String[] strings = strs;

            public int getSize() {
                return strings.length;
            }

            public Object getElementAt(int i) {
                return strings[i];
            }
        });
        list.addListSelectionListener(this::jList1ValueChanged);

        scrollPane.setViewportView(list);

        setList();
        setCancel();
//        setApply();
        setOk();
//        setLabel();

        String s = (String) list.getModel().getElementAt(0);
        AbstractPreference ap = items.get(s);
//        GPropertyEditor gp = new GPropertyEditor();
//        gp.connect(ap.attributeSet);
//        gp.setVisible(true);
        tabPane.connect(ap.attributeSet);

        tabPane.setVisible(true);
        setSize(GFrameLocationProvider.getPrefSize());
        setLocation(GFrameLocationProvider.getLocation());
    }

    private void setList() {
        java.awt.GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.ipady = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(scrollPane, gridBagConstraints);
    }

    private void setLabel() {
        java.awt.GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
//           gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(label, gridBagConstraints);
    }

    private void setTab() {
        java.awt.GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.ipady = 250;
        gridBagConstraints.weightx = 6;
        gridBagConstraints.weighty = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(tabPane, gridBagConstraints);
    }

    private void setCancel() {
        java.awt.GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);

        add(cancel, gridBagConstraints);
    }

    /*private void setApply(){
        java.awt.GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 8;
         gridBagConstraints.fill = java.awt.GridBagConstraints.CENTER;        
        gridBagConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        add(apply, gridBagConstraints);
    }*/
    private void setOk() {
        java.awt.GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        add(ok, gridBagConstraints);
    }

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {
//          tabPane=new gpropertyeditor();
        String s = (String) list.getSelectedValue();
        AbstractPreference ap = tabs.get(s);
//        GPropertyEditor gp = new GPropertyEditor();
//        gp.connect(ap.attributeSet);
//        gp.setVisible(true);
        tabPane.connect(ap.attributeSet);
        tabPane.setVisible(true);

        setTab();
    }

    private boolean status = false;
    private boolean finished = false;

    private void closeDialog() {
        finished = true;
        dispose();
    }

    private void finished(boolean status) {
        this.status = status;
        closeDialog();
        dispose();
    }


}

