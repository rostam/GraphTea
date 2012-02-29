// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library;

/**
 * Wrapper for basic properties of a vertex.
 * @author Omid Aladini
 */

/**
 * @author Omid
 */
public class BaseVertexProperties
        implements Cloneable {
    public int color;
    public boolean mark;
    /**
     * You can store anything you want.
     */
    public Object obj;

    public BaseVertexProperties(BaseVertexProperties p) {
        color = p.color;
        mark = p.mark;
    }

    public BaseVertexProperties(int color, boolean mark) {
        this.color = color;
        this.mark = mark;
    }

    /* (non-Javadoc)
      * @see java.lang.Object#equals(java.lang.Object)
      */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseVertexProperties))
            return false;

        BaseVertexProperties b = (BaseVertexProperties) obj;

        return b.color == color;

    }
}
