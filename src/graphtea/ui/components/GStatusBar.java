// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components;


import graphtea.platform.core.BlackBoard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author azin azadi
 */
public class GStatusBar extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 7638151469264118382L;
    private final BlackBoard blackboard;

    public GStatusBar(BlackBoard blackboard) {
        super();
        this.blackboard = blackboard;
        FlowLayout l = new FlowLayout(FlowLayout.LEFT, 1, 1);
        setLayout(l);
        setBorder(new EmptyBorder(1, 1, 0, 1));

    }

    /**
     * adds a new Component to the status bar
     */
    public void addComponent(Component c) {
        add(c);
        add(newSeparator());
        validate();
    }

    /**
     * @return a new instance of JSeperator to put between components in status bar
     */
    private JSeparator newSeparator() {
        JSeparator s = new JSeparator(JSeparator.VERTICAL);
        s.setPreferredSize(new Dimension(3, 15));
        //sp.setForeground(Color.orange);
        return s;
    }
}