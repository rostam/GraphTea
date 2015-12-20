// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.core;

import graphtea.platform.StaticUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

/**
 * <b>BlackBoard is just like a blackboard. Anyone can write on anywhere of it, Any one can read anywhere of it,
 * and anyone can look for changes in anewhere of it. It's the environment of anyone, just like the air.</b>
 * <br/><br/>
 * Structurally BlackBoard is a listanable hashmap. So it makes a usefull environment in plugable applications.
 * here you don't have extension points. all you have is some data which stores in BlackBoard as a hash map,
 * and some events which occurs on changing of data. (normally most places in BlackBoard are event place holders)
 * <br/><br/>
 * BlackBoard is where plugins (and almost everything in the GraphTea ui) connect together. It is the only common thing between all plugins. So if you want to share some data between your plugins (or perhaps inside a plugin) you can safely use blackboard:
 * <p/>
 * //here you save your data
 * <p/>
 * blackboard.setData("mydata", data);
 * <p/>
 * //here you load it
 * <p/>
 * data = blackboard.getData("mydata");
 * <p/>
 * Also every place in blackboard is listenable, this means that you can listen to change of any data in the blackboard. suppose you want to listen to the change of "mydata"
 * <p/>
 * <pre>
 * blackboard.addListener("mydata", new Listener() {
 *      public void keyChanged(String key, Object value) {
 *          System.out.println(String.valueOf(value));
 *      }
 * });
 * </pre>
 * <p/>
 * so every time that some one set "mydata" on blackboard keyChanged will be called.
   <br>
 * <br/>
 * The difference between a NotifiableAttributeSet and a BlackBoard is that, NotifiableAttributeSet is designed
 * for a small set of attributes, so for example getAttributeListeners() will return all listeners of all attributes,
 * but BlackBoard is for a bigger set of attributes, and there you can give listeners for just one key at a time.
 *
 * @author rouzbeh Ebrahimi  some minor revisions, removing getEvent, ...
 * @author azin azadi
 *
 */
public class BlackBoard {
    private HashMap<String, Object> data = new HashMap<String, Object>();
    private HashMap<String, HashSet<Listener>> listeners = new HashMap<String, HashSet<Listener>>();
    private HashMap<String, Vector<Couple<Boolean, Listener>>> addRemoveAfterFiring = new HashMap<String, Vector<Couple<Boolean, Listener>>>();
    private HashMap<String, Integer> firingNames = new HashMap<>();

    /**
     * @param eventName
     * @param value
     * @see BlackBoard#setEvent(graphtea.platform.core.Event,Object)
     */
    public <T> T getData(String key) {
        return (T) data.get(key);
    }


    /**
     * @param key
     * @param value
     */
    public void setData(String key, Object value) {
        data.put(key, value);
        fireListeners(key, value);
    }

    public boolean contains(String key) {
        return data.containsKey(key);
    }

    /**
     * adds a listener to the Data , which when the data changed, will be notified
     *
     * @param key
     * @param listener
     */
    public void addListener(String key, Listener listener) {
        if (firingCount(key) == 0) {
            putEvent(listeners, key, listener);
        } else {
            putEventAfter(key, listener, true);
        }
    }

    private void putEvent(HashMap<String, HashSet<Listener>> _map, String key, Listener listener) {
        HashSet<Listener> notifiables = _map.get(key);
        if (notifiables == null) {
            notifiables = new HashSet<Listener>();
            _map.put(key, notifiables);
        }
        notifiables.add(listener);
    }

    private void putEventAfter(String key, Listener listener, boolean isAdded) {
        Vector<Couple<Boolean, Listener>> couples = addRemoveAfterFiring.get(key);
        if (couples == null) {
            couples = new Vector<Couple<Boolean, Listener>>();
            addRemoveAfterFiring.put(key, couples);
        }
        couples.add(new Couple<Boolean, Listener>(isAdded, listener));
    }

    /**
     * see addAttributeListener
     *
     * @param listener
     */
    public void removeListener(String key, Listener listener) {
        if (firingCount(key) == 0) {
            HashSet<Listener> notifiables = listeners.get(key);
            if (notifiables != null)
                notifiables.remove(listener);
        } else {
            putEventAfter(key, listener, false);
        }
    }

    public HashSet<Listener> getListeners(String key) {
        return listeners.get(key);
    }

    /**
     * @param key
     */
    protected void fireListeners(String key, Object newValue) {
        int fi = firingCount(key);
        firingNames.put(key, fi + 1);
        HashSet<Listener> notifiables = listeners.get(key);
        if (notifiables != null)
            for (Listener listener : notifiables)
                try {
                    listener.keyChanged(key, newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                    StaticUtils.addExceptiontoLog(e, this);
                }
//                }
        firingNames.put(key, fi);
        if (fi == 0) {
            Vector<Couple<Boolean, Listener>> couples = addRemoveAfterFiring.get(key);
            if (couples != null) {
                for (Couple<Boolean, Listener> couple : couples) {
                    if (couple.a) {
                        addListener(key, couple.b);
                    } else {
                        removeListener(key, couple.b);
                    }
                }
                couples.clear();
            }
//            HashSet<Listener> nn = removeAfterFiring.get(event);
//            if (nn != null) {
//                for (Listener _ : nn)
//                    removeListener(event, _);
//                nn.clear();
//            }
//            nn = addAfterFiring.get(event);
//            if (nn != null) {
//                for (Listener _ : nn)
//                    addListener(event, _);
//                nn.clear();
//            }
        }
    }

    //represents the number of threads that are firing the listeners of the given name
    private int firingCount(String name) {
        Integer fi = firingNames.get(name);
        if (fi == null)
            fi = 0;
        return fi;
    }

    //todo: agar vasat e fire kardan iek thread e dige set kard oon name ro chekar baiad kard?
    private class Couple<A, B> {
        public A a;
        public B b;

        public Couple(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }
}
