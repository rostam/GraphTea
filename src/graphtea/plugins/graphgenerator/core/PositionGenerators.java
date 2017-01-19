// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.graphgenerator.core;


import graphtea.graph.graph.GPoint;

import java.awt.*;

/**
 * User: root
 */
public class PositionGenerators {
    public static Point[] line(int xOffset, int yOffset, int w, int h, int n) {
        Point[] ret = new Point[n];
        int dx = w / n;
        int dy = h / n;
        for (int i = 0; i < n; i++) {
            ret[i] = new Point(xOffset + i * dx, yOffset + i * dy);
        }
        return ret;
    }

    public static Point[] circle(int xOffset, int yOffset, int w, int h, int n) {
        Point[] ret = new Point[n];
        w = w / 2;
        h = h / 2;
        w -= xOffset;
        h -= yOffset;
        for (int i = 0; i < n; i++) {
            double deg = 2 * Math.PI / n * i;
            double x = Math.sin(deg);
            double y = Math.cos(deg);
            x *= w;
            y *= h;
            x += w;
            y += h;
            x += xOffset;
            y += yOffset;
            ret[i] = new Point((int) x, (int) y);
        }
        return ret;
    }

    public static Point[] circle(int xOffset, int yOffset, int w, int h, int n, double degree) {
        Point[] ret = new Point[n];
        w = w / 2;
        h = h / 2;
        w -= xOffset;
        h -= yOffset;
        for (int i = 0; i < n; i++) {
            double deg = 2 * Math.PI / n * i;
            deg += degree;
            double x = Math.sin(deg);
            double y = Math.cos(deg);
            x *= w;
            y *= h;
            x += w;
            y += h;
            x += xOffset;
            y += yOffset;
            ret[i] = new Point((int) x, (int) y);
        }
        return ret;
    }

    public static Point[] circle(int r, int x, int y, int n) {
        Point[] ret = circle(0, 0, r, r, n);
        shift(ret, x - r / 2, y - r / 2);
        return ret;
    }

    private static Point[] shift(Point[] input, int xOffset, int yOffset) {
        for (Point p : input) {
            p.x += xOffset;
            p.y += yOffset;
        }
        return input;
    }

    public static Point[] rotate(Point[] input, double degree) {

        for (Point p : input) {
            int x = (int) (p.x * Math.cos(degree) - p.y * Math.sin(degree));
            int y = (int) (p.x * Math.sin(degree) + p.y * Math.cos(degree));
            p.x = x;p.y = y;
        }

        return input;
    }

    public static GPoint rotate(GPoint input, double degree) {

        double x =  (input.x * Math.cos(degree) - input.y * Math.sin(degree));
        double y =  (input.x * Math.sin(degree) + input.y * Math.cos(degree));
        input.x = x;input.y = y;
        return input;
    }
}
