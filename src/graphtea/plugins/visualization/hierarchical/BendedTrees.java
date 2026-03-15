// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.hierarchical;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.visualization.corebasics.animator.GeneralAnimator;
import graphtea.ui.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Rouzbeh Ebrahimi
 */
public class BendedTrees extends AbstractAction {
    String event = UIUtils.getUIEventKey("BendedTrees");
    public List<Vertex> visitedVertices = new ArrayList<>();
    public HashMap<Vertex, GPoint> vertexPlaces = new HashMap<>();
    public HashMap<Edge, List<GPoint>> edgeBendPoints = new HashMap<>();
    public List<Vertex> children = new ArrayList<>();
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

    private Vertex findAppropriateRoot(GraphModel g) {
        Vertex root = g.getAVertex();
        for (Vertex e : g) {
            root = findHigherVertex(e, root);
        }
        return root;
    }

    private Vertex findHigherVertex(Vertex v1, Vertex v2) {
        List<Vertex> t1 = new ArrayList<>();
        List<Vertex> t2 = new ArrayList<>();
        t1.add(v1);
        t2.add(v2);
        if (BFS(new ArrayList<>(), t1, 0) > BFS(new ArrayList<>(), t2, 0)) {
            return v1;
        } else {
            return v2;
        }
    }

    private int BFS(List<Vertex> marked, List<Vertex> currentLevel, int maxLevel) {
        marked.addAll(currentLevel);
        List<Vertex> nextLevel = new ArrayList<>();
        for (Vertex v : currentLevel) {
            v.setMark(true);
            for (Edge e : g.edges(v)) {
                Vertex v2 = e.source;
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
        visitedVertices = new ArrayList<>();
        vertexPlaces = new HashMap<>();
        children = new ArrayList<>();
        edgeBendPoints = new HashMap<>();
        g = blackboard.getData(GraphAttrSet.name);
        try {
            this.graph = g;
            Vertex root = findAppropriateRoot(g);
            visitedVertices.add(root);
            locateAllVertices(visitedVertices, 800, 50);
            reshapeAllEdges();
            GeneralAnimator t = new GeneralAnimator(vertexPlaces, edgeBendPoints, g, blackboard);
            t.start();
        } catch (NullPointerException e) {

//            ExceptionHandler.catchException(e);
        }

    }

    public List<Vertex> findNextLevelChildren(List<Vertex> currentLevelVertices) {
        List<Vertex> newChildren = new ArrayList<>();
        for (Vertex v : currentLevelVertices) {
            for (Edge ed : g.edges(v)) {
                Vertex dest = ed.source;
                if (!visitedVertices.contains(dest)) {
                    newChildren.add(dest);
                }
            }
        }
        return newChildren;
    }

    public void reshapeAllEdges() {
        for (Edge e : graph.getEdges()) {
            GPoint d1 = vertexPlaces.get(e.target);
            GPoint d2 = vertexPlaces.get(e.source);
            List<GPoint> bendPoints = new ArrayList<>();
            bendPoints.add(new GPoint(d1.getX(), d1.getY() - 15));
            bendPoints.add(new GPoint(d2.getX(), d2.getY() + 15));
            edgeBendPoints.put(e, bendPoints);
        }
    }

    public void locateAllVertices(List<Vertex> currentLevelVertices, int width, int currentLevelHeight) {
        int currentLevelCount = currentLevelVertices.size();
        int i = 0;

        List<Vertex> nextLevel = findNextLevelChildren(currentLevelVertices);
        int nextLevelCount = nextLevel.size();
        int horizontalDist = width / (currentLevelCount + nextLevelCount);


        for (Vertex v : currentLevelVertices) {
            if (nextLevelCount != 0) {
                GPoint newPoint = new GPoint(horizontalDist * (i + 1) + width / (nextLevelCount + currentLevelCount), currentLevelHeight);
                vertexPlaces.put(v, newPoint);
                i += g.getInDegree(v);
            } else {
                GPoint newPoint = new GPoint(horizontalDist * (i) + width / (currentLevelCount), currentLevelHeight);
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
