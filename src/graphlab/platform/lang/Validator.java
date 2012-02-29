// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.lang;

/**
 * The basic validator, used in attributes that needs to be validated every time they set.
 *
 * @see graphlab.platform.lang.BoundedInteger
 * @see graphlab.platform.lang.ArrayX
 * @author azin azadi
 */
public interface Validator<T> {
    public boolean isValid(T x);
}
