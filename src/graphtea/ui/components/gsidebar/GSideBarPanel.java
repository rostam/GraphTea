// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.components.gsidebar;

import graphtea.ui.components.gbody.GBody;

import java.awt.*;

/**
 * @author azin azadi
 */
public class GSideBarPanel extends javax.swing.JPanel {
    private GBody body;

    /**
     * Creates new form GSideBarPanel
     *
     * @param gBody The body
     * @param leftPanel The left panel
     * @param label The label
     */
    public GSideBarPanel(GBody gBody, Component leftPanel, String label) {
        initComponents();
        setTitle(label);
        setBody(gBody);
        jPanel2.add(leftPanel);
//        sidebarWrapper.jPanel2.setLayout(new BorderLayout(0,0));
        jPanel2.validate();

    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(242, 204, 128));
        jPanel1.setBorder(new javax.swing.border.EtchedBorder());
        jPanel1.setMinimumSize(new java.awt.Dimension(300, 700));
        jPanel1.setPreferredSize(new java.awt.Dimension(70, 18));
        jLabel1.setText("Sidebar");
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        jButton1.setText("x");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setIconTextGap(1);
        jButton1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jButton1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButton1MouseDragged(evt);
            }
        });

        jPanel1.add(jButton1, java.awt.BorderLayout.EAST);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.X_AXIS));

        add(jPanel2, java.awt.BorderLayout.CENTER);

    }

    private void jButton1MouseDragged(java.awt.event.MouseEvent evt) {
    }


    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    // End of variables declaration


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        body.hideSideBar(jPanel2.getComponent(0), jLabel1.getText());
    }

    public void setTitle(String title) {
        jLabel1.setText(title);
    }

    public void setBody(GBody b) {
        this.body = b;
    }
}