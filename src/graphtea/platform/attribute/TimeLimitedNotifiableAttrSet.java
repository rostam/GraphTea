// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.platform.attribute;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * A handy NotifiableSet which acts on any AttributeSet and converts it to a NotifiableAttributeSet,
 * This is done by checking the AttributeSet for changes in each (100) mili seconds.
 * changes on (T) input will take effect on next 100 milisecond,
 * using this class should be done with care, this class uses a thread
 * and checks the edge on each 100ms for any changes, so creating a lot of
 * instances of this class (for example for all edges of graph)
 * will take more and more cpu,
 * <p/>
 * try to create as few as possible instances of this class and call stop() when you don't need
 * it any more!
 * <p/>
 */
public class TimeLimitedNotifiableAttrSet<T extends AttributeSet> implements Runnable, NotifiableAttributeSet {
    private boolean started = false;
    private final long millis = 100;
    Thread thread;
    private final T inp;
    NotifiableAttributeSetImpl as = new NotifiableAttributeSetImpl();

    public TimeLimitedNotifiableAttrSet(T input) {
        this.inp = input;
        thread = new Thread(this);
    }

    /**
     * starts firinig listeners to this class
     */
    public void start() {
        started = true;
        thread.start();
    }

    /**
     * stops firing listeners
     */
    public void stop() {
        started = false;
    }

    /**
     * -> Thread
     */
    public void run() {
        Map<String, Object> old = inp.getAttrs();
        Map<String, Object> _new;
        while (started) {
            _new = inp.getAttrs();
            fireChange(old, _new);
            try {
                old = inp.getAttrs();
                Thread.sleep(millis);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void fireChange(Map<String, Object> _new, Map<String, Object> old) {
        for (Map.Entry<String, Object> e : old.entrySet()) {
            String key = e.getKey();
            Object value = e.getValue();
            if (value == null) {
                if (!(_new.get(key) == null && _new.containsKey(key))) {
                    inp.put(key, value);

                }
            } else {
                if (!value.equals(_new.get(key))) {
                    fireAttributeChange(getAttributeListeners(), key, old, value);
                    inp.put(key, value);
                    fireAttributeChange(getAttributeListeners(), key, old, value);
                }
            }
        }

    }

//-----   NotifiableAttributeSet Methods

    public Map<String, Object> getAttrs() {
        return inp.getAttrs();
    }

    public void put(String name, Object value) {
        Object old = get(name);
        inp.put(name, value);
        fireAttributeChange(getAttributeListeners(), name, old, value);
    }

    public Object get(String name) {
        return inp.get(name);
    }

    public void addAttributeListener(AttributeListener attributeListener) {
        as.addAttributeListener(attributeListener);
    }

    public Collection<AttributeListener> getAttributeListeners() {
        return as.getAttributeListeners();
    }

    public void removeAttributeListener(AttributeListener attributeListener) {
        as.removeAttributeListener(attributeListener);
    }

    public void fireAttributeChange(Collection<AttributeListener> listeners, String name, Object oldVal, Object newVal) {
        if (listeners != null) {
            for (AttributeListener l : listeners) {
                l.attributeUpdated(name, oldVal, newVal);
            }
        }
    }

}
