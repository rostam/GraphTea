// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.components;

import javax.swing.Icon;
import javax.swing.UIManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * Line icons for the toolbar, drawn with Java2D so they stay sharp at any display scale and
 * take their colour from the button they sit on.
 *
 * <p>The UI XML refers to them as {@code image="icon:<name>"}.
 */
public final class ToolbarIcons {
    /** Prefix that marks an {@code image} attribute as one of these icons. */
    public static final String PREFIX = "icon:";

    private static final int SIZE = 16;

    private ToolbarIcons() {
    }

    /**
     * @param spec an {@code image} attribute value, such as {@code "icon:undo"}
     * @return the icon it names, or null if it does not name one
     */
    public static Icon forSpec(String spec) {
        if (spec == null || !spec.startsWith(PREFIX)) {
            return null;
        }
        return switch (spec.substring(PREFIX.length()).toLowerCase(Locale.ROOT)) {
            case "new-tab" -> new LineIcon(ToolbarIcons::newTab);
            case "reset-colors" -> new LineIcon(ToolbarIcons::resetColors);
            case "undo" -> new LineIcon(g -> undo(g, false));
            case "redo" -> new LineIcon(g -> undo(g, true));
            case "search" -> new LineIcon(ToolbarIcons::search);
            default -> null;
        };
    }

    private static void newTab(Graphics2D g) {
        g.draw(new RoundRectangle2D.Double(2, 3, 12, 10, 3, 3));
        g.draw(new Line2D.Double(8, 5.5, 8, 10.5));
        g.draw(new Line2D.Double(5.5, 8, 10.5, 8));
    }

    private static void resetColors(Graphics2D g) {
        // An almost-closed circular arrow around a filled dot: "back to plain".
        g.draw(new Arc2D.Double(2.5, 2.5, 11, 11, 110, 300, Arc2D.OPEN));
        Path2D head = new Path2D.Double();
        head.moveTo(4.2, 1.8);
        head.lineTo(6.6, 3.6);
        head.lineTo(4.0, 5.4);
        g.draw(head);
        g.fill(new Ellipse2D.Double(6.25, 6.25, 3.5, 3.5));
    }

    private static void undo(Graphics2D g, boolean mirrored) {
        if (mirrored) {
            g.translate(SIZE, 0);
            g.scale(-1, 1);
        }
        Path2D p = new Path2D.Double();
        p.moveTo(3, 7);
        p.lineTo(10, 7);
        p.curveTo(12.8, 7, 14, 9, 14, 10.5);
        p.curveTo(14, 12, 12.8, 13.5, 10, 13.5);
        p.lineTo(7, 13.5);
        g.draw(p);
        Path2D head = new Path2D.Double();
        head.moveTo(6, 4);
        head.lineTo(3, 7);
        head.lineTo(6, 10);
        g.draw(head);
    }

    private static void search(Graphics2D g) {
        g.draw(new Ellipse2D.Double(2, 2, 8.5, 8.5));
        g.draw(new Line2D.Double(9.5, 9.5, 14, 14));
    }

    /** Strokes a drawing in the owning component's foreground colour. */
    private static final class LineIcon implements Icon {
        private final Consumer<Graphics2D> drawing;

        LineIcon(Consumer<Graphics2D> drawing) {
            this.drawing = drawing;
        }

        public void paintIcon(Component c, Graphics graphics, int x, int y) {
            Graphics2D g = (Graphics2D) graphics.create();
            try {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                g.setColor(colorFor(c));
                g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.translate(x, y);
                drawing.accept(g);
            } finally {
                g.dispose();
            }
        }

        private static Color colorFor(Component c) {
            if (c != null && !c.isEnabled()) {
                Color disabled = UIManager.getColor("Button.disabledText");
                if (disabled != null) {
                    return disabled;
                }
            }
            return c != null ? c.getForeground() : Color.DARK_GRAY;
        }

        public int getIconWidth() {
            return SIZE;
        }

        public int getIconHeight() {
            return SIZE;
        }
    }
}
