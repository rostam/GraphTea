// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports;

import graphtea.library.BaseVertex;

import java.util.ArrayDeque;

/**
 * @author Azin Azadi
 * @see graphtea.extensions.reports.Partitioner
 */
public interface SubSetListener {
    boolean subsetFound(int t, ArrayDeque<BaseVertex> complement, ArrayDeque<BaseVertex> set);
}
