// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.treevisualizations;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.preferences.lastsettings.UserModifiableProperty;
import graphtea.plugins.visualization.corebasics.extension.VisualizationExtension;
import graphtea.ui.UIUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Rouzbeh Ebrahimi
 */
public class HierarchicalTreeVisualization implements VisualizationExtension {
    public static final String event = UIUtils.getUIEventKey("HierarchicalTreeVisualization");
    public Vector<Vertex> visitedVertices = new Vector<>();
    public HashMap<Vertex, GPoint> vertexPlaces = new HashMap<>();
    public Vector<Vertex> children = new Vector<>();

    private void unMarkVertices() {
        for (Vertex v : g) {
            v.setMark(false);
        }
    }


    static GraphModel g;

    /**
     * @param eventName
     * @param value
     */
    public void performJob(String eventName, Object value) {
        visitedVertices = new Vector<>();
        vertexPlaces = new HashMap<>();
        children = new Vector<>();
        try {
            Vertex root = findAppropriateRoot(g);
            visitedVertices.add(root);
            unMarkVertices();
            locateAll(visitedVertices, 600, 50);
        } catch (NullPointerException e) {
            System.out.println("Graph is Empty");
//            ExceptionHandler.catchException(e);
        }

    }

    private Vertex findHigherVertex(Vertex v1, Vertex v2) {
        Vector<Vertex> t1 = new Vector<>();
        Vector<Vertex> t2 = new Vector<>();
        t1.add(v1);
        t2.add(v2);
        if (BFS(t1, 0) > BFS(t2, 0)) {
            return v1;
        } else {
            return v2;
        }
    }

    private Vertex findAppropriateRoot(GraphModel g) {
        Vertex root = g.getAVertex();
        Iterator<Vertex> ei = g.iterator();
        for (; ei.hasNext();) {
            Vertex e = ei.next();
            root = findHigherVertex(e, root);
        }
        return root;
    }

    private int BFS(Vector<Vertex> currentLevel, int maxLevel) {
        Vector<Vertex> nextLevel = new Vector<>();
        for (Vertex v : currentLevel) {
            v.setMark(true);
            Iterator<Edge> em = g.edgeIterator(v);
            for (; em.hasNext();) {
                Edge e = em.next();
                Vertex v2 = e.source;
                if (!v2.getMark()) {
                    nextLevel.add(v2);
                    v2.setMark(true);
                }
            }
        }
        maxLevel++;
        if (nextLevel.size() != 0) {
            return BFS(nextLevel, maxLevel);
        } else {
            return maxLevel;
        }
    }

    public Vector<Vertex> findNextLevelChildren(Vector<Vertex> currentLevelVertices) {
        Vector<Vertex> newChildren = new Vector<>();
        if (currentLevelVertices.size() != 0) {
            for (Vertex v : currentLevelVertices) {
                Iterator<Edge> e = g.edgeIterator(v);
                for (; e.hasNext();) {
                    Edge ed = e.next();
                    Vertex dest = ed.source;
                    if (!visitedVertices.contains(dest)) {
                        newChildren.add(dest);
                    }
                }
            }
        } else {
        }
        return newChildren;
    }

    public void locateAll(Vector<Vertex> currentLevelVertices, int width, int currentLevelHeight) {
        int currentLevelCount = currentLevelVertices.size();
        int horizontalDist = width / currentLevelCount;
        int i = 0;
        Vector<Vertex> nextLevel = findNextLevelChildren(currentLevelVertices);

        for (Vertex v : currentLevelVertices) {
            GPoint newPoint = new GPoint(horizontalDist * i + width / (currentLevelCount + 1), currentLevelHeight);
            vertexPlaces.put(v, newPoint);
            i++;
        }

        if (!nextLevel.isEmpty()) {
            visitedVertices.addAll(nextLevel);
            locateAll(nextLevel, width, currentLevelHeight + eachLevelHeigh);
        } else {
            return;
        }
    }

    public String getName() {
        return "Hierarchical Tree Visualization";
    }

    public String getDescription() {
        return "Hierarchical Tree Visualization";
    }/*
     @param g
    */

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    @UserModifiableProperty(displayName = "Hierachical Tree Visualization Width", obeysAncestorCategory = false
            , category = "Visualization Options")
    public static Integer width = 600;
    @UserModifiableProperty(displayName = "Hierachical Tree Visualization : Each Level's height", obeysAncestorCategory = false
            , category = "Visualization Options")
    public static Integer eachLevelHeigh = 50;

    public HashMap<Vertex, GPoint> getNewVertexPlaces() {
        visitedVertices = new Vector<>();
        vertexPlaces = new HashMap<>();
        children = new Vector<>();
        try {
            Vertex root = findAppropriateRoot(g);
            visitedVertices.add(root);
            unMarkVertices();
            locateAll(visitedVertices, width, eachLevelHeigh);
        } catch (NullPointerException e) {
            System.out.println("Graph is Empty");
//            ExceptionHandler.catchException(e);
        }
        return vertexPlaces;
    }

    public HashMap<Edge, GPoint> getNewEdgeCurveControlPoints() {
        return null;
    }
}

