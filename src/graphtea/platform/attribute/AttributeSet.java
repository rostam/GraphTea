// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.attribute;

import java.util.Map;

/**
 * The base interface for storing a set of attributes, it is very similar to a map.
 *
 * @author Azin Azadi
 */
public interface AttributeSet {

    /**
     * @return a unmodifiable copy of attributes in this object
     */
    Map<String, Object> getAttrs();


    void put(String name, Object value);

    Object get(String name);

}
