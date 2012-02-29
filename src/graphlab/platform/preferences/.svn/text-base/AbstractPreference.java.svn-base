// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.preferences;

import graphlab.platform.attribute.AttributeListener;
import graphlab.platform.attribute.NotifiableAttributeSetImpl;
import graphlab.platform.lang.ArrayX;

import java.util.HashMap;

/**
 * @author Rouzbeh
 */
public abstract class AbstractPreference {
    public String preferenceName;
    public String displayName;
    public String category;


    public AbstractPreference(String name, Preferences pref, String category) {
        this.preferenceName = name;
        this.category = category;

        pref.putNewSetOfAttributes(this);
        defineAttributes(new HashMap<Object, ArrayX>());

    }

    public NotifiableAttributeSetImpl attributeSet = new NotifiableAttributeSetImpl();

    protected void putAttribute(String name, ArrayX values) {
        attributeSet.put(name, values);
    }

    protected void putAttribute(String name, Object value) {
        attributeSet.put(name, value);

    }

    protected <T> T getAttribute(String name) {
        return (T) attributeSet.get(name);

    }


    public abstract void defineAttributes(HashMap<Object, ArrayX> objectValues);


    public abstract void defineListeners(AttributeListener al);
}
