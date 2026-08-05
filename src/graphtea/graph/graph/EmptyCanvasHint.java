// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

/**
 * Draws a short "here is how to start" hint over an empty canvas.
 *
 * <p>A new tab is a large white rectangle with no controls on it and no indication that
 * clicking does anything. The three things a first-time user needs to know &mdash; click adds a
 * vertex, drag connects two, and there is a menu full of ready-made graphs &mdash; are not
 * discoverable from looking at it. This paints them where the user is already looking, and
 * disappears the moment the graph has a vertex in it.
 */
public class EmptyCanvasHint implements PaintHandler {

    private static final Color TEXT = new Color(0x9AA5AF);
    private static final Color ACCENT = new Color(0xC3CCD4);

    /** Below this the canvas is too small for the hint to be anything but clutter. */
    private static final int MIN_WIDTH = 320;
    private static final int MIN_HEIGHT = 240;

    private final AbstractGraphRenderer renderer;

    /**
     * @param renderer the canvas to draw on
     */
    public EmptyCanvasHint(AbstractGraphRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void paint(Graphics g, Object destinationComponent, Boolean drawExtras) {
        GraphModel graph = renderer.getGraph();
        if (graph == null || graph.getVerticesCount() > 0) {
            return;
        }
        int w = renderer.getWidth();
        int h = renderer.getHeight();
        if (w < MIN_WIDTH || h < MIN_HEIGHT) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int cx = w / 2;
            int cy = h / 2;

            drawSketch(g2, cx, cy - 70);

            g2.setColor(TEXT);
            g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            centre(g2, "This canvas is empty", cx, cy + 30);

            g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
            centre(g2, "Click anywhere to add a vertex, then drag from one vertex to another"
                    + " to connect them.", cx, cy + 60);
            centre(g2, "Or pick a ready-made graph from the Generate Graph menu.", cx, cy + 80);
            centre(g2, "Press Ctrl+K to search every command by name.", cx, cy + 106);
        } finally {
            g2.dispose();
        }
    }

    /** A two-vertex, one-edge diagram: the smallest thing the canvas is for. */
    private static void drawSketch(Graphics2D g2, int cx, int cy) {
        int r = 13;
        int gap = 52;
        Stroke old = g2.getStroke();
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(ACCENT);
        g2.drawLine(cx - gap + r, cy, cx + gap - r, cy);
        for (int dx : new int[]{-gap, gap}) {
            Ellipse2D.Double dot = new Ellipse2D.Double(cx + dx - r, cy - r, 2.0 * r, 2.0 * r);
            g2.setColor(Color.WHITE);
            g2.fill(dot);
            g2.setColor(ACCENT);
            g2.draw(dot);
        }
        g2.setStroke(old);
    }

    private static void centre(Graphics2D g2, String text, int cx, int y) {
        int width = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, cx - width / 2, y);
    }
}
