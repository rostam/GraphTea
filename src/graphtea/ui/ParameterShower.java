// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui;

import graphtea.platform.attribute.AttributeListener;
import graphtea.platform.attribute.AttributeSet;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.Extension;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.ui.components.utils.GAttrFrame;

import javax.swing.*;
import java.awt.KeyboardFocusManager;
import java.lang.reflect.Field;
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

    public boolean xshow(Object o) {
        try {
            this.o = o;
            PortableNotifiableAttributeSetImpl p = new PortableNotifiableAttributeSetImpl();
            p.addAttributeListener(this);
            for (Field f : o.getClass().getFields()) {
                Parameter anot = f.getAnnotation(Parameter.class);
                if (anot != null) {
                    addField(p, f, o, anot.name(), anot.description());
                }
            }
            String title = o instanceof Extension extension ? extension.getName() : "Parameters";
            String intro = o instanceof Extension extension ? extension.getDescription() : null;
            if (intro != null && intro.strip().equalsIgnoreCase(title.strip())) {
                // Many extensions describe themselves with their own name; the title says it already.
                intro = null;
            }
            return GAttrFrame.showEditDialog(p, true, title, intro).getReturnStatus();
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
                JOptionPane.showMessageDialog(KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow(),
                        s, "Check the parameters", JOptionPane.WARNING_MESSAGE);
        }
        // A busy-wait on checkParameters() used to follow here; it could only ever spin
        // forever, and never did anything useful when it did not.
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
            if (!name.isEmpty())
                p.getView().setDisplayName(f.getName(), name);
            p.getView().setDescription(f.getName(), desc);
        }
    }

    public void attributeUpdated(String name, Object oldVal, Object newVal) {
        applyParameter(o, name, newVal);
    }

    /**
     * Writes an edited value back to the parameter field it came from.
     *
     * @param target the object whose parameters are being edited
     * @param name   the attribute name: a field name, or "atrset.field.key" for an entry of an
     *               {@link AttributeSet} field
     * @param value  the new value
     */
    static void applyParameter(Object target, String name, Object value) {
        try {
            if (name.startsWith("atrset")) {
                String rest = name.substring(7);
                String atn = rest.substring(0, rest.indexOf("."));
                String fn = rest.substring(rest.indexOf(".") + 1);
                if (target.getClass().getField(atn).get(target) instanceof AttributeSet attributeSet) {
                    attributeSet.put(fn, value);
                }
            } else {
                // getField, not getDeclaredField: the dialog lists public fields including
                // inherited ones (xshow uses getFields), and getDeclaredField cannot see those.
                // Every parameter declared in a base class, such as the graph names of the
                // product operators, was silently left at its default however it was edited.
                target.getClass().getField(name).set(target, value);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            ExceptionHandler.catchExceptionQuietly(e);
        }
    }
}
