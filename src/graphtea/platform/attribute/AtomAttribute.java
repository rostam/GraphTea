// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.attribute;

import graphtea.platform.lang.Validator;

import java.io.Serializable;

/**
 * the place holder for a single validable attribute
 *
 * @author Azin Azadi
 */
public interface AtomAttribute<T> extends Validator<T>, Serializable {
    public boolean setValue(T value);

    public T getValue();
}
