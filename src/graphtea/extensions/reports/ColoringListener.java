// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports;

public interface ColoringListener {

    /**
     * Fires whenever a coloring is found
     *
     * @param t the maximum color, so the set of colors will be {1, 2, ..., t}
     * @return whenever a coloring is found
     */
    boolean coloringFound(final int t);
}
