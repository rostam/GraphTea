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
public class VertexCycleLengthComparator implements Comparator {
    public VertexCycleLengthComparator() {

    }

    public int compare(Object o1, Object o2) {
        Vertex v1 = (Vertex) o1;
        Vertex v2 = (Vertex) o2;
        int v1i1 = ((PathProperties) v1.getProp().obj).getFirstColor();
        int v1i2 = ((PathProperties) v1.getProp().obj).getSecondColor();
        Integer v1i = new Integer(v1i1 + v1i2);
        Integer v2i1 = ((PathProperties) v2.getProp().obj).getFirstColor();
        Integer v2i2 = ((PathProperties) v2.getProp().obj).getSecondColor();
        Integer v2i = new Integer(v2i1 + v2i2);

        return v1i.compareTo(v2i);

    }
}
