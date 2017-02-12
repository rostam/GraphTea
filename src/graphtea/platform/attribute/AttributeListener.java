// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.attribute;

/**
 * The base interface for listening to the changes of attributes in an attribute set
 * @author Reza Mohammadi
 */
public interface AttributeListener {

    void attributeUpdated(String name, Object oldVal, Object newVal);
}
