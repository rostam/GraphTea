// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components;

import graphlab.platform.core.BlackBoard;
import graphlab.ui.components.gbody.GBody;
import graphlab.ui.components.gmenu.GMenuBar;
import graphlab.ui.components.gsidebar.GSidebar;

import javax.swing.*;
import java.awt.*;

/**
 * this class is the extension of JFrame, which is not only a simple JFrame, it
 * is a frame work that has all parts of an User Interface, it contains:
 * Toolbar, Status Bar, Side Bar, Menu Bar and a Body which is the main program
 *
 * @author azin azadi

 */
public class GFrame extends javax.swing.JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1087497772015236434L;
    public BlackBoard blackboard;
//    static GWinAdapter winAdapter;
//    static {
//        winAdapter = new GWinAdapter();
//    }

    private GBody body1;
    private GMenuBar gMenuBar2;
    private GSidebar gSidebar1;
    private GToolbar gToolbar1;
    private GStatusBar gStatusbar1;


    /**
     * Creates new form GFrame
     */
    public GFrame(BlackBoard blackboard) {
        this.blackboard = blackboard;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        initComponents();
        gToolbar1.createToolBar();
        validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {// GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        body1 = new GBody();
        gToolbar1 = new GToolbar();
        gSidebar1 = new GSidebar(body1, blackboard);
        gMenuBar2 = new GMenuBar();
        gStatusbar1 = new GStatusBar(blackboard);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(gSidebar1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(body1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(gToolbar1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(gStatusbar1, gridBagConstraints);

        setJMenuBar(gMenuBar2);

        pack();
    }

    public GSidebar getSidebar() {
        return gSidebar1;
    }

    public GMenuBar getMenu() {
        return gMenuBar2;
    }

    public GToolbar getToolbar() {
        return gToolbar1;
    }

    public GBody getBody() {
        return body1;
    }

    public GStatusBar getStatusbar() {
        return gStatusbar1;
    }
}