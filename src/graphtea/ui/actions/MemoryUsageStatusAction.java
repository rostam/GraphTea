// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.actions;

import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;
import graphtea.ui.components.GComponentInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Azin Azadi
 */
public class MemoryUsageStatusAction extends graphtea.platform.core.AbstractAction implements ActionListener, GComponentInterface {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public MemoryUsageStatusAction(BlackBoard bb) {
        super(bb);
        new Timer(1000, this).start();
        bname = UIUtils.getComponentVariableKeyNameInBlackBoard("memory usage");
        blackboard.addListener(bname, this);
    }

    /**
     * called when the variable bname is changed
     *
     * @param eventName
     * @param value
     */
    public void performAction(String eventName, Object value) {
        button = blackboard.getData(bname);
        button.setToolTipText("Press to free the memory");
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.gc();
            }
        });
    }

    /**
     * occurs when the button pressed
     */
    public void actionPerformed(ActionEvent e) {
        long totalmem = Runtime.getRuntime().totalMemory();
        long used = totalmem - Runtime.getRuntime().freeMemory();
        try {
            button.setText(getOutStr(used, totalmem));
            button.setSize(100, 15);
        } catch (Exception ex) {
        }
    }

    /**
     * :)) my simple formating methods. :D
     */
    private String getOutStr(long usedmem, long totalmem) {
        return "<html><body>" +
                _strout("mem(kb): ") +
                _numout(usedmem / 1000) +
                _strout(" of ") +
                _numout(totalmem / 1000);
    }

    /**
     * :)) my simple formating methods. :D
     */
    private String _numout(long num) {
        return "<font color='#222244'>" + num + "</font>";
    }

    /**
     * :)) my simple formating methods. :D
     */
    private String _strout(String str) {
        return "<font color='#444477'>" + str + "</font>";
    }

    private String bname;
    private JButton button = new JButton("memory usage");

    public Component getComponent(BlackBoard b) {
        button.setUI(new BasicButtonUI());
        return button;
    }
}