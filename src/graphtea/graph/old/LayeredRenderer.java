// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.old;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;

import java.awt.*;
import java.awt.image.VolatileImage;
import java.util.Iterator;

/**
 * @author azin azadi
 */
public class LayeredRenderer extends AcceleratedRenderer {
    public LayeredRenderer(GraphModel g, BlackBoard blackboard) {
        super(g, blackboard);
    }

    VolatileImage vLayer, eLayer;

    private VolatileImage createTransparentBuffer() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        VolatileImage c = gc.createCompatibleVolatileImage(getWidth(), getHeight(), VolatileImage.TRANSLUCENT);
        // This uses the same code as from Code Example 5, but replaces the try block.
        Graphics2D gImg = (Graphics2D) c.getGraphics();
        gImg.setComposite(AlphaComposite.Src);
        gImg.setColor(new Color(0, 0, 0, 0));
        gImg.fillRect(0, 0, 100, 100);
        gImg.dispose();
//        c.createGraphics().cl
        return c;
    }

    public void paint(Graphics2D g) {
        if (vLayer == null)
            vLayer = createTransparentBuffer();
        if (eLayer == null)
            eLayer = createTransparentBuffer();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(eLayer, 0, 0, this);
        g.drawImage(vLayer, 0, 0, this);
    }

    public void repaintVLayer() {
        vLayer = createTransparentBuffer();
        Graphics2D graphics = vLayer.createGraphics();
        for (Vertex v : getGraph()) {
            repaint(v, graphics);
        }
        graphics.dispose();
    }

    public void repaintELayer() {
        eLayer = createTransparentBuffer();
        Graphics2D graphics = eLayer.createGraphics();
        Iterator<Edge> ie = getGraph().edgeIterator();
        while (ie.hasNext()) {
            repaint(ie.next(), graphics);
        }
        graphics.dispose();
    }

    public void repaint(Vertex src) {
        super.repaint(src, vLayer.createGraphics());
//        repaint();
    }

    public void repaint(Edge src) {
        super.repaint(src, eLayer.createGraphics());
//        repaint();
    }

    public void vertexRemoved(Vertex v) {
        repaintVLayer();
        repaint();
    }

    public void edgeRemoved(Edge e) {
        repaintELayer();
        repaint();
    }

    public void graphCleared() {
        repaintVLayer();
        repaintELayer();
        repaint();
    }

    public void updateSize(Vertex src, GPoint newSize) {
        repaintVLayer();
        repaint();
    }

    public void updateLocation(Vertex src, GPoint newLocation) {
        repaintVLayer();
        repaint();
    }

    public void updateBounds(Rectangle r, Edge src) {
        repaintELayer();
//        repaint();
    }
}
