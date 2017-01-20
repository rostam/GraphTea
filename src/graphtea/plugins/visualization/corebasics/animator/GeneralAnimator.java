// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.corebasics.animator;

import graphtea.graph.graph.*;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Rouzbeh Ebrahimi  Ebrahimi ruzbehus@yahoo.com
 */
public class GeneralAnimator implements Runnable {

    public HashMap<Vertex, GPoint> vertexDestinations = new HashMap<Vertex, GPoint>();
    public HashMap<Edge, Vector<GPoint>> edgeBendPoints = new HashMap<Edge, Vector<GPoint>>();
    public boolean supportBendedEdge;

    GraphModel g;
    AbstractGraphRenderer gv;
    public Thread animate;
    private BlackBoard blackboard;

    public GeneralAnimator(HashMap<Vertex, GPoint> vertexDestinations, GraphModel g, BlackBoard blackboard) {
        this.vertexDestinations = vertexDestinations;
        this.g = g;
        this.blackboard = blackboard;
    }

    public GeneralAnimator(HashMap<Vertex, GPoint> vertexDestinations, HashMap<Edge, Vector<GPoint>> edgeBendPoints, GraphModel g, BlackBoard blackboard) {
        this.vertexDestinations = vertexDestinations;
        this.edgeBendPoints = edgeBendPoints;
        this.g = g;
        supportBendedEdge = true;
        this.blackboard = blackboard;
    }

    public void start() {
        if (animate == null) {
            if (supportBendedEdge)
                animate = new Thread(new GeneralAnimator(vertexDestinations, edgeBendPoints, g, blackboard));
            else
                animate = new Thread(new GeneralAnimator(vertexDestinations, g, blackboard));

            animate.start();
        }
    }

    public void run() {
        final Thread current = Thread.currentThread();
        Iterator<Vertex> v = vertexDestinations.keySet().iterator();
        final Vector<GPoint> movements = new Vector<GPoint>();
        final Vector<GPoint> initials = new Vector<GPoint>();
        for (; v.hasNext();) {
            Vertex vertex = v.next();
            double initalX = vertex.getLocation().getX();
            GPoint GPoint = vertexDestinations.get(vertex);
            double totalXMovement = (GPoint.getX() - initalX);
            double initialY = vertex.getLocation().getY();
            double totalYMovement = (GPoint.getY() - initialY);
            initials.add(new GPoint(initalX, initialY));
            movements.add(new GPoint(totalXMovement, totalYMovement));
        }

        Iterator<GPoint> m;
        Iterator<GPoint> i;
        final int k = 21;
        for (int j = 1; j != k; j++) {
            AbstractGraphRenderer ren = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
            final int j1 = j;
            ren.ignoreRepaints(new Runnable() {
                public void run() {
                    doAnimateStep(movements, initials, j1, k, current);
                }
            });
        }
        if (supportBendedEdge) {
            paintEdges();
        }
    }

    private void doAnimateStep(Vector<GPoint> movements, Vector<GPoint> initials, int j, int k, Thread current) {
        Iterator<Vertex> v;
        Iterator<GPoint> m;
        Iterator<GPoint> i;
        v = vertexDestinations.keySet().iterator();

        m = movements.iterator();
        i = initials.iterator();
        for (; v.hasNext();) {
            Vertex vertex = v.next();
            GPoint movement = m.next();
            GPoint initial = i.next();
            //                vertex.setLabel(initial.getY()+"");
            vertex.setLocation(new GPoint(initial.getX() + j * movement.getX() / k, initial.getY() + j * movement.getY() / k));

        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            ExceptionHandler.catchException(e);
        }
        while (animate == current) {
            //todo
            //                g.view.repaint();
        }
    }

    public void paintEdges() {
        Iterator<Edge> ei = g.edgeIterator();
        for (; ei.hasNext();) {
            Edge e = ei.next();
//            e.view.ssetBendedEdge(true);
//            e.view.setBendPoints(edgeBendPoints.get(e));
        }
//todo        g.repaint();
    }
}
