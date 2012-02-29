// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.graphgenerator.core;

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

    public static Point[] circle(int r, int x, int y, int n) {
        Point[] ret = circle(0, 0, r, r, n);
        shift(ret, x - r / 2, y - r / 2);
        return ret;
    }

    public static Point[] shift(Point[] input, int xOffset, int yOffset) {
        for (Point p : input) {
            p.x += xOffset;
            p.y += yOffset;
        }
        return input;
    }
}
