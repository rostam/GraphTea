// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.visualization.localsfvis;

/**
 * @author Rouzbeh Ebrahimi
 * Email: ruzbehus@gmail.com
 */
class pair implements Comparable<pair> {
    int node;
    double d;

    public pair(int node, double d) {
        this.node = node;
        this.d = d;
    }

    public int compareTo(pair o) {
        return Double.compare(d, o.d);
    }
}
