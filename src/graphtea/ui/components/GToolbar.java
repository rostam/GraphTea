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
import java.util.Comparator;
import java.util.Vector;

/**
 * @author azin azadi
 */
public class GToolbar extends JComponent {
    /**
     *
     */
    private static final long serialVersionUID = -7694324289573873831L;

    private JToolBar lastToolbar;

    Vector<pair> indices = new Vector<>();

    public Component addIndexed(JToolBar comp, int index) {
        pair o = new pair(comp, index);
        indices.add(o);
        Object[] a = indices.toArray();
//        Arrays.sort(a, (Comparator<? super Object>) new pair(null, null));
        for (int _index = 0; _index < a.length; _index++)
            if (a[_index] == o)
                return super.add(comp, _index);
        return super.add(comp, -1);
    }

    public GToolbar() {
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        fl.setHgap(0);
        fl.setVgap(0);
        setLayout(fl);
        setBorder(null);
//        setBorder(new LineBorder(Color.WHITE,1,true));
    }

    /**
     * creates a toolbar, add it, and return it
     */
    public JToolBar createToolBar() {
        lastToolbar = new JToolBar();
//        lastToolbar.setBorderPainted(false);
        lastToolbar.setFloatable(false);
        lastToolbar.setMargin(new Insets(0, 0, 0, 0));
        lastToolbar.setRollover(false);
        lastToolbar.setBorder(null);
        lastToolbar.setOpaque(true);
        add(lastToolbar);
        return lastToolbar;
    }

    /**
     * returns the last toolbar which created with createToolbar()
     */
    public JToolBar getLastToolBar() {
        return lastToolbar;
    }
}

class pair implements Comparator<pair> {
    private final JToolBar b;
    private final Integer index;

    public pair(JToolBar j, Integer index) {
        this.b = j;
        this.index = index;
    }

    public int compareTo(pair o) {
        return index.compareTo(o.index);
    }

    public int compare(pair o1, pair o2) {
        return o1.compareTo(o2);
    }
}