// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.graph;

import graphtea.platform.StaticUtils;
import graphtea.platform.lang.FromStringProvider;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class GraphPoint extends Point2D.Double implements Serializable, FromStringProvider {
    static {
        StaticUtils.setFromStringProvider(GraphPoint.class.getName(), new GraphPoint());
    }

    private static final long serialVersionUID = -1000000001L;

    public static GraphPoint add(GraphPoint p1, GraphPoint p2) {
        return new GraphPoint(p1.getX() + p2.getX(), p1.getY() + p2.getY());
    }

    public static GraphPoint sub(GraphPoint p1, GraphPoint p2) {
        return new GraphPoint(p1.getX() - p2.getX(), p1.getY() - p2.getY());
    }

    public static GraphPoint div(GraphPoint gp, double scalar) {
        return new GraphPoint(gp.getX()/scalar, gp.getY()/scalar);
    }

    public static GraphPoint mul(GraphPoint gp, double scalar) {
        return new GraphPoint(gp.getX()*scalar, gp.getY()*scalar);
    }

    public static GraphPoint normalise(GraphPoint gp) {
        return GraphPoint.div(gp, gp.norm());
    }

    public GraphPoint() {
        super();
    }

    public GraphPoint(GraphPoint p) {
        this.x = p.x;
        this.y = p.y;
    }

    public GraphPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * multiplies x and y by p (x=x*p, y=y*p)
     *
     * @param p
     */
    public void multiply(double p) {
        x = x * p;
        y = y * p;
    }

    /**
     * adds this with dp (x=x+dp.x, y=y+dp.y)
     *
     * @param dp
     */
    public void add(GraphPoint dp) {
        x = x + dp.x;
        y = y + dp.y;
    }

    /**
     * adds this with dp (x=x+dx.x, y=y+dy)
     *
     * @return this
     */
    public GraphPoint add(double dx, double dy) {
        x = x + dx;
        y = y + dy;
        return this;
    }

    public double norm() {
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }


    public String toString() {
        return x + " , " + y;
    }

    public double distance(GraphPoint pt) {
        double PX = pt.getX() - this.getX();
        double PY = pt.getY() - this.getY();
        return Math.sqrt(PX * PX + PY * PY);
    }

    public GraphPoint fromString(String data) {
        String s1 = data.substring(0,data.indexOf(",")-1);
        String s2= data.substring(data.indexOf(",")+1);
        return new GraphPoint(java.lang.Double.parseDouble(s1),
                java.lang.Double.parseDouble(s2));
    }
}
