// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.treevisualizations;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseVertexProperties;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.preferences.lastsettings.UserModifiableProperty;
import graphtea.plugins.visualization.corebasics.extension.VisualizationExtension;
import graphtea.ui.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author Rouzbeh Ebrahimi
 */
public class CircularTreeVisualization implements VisualizationExtension {
    String event = UIUtils.getUIEventKey("CircularTreeVisualization");
    public List<Vertex> visitedVertices = new ArrayList<>();
    public HashSet<Vertex> placedVertices = new HashSet<>();
    public HashMap<Vertex, GPoint> vertexPlaces = new HashMap<>();
    Vertex root;
    public List<Vertex> children = new ArrayList<>();
    public HashMap<Vertex, Integer> vertexHeights = new HashMap<>();
    @UserModifiableProperty(displayName = "Circular Tree Visualization Radius", obeysAncestorCategory = false
            , category = "Visualization Options")
    public static Integer radius = 80;

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

    private int maxHeight(List<Vertex> currentLevel, int maxLevel) {

        List<Vertex> nextLevel = new ArrayList<>();
        for (Vertex v : currentLevel) {
            v.setMark(true);
            for (Edge e : g.edges(v)) {
                Vertex v2 = e.source;
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

    public void locateAllSubTrees(Vertex v, double radius, double offSet) {
        if (placedVertices.contains(root)) {
            double angularSpan = (Double) v.getProp().obj;
            int numberOfDivides = 1;
            numberOfDivides = g.getOutDegree(v);
            if (numberOfDivides == 0) {
                return;
            }
            int j = 0;
            int sum = 0;
            for (Edge e : g.edges(v)) {
                Vertex v1 = e.source.equals(v) ? e.target : e.source;
                if (!placedVertices.contains(v1)) {
                    sum += g.getOutDegree(v1);
                }
            }
            for (Edge e : g.edges(v)) {
                Vertex v1 = e.source.equals(v) ? e.target : e.source;
                if (!placedVertices.contains(v1)) {
                    double x = 350 + radius * Math.cos((angularSpan * j / (numberOfDivides + 1) + offSet));
                    double y = 350 + radius * Math.sin((angularSpan * j / (numberOfDivides + 1) + offSet));
                    double newOffset = (angularSpan * j / numberOfDivides + offSet);
                    GPoint newPoint = new GPoint(x, y);
                    vertexPlaces.put(v1, newPoint);
                    placedVertices.add(v1);
                    BaseVertexProperties properties = new BaseVertexProperties(v1.getColor(), v1.getMark());
                    properties.obj = (angularSpan / sum) * (g.getOutDegree(v));
                    v1.setProp(properties);
                    locateAllSubTrees(v1, 2 * radius, newOffset);
                    j++;

                }
            }
        } else {
            double x = 350;
            double y = 350;
            GPoint newPoint = new GPoint(x, y);
            placedVertices.add(v);
            vertexPlaces.put(v, newPoint);
            locateAllSubTrees(v, radius, offSet);
        }
    }

    private void unMarkVertices() {
        for (Vertex v : g) {
            v.setMark(false);
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


    public void locateAll(List<Vertex> currentLevelVertices, int width, int radius) {
        int currentLevelCount = currentLevelVertices.size();
        List<Vertex> nextLevel = findNextLevelChildren(currentLevelVertices);
        int nextLevelCount = nextLevel.size();
        double degree = 360 / currentLevelCount;
        int j = 0;
        if (currentLevelCount == 1 && currentLevelVertices.get(0).equals(root)) {
            GPoint newPoint = new GPoint(350, 350);
            vertexPlaces.put(root, newPoint);

        } else {
            for (Vertex v : currentLevelVertices) {
                double x = 350 + radius * Math.cos((Math.PI / 180) * (j * degree));
                double y = 350 + radius * Math.sin((Math.PI / 180) * (j * degree));
                GPoint newPoint = new GPoint(x, y);
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
        CircularTreeVisualization.g = g;
    }

    public HashMap<Vertex, GPoint> getNewVertexPlaces() {
        visitedVertices = new ArrayList<>();
        vertexPlaces = new HashMap<>();
        children = new ArrayList<>();
        placedVertices = new HashSet<>();

        try {
            root = findAppropriateRoot(g);
            unMarkVertices();
            visitedVertices.add(root);
            locateAll(visitedVertices, 800, radius);
        } catch (NullPointerException e) {
            ExceptionHandler.catchException(e);
        }
        return vertexPlaces;
    }

    public HashMap<Edge, GPoint> getNewEdgeCurveControlPoints() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
