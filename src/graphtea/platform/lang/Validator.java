// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.lang;

/**
 * The basic validator, used in attributes that needs to be validated every time they set.
 *
 * @see graphtea.platform.lang.BoundedInteger
 * @see graphtea.platform.lang.ArrayX
 * @author azin azadi
 */
public interface Validator<T> {
    boolean isValid(T x);
}
