// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor;


import graphtea.platform.StaticUtils;
import graphtea.ui.AttributeSetView;
import graphtea.ui.components.gpropertyeditor.editors.GStringEditor;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 1- inplace editing and outplace editing
 * @see graphtea.ui.components.gpropertyeditor.GPropertyEditor
 *
 * @author  Azin Azadi
 */
public class GCellEditor extends AbstractCellEditor implements TableCellEditor, EditingFinishedListener {

    /**
     *
     */
    private static final long serialVersionUID = -7943480654474872421L;
    protected static HashMap<Class, GBasicCellEditor> knownEditors = new HashMap<>();
    //current editor!
    private GBasicCellEditor editor;
    protected AttributeSetView atr;

    public static void registerEditor(Class clazz, GBasicCellEditor editor) {
        knownEditors.put(clazz, editor);
    }

    public GCellEditor() {
    }

    public void cancelCellEditing() {
        super.cancelCellEditing();
        if (lastEditor != null)
            lastEditor.cancelEditing();
    }

    GBasicCellEditor lastEditor;

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        String name = atr.getNameAt(row);
        if (!atr.isEditable(name))
            return null;
        if (value == null) {
            return null;
        }

        editor = atr.getEditor(name);
        if (editor == null) {
            editor = getEditorFor(value);
            if (editor == null)
                return null;
        }

        editor.setEditingFinishedListener(this);
        return editor.getEditorComponent(value);
    }

    private static ObjectEditor oe = new ObjectEditor();

    /**
     * gets an editor for the given object, the editor should be registered before,...
     * several editors are registered as default
     */
    public static GBasicCellEditor getEditorFor(Object value) {
        GBasicCellEditor editor = null;
        Class c = value.getClass();
        //search super classes
        while (editor == null && c != Object.class) {
            editor = knownEditors.get(c);
            c = c.getSuperclass();
        }
        if (editor == null) {
            //search implementing interfaces
            Class cc[] = value.getClass().getInterfaces();
            for (int i = 0; i < cc.length && editor == null; i++)
                editor = knownEditors.get(cc[i]);
        }
        if (editor == null) {
            //no editor was defiend for this Class
            //take the last chance
            try {
                if (StaticUtils.fromString(value.getClass().getName(), value.toString()) != null) {
                    return oe;   //return oe if it can be reconstructed from a string in StaticUtils
                }
            } catch (Exception e) {
                return null;
            }
        }
        return editor;
    }

    public Object getCellEditorValue() {
        return editor.getEditorValue();
    }

    public void editingFinished(Object editorValue) {
        stopCellEditing();
    }

    void setAtrView(AttributeSetView attribute) {
        this.atr = attribute;
    }

    /**
     * @return known editors as an unmodifiable map
     */
    public static Map<Class, GBasicCellEditor> getKnownEditors(){
        return Collections.unmodifiableMap(knownEditors);
    }
}

class ObjectEditor extends GStringEditor {
    Class clazz;

    public Component getEditorComponent(Object value) {
        clazz = value.getClass();
        return super.getEditorComponent(value);
    }

    protected void finishEdit() {
        jt.setText(getEditorValue() + "");
        super.finishEdit();
    }

    public Object getEditorValue() {
        String s = super.getEditorValue() + "";

        Object o;
        try {
            o = StaticUtils.fromString(clazz.getName(), s);
        } catch (Exception e) {
            return StaticUtils.fromString(clazz.getName(), initVal);
        }
        return o;
    }
}