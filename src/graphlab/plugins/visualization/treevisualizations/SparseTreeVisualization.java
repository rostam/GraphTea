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
public class SparseTreeVisualization implements VisualizationExtension {
    String event = UIUtils.getUIEventKey("SparseTreeVisualization");
    public Vector<VertexModel> visitedVertices = new Vector<VertexModel>();
    public HashMap<VertexModel, Point2D> vertexPlaces;
    public Vector<VertexModel> children;

    private void unMarkVertices() {
        for (VertexModel v : graph) {
            v.setMark(false);
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

    private int BFS(Vector<VertexModel> currentLevel, int maxLevel) {
        Vector<VertexModel> nextLevel = new Vector<VertexModel>();
        for (VertexModel v : currentLevel) {
            v.setMark(true);
            Iterator<EdgeModel> em = graph.edgeIterator(v);
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

    static GraphModel graph;

    public void performJob(String eventName, Object value) {
        visitedVertices = new Vector<VertexModel>();
        vertexPlaces = new HashMap<VertexModel, Point2D>();
        children = new Vector<VertexModel>();
        try {
            VertexModel root = findAppropriateRoot(graph);
            visitedVertices.add(root);
            unMarkVertices();
            locateAll(visitedVertices, width, eachLevelHeigh);
        } catch (NullPointerException e) {
            System.out.println("Graph is Empty");
//            ExceptionHandler.catchException(e);
        }

    }


    public Vector<VertexModel> findNextLevelChildren(Vector<VertexModel> currentLevelVertices) {
        Vector<VertexModel> newChildren = new Vector<VertexModel>();
        for (VertexModel v : currentLevelVertices) {
            Iterator<EdgeModel> e = graph.edgeIterator(v);
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

    public void locateAll(Vector<VertexModel> currentLevelVertices, int width, int LevelHeight) {
        int currentLevelCount = currentLevelVertices.size();
        int i = 0;

        Vector<VertexModel> nextLevel = findNextLevelChildren(currentLevelVertices);
        int nextLevelCount = nextLevel.size();
        int horizontalDist = width / (currentLevelCount + nextLevelCount);

        for (VertexModel x : currentLevelVertices) {

        }
        for (VertexModel v : currentLevelVertices) {
            if (nextLevelCount != 0) {
                Point2D.Double newPoint = new Point2D.Double(horizontalDist * (i + 1) + width / (nextLevelCount + currentLevelCount), LevelHeight);
                vertexPlaces.put(v, newPoint);
                i += graph.getOutDegree(v);
            } else {
                Point2D.Double newPoint = new Point2D.Double(horizontalDist * (i) + width / (currentLevelCount), LevelHeight);
                vertexPlaces.put(v, newPoint);
                i++;
            }
        }

        if (!nextLevel.isEmpty()) {
            visitedVertices.addAll(nextLevel);
            locateAll(nextLevel, width, LevelHeight + eachLevelHeigh);
        } else {
            return;
        }
    }

    public String getName() {
        return "Sparse Tree Visualization";
    }

    public String getDescription() {
        return "Sparse Tree Visualization";
    }/*
     @param g
    */

    public void setWorkingGraph(GraphModel g) {
        this.graph = g;
    }

    @UserModifiableProperty(displayName = "Sparse Tree Visualization Width", obeysAncestorCategory = false
            , category = "Visualization Options")
    public static Integer width = 800;
    @UserModifiableProperty(displayName = "Sparse Tree Visualization : Each Level's height", obeysAncestorCategory = false
            , category = "Visualization Options")
    public static Integer eachLevelHeigh = 50;

    public HashMap<VertexModel, Point2D> getNewVertexPlaces() {
        visitedVertices = new Vector<VertexModel>();
        vertexPlaces = new HashMap<VertexModel, Point2D>();
        children = new Vector<VertexModel>();
        try {
            VertexModel root = findAppropriateRoot(graph);
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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
