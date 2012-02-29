// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library;

/**
 * Wrapper for basic properties of an edge.
 *
 * @author Omid Aladini
 */
public class BaseEdgeProperties
        implements Cloneable {
    public int color;
    public int weight;
    public boolean mark;
    /**
     * You can store anything you want.
     */
    public Object obj;

    public BaseEdgeProperties(BaseEdgeProperties p) {
        color = p.color;
        weight = p.weight;
        mark = p.mark;
    }

    public BaseEdgeProperties(int color, int weight, boolean mark) {
        this.color = color;
        this.weight = weight;
        this.mark = mark;
    }

    /* (non-Javadoc)
      * @see java.lang.Object#equals(java.lang.Object)
      */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseEdgeProperties))
            return false;

        BaseEdgeProperties b = (BaseEdgeProperties) obj;

        return b.color == color && weight == b.weight && b.mark == mark;

    }

}
