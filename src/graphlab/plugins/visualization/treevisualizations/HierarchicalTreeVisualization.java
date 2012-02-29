// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.visualization.treevisualizations;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.preferences.lastsettings.UserModifiableProperty;
import graphlab.plugins.visualization.corebasics.extension.VisualizationExtension;
import graphlab.ui.UIUtils;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Rouzbeh Ebrahimi
 */
public class HierarchicalTreeVisualization implements VisualizationExtension {
    public static final String event = UIUtils.getUIEventKey("HierarchicalTreeVisualization");
    public Vector<VertexModel> visitedVertices = new Vector<VertexModel>();
    public HashMap<VertexModel, Point2D> vertexPlaces = new HashMap<VertexModel, Point2D>();
    public Vector<VertexModel> children = new Vector<VertexModel>();

    private void unMarkVertices() {
        for (VertexModel v : g) {
            v.setMark(false);
        }
    }


    static GraphModel g;

    /**
     * @param eventName
     * @param value
     */
    public void performJob(String eventName, Object value) {
        visitedVertices = new Vector<VertexModel>();
        vertexPlaces = new HashMap<VertexModel, Point2D>();
        children = new Vector<VertexModel>();
        try {
            VertexModel root = findAppropriateRoot(g);
            visitedVertices.add(root);
            unMarkVertices();
            locateAll(visitedVertices, 600, 50);
        } catch (NullPointerException e) {
            System.out.println("Graph is Empty");
//            ExceptionHandler.catchException(e);
        }

    }

    private VertexModel findHigherVertex(VertexModel v1, VertexModel v2) {
        Vector<VertexModel> t1 = new Vector<VertexModel>();
        Vector<VertexModel> t2 = new Vector<VertexModel>();
        t1.add(v1);
        t2.add(v2);
        if (BFS(t1, 0) > BFS(t2, 0)) {
            return v1;
        } else {
            return v2;
        }
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

    private int BFS(Vector<VertexModel> currentLevel, int maxLevel) {
        Vector<VertexModel> nextLevel = new Vector<VertexModel>();
        for (VertexModel v : currentLevel) {
            v.setMark(true);
            Iterator<EdgeModel> em = g.edgeIterator(v);
            for (; em.hasNext();) {
                EdgeModel e = em.next();
                VertexModel v2 = e.source;
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

    public Vector<VertexModel> findNextLevelChildren(Vector<VertexModel> currentLevelVertices) {
        Vector<VertexModel> newChildren = new Vector<VertexModel>();
        if (currentLevelVertices.size() != 0) {
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
        } else {
        }
        return newChildren;
    }

    public void locateAll(Vector<VertexModel> currentLevelVertices, int width, int currentLevelHeight) {
        int currentLevelCount = currentLevelVertices.size();
        int horizontalDist = width / currentLevelCount;
        int i = 0;
        Vector<VertexModel> nextLevel = findNextLevelChildren(currentLevelVertices);

        for (VertexModel v : currentLevelVertices) {
            Point2D.Double newPoint = new Point2D.Double(horizontalDist * i + width / (currentLevelCount + 1), currentLevelHeight);
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

    public HashMap<VertexModel, Point2D> getNewVertexPlaces() {
        visitedVertices = new Vector<VertexModel>();
        vertexPlaces = new HashMap<VertexModel, Point2D>();
        children = new Vector<VertexModel>();
        try {
            VertexModel root = findAppropriateRoot(g);
            visitedVertices.add(root);
            unMarkVertices();
            locateAll(visitedVertices, width, eachLevelHeigh);
        } catch (NullPointerException e) {
            System.out.println("Graph is Empty");
//            ExceptionHandler.catchException(e);
        }
        return vertexPlaces;
    }

    public HashMap<EdgeModel, Point2D> getNewEdgeCurveControlPoints() {
        return null;
    }
}

