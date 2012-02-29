// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.visualization.treevisualizations;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.library.BaseVertexProperties;
import graphlab.platform.preferences.lastsettings.UserModifiableProperty;
import graphlab.plugins.visualization.corebasics.extension.VisualizationExtension;
import graphlab.ui.UIUtils;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Rouzbeh Ebrahimi
 */
public class CircularTreeVisualization implements VisualizationExtension {
    String event = UIUtils.getUIEventKey("CircularTreeVisualization");
    public Vector<VertexModel> visitedVertices = new Vector<VertexModel>();
    public HashSet<VertexModel> placedVertices = new HashSet<VertexModel>();
    public HashMap<VertexModel, Point2D> vertexPlaces = new HashMap<VertexModel, Point2D>();
    VertexModel root;
    public Vector<VertexModel> children = new Vector<VertexModel>();
    public HashMap<VertexModel, Integer> vertexHeights = new HashMap<VertexModel, Integer>();
    @UserModifiableProperty(displayName = "Circular Tree Visualization Radius", obeysAncestorCategory = false
            , category = "Visualization Options")
    public static Integer radius = 80;

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
        int i = maxHeight(t1, 0);
        vertexHeights.put(v1, i);
        int j = maxHeight(t2, 0);
        vertexHeights.put(v2, i);
        if (i > j) {
            return v1;
        } else {
            return v2;
        }
    }

    private int maxHeight(Vector<VertexModel> currentLevel, int maxLevel) {

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
            return maxHeight(nextLevel, maxLevel);
        } else {
            return maxLevel;
        }
    }

    static GraphModel g;

    /* public void performJob(Event eventName, Object value) {
visitedVertices = new Vector<VertexModel>();
vertexPlaces = new HashMap<VertexModel, Point2D>();
children = new Vector<VertexModel>();
placedVertices = new HashSet<VertexModel>();

try {
    root = findAppropriateRoot(g);
    unMarkVertices();
    visitedVertices.add(root);
    locateAll(visitedVertices, 800, 80);
    *//*BaseVertexProperties properties = new BaseVertexProperties(root.getColor(), root.getMark());
            properties.obj = new Double(2*Math.PI);
            root.setProp(properties);
            locateAllSubTrees(root, 40, 0);*//*
            GeneralAnimator t = new GeneralAnimator(vertexPlaces, g, blackboard);
            t.start();
        } catch (NullPointerException e) {
            System.out.println("Graph is Empty");
//            ExceptionHandler.catchException(e);
        }

    }*/

    public void locateAllSubTrees(VertexModel v, double radius, double offSet) {
        if (placedVertices.contains(root)) {
            double angularSpan = (Double) v.getProp().obj;
            int numberOfDivides = 1;
            numberOfDivides = g.getOutDegree(v);
            if (numberOfDivides == 0) {
                return;
            }
            Iterator<EdgeModel> iter = g.edgeIterator(v);
            int j = 0;
            int sum = 0;
            for (; iter.hasNext();) {
                EdgeModel e = iter.next();
                VertexModel v1 = e.source.equals(v) ? e.target : e.source;
                if (!placedVertices.contains(v1)) {
                    sum += g.getOutDegree(v1);
                } else {
                }
            }
            iter = g.edgeIterator(v);
            for (; iter.hasNext();) {
                EdgeModel e = iter.next();
                VertexModel v1 = e.source.equals(v) ? e.target : e.source;
                if (!placedVertices.contains(v1)) {
                    double x = 350 + radius * Math.cos((angularSpan * j / (numberOfDivides + 1) + offSet));
                    double y = 350 + radius * Math.sin((angularSpan * j / (numberOfDivides + 1) + offSet));
                    double newOffset = (angularSpan * j / numberOfDivides + offSet);
                    Point2D.Double newPoint = new Point2D.Double(x, y);
                    vertexPlaces.put(v1, newPoint);
                    placedVertices.add(v1);
                    BaseVertexProperties properties = new BaseVertexProperties(v1.getColor(), v1.getMark());
                    properties.obj = new Double((angularSpan / sum) * (g.getOutDegree(v)));
                    v1.setProp(properties);
                    locateAllSubTrees(v1, 2 * radius, newOffset);
                    j++;

                } else {
                }

            }
            return;
        } else {
            double x = 350;
            double y = 350;
            Point2D.Double newPoint = new Point2D.Double(x, y);
            placedVertices.add(v);
            vertexPlaces.put(v, newPoint);
            locateAllSubTrees(v, radius, offSet);
        }
    }

    private void unMarkVertices() {
        for (VertexModel v : g) {
            v.setMark(false);
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


    public void locateAll(Vector<VertexModel> currentLevelVertices, int width, int radius) {
        int currentLevelCount = currentLevelVertices.size();
        Vector<VertexModel> nextLevel = findNextLevelChildren(currentLevelVertices);
        int nextLevelCount = nextLevel.size();
        double degree = 360 / currentLevelCount;
        int j = 0;
        if (currentLevelCount == 1 && currentLevelVertices.elementAt(0).equals(root)) {
            Point2D.Double newPoint = new Point2D.Double(350, 350);
            vertexPlaces.put(root, newPoint);

        } else {
            for (VertexModel v : currentLevelVertices) {
                double x = 350 + radius * Math.cos((Math.PI / 180) * (j * degree));
                double y = 350 + radius * Math.sin((Math.PI / 180) * (j * degree));
                Point2D.Double newPoint = new Point2D.Double(x, y);
                vertexPlaces.put(v, newPoint);
                j++;

            }
        }

        if (!nextLevel.isEmpty()) {
            visitedVertices.addAll(nextLevel);
            locateAll(nextLevel, width, radius + radius * 3 / 8);
        } else {
            return;
        }
    }

    public String getName() {
        return "Circular Tree Visualization";
    }

    public String getDescription() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }/*
     @param g
    */

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public HashMap<VertexModel, Point2D> getNewVertexPlaces() {
        visitedVertices = new Vector<VertexModel>();
        vertexPlaces = new HashMap<VertexModel, Point2D>();
        children = new Vector<VertexModel>();
        placedVertices = new HashSet<VertexModel>();

        try {
            root = findAppropriateRoot(g);
            unMarkVertices();
            visitedVertices.add(root);
            locateAll(visitedVertices, 800, radius);
            /*BaseVertexProperties properties = new BaseVertexProperties(root.getColor(), root.getMark());
            properties.obj = new Double(2*Math.PI);
            root.setProp(properties);
            locateAllSubTrees(root, 40, 0);*/

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
