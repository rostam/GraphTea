// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.help.actions;

import graphlab.plugins.commonplugin.help.Utils;
import graphlab.plugins.commonplugin.reporter.Browser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class HelpWindow extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -6779084226685712613L;

    public HelpWindow() throws HeadlessException {
        super();
        initComponents();
    }

    private void initComponents() {// GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jList1 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        commentLabel = new JLabel("");

        getContentPane().setLayout(new java.awt.GridBagLayout());

        jList1.setToolTipText("Select a plugin to show the help of it:");

        DefaultListModel list = new DefaultListModel();
        for (String pluginName : Utils.pluginHelps.keySet()) {
            list.addElement(pluginName);
        }

        jList1.setModel(list);
        jList1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showSelectedPluginHelp();
                }
            }
        });

        setPreferredSize(new Dimension(250, 320));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.85;
        getContentPane().add(jList1, gridBagConstraints);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel1.setPreferredSize(new java.awt.Dimension(191, 5));

        jPanel1.add(commentLabel);

        jButton1.setText("Show");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton1);

        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.15;
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        showSelectedPluginHelp();
    }

    private void showSelectedPluginHelp() {
        for (Object obj : jList1.getSelectedValues()) {
            String pluginName = obj.toString();
            URL url = Utils.pluginHelps.get(pluginName);
            System.out.println(url);
            if (url != null) {
//                Browser b = new Browser();
//                b.pack();
//                b.setVisible(true);
                Browser.browse(url);
            }

        }
    }

    private JLabel commentLabel;

    private javax.swing.JButton jButton1;

    private javax.swing.JButton jButton2;

    public javax.swing.JList jList1;

    private javax.swing.JPanel jPanel1;

}
