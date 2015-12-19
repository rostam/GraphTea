// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.old;

import graphtea.platform.StaticUtils;
import graphtea.platform.lang.FromStringProvider;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * User: Azin Azadi,Rouzbeh Ebrahimi
 */
public class GShape implements Serializable, FromStringProvider<GShape> {
    static {
        StaticUtils.setFromStringProvider(GShape.class.getName(), new GShape(""));
    }

    /**
     *
     */
    private static final long serialVersionUID = -660213117046506019L;

    @Override
    public String toString() {
        return name;
    }
    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeObject(name);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        name = (String) stream.readObject();
    }

    public GShape(String name) {
        this.name = name;
//        if (! all.containsKey(name))
//            all.put(name, this);
    }

    public void draw(Graphics g, int x, int y, int shapeWidth, int shapeHeight) {
        ((Graphics2D) g).translate(x, y);
        drawShape(this.name, g, shapeWidth, shapeHeight);
        ((Graphics2D) g).translate(-x, -y);
    }

    public void fill(Graphics g, int x, int y, int shapeWidth, int shapeHeight) {
        ((Graphics2D) g).translate(x, y);
        fillShape(this.name, g, shapeWidth, shapeHeight);
        ((Graphics2D) g).translate(-x, -y);
    }

    public String name;
    public static HashMap<String, GShape> all = new HashMap<>();
    public static final GShape RECTANGLE = new GShape("Rectangle");
    public static final GShape OVAL = new GShape("Oval");
    public static final GShape ROUNDRECT = new GShape("Round Rectangle");
    public static final GShape STAR = new GShape("Star");
    public static final GShape SIXPOINTSTAR = new GShape("6 Point Star");
    public static final GShape SEVENPOINTSTAR = new GShape("7 Point Star");
    public static final GShape EIGHTPOINTSTAR = new GShape("8 Point Star");
    public static final GShape NINEPOINTSTAR = new GShape("9 Point Star");
    public static final GShape TENPOINTSTAR = new GShape("10 Point Star");
    public static final GShape LEFTWARDTTRIANGLE = new GShape("Leftward Triangle");
    public static final GShape RIGHTWARDTRIANGLE = new GShape("Rightward Triangle");
    public static final GShape UPWARDTRIANGLE = new GShape("Upward Triangle");
    public static final GShape DOWNWARDTRIANGLE = new GShape("Downward Triangle");
    public static final GShape REGULARHEXAGON = new GShape("Regular Hexagon");
    public static final GShape REGULARPENTAGON = new GShape("Regular Pentagon");
    public static final GShape DOWNWARDTRAPEZOID = new GShape("Downward Trapezoid");
    public static final GShape RIGHTWARDTRAPEZOID = new GShape("Rightward Trapezoid");
    public static final GShape UPWARDTRAPEZOID = new GShape("Upward Trapezoid");
    public static final GShape LEFTWARDTRAPEZOID = new GShape("Leftward Trapezoid");
    public static final GShape DOWNWARDPARALLELOGRAM = new GShape("Downward Parallelogram");
    public static final GShape UPWARDPARALLELOGRAM = new GShape("Upward Parallelogram");
    public static final GShape NICESTAR = new GShape("Nice Star");
    public static final GShape NICESIXPOINTSTAR = new GShape("6 Point Nice Star");
    public static final GShape NICESEVENPOINTSTAR = new GShape("7 Point Nice Star");
    public static final GShape NICEEIGHTPOINTSTAR = new GShape("8 Point Nice Star");
    public static final GShape NICENINEPOINTSTAR = new GShape("9 Point Nice Star");
    public static final GShape NICETENPOINTSTAR = new GShape("10 Point Nice Star");


    public static void drawShape(String shapeName, Graphics g, int w, int h) {
        if (shapeName.equals(RECTANGLE.name)) {
            g.drawRect(0, 0, w - 1, h - 1);
        } else if (shapeName.equals(OVAL.name)) {
            g.drawOval(0, 0, w - 1, h - 1);
        } else if (shapeName.equals(ROUNDRECT.name)) {
            g.drawRoundRect(0, 0, w - 1, h - 1, w / 2, h / 2);
        } else if (shapeName.equals(LEFTWARDTTRIANGLE.name)) {
            int[][] points = provideTrianglePoints(h, w, true);
            g.drawPolygon(points[0], points[1], 3);
        } else if (shapeName.equals(RIGHTWARDTRIANGLE.name)) {
            int[][] points = provideTrianglePoints(h, w, false);
            g.drawPolygon(points[0], points[1], 3);
        } else if (shapeName.equals(UPWARDTRIANGLE.name)) {
            int[][] points = provideTrianglePoints(h, w, true);
            g.drawPolygon(points[1], points[0], 3);
        } else if (shapeName.equals(DOWNWARDTRIANGLE.name)) {
            int[][] points = provideTrianglePoints(h, w, false);
            g.drawPolygon(points[1], points[0], 3);
        } else if (shapeName.equals(DOWNWARDTRAPEZOID.name)) {
            int[][] points = provideTrapezoidPoints(h, w, true);
            g.drawPolygon(points[0], points[1], 4);
        } else if (shapeName.equals(RIGHTWARDTRAPEZOID.name)) {
            int[][] points = provideTrapezoidPoints(h, w, true);
            g.drawPolygon(points[1], points[0], 4);
        } else if (shapeName.equals(UPWARDTRAPEZOID.name)) {
            int[][] points = provideTrapezoidPoints(h, w, false);
            g.drawPolygon(points[0], points[1], 4);
        } else if (shapeName.equals(LEFTWARDTRAPEZOID.name)) {
            int[][] points = provideTrapezoidPoints(h, w, false);
            g.drawPolygon(points[1], points[0], 4);
        } else if (shapeName.equals(DOWNWARDPARALLELOGRAM.name)) {
            int[][] points = provideParallelogramPoints(h, w, true);
            g.drawPolygon(points[0], points[1], 4);
        } else if (shapeName.equals(UPWARDPARALLELOGRAM.name)) {
            int[][] points = provideParallelogramPoints(h, w, false);
            g.drawPolygon(points[1], points[0], 4);
        } else if (shapeName.equals(REGULARHEXAGON.name)) {
            int[][] points = provideRegularPolygonPoints(h, w, 6);
            g.drawPolygon(points[0], points[1], 6);
        } else if (shapeName.equals(REGULARPENTAGON.name)) {
            int[][] points = provideRegularPolygonPoints(h, w, 5);
            g.drawPolygon(points[0], points[1], 5);
        } else if (shapeName.equals(STAR.name)) {
            int[][] points = provideStarPoints(h, w, 5);
            g.drawPolygon(points[0], points[1], 10);
        } else if (shapeName.equals(SIXPOINTSTAR.name)) {
            int[][] points = provideStarPoints(h, w, 6);
            g.drawPolygon(points[0], points[1], 12);
        } else if (shapeName.equals(SEVENPOINTSTAR.name)) {
            int[][] points = provideStarPoints(h, w, 7);
            g.drawPolygon(points[0], points[1], 14);
        } else if (shapeName.equals(EIGHTPOINTSTAR.name)) {
            int[][] points = provideStarPoints(h, w, 8);
            g.drawPolygon(points[0], points[1], 16);
        } else if (shapeName.equals(NINEPOINTSTAR.name)) {
            int[][] points = provideStarPoints(h, w, 9);
            g.drawPolygon(points[0], points[1], 18);
        } else if (shapeName.equals(TENPOINTSTAR.name)) {
            int[][] points = provideStarPoints(h, w, 10);
            g.drawPolygon(points[0], points[1], 20);
        } else if (shapeName.equals(NICESTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 5);
            g.drawPolygon(points[0], points[1], 10);
        } else if (shapeName.equals(NICESIXPOINTSTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 6);
            g.drawPolygon(points[0], points[1], 12);
        } else if (shapeName.equals(NICESEVENPOINTSTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 7);
            g.drawPolygon(points[0], points[1], 14);
        } else if (shapeName.equals(NICEEIGHTPOINTSTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 8);
            g.drawPolygon(points[0], points[1], 16);
        } else if (shapeName.equals(NICENINEPOINTSTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 9);
            g.drawPolygon(points[0], points[1], 18);
        } else if (shapeName.equals(NICETENPOINTSTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 10);
            g.drawPolygon(points[0], points[1], 20);
        }


    }


    public static void fillShape(String shapeName, Graphics g, int w, int h) {
        if (shapeName.equals(RECTANGLE.name)) {
            g.fillRect(0, 0, w - 1, h - 1);
        }
        if (shapeName.equals(OVAL.name)) {
            g.fillOval(0, 0, w - 1, h - 1);
        }
        if (shapeName.equals(ROUNDRECT.name)) {
            g.fillRoundRect(0, 0, w - 1, h - 1, w / 2, h / 2);
        }
        if (shapeName.equals(LEFTWARDTTRIANGLE.name)) {
            int[][] points = provideTrianglePoints(h, w, true);
            g.fillPolygon(points[0], points[1], 3);
        }
        if (shapeName.equals(RIGHTWARDTRIANGLE.name)) {
            int[][] points = provideTrianglePoints(h, w, false);
            g.fillPolygon(points[0], points[1], 3);
        }
        if (shapeName.equals(UPWARDTRIANGLE.name)) {
            int[][] points = provideTrianglePoints(h, w, true);
            g.fillPolygon(points[1], points[0], 3);
        }
        if (shapeName.equals(DOWNWARDTRIANGLE.name)) {
            int[][] points = provideTrianglePoints(h, w, false);
            g.fillPolygon(points[1], points[0], 3);
        }
        if (shapeName.equals(RIGHTWARDTRAPEZOID.name)) {
            int[][] points = provideTrapezoidPoints(h, w, true);
            g.fillPolygon(points[1], points[0], 4);
        }
        if (shapeName.equals(DOWNWARDTRAPEZOID.name)) {
            int[][] points = provideTrapezoidPoints(h, w, true);
            g.fillPolygon(points[0], points[1], 4);
        }
        if (shapeName.equals(LEFTWARDTRAPEZOID.name)) {
            int[][] points = provideTrapezoidPoints(h, w, false);
            g.fillPolygon(points[1], points[0], 4);
        }
        if (shapeName.equals(UPWARDTRAPEZOID.name)) {
            int[][] points = provideTrapezoidPoints(h, w, false);
            g.fillPolygon(points[0], points[1], 4);
        }
        if (shapeName.equals(DOWNWARDPARALLELOGRAM.name)) {
            int[][] points = provideParallelogramPoints(h, w, true);
            g.fillPolygon(points[0], points[1], 4);
        }

        if (shapeName.equals(UPWARDPARALLELOGRAM.name)) {
            int[][] points = provideParallelogramPoints(h, w, false);
            g.fillPolygon(points[1], points[0], 4);
        }
        if (shapeName.equals(REGULARHEXAGON.name)) {
            int[][] points = provideRegularPolygonPoints(h, w, 6);
            g.fillPolygon(points[0], points[1], 6);
        }
        if (shapeName.equals(REGULARPENTAGON.name)) {
            int[][] points = provideRegularPolygonPoints(h, w, 5);
            g.fillPolygon(points[0], points[1], 5);
        }
        if (shapeName.equals(STAR.name)) {
            int[][] points = provideStarPoints(h, w, 5);
            g.fillPolygon(points[0], points[1], 10);
        }
        if (shapeName.equals(SIXPOINTSTAR.name)) {
            int[][] points = provideStarPoints(h, w, 6);
            g.fillPolygon(points[0], points[1], 12);
        }
        if (shapeName.equals(SEVENPOINTSTAR.name)) {
            int[][] points = provideStarPoints(h, w, 7);
            g.fillPolygon(points[0], points[1], 14);
        }
        if (shapeName.equals(EIGHTPOINTSTAR.name)) {
            int[][] points = provideStarPoints(h, w, 8);
            g.fillPolygon(points[0], points[1], 16);
        }
        if (shapeName.equals(NINEPOINTSTAR.name)) {
            int[][] points = provideStarPoints(h, w, 9);
            g.fillPolygon(points[0], points[1], 18);
        }
        if (shapeName.equals(TENPOINTSTAR.name)) {
            int[][] points = provideStarPoints(h, w, 10);
            g.fillPolygon(points[0], points[1], 20);
        }
        if (shapeName.equals(NICESTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 5);
            g.fillPolygon(points[0], points[1], 10);
        }
        if (shapeName.equals(NICESIXPOINTSTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 6);
            g.fillPolygon(points[0], points[1], 12);
        }
        if (shapeName.equals(NICESEVENPOINTSTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 7);
            g.fillPolygon(points[0], points[1], 14);
        }
        if (shapeName.equals(NICEEIGHTPOINTSTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 8);
            g.fillPolygon(points[0], points[1], 16);
        }
        if (shapeName.equals(NICENINEPOINTSTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 9);
            g.fillPolygon(points[0], points[1], 18);
        }
        if (shapeName.equals(NICETENPOINTSTAR.name)) {
            int[][] points = provideNiceStarPoints(h, w, 10);
            g.fillPolygon(points[0], points[1], 20);
        }


    }

    public GShape fromString(String data) {
        if (data.equals(RECTANGLE.name))
            return RECTANGLE;
        if (data.equals(OVAL.name))
            return OVAL;
        if (data.equals(ROUNDRECT.name))
            return ROUNDRECT;
        if (data.equals(LEFTWARDTTRIANGLE.name))
            return LEFTWARDTTRIANGLE;
        if (data.equals(UPWARDTRIANGLE.name))
            return UPWARDTRIANGLE;
        if (data.equals(DOWNWARDTRAPEZOID.name))
            return DOWNWARDTRAPEZOID;
        if (data.equals(RIGHTWARDTRAPEZOID.name))
            return RIGHTWARDTRAPEZOID;
        if (data.equals(REGULARHEXAGON.name))
            return REGULARHEXAGON;
        if (data.equals(REGULARPENTAGON.name))
            return REGULARPENTAGON;
        if (data.equals(STAR.name))
            return STAR;
        if (data.equals(NICESTAR.name))
            return NICESTAR;
        if (data.equals(RIGHTWARDTRIANGLE.name))
            return RIGHTWARDTRIANGLE;
        if (data.equals(DOWNWARDTRIANGLE.name))
            return DOWNWARDTRIANGLE;
        if (data.equals(DOWNWARDPARALLELOGRAM.name))
            return DOWNWARDPARALLELOGRAM;
        if (data.equals(UPWARDPARALLELOGRAM.name))
            return UPWARDPARALLELOGRAM;
        if (data.equals(SIXPOINTSTAR.name))
            return SIXPOINTSTAR;
        if (data.equals(SEVENPOINTSTAR.name))
            return SEVENPOINTSTAR;
        if (data.equals(EIGHTPOINTSTAR.name))
            return EIGHTPOINTSTAR;
        if (data.equals(NINEPOINTSTAR.name))
            return NINEPOINTSTAR;
        if (data.equals(TENPOINTSTAR.name))
            return TENPOINTSTAR;
        if (data.equals(NICESIXPOINTSTAR.name))
            return NICESIXPOINTSTAR;
        if (data.equals(NICESEVENPOINTSTAR.name))
            return NICESEVENPOINTSTAR;
        if (data.equals(NICEEIGHTPOINTSTAR.name))
            return NICEEIGHTPOINTSTAR;
        if (data.equals(NICENINEPOINTSTAR.name))
            return NICENINEPOINTSTAR;
        if (data.equals(NICETENPOINTSTAR.name))
            return NICETENPOINTSTAR;


        System.out.println("this shape isn't a member of default shapes :" + data);
        return ROUNDRECT;
    }

    private static int[][] provideTrapezoidPoints(int h, int w, boolean t) {
//        h = h < w ? h : w;
        if (t) {
            int[] xPoints = new int[]{(w - 1) * 2 / 3, (w - 1) * 1 / 3, 0, (w - 1)};
            int[] yPoints = new int[]{h - 1, h - 1, 0, 0};
            int[][] points = new int[][]{xPoints, yPoints};
            return points;
        } else {
            int[] xPoints = new int[]{(w - 1) * 2 / 3, (w - 1) * 1 / 3, 0, (w - 1)};
            int[] yPoints = new int[]{0, 0, h - 1, h - 1};
            int[][] points = new int[][]{xPoints, yPoints};
            return points;
        }
    }

    private static int[][] provideParallelogramPoints(int h, int w, boolean t) {
//        h = h < w ? h : w;
        if (t) {
            int[] xPoints = new int[]{(w - 1), (w - 1) * 1 / 2, 0, (w - 1) / 2};
            int[] yPoints = new int[]{(h - 1) * 3 / 4, (h - 1) * 3 / 4, (h - 1) * 1 / 4, (h - 1) * 1 / 4};
            int[][] points = new int[][]{xPoints, yPoints};
            return points;
        } else {
            int[] xPoints = new int[]{(w - 1), (w - 1) * 1 / 2, 0, (w - 1) / 2};
            int[] yPoints = new int[]{(h - 1) * 1 / 4, (h - 1) * 1 / 4, (h - 1) * 3 / 4, (h - 1) * 3 / 4};
            int[][] points = new int[][]{xPoints, yPoints};
            return points;
        }
    }

    private static int[][] provideRegularPolygonPoints(int h, int w, int n) {
        int[] xPoints = new int[n];
        int[] yPoints = new int[n];
        h = h < w ? h : w;
        for (int i = 0; i != n; i++) {
            if (n % 2 == 0) {

                xPoints[i] = (int) (((h - 1) / 2) * Math.cos((i) * Math.PI * 2 / n) + (h - 1) / 2);
                yPoints[i] = (int) (((h - 1) / 2) * Math.sin((i) * Math.PI * 2 / n) + (h - 1) / 2);

            } else {

                xPoints[i] = (int) (((h - 1) / 2) * Math.cos((i) * (Math.PI * 2) / n + Math.PI / 2) + (h - 1) / 2);
                yPoints[i] = (int) (((h - 1) / 2) * Math.sin((i) * (Math.PI * 2) / n + Math.PI / 2) + (h - 1) / 2);

            }
        }
        return new int[][]{xPoints, yPoints};
    }

    private static int[][] provideStarPoints(int h, int w, int n) {
        int[] xOuterPoints = new int[n];
        int[] xInnerPoints = new int[n];
        int[] yOuterPoints = new int[n];
        int[] yInnerPoints = new int[n];
        int[] xPoints = new int[2 * n];
        int[] yPoints = new int[2 * n];
//        h = h < w ? h : w;
        for (int i = 0; i != n; i++) {
            if (n % 2 == 0) {
                xInnerPoints[i] = (int) (((w - 1) / 5) * Math.cos((i) * (Math.PI * 2) / n + (n - 1) * Math.PI / n) + (w - 1) / 2);
                xOuterPoints[i] = (int) (((w - 1) / 2) * Math.cos((i) * (Math.PI * 2) / n) + (w - 1) / 2);
                yInnerPoints[i] = (int) (((h - 1) / 5) * Math.sin((i) * (Math.PI * 2) / n + (n - 1) * Math.PI / n) + (h - 1) / 2);
                yOuterPoints[i] = (int) (((h - 1) / 2) * Math.sin((i) * (Math.PI * 2) / n) + (h - 1) / 2);
            } else {
                xInnerPoints[i] = (int) (((w - 1) / 5) * Math.cos((i) * (Math.PI * 2) / n + Math.PI / 2) + (w - 1) / 2);
                xOuterPoints[i] = (int) (((w - 1) / 2) * Math.cos((i) * (Math.PI * 2) / n - Math.PI / 2) + (w - 1) / 2);
                yInnerPoints[i] = (int) (((h - 1) / 5) * Math.sin((i) * (Math.PI * 2) / n + Math.PI / 2) + (h - 1) / 2);
                yOuterPoints[i] = (int) (((h - 1) / 2) * Math.sin((i) * (Math.PI * 2) / n - Math.PI / 2) + (h - 1) / 2);
            }

        }
        dc(n, xOuterPoints, xPoints, xInnerPoints, yOuterPoints, yPoints, yInnerPoints);
        return new int[][]{xPoints, yPoints};
    }

    private static void dc(int n, int[] xOuterPoints, int[] xPoints, int[] xInnerPoints, int[] yOuterPoints, int[] yPoints, int[] yInnerPoints) {
        for (int i = 0; i != n; i++) {
            int y = i + n / 2 + 1;
            y = y >= n ? y - n : y;
            xPoints[2 * i] = xOuterPoints[i];
            xPoints[2 * i + 1] = xInnerPoints[y];
            yPoints[2 * i] = yOuterPoints[i];
            yPoints[2 * i + 1] = yInnerPoints[y];
        }
    }

    private static int[][] provideNiceStarPoints(int h, int w, int n) {
        int[] xOuterPoints = new int[n];
        int[] xInnerPoints = new int[n];
        int[] yOuterPoints = new int[n];
        int[] yInnerPoints = new int[n];
        int[] xPoints = new int[2 * n];
        int[] yPoints = new int[2 * n];
//       h = h < w ? h : w;
        for (int i = 0; i != n; i++) {
//            if (n % 2 == 0) {
            xInnerPoints[i] = (int) (((w - 1) / 5) * Math.cos((i) * (Math.PI * 2) / n) + (w - 1) / 2);
            xOuterPoints[i] = (int) (((w - 1) / 2) * Math.cos((i) * (Math.PI * 2) / n) + (w - 1) / 2);
            yInnerPoints[i] = (int) (((h - 1) / 5) * Math.sin((i) * (Math.PI * 2) / n) + (h - 1) / 2);
            yOuterPoints[i] = (int) (((h - 1) / 2) * Math.sin((i) * (Math.PI * 2) / n) + (h - 1) / 2);
//            } else {
//                xInnerPoints[i] = (int) (((h - 1) / 5) * Math.cos((i) * (Math.PI * 2) / n ) + (h - 1) / 2);
//                xOuterPoints[i] = (int) (((h - 1) / 2) * Math.cos((i) * (Math.PI * 2) / n  ) + (h - 1) / 2);
//                yInnerPoints[i] = (int) (((h - 1) / 5) * Math.sin((i) * (Math.PI * 2) / n) + (h - 1) / 2);
//                yOuterPoints[i] = (int) (((h - 1) / 2) * Math.sin((i) * (Math.PI * 2) / n  ) + (h - 1) / 2);
//            }

        }
        dc(n, xOuterPoints, xPoints, xInnerPoints, yOuterPoints, yPoints, yInnerPoints);
        return new int[][]{xPoints, yPoints};
    }

    private static int[][] provideTrianglePoints(int h, int w, boolean t) {
        // h = h < w ? h : w;
        if (t) {
            int[] xPoints = new int[]{0, w - 1, w - 1};
            int[] yPoints = new int[]{(h - 1) / 2, 0, h - 1};
            int[][] points = new int[][]{xPoints, yPoints};
            return points;
        } else {
            int[] xPoints = new int[]{w - 1, 0, 0};
            int[] yPoints = new int[]{(h - 1) / 2, 0, h - 1};
            int[][] points = new int[][]{xPoints, yPoints};
            return points;
        }

    }
}