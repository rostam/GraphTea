// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.platform.lang;

/**
 * provides a "From String" for a given String, which means to create an object
 * from it's toString string.
 *
 * This is GraphTea's standard way of loading objects from strings.
 */
public interface FromStringProvider<t> {
    public t fromString(String toString);
}
