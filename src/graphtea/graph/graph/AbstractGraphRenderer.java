// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.graph;

import graphtea.graph.atributeset.GraphNotifiableAttrSet;
import graphtea.graph.event.GraphModelListener;
import graphtea.platform.attribute.AttributeListener;
import graphtea.platform.core.BlackBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;

/**
 * The basic renderer interface
 */
public abstract class AbstractGraphRenderer extends JPanel implements GraphModelListener, AttributeListener {
    public static final String EVENT_KEY = "Graph View";
    HashSet<PaintHandler> postPaintHandlers = new HashSet<PaintHandler>();
    private GraphModel graph;
    boolean ignoreRapaints;
    int minx, miny;
    boolean isGraphChanged = true;
    private HashSet<PaintHandler<AbstractGraphRenderer>> prePaintHandlers = new HashSet<PaintHandler<AbstractGraphRenderer>>();

    public static AbstractGraphRenderer getCurrentGraphRenderer(BlackBoard b) {
        return b.getData(EVENT_KEY);
    }

    protected AbstractGraphRenderer() {
        super();
    }

    public void setGraph(GraphModel g) {
        this.graph = g;
        System.out.println(g.getLabel() + "<<<");
        g.addGraphListener(this);
        new GraphNotifiableAttrSet(g).addAttributeListener(this);
        this.repaintGraph();
    }

    public GraphModel getGraph() {
        return graph;
    }

    /**
     * adds ph to Post Paint Handlers which means that ph.paint will be called after each rendering of graph
     *
     * @param ph
     */
    public void addPostPaintHandler(PaintHandler<AbstractGraphRenderer> ph) {
        postPaintHandlers.add(ph);
    }

    /**
     * adds ph to Pre Paint Handlers which means that ph.paint will be called before each rendering of graph
     *
     * @param ph
     */
    public void addPrePaintHandler(PaintHandler<AbstractGraphRenderer> ph) {
        prePaintHandlers.add(ph);
    }

    /**
     * removes ph from both pre and post paint handlers and then repaints the graph
     *
     * @param ph
     */
    public void removePaintHandler(PaintHandler ph) {
        postPaintHandlers.remove(ph);
        prePaintHandlers.remove(ph);
        repaint();
    }

    public abstract void render(Graphics2D gg, Boolean drawExtras);

    public void repaint() {
        if (!ignoreRapaints) {
            super.repaint();
        }
    }

    BufferedImage bi;
    int lastWidth = 0, lastHeight = 0;
    boolean xtrans = false, ytrans = false;

    final public void paint(Graphics gg) {
        paint(gg, true);
    }

    /**
     * paint the graph on gg
     *
     * @param mainG
     * @param e
     * @param graph
     * @param y
     * @param labelx
     * @param drawExtras specifies wheter to draw extra things such as Curved Edges Control Points, on graph or not,
     */
    final public void paint(Graphics mainG, Boolean drawExtras) {
        int w = getWidth();
        int h = getHeight();
        if (w * h > 1000 * 1500) { //the limit which creating a bufferimage and double buffering consumes too much memory
            doRender(mainG, w, h, mainG, drawExtras);
            isGraphChanged = false;
            bi = null;
        } else {
            if (w != lastWidth || h != lastHeight)
                isGraphChanged = true;
//        showTime(0);
            if (isGraphChanged || bi == null) {
//            showTime(20);
                if (w != lastWidth || h != lastHeight) {
                    bi = this.getGraphicsConfiguration().createCompatibleImage(getWidth(), getHeight());
                    lastWidth = w;
                    lastHeight = h;
                }

//            showTime(21);
                Graphics bufferedG = bi.getGraphics();
                doRender(bufferedG, w, h, mainG, drawExtras);

            } else {
//            showTime(10);
                //painting the buffered graph

                doTranslate(mainG);
                for (PaintHandler p : prePaintHandlers)
                    p.paint(mainG, this, drawExtras);
                translateBack(mainG);


                mainG.drawImage(bi, 0, 0, this);
//            showTime(11);
                doTranslate(mainG);
                for (PaintHandler p : postPaintHandlers)
                    p.paint(mainG, this, drawExtras);
                translateBack(mainG);
//            showTime(12);
                return;
            }
            isGraphChanged = false;
        }

    }

    private void translateBack(Graphics mainG) {
        if (xtrans) {
            mainG.translate(minx, 0);
        }
        if (ytrans) {
            mainG.translate(0, miny);
        }
    }

    private void doTranslate(Graphics mainG) {
        xtrans = false;
        ytrans = false;
        if (minx < 0) {
            mainG.translate(-minx, 0);
            xtrans = true;
        }
        if (miny < 0) {
            mainG.translate(0, -miny);
            ytrans = true;
        }
    }

    private void doRender(Graphics bufferedG, int w, int h, Graphics mainG, Boolean drawExtras) {
        boolean xtrans = false, ytrans = false;
        if (!ignoreRapaints) {
            if (minx < 0) {
                bufferedG.translate(-minx, 0);
//                mainG.translate(-minx, 0);
                xtrans = true;
            }
            if (miny < 0) {
                bufferedG.translate(0, -miny);
//                mainG.translate(0, -miny);
                ytrans = true;
            }
//                showTime(3);
            bufferedG.setColor(Color.white);

//                g.setClip(minx, miny, w, h);
            bufferedG.fillRect(minx, miny, w + Math.abs(minx), h + Math.abs(miny));
//                showTime(4);
//                g.clearRect(0,0, getWidth(), getHeight());

            for (PaintHandler p : prePaintHandlers)
                p.paint(mainG, this, drawExtras);
            render((Graphics2D) bufferedG, drawExtras);
//                showTime(5);

            mainG.drawImage(bi, 0, 0, this);
//                showTime(6);
            for (PaintHandler p : postPaintHandlers)
                p.paint(mainG, this, drawExtras);
        }
        if (!ignoreRapaints) {
            if (xtrans) {
                bufferedG.translate(minx, 0);
//                mainG.translate(minx, 0);
            }
            if (ytrans) {
                bufferedG.translate(0, miny);
//                mainG.translate(0, miny);
            }
        }
    }

    long lastTime;

    void showTime(int i) {
        if (i == 0) {
            lastTime = System.currentTimeMillis();
        }
//        System.out.println(i + ":" + (System.currentTimeMillis() - lastTime));
        lastTime = System.currentTimeMillis();
    }

    /**
     * ignores every repaint event on running the run
     * <p/>
     * This method is useful when you want to do a great
     * change on graph structure
     * <p/>
     * It is equal to call ignoreRepaints(run, true)
     *
     * @param run
     */
    public void ignoreRepaints(Runnable run) {
        ignoreRepaints(run, true);
    }

    /**
     * Runs run.run() and ignore all repaints until Its execution finishes
     *
     * @param run
     * @param repaintAfter
     */
    public void ignoreRepaints(Runnable run, boolean repaintAfter) {
        this.ignoreRapaints = true;
        run.run();
        this.ignoreRapaints = false;
        isGraphChanged = true;
        if (repaintAfter)
            repaint();
    }

    //currently listenin to graph attributes
    public void attributeUpdated(String name, Object oldVal, Object newVal) {
        isGraphChanged = true;
    }

    public void repaintGraph() {
        isGraphChanged = true;
        repaint();
    }


    public int getMinx() {
        return minx;
    }

    public int getMiny() {
        return miny;
    }
}
