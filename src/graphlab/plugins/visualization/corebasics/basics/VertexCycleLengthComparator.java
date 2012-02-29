// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.visualization.corebasics.basics;

import graphlab.graph.graph.VertexModel;

import java.util.Comparator;

/**
 * @author Rouzbeh Ebrahimi
 */
public class VertexCycleLengthComparator implements Comparator {
    public VertexCycleLengthComparator() {

    }

    public int compare(Object o1, Object o2) {
        VertexModel v1 = (VertexModel) o1;
        VertexModel v2 = (VertexModel) o2;
        int v1i1 = ((PathProperties) v1.getProp().obj).getFirstColor();
        int v1i2 = ((PathProperties) v1.getProp().obj).getSecondColor();
        Integer v1i = new Integer(v1i1 + v1i2);
        Integer v2i1 = ((PathProperties) v2.getProp().obj).getFirstColor();
        Integer v2i2 = ((PathProperties) v2.getProp().obj).getSecondColor();
        Integer v2i = new Integer(v2i1 + v2i2);

        return v1i.compareTo(v2i);

    }
}
