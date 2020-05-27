// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui;

import graphtea.platform.attribute.AttributeListener;
import graphtea.platform.attribute.AttributeSet;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.ui.components.utils.GAttrFrame;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * this class provides the ability to show and edit the parametr of a
 * parametrizable object with a property editor
 *
 * @author azin azadi
 */
public class ParameterShower implements AttributeListener {
    private Object o;

    /**
     * show all the fields of the object which have setter and getter in a property editor in runtime
     * so you can change them easily
     *
     * @param o The object to be shown
     */
    public void show(Object o) {
        try {
            this.o = o;
            PortableNotifiableAttributeSetImpl p = new PortableNotifiableAttributeSetImpl();
            p.addAttributeListener(this);
            for (Method m : o.getClass().getMethods()) {
                String name = m.getName();
                if (name.startsWith("set")) {
                    name = name.substring(3);
                    Method getter = null;
                    try {
                        getter = o.getClass().getMethod("get" + name);
                    } catch (Exception e) {
//                    ExceptionHandler.catchException(e);  //To change body of catch statement use File | Settings | File Templates.
                    }
                    if (getter != null) {
                        p.put(name, getter.invoke(o, new Class[0]));
                    }
                }

            }
            GAttrFrame.showEditDialog(p, false);
        } catch (Exception e) {
            ExceptionHandler.catchException(e);  //To change body of catch statement use File | Settings | File Templates.
        }

    }

//    public void attributeUpdated(String name, Object oldVal, Object newVal) {
//        if (o == null)
//            return;
//        try {
//            Method m = o.getClass().getMethod("set" + name, newVal.getClass());
//            m.invoke(o, newVal);
////            Field f = o.getClass().getField(name);
////            f.set(o, newVal);
//        } catch (Exception e) {
//            System.err.println("err on " + name);
////            ExceptionHandler.catchException(e);  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }

    public boolean xshow(Object o) {
        try {
            this.o = o;
            PortableNotifiableAttributeSetImpl p = new PortableNotifiableAttributeSetImpl();
            p.addAttributeListener(this);
            for (Field f : o.getClass().getFields()) {
                Parameter anot = f.getAnnotation(Parameter.class);
//                System.out.println(anot);
//                System.out.println(f.getName());
                if (anot != null) {
                    addField(p, f, o, anot.name(), anot.description());
                }
            }
            return GAttrFrame.showEditDialog(p, true).getReturnStatus();
        }
        catch (Exception e) {
            ExceptionHandler.catchException(e);
        }
        return false;
    }

    public boolean show(Parametrizable p) {
        boolean finished = false;
        while (!finished) {
            boolean b = xshow(p);
            if (!b) {
                return false;   //cancelled
            }
            String s = p.checkParameters();
            if (s == null)
                finished = true;
            else
                JOptionPane.showMessageDialog(null, s);
        }
        while (p.checkParameters() != null) ;
        return true;
    }

    /**
     * if f is an AttributeSet adds its members to the p
     *
     * @param p The attr set
     * @param f The field
     * @param o The object
     * @param name The name
     * @param desc The description
     * @throws IllegalAccessException Illegal access exception
     */
    private void addField(PortableNotifiableAttributeSetImpl p, Field f, Object o, String name, String desc) throws IllegalAccessException {
        if (AttributeSet.class.isAssignableFrom(f.getType())) {
            if (f.get(o) instanceof AttributeSet) {
                AttributeSet as = (AttributeSet) f.get(o);
                for (Map.Entry<String, Object> x : ((Set<Map.Entry<String, Object>>) as.getAttrs().entrySet())) {
                    String nam = "atrset." + f.getName() + "." + x.getKey();
                    p.put(nam, x.getValue());
                    p.getView().setDisplayName(nam, x.getKey());
                }
            }
        } else {
            p.put(f.getName(), f.get(o));
            if (!name.equals(""))
                p.getView().setDisplayName(f.getName(), name);
            p.getView().setDescription(f.getName(), desc);
        }
    }

    HashMap<String, String> names = new HashMap<>();

    public void attributeUpdated(String name, Object oldVal, Object newVal) {
        try {
            if (name.startsWith("atrset")) {
                name = name.substring(7);
                String atn = name.substring(0, name.indexOf("."));
                String fn = name.substring(name.indexOf(".") + 1);
                Object field = o.getClass().getDeclaredField(atn).get(o);
                if (field instanceof AttributeSet) {
                    AttributeSet attributeSet = (AttributeSet) field;
                    attributeSet.put(fn, newVal);
                }
            } else {
                o.getClass().getDeclaredField(name).set(o, newVal);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
//            ExceptionHandler.catchException(e);
        }
    }
}
