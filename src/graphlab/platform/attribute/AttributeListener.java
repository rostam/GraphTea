// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.attribute;

/**
 * The base interface for listening to the changes of attributes in an attribute set
 * @author Reza Mohammadi
 */
public interface AttributeListener {

    public void attributeUpdated(String name, Object oldVal, Object newVal);
}
