// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.boundcheck.forall;

import graphtea.library.util.Pair;
import graphtea.platform.attribute.AttributeListener;
import graphtea.platform.attribute.AttributeSet;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.ui.PortableNotifiableAttributeSetImpl;
import graphtea.ui.components.utils.GAttrFrame;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * this class provides the ability to show and edit the parametr of a
 * parametrizable object with a property editor
 *
 * @author azin azadi
 */
public class ForAllParameterShower implements AttributeListener {
    private Object o;

    public ForAllParameterShower(Object o) {
        this.o = o;
    }

    /**
     * show all the fields of the object which have setter and getter in a property editor in runtime
     * so you can change them easily
     *
     * @param o
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
                    System.out.println(m.getName() + "," + name);

                    Method getter = null;
                    try {
                        getter = o.getClass().getMethod("get" + name, new Class[0]);
                    } catch (Exception e) {
//                    ExceptionHandler.catchException(e);  //To change body of catch statement use File | Settings | File Templates.
                    }
                    if (getter != null) {
                        p.put(name, getter.invoke(o, (Object[]) new Class[0]));
                    }
                }

            }
            GAttrFrame.showEditDialog(p, false);
        } catch (Exception e) {
            ExceptionHandler.catchException(e);  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public Vector<Pair<Integer, Integer>> xshow(Object o) {
        try {
            this.o = o;
            Vector<JTextField> v = new Vector<>();
            JPanel myPanel = new JPanel();

            for (Field f : o.getClass().getFields()) {
                Parameter anot = f.getAnnotation(Parameter.class);
                if (anot != null) {
                    JTextField xField = new JTextField(5);
                    v.add(xField);
                    myPanel.add(new JLabel("x:"));
                    myPanel.add(xField);
                }
            }
            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Please enter bound values", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Vector<Pair<Integer,Integer>> res = new Vector<>();
                for(int i=0;i<v.size();i++) {
                    Scanner sc = new Scanner(v.get(i).getText());
                    sc.useDelimiter(":");
                    res.add(new Pair<Integer, Integer>(
                            Integer.parseInt(sc.next()),
                            Integer.parseInt(sc.next())));
                    return res;
                }
            }
        }
        catch (Exception e) {
            ExceptionHandler.catchException(e);
        }
        return new Vector<>();
    }

    public Vector<Pair<Integer, Integer>> show(Parametrizable p) {
        //boolean finished = false;
        //while (!finished) {
            return xshow(p);
            //if (!b) {
            //    return false;   //cancelled
            //}
            //String s = p.checkParameters();
            //if (s == null)
            //    finished = true;
            //else
            //    JOptionPane.showMessageDialog(null, s);
        //}
        //while (p.checkParameters() != null) ;
        //return new Vector<>();
    }

    /**
     * if f is an AttributeSet adds its members to the p
     *
     * @param p
     * @param f
     * @param o
     * @param name
     * @param desc
     * @throws IllegalAccessException
     */
    private void addField(PortableNotifiableAttributeSetImpl p, Field f, Object o, String name, String desc) throws IllegalAccessException {
        if (AttributeSet.class.isAssignableFrom(f.getType())) {
            if (f.get(o) instanceof AttributeSet) {
                AttributeSet as = (AttributeSet) f.get(o);
                for (Map.Entry<String, Object> x : ((Set<Map.Entry>) as.getAttrs().entrySet())) {
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

    HashMap<String, String> names = new HashMap<String, String>();

    public void attributeUpdated(String name, Object oldVal, Object newVal) {
        System.out.println(newVal);
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
        } catch (IllegalAccessException e) {
//            ExceptionHandler.catchException(e);
        } catch (NoSuchFieldException e) {
//            ExceptionHandler.catchException(e);
        }
    }
}
