// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.old;

import java.awt.*;

/**
 * an arrow which is a0 polygon
 */
public class PolygonArrow implements Arrow {
    /**
     *
     */
    private static final long serialVersionUID = -17351484519616222L;
    private final Polygon p;
    private final String name;

    public PolygonArrow(Polygon p, String name) {
        this.p = p;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    /**
     * paints the arrow on the g which the size of (w,h), the w & h are disabled for this version
     */
    public void paintArrow(Graphics g, int w, int h) {
//        double sx=w/p.getBounds().getWidth();
//        double sy=h/p.getBounds().getHeight();

        Graphics2D gg = ((Graphics2D) g);

//        gg.scale(sx, sy);
        gg.fill(p);
//      u should scale it back if scaled it
    }

    public Rectangle getBounds() {
        return p.getBounds();
    }
}
