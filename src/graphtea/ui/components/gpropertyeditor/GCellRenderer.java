// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor;

import graphtea.ui.AttributeSetView;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * this class is a part of gpropertyeditor, which creates a property editor with a jtable
 * so it is implementation os TableCellRenderer. it returns a renderer for a cell by the
 * "type of Object" that is stored in that cell.
 * u can register for each class a renderer by the method registerRenderer.
 *
 * @see graphtea.ui.components.gpropertyeditor.GPropertyEditor
 *
 * @author azin azadi
 */
public class GCellRenderer implements TableCellRenderer, ListCellRenderer {

    public static HashMap<Class, GBasicCellRenderer> knownRenderers = new HashMap<>();
    private AttributeSetView attributes;
    //private int lastRow,lastColumn;
    private final HashMap<Integer, Component> lastRenderers = new HashMap<>();

    public static final Color SELECTED_COLOR = Color.red;
    /**
     * returns the last generated renderer by this object for the given row,
     * note that this is not a very safe method, It may return a generated renderer for another table,
     * so use it carefully
     */
    public Component getLastCreatedRenderer(int row) {
        return lastRenderers.get(row);
    }


    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String name = attributes.getNameAt(row);
        GBasicCellRenderer ren = attributes.getrenderer(name);
        Component rend = (ren != null ? ren.getRendererComponent(value) : getRendererFor(value));
        lastRenderers.put(row, rend);
        if (rend == null) {
            return null;
        }
        int h = (int) rend.getPreferredSize().getHeight();
        if (h > 5)
            setRowHeight(table, row, h);
        if (isSelected) {
            rend.setForeground(SELECTED_COLOR);
        }
        return rend;
    }

    private void setRowHeight(JTable table, int row, int h) {
        if (table.getRowHeight(row) != h)
            table.setRowHeight(row, h);
    }

    /**
     * return a renderer for he object from previously registered renderers
     */
    public static Component getRendererFor(Object value) {
        //todo: value=null => bug
        //  lastRow=row;lastColumn=column;
        if (value == null)
            return null;

        //try to find the best renderer for value
        GBasicCellRenderer renderer = null;
        Class<?> valueClass = value.getClass();
        Class c = valueClass;
        //search super classes
        while (renderer == null && c != Object.class) {
            renderer = knownRenderers.get(c);
            c = c.getSuperclass();
        }
        if (renderer == null)
            for (Class cc : c.getInterfaces())
                if ((renderer = knownRenderers.get(cc)) != null) break;
        if (renderer == null) {
            //search implementing interfaces
            Set<Class> keys = knownRenderers.keySet();
//            Class cc[] = value.getClass().getInterfaces();
            for (Class cc : keys) {
                if (cc.isAssignableFrom(valueClass)) {
                    renderer = knownRenderers.get(cc);
                    break;
                }
            }
        }
        if (renderer == null)
            return new JLabel(value + "");
        else return renderer.getRendererComponent(value);
    }

    /**
     * register the "renderer" for the "clazz". so after this, calls to getTableCellRendererComponent method will
     * return this renderer if the value passed to that method has the same class with "clazz"
     */
    public static void registerRenderer(Class clazz, GBasicCellRenderer renderer) {
//        renderers.put(clazz, renderer);
        knownRenderers.put(clazz, renderer);
    }

    public void setAtrView(AttributeSetView attributes) {

        this.attributes = attributes;
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component rendererFor = getRendererFor(value);
        if (isSelected) {
            rendererFor.setForeground(Color.red);
            rendererFor.setBackground(Color.blue);
        }
        lastRenderers.put(index, rendererFor);
        return rendererFor;
    }

    /**
     * @return known editors as an unmodifiable map
     */
    public static Map<Class, GBasicCellRenderer> getKnownRenderers(){
        return Collections.unmodifiableMap(knownRenderers);
    }

}
