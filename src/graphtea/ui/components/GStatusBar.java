// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components;


import graphtea.platform.core.BlackBoard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
        setLayout(new FlowLayout(FlowLayout.LEFT, 6, 2));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("Separator.foreground")),
                new EmptyBorder(1, 4, 1, 4)));
    }

    /**
     * adds a new Component to the status bar
     *
     * <p>The separator goes <em>before</em> each component after the first. Appending one
     * after every component left a stray divider dangling off the end of the bar.
     *
     * @param c the component to add
     */
    public void addComponent(Component c) {
        if (getComponentCount() > 0) {
            JSeparator separator = newSeparator();
            // A component that hides itself when it has nothing to say (an empty message
            // label) takes its divider with it, rather than leaving one dangling at the end.
            separator.setVisible(c.isVisible());
            c.addComponentListener(new ComponentAdapter() {
                public void componentShown(ComponentEvent e) {
                    separator.setVisible(true);
                }

                public void componentHidden(ComponentEvent e) {
                    separator.setVisible(false);
                }
            });
            add(separator);
        }
        add(c);
        validate();
    }

    /**
     * @return a new instance of JSeperator to put between components in status bar
     */
    private JSeparator newSeparator() {
        JSeparator s = new JSeparator(JSeparator.VERTICAL);
        s.setPreferredSize(new Dimension(3, 15));
        return s;
    }
}