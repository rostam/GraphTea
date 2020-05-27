// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.utils;

import java.awt.*;

/**
 * @author rouzbeh ebrahimi
 */
public class GFrameLocationProvider {
    private static final int coefficent = 16;
    private static final int prefCoef = 18;

    public static Point getLocation() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((int) d.getWidth() / coefficent, (int) d.getHeight() / coefficent);
    }

    public static Dimension getSize() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int determinator = 14;
        return new Dimension((int) d.getWidth() * determinator / coefficent, (int) d.getHeight() * determinator / coefficent);
    }

    public static Dimension getPopUpSize() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension((int) d.getWidth() * 8 / coefficent, (int) d.getHeight() * 8 / coefficent);
    }

    public static Dimension getPrefSize() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension((int) d.getWidth() * 8 / prefCoef, (int) d.getHeight() * 8 / prefCoef);
    }

    public static Point getPrefLocation() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((int) d.getWidth() * 3 / prefCoef, (int) d.getHeight() * 3 / prefCoef);
    }

    public static Point getPopUpLocation() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((int) d.getWidth() * 4 / coefficent, (int) d.getHeight() * 4 / coefficent);
    }
}
