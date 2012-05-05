// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.attribute;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author azin azadi
 */
public class AttributeSetImpl implements AttributeSet {
    protected HashMap<String, Object> atr = new HashMap<String, Object>();

    public AttributeSetImpl() {
        //Nothing to do!
    }


    public Map<String, Object> getAttrs() {
        return Collections.unmodifiableMap(atr);
    }

    public void put(String name, Object value) {
        if (name == null) {
            throw new RuntimeException("key=null" + value);
        }
        atr.put(name, value);
    }


    public Object get(String name) {
        return atr.get(name);
    }

    public boolean contains(String name) {
        return atr.containsKey(name);
    }


    /**
     * clears all attributes in this set, this means that after calling this method the set of attributes will be empty
     */
    public void clear() {
        atr = new HashMap<String, Object>();
    }
}