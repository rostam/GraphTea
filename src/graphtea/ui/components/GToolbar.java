// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

/*
 * GToolbar.java
 *
 * Created on February 25, 2005, 11:14 AM
 */
package graphtea.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * The strip of toolbars under the menu bar. Each {@code <toolbar>} in the UI XML becomes one
 * {@link JToolBar}, ordered by its {@code place} and divided from its neighbours by a rule.
 *
 * @author azin azadi
 */
public class GToolbar extends JComponent {
    private static final long serialVersionUID = -7694324289573873831L;

    /** Key under which each toolbar remembers its {@code place}. */
    private static final String PLACE = "graphtea.toolbar.place";

    private JToolBar lastToolbar;

    public GToolbar() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
    }

    /**
     * Places {@code comp} among the toolbars by {@code index}.
     *
     * <p>This used to record the index and then insert at the end regardless, so toolbars
     * appeared in plugin load order whatever their {@code place} said.
     *
     * @param comp  the toolbar
     * @param index its place; lower comes first
     * @return the toolbar
     */
    public Component addIndexed(JToolBar comp, int index) {
        remove(comp);
        comp.putClientProperty(PLACE, index);
        int at = 0;
        while (at < getComponentCount() && placeOf(getComponent(at)) <= index) {
            at++;
        }
        add(comp, at);
        updateDividers();
        revalidate();
        return comp;
    }

    private static int placeOf(Component c) {
        Object place = c instanceof JComponent jc ? jc.getClientProperty(PLACE) : null;
        return place instanceof Integer i ? i : Integer.MAX_VALUE;
    }

    /**
     * Draws a rule before every non-empty toolbar but the first, so none trails off the end
     * (the frame keeps an empty toolbar of its own at the back).
     */
    private void updateDividers() {
        Color rule = UIManager.getColor("Separator.foreground");
        boolean first = true;
        for (Component c : getComponents()) {
            if (!(c instanceof JToolBar tb)) {
                continue;
            }
            if (tb.getComponentCount() == 0) {
                tb.setBorder(BorderFactory.createEmptyBorder());
            } else if (first) {
                tb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
                first = false;
            } else {
                tb.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 1, 0, 0, rule),
                        BorderFactory.createEmptyBorder(0, 4, 0, 4)));
            }
        }
    }

    /**
     * creates a toolbar, add it, and return it
     */
    public JToolBar createToolBar() {
        lastToolbar = new JToolBar();
        lastToolbar.setFloatable(false);
        // Rollover gives the flat buttons a hover state; without it nothing tells the user
        // that the labels are clickable.
        lastToolbar.setRollover(true);
        lastToolbar.setOpaque(false);
        add(lastToolbar);
        updateDividers();
        return lastToolbar;
    }

    /**
     * returns the last toolbar which created with createToolbar()
     */
    public JToolBar getLastToolBar() {
        return lastToolbar;
    }
}
