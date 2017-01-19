// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.old;

import graphtea.graph.graph.*;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;

import java.awt.*;
import java.awt.image.VolatileImage;

/**
 * a super renderer, not working completely yet.
 */
public class AcceleratedRenderer extends FastRenderer {
    public AcceleratedRenderer(GraphModel g, BlackBoard blackboard) {
        super(g, blackboard);
    }

    private Image offscreenImage;
    private Graphics offscreenGraphics;
    private Dimension offscreenDimension;
    private VolatileImage volatileImg;
//    Thread paintThread = new Thread(this);

//    public void repaint() {
//        wait += 100;
//    }

    long wait = 0;

    public void run() {
        while (true) {
            while (wait <= 0)
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    ExceptionHandler.catchException(e);
                }
            while (wait > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    ExceptionHandler.catchException(e);
                }
                wait -= 100;
            }
            paint(getGraphics());
        }
    }

    public void paint(Graphics2D g, boolean drawExtras) {
        System.out.println("paint");
//        new RuntimeException().printStackTrace();
        // create the hardware accelerated image.
        createBackBuffer();

        // Main rendering loop. Volatile images may lose their contents.
        // This loop will continually render to (and produce if neccessary) volatile images
        // until the rendering was completed successfully.
        do {

            // Validate the volatile image for the graphics configuration of this
            // component. If the volatile image doesn't apply for this graphics configuration
            // (in other words, the hardware acceleration doesn't apply for the new device)
            // then we need to re-create it.
            GraphicsConfiguration gc = this.getGraphicsConfiguration();
            int valCode = volatileImg.validate(gc);

            // This means the device doesn't match up to this hardware accelerated image.
            if (valCode == VolatileImage.IMAGE_INCOMPATIBLE) {
                createBackBuffer(); // recreate the hardware accelerated image.
            }

            Graphics offscreenGraphics = volatileImg.getGraphics();
//   offscreenGraphics.setColor(Color.WHITE);
//   offscreenGraphics.fillRect(0, 0, getWidth(), getHeight());
//   offscreenGraphics.setColor(Color.BLACK);
//   offscreenGraphics.drawLine(0, 0, 10, 10); // arbitrary rendering logic
            paintGraph(offscreenGraphics, drawExtras);
            // paint back buffer to main graphics
            g.drawImage(volatileImg, 0, 0, this);
            // Test if content is lost
        } while (volatileImg.contentsLost());
    }

    // This method produces a new volatile image.
    private void createBackBuffer() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        volatileImg = gc.createCompatibleVolatileImage(getWidth(), getHeight());
    }

//    public void update(Graphics g) {
//        wait += 100;
////        paint(g);
//    }

    public void repaint(Vertex src) {
        Graphics gg = getGraphics();
        repaint(src, gg);
    }

    public void repaint(Vertex src, Graphics gg) {
        GPoint l = src.getLocation();
        gg.setColor(GraphModel.getColor(src.getColor()));
        if (src.isSelected())
            gg.setColor(Color.black);
        else
            gg.setColor(GraphModel.getColor(src.getColor()));
        gg.fillOval((int) l.x - 5, (int) l.y - 5, 10, 10);
    }

    public void repaint(Edge src) {
        Graphics gg = getGraphics();
        repaint(src, gg);
    }

    public void repaint(Edge src, Graphics gg) {
        GPoint l, r;
        l = src.source.getLocation();
        r = src.target.getLocation();

//        if (src.isSelected())
//            gg.setColor(Color.black);
//        else
//            gg.setColor(src.getRGBColor());
        gg.drawLine((int) l.x, (int) l.y, (int) r.x, (int) r.y);
    }

    public void vertexAdded(Vertex v) {
        v.setLabel(v.getId() + "");
        v.setVertexListener(this);
        repaint(v);
    }

    public void edgeAdded(Edge e) {
        e.setLabel(e.getId());
        e.setEdgeListener(this);
//        e.updateBounds();
        repaint(e);
    }
}
