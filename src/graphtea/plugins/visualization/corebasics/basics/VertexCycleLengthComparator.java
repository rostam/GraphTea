// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.corebasics.basics;

import graphtea.graph.graph.Vertex;

import java.util.Comparator;

/**
 * @author Rouzbeh Ebrahimi
 */
public class VertexCycleLengthComparator implements Comparator<Vertex> {
    public VertexCycleLengthComparator() {
    }

    public int compare(Vertex v1, Vertex v2) {
        int v1i = ((PathProperties) v1.getProp().obj).getFirstColor()
                + ((PathProperties) v1.getProp().obj).getSecondColor();
        int v2i = ((PathProperties) v2.getProp().obj).getFirstColor()
                + ((PathProperties) v2.getProp().obj).getSecondColor();
        return Integer.compare(v1i, v2i);
    }
}
