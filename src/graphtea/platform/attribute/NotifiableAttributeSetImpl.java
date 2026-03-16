// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.attribute;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Default implementation for the NotifiableAttributeSet
 *
 * @see graphtea.platform.attribute.NotifiableAttributeSet
 * @author Azin Azadi, Reza Mohammadi, Rouzbeh Ebrahimi
 */
public class NotifiableAttributeSetImpl extends AttributeSetImpl implements NotifiableAttributeSet {

    CopyOnWriteArrayList<AttributeListener> globalListeners = new CopyOnWriteArrayList<>();


    public void put(String name, Object value) {
        Object old = get(name);
        super.put(name, value);
        fireAttributeChange(getAttributeListeners(), name, old, value);
    }

    @Override
    public void clear() {
        for (String name : atr.keySet()) {
            fireAttributeChange(getAttributeListeners(), name, atr.get(name), null);
        }
        super.clear();
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
