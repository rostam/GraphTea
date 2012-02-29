// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.visualization.hierarchical;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.visualization.corebasics.animator.GeneralAnimator;
import graphlab.ui.UIUtils;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Rouzbeh Ebrahimi
 */
public class BendedTrees extends AbstractAction {
    String event = UIUtils.getUIEventKey("BendedTrees");
    public Vector<VertexModel> visitedVertices = new Vector<VertexModel>();
    public HashMap<VertexModel, Point2D> vertexPlaces = new HashMap<VertexModel, Point2D>();
    public HashMap<EdgeModel, Vector<Point2D>> edgeBendPoints = new HashMap<EdgeModel, Vector<Point2D>>();
    public Vector<VertexModel> children = new Vector<VertexModel>();
    public GraphModel graph;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public BendedTrees(BlackBoard bb) {
        super(bb);
        listen4Event(event);
    }

    private VertexModel findAppropriateRoot(GraphModel g) {
        VertexModel root = g.getAVertex();
        Iterator<VertexModel> ei = g.iterator();
        for (; ei.hasNext();) {
            VertexModel e = ei.next();
            root = findHigherVertex(e, root);
        }
        return root;
    }

    private VertexModel findHigherVertex(VertexModel v1, VertexModel v2) {
        Vector<VertexModel> t1 = new Vector<VertexModel>();
        Vector<VertexModel> t2 = new Vector<VertexModel>();
        t1.add(v1);
        t2.add(v2);
        if (BFS(new Vector<VertexModel>(), t1, 0) > BFS(new Vector<VertexModel>(), t2, 0)) {
            return v1;
        } else {
            return v2;
        }
    }

    private int BFS(Vector<VertexModel> marked, Vector<VertexModel> currentLevel, int maxLevel) {
        marked.addAll(currentLevel);
        Vector<VertexModel> nextLevel = new Vector<VertexModel>();
        for (VertexModel v : currentLevel) {
            v.setMark(true);
            Iterator<EdgeModel> em = g.edgeIterator(v);
            for (; em.hasNext();) {
                EdgeModel e = em.next();
                VertexModel v2 = e.source;
                if (!marked.contains(v2)) {
                    nextLevel.add(v2);
                }
            }
        }
        maxLevel++;
        if (nextLevel.size() != 0) {
            return BFS(marked, nextLevel, maxLevel);
        } else {
            return maxLevel;
        }
    }

    static GraphModel g;

    public void performAction(String eventName, Object value) {
        visitedVertices = new Vector<VertexModel>();
        vertexPlaces = new HashMap<VertexModel, Point2D>();
        children = new Vector<VertexModel>();
        edgeBendPoints = new HashMap<EdgeModel, Vector<Point2D>>();
        g = ((GraphModel) (blackboard.getData(GraphAttrSet.name)));
        try {
            this.graph = g;
            VertexModel root = findAppropriateRoot(g);
            visitedVertices.add(root);
            locateAllVertices(visitedVertices, 800, 50);
            reshapeAllEdges();
            GeneralAnimator t = new GeneralAnimator(vertexPlaces, edgeBendPoints, g, blackboard);
            t.start();
        } catch (NullPointerException e) {
            System.out.println("Graph is Empty");
//            ExceptionHandler.catchException(e);
        }

    }

    public Vector<VertexModel> findNextLevelChildren(Vector<VertexModel> currentLevelVertices) {
        Vector<VertexModel> newChildren = new Vector<VertexModel>();
        for (VertexModel v : currentLevelVertices) {
            Iterator<EdgeModel> e = g.edgeIterator(v);
            for (; e.hasNext();) {
                EdgeModel ed = e.next();
                VertexModel dest = ed.source;
                if (!visitedVertices.contains(dest)) {
                    newChildren.add(dest);
                }
            }
        }
        return newChildren;
    }

    public void reshapeAllEdges() {
        Iterator<EdgeModel> ei = graph.edgeIterator();
        for (; ei.hasNext();) {
            EdgeModel e = ei.next();
            Point2D d1 = vertexPlaces.get(e.target);
            Point2D d2 = vertexPlaces.get(e.source);
            Vector<Point2D> bendPoints = new Vector<Point2D>();
            bendPoints.add(new Point2D.Double(d1.getX(), d1.getY() - 15));
            bendPoints.add(new Point2D.Double(d2.getX(), d2.getY() + 15));
            edgeBendPoints.put(e, bendPoints);
        }
    }

    public void locateAllVertices(Vector<VertexModel> currentLevelVertices, int width, int currentLevelHeight) {
        int currentLevelCount = currentLevelVertices.size();
        int i = 0;

        Vector<VertexModel> nextLevel = findNextLevelChildren(currentLevelVertices);
        int nextLevelCount = nextLevel.size();
        int horizontalDist = width / (currentLevelCount + nextLevelCount);


        for (VertexModel v : currentLevelVertices) {
            if (nextLevelCount != 0) {
                Point2D.Double newPoint = new Point2D.Double(horizontalDist * (i + 1) + width / (nextLevelCount + currentLevelCount), currentLevelHeight);
                vertexPlaces.put(v, newPoint);
                i += g.getInDegree(v);
            } else {
                Point2D.Double newPoint = new Point2D.Double(horizontalDist * (i) + width / (currentLevelCount), currentLevelHeight);
                vertexPlaces.put(v, newPoint);
                i++;
            }
        }

        if (!nextLevel.isEmpty()) {
            visitedVertices.addAll(nextLevel);
            locateAllVertices(nextLevel, width, currentLevelHeight + 30);
        } else {
            return;
        }
    }
}
