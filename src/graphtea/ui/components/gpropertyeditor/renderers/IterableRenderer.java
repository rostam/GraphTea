// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.components.gpropertyeditor.renderers;

import graphtea.ui.components.gpropertyeditor.GBasicCellRenderer;
import graphtea.ui.components.gpropertyeditor.GCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

/**
 * Renders any Iterable object( including vcollections, ectors, sets, ...)
 *
 * @author Azin Azadi
 */
public class IterableRenderer implements GBasicCellRenderer<Iterable> {
    public Component getRendererComponent(Iterable value) {
        GridLayout layout = new GridLayout();
        int n = 0;
        int w = Integer.MIN_VALUE;
        int h = 0;
//        final JPanel p = new JPanel(layout);
        Vector v = new Vector();
        for (Object o : value) {
            v.add(o);
            n++;
        }
        final JList ret = new JList(v);
        final GCellRenderer renderer = new GCellRenderer();
        ret.setCellRenderer(renderer);
        if (n > 0)
            layout.setRows(n);
        if (n == 1)
            return GCellRenderer.getRendererFor(v.get(0));
        else {
            ret.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int row = ret.getSelectedIndex();
                    if (row == -1)
                        return;
                    MouseListener[] mouseListeners = renderer.getLastCreatedRenderer(row).getMouseListeners();
                    if (mouseListeners != null) {
                        for (MouseListener ml : mouseListeners) {
                            ml.mouseClicked(e);
                        }
                    }
                }
            });
        }

        //pass the click events to the childs
        return new JScrollPane(ret);
    }
}
