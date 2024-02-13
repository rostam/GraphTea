// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.graph;

import graphtea.platform.StaticUtils;
import graphtea.platform.lang.FromStringProvider;

import java.io.Serializable;

public class GPoint implements Serializable, FromStringProvider<GPoint> {
    static {
        StaticUtils.setFromStringProvider(GPoint.class.getName(), new GPoint());
    }

    public double x,y;

    public double getX() {return x;}
    public double getY() {return y;}

    private static final long serialVersionUID = -1000000001L;

    public static GPoint add(GPoint p1, GPoint p2) {
        return new GPoint(p1.getX() + p2.getX(), p1.getY() + p2.getY());
    }

    public static GPoint sub(GPoint p1, GPoint p2) {
        return new GPoint(p1.getX() - p2.getX(), p1.getY() - p2.getY());
    }

    public static GPoint div(GPoint gp, double scalar) {
        return new GPoint(gp.getX()/scalar, gp.getY()/scalar);
    }

    public static GPoint mul(GPoint gp, double scalar) {
        return new GPoint(gp.getX()*scalar, gp.getY()*scalar);
    }

    public static GPoint normalise(GPoint gp) {
        return GPoint.div(gp, gp.norm());
    }

    public GPoint() {
        super();
    }

    public GPoint(GPoint p) {
        this.x = p.x;
        this.y = p.y;
    }

    public GPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * multiplies x and y by p (x=x*p, y=y*p)
     *
     * @param p The coefficient
     */
    public void multiply(double p) {
        x = x * p;
        y = y * p;
    }

    /**
     * adds this with dp (x=x+dp.x, y=y+dp.y)
     *
     * @param dp The graph point to be added
     */
    public void add(GPoint dp) {
        x = x + dp.x;
        y = y + dp.y;
    }

    /**
     * adds this with dp (x=x+dx.x, y=y+dy)
     *
     * @return this
     */
    public GPoint add(double dx, double dy) {
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

    public double distance(GPoint pt) {
        double PX = pt.getX() - this.getX();
        double PY = pt.getY() - this.getY();
        return Math.sqrt(PX * PX + PY * PY);
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return distance(new GPoint(x1,y1),new GPoint(x2,y2));
    }

    public static double distance(GPoint p1, GPoint p2) {
        double PX = p1.getX() - p2.getX();
        double PY = p1.getY() - p2.getY();
        return Math.sqrt(PX * PX + PY * PY);
    }

    public GPoint fromString(String data) {
        String s1 = data.substring(0,data.indexOf(",")-1);
        String s2= data.substring(data.indexOf(",")+1);
        return new GPoint(java.lang.Double.parseDouble(s1),
                java.lang.Double.parseDouble(s2));
    }
}
