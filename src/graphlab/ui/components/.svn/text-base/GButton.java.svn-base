// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components;


import graphlab.platform.core.BlackBoard;
import graphlab.ui.actions.UIEventData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * This is class is the child of JButton which is using the blackboard to pass the action of itself.
 * it is specially passing the actions to the log which UIEventHandler will be map it to its corresponding action
 * I used this buttons in the GToolBar
 * User: Azin Azadi
 */
public class GButton extends JButton implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 938792224562609943L;
    BlackBoard blackboard;
    UIEventData t;

    /**
     * action is the name of the class which the UIEventHandler will map the button to.
     */
    public GButton(String label, URL iconURL, BlackBoard b, String action) {
        super();
        blackboard = b;
        ImageIcon icon = null;
        if (iconURL != null)
            icon = new ImageIcon(iconURL);
        System.out.println(iconURL + "");
        setLabelAndIcon(label, icon);
        addActionListener(this);
        //-------------
        t = new UIEventData();
        t.action = action;
    }

    /**
     * action is the name of the class which the UIEventHandler will map the button to.
     */
    public GButton(String label, String iconFileName, BlackBoard b, String action) {
        super();
        blackboard = b;
        ImageIcon icon = loadIcon(iconFileName);
        setLabelAndIcon(label, icon);
        addActionListener(this);
        //-------------
        t = new UIEventData();
        t.action = action;
    }

    private ImageIcon loadIcon(String iconFileName) {
        ImageIcon icon = null;
        if (iconFileName != null && !iconFileName.equals("")) {
            icon = new ImageIcon(iconFileName);
            if (icon.getIconWidth() < 1) {
                //solving the problem between GraphLab and GraphLabDebugger
                if (iconFileName.charAt(0) == '/')
                    iconFileName = iconFileName.substring(1);
                else
                    iconFileName = iconFileName + "/";
                icon = new ImageIcon(iconFileName);

            }
        }

        return icon;
    }

    //set the icon and text of the button according to input
    private void setLabelAndIcon(String label, ImageIcon icon) {
        if (icon != null && icon.getIconWidth() > 1) {
            setIcon(icon);
            setToolTipText(label);
        } else {
            setText(label);
        }
    }

    public void actionPerformed(ActionEvent e) {
        blackboard.setData(UIEventData.name(""), t);
    }
}