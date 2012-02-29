// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.utils;

import java.awt.*;

/**
 * @author rouzbeh ebrahimi
 */
public class GFrameLocationProvider {
    static int coefficent = 16, determinator = 14;
    static int prefCoef = 18;

    public static Point getLocation() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Point p = new Point((int) d.getWidth() / coefficent, (int) d.getHeight() / coefficent);
        return p;
    }

    public static Dimension getSize() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension((int) d.getWidth() * determinator / coefficent, (int) d.getHeight() * determinator / coefficent);
        return dim;
    }

    public static Dimension getPopUpSize() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension((int) d.getWidth() * 8 / coefficent, (int) d.getHeight() * 8 / coefficent);
        return dim;
    }

    public static Dimension getPrefSize() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension((int) d.getWidth() * 8 / prefCoef, (int) d.getHeight() * 8 / prefCoef);
        return dim;
    }

    public static Point getPrefLocation() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Point p = new Point((int) d.getWidth() * 3 / prefCoef, (int) d.getHeight() * 3 / prefCoef);
        return p;
    }

    public static Point getPopUpLocation() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Point p = new Point((int) d.getWidth() * 4 / coefficent, (int) d.getHeight() * 4 / coefficent);
        return p;
    }
}
