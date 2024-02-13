// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.preferences;

import graphtea.platform.attribute.AttributeListener;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.preferences.lastsettings.UserModifiableProperty;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Rouzbeh Ebrahimi
 */
public class GraphPreferences extends AbstractPreference implements AttributeListener {
    public Object oneInstance;
    public HashSet<Object> oneInstances;
    public static Preferences pref;

    public GraphPreferences(String name, Object oneInstance, String category) {
        super(name, pref, category);
        oneInstances = new HashSet<>();
        this.oneInstance = oneInstance;
        oneInstances.add(oneInstance);
        defineListeners(this);
    }

    public GraphPreferences(String name, HashSet<Object> oneInstances, String category) {
        super(name, pref, category);
        this.oneInstances = oneInstances;
        defineListeners(this);
    }

    public void defineAttributes(HashMap<Object, ArrayX> objectValues) {

        for (Object o : objectValues.keySet()) {
            putAttribute(o.toString(), objectValues.get(o));
        }

    }

    public void defineAttributes(HashMap<Object, Object> objectValues, boolean t) {

        for (Object o : objectValues.keySet()) {
            putAttribute(o.toString(), objectValues.get(o));
        }
    }

    public void defineMultipleAttributes(HashMap<Object, HashMap<Object, ArrayX>> map) {

        for (Object o : map.keySet()) {
            HashMap<Object, ArrayX> hashMap = map.get(o);
            for (Object fields : hashMap.keySet()) {
                putAttribute(o.toString() + "*" + fields.toString(), hashMap.get(fields));

            }
        }
    }

    public void addObject(Object o) {
        oneInstances.add(o);
    }

    public void defineListeners(AttributeListener al) {

        attributeSet.addAttributeListener(al);
    }

    public void attributeUpdated(String name, Object oldVal, Object newVal) {
        for (Object o : oneInstances) {
            for (Field f : o.getClass().getFields()) {
                UserModifiableProperty anot = f.
                        getAnnotation(UserModifiableProperty.class);
                if (anot != null && (anot.displayName()).equals(name)) {
                    try {
//                            o.getClass().getDeclaredField(f.getName()).set(o, newVal);
                        f.set(o, newVal);
                    } catch (IllegalAccessException e) {
                        ExceptionHandler.catchException(e);
                    }
                }
            }
        }
    }
}
