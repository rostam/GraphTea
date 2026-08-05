// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * Pair.java
 *
 * Created on January 4, 2005, 2:37 AM
 */

package graphtea.library.util;

import java.io.Serializable;

/**
 * @author Omid
 */
public class Pair<First, Second> implements Serializable{
    public First first;
    public Second second;

    public Pair(First f, Second s) {
        first = f;
        second = s;
    }

}
