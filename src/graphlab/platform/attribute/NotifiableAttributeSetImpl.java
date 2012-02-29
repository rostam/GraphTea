// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.attribute;

import java.util.Collection;
import java.util.Vector;

/**
 * Default implementation for the NotifiableAttributeSet
 *
 * @see graphlab.platform.attribute.NotifiableAttributeSet
 * @author Azin Azadi, Reza Mohammadi, Rouzbeh Ebrahimi
 */
public class NotifiableAttributeSetImpl extends AttributeSetImpl implements NotifiableAttributeSet {

    Vector<AttributeListener> globalListeners = new Vector<AttributeListener>();


    public void put(String name, Object value) {
        if (name == null) {
            throw new RuntimeException("key=null" + value);
        }
        Object old = atr.put(name, value);
//        Collection<AttributeListener> listeners = notifiableAttributeSet.getAttributeListeners(name);
        fireAttributeChange(getAttributeListeners(), name, old, value);

    }

    public Object get(String name) {
        return super.get(name);
    }

    public void addAttributeListener(AttributeListener attributeListener) {
        globalListeners.add(attributeListener);
    }

    public Collection<AttributeListener> getAttributeListeners() {
        return globalListeners;
    }

    public void removeAttributeListener(AttributeListener x) {
        globalListeners.remove(x);
    }

    public void fireAttributeChange(Collection<AttributeListener> listeners, String name, Object oldVal, Object newVal) {
        if (listeners != null) {
            for (AttributeListener l : listeners) {
                l.attributeUpdated(name, oldVal, newVal);
            }
        }
    }
}
