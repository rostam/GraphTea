// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library;

/**
 * Wrapper for basic properties of a vertex.
 * @author Omid Aladini
 */
public class BaseVertexProperties
        implements Cloneable {
    public int color;
    public boolean mark;

    public static boolean isLabelColorImp = false;
    /**
     * You can store anything you want.
     */
    public Object obj;

    public BaseVertexProperties(BaseVertexProperties p) {
        color = p.color;
        mark = p.mark;
        obj = p.obj;
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
