// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor.utils;

import graphtea.platform.attribute.NotifiableAttributeSet;
import graphtea.platform.attribute.NotifiableAttributeSetImpl;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.ui.AttributeSetView;
import graphtea.ui.PortableNotifiableAttributeSetImpl;
import graphtea.ui.components.gpropertyeditor.GPropertyEditor;
import graphtea.ui.components.utils.GAttrFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author Azin Azadi
 */
public class ObjectViewer implements ListSelectionListener {
    GPropertyEditor ped;

    public static ObjectViewer showObject(Object o) {
        ObjectViewer oo = new ObjectViewer();
        oo.Connect(o);
        return oo;
    }

    public void Connect(Object obj) {
        PortableNotifiableAttributeSetImpl a = Object2NotifiableAttributeSet(obj);
        GAttrFrame f = GAttrFrame.showEditDialog(a, false);
        ped = f.getPropertyEditor();
        JTable t = ped.getTable();
        t.getSelectionModel().addListSelectionListener(this);
    }

    boolean b = true;

    /**
     * calls when user click on one of the properties
     */
    public void valueChanged(ListSelectionEvent e) {
        if (b) {
            ListSelectionModel ta = (ListSelectionModel) e.getSource();
            int selRow = ta.getMinSelectionIndex();
            showObject(ped.getTable().getValueAt(selRow, 1));
        }
        b = !b;
    }

    private int index(int m) {
        if (Modifier.isFinal(m))
            return 2000;
        if (Modifier.isStatic(m))
            return 900;
        if (Modifier.isProtected(m))
            return 600;
        if (Modifier.isPrivate(m))
            return 500;
        if (Modifier.isPublic(m))
            return 400;
        return 2000;
    }

    private int classP(Member m, Object o) {
        try {
            if (m.getDeclaringClass() != o.getClass())
                return 50000;
        }
        catch (Exception e) {
            return 500000;
        }
        return 0;
    }

    private PortableNotifiableAttributeSetImpl Object2NotifiableAttributeSet(Object obj) {

        PortableNotifiableAttributeSetImpl a = new PortableNotifiableAttributeSetImpl();
        AttributeSetView x = a.getView();
        for (Field f : obj.getClass().getFields()) {
            int mod = f.getModifiers();
            try {
                Object o = f.get(obj);
                String name = f.getName();
                a.put(name, o);
                x.setIndex(name, index(mod) + classP(f, o));
//                if(!Modifier.isFinal(mod))

            } catch (IllegalAccessException e) {
                ExceptionHandler.catchException(e);
            }
        }
        for (Method m : obj.getClass().getMethods()) {
            if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
                try {
                    Object o = m.invoke(obj, new Object[]{});
                    int mod = m.getModifiers();
                    String name = m.getName();
                    name = name.substring(3);
                    a.put(name, o);
                    x.setIndex(name, index(mod) + classP(m, o) + 1000);
                } catch (IllegalAccessException e) {
                    ExceptionHandler.catchException(e);
                } catch (InvocationTargetException e) {
                    System.err.println(obj.getClass() + "." + m.getName() + " invoke exception");
//                    ExceptionHandler.catchException(e);
                }
            }
        }
        return a;
    }

    public NotifiableAttributeSetImpl getSortedNotifiableAttributeSet(NotifiableAttributeSet in) {
        Map<String, Object> atr = in.getAttrs();
        Set<String> keys = atr.keySet();
        Object[] o = keys.toArray();
        String k[] = new String[keys.size()];
        NotifiableAttributeSetImpl ret = new NotifiableAttributeSetImpl();
        for (int i = 0; i < k.length; i++) {
            k[i] = (String) o[i];
        }
        Arrays.sort(k);
        for (String aK : k) {
            ret.put(aK, atr.get(aK));
        }
        return ret;
    }
}
