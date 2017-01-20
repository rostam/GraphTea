// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.circular;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseVertexProperties;
import graphtea.plugins.visualization.corebasics.basics.Cycle;
import graphtea.plugins.visualization.corebasics.basics.PathProperties;
import graphtea.plugins.visualization.corebasics.basics.VertexCycleLengthComparator;
import graphtea.plugins.visualization.corebasics.extension.VisualizationExtension;
import graphtea.ui.UIUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Rouzbeh Ebrahimi
 */
public class CircularDispatchVisualization implements VisualizationExtension {
    String event = UIUtils.getUIEventKey("CircularTreeVisualization");
    public Vector<Vertex> visitedVertices = new Vector<>();
    public HashMap<Vertex, GPoint> vertexPlaces = new HashMap<>();
    Vertex root;
    public Vector<Vertex> children = new Vector<>();
    public HashMap<Vertex, Integer> vertexHeights = new HashMap<>();

    public HashMap<Vertex, Integer> vertexCycleLength = new HashMap<>();


    private Cycle FindMainCycle(GraphModel g) {
        Vertex root = g.getAVertex();
        Iterator<Vertex> ei = g.iterator();
        for (; ei.hasNext();) {
            Vertex e = ei.next();
            root = findHigherVertex(e, root);
        }

        Vector<Vertex> t1 = new Vector<>();
        t1.add(root);
        findCycle(t1, (int) vertexHeights.get(root), 0);
        for (Vertex v : g) {
            int firstColor = ((PathProperties) v.getProp().obj).getFirstColor();
            int secColor = ((PathProperties) v.getProp().obj).getSecondColor();
            if (secColor != -1) {
                int i = firstColor + secColor;
                vertexCycleLength.put(v, i);
            }
        }
        Object[] verticeArray = vertexCycleLength.keySet().toArray();
        Arrays.sort(verticeArray, new VertexCycleLengthComparator());
        Vertex maxLengthCycle = (Vertex) verticeArray[0];
        return new Cycle();
    }

    private Vertex findHigherVertex(Vertex v1, Vertex v2) {
        Vector<Vertex> t1 = new Vector<>();
        Vector<Vertex> t2 = new Vector<>();
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

    private void findCycle(Vector<Vertex> currentLevel, int minLength, int color) {
        Vector<Vertex> nextLevel = new Vector<>();
        for (Vertex v : currentLevel) {
            v.setMark(true);
            Iterator<Edge> em = g.edgeIterator(v);

            for (; em.hasNext();) {
                Edge e = em.next();
                Vertex v2 = e.source;
                String vPathName = ((PathProperties) v.getProp().obj).getName();
                Object obj = v2.getProp().obj;
                boolean check = true;
                if (obj != null) {
                    String v2PathName = ((PathProperties) obj).getName();
                    String temp = vPathName.substring(0, v2PathName.length());
                    if (temp.equals(v2PathName)) check = false;
                }
                if (!v2.getProp().mark) {
                    nextLevel.add(v2);
                    if (!v2.getMark()) {
                        v2.setMark(true);
                        BaseVertexProperties bvp = new BaseVertexProperties(color, false);
                        PathProperties pathProp = new PathProperties("" + g.getOutDegree(v2));
                        pathProp.setFirstColor(color);
                        bvp.obj = pathProp;
                        v2.setProp(bvp);
                    } else {
                        v2.getProp().mark = true;
                        BaseVertexProperties bvp = new BaseVertexProperties(color, true);
                        PathProperties pathProp = new PathProperties("" + g.getOutDegree(v2));
                        pathProp.setSecondColor(color);
                        bvp.obj = pathProp;
                        v2.setProp(bvp);
                    }

                }
            }
        }

        if (nextLevel.size() != 0) {
            findCycle(nextLevel, minLength, color + 1);
        } else {
            return;
        }
    }

    private int maxHeight(Vector<Vertex> currentLevel, int maxLevel) {
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
            return maxHeight(nextLevel, maxLevel);
        } else {
            return maxLevel;
        }
    }

    static GraphModel g;
    /* public void performJob(Event eventName, Object value) {
        System.out.println("hello");
        visitedVertices=new Vector<Vertex>();
        vertexPlaces=new HashMap<Vertex, Point2D>();
        children=new Vector<Vertex>();
        g = ((GraphModel) (blackboard.getData(GraphAttrSet.name)));
        try {
            Cycle c = FindMainCycle(g);
            visitedVertices.add(root);
            locateAll(visitedVertices, 800, 80);
            GeneralAnimator t = new GeneralAnimator(vertexPlaces, g, blackboard);
            t.start();
        } catch (NullPointerException e) {
            System.out.println("Graph is Empty");
//            ExceptionHandler.catchException(e);
        }

    }*/


    public Vector<Vertex> findNextLevelChildren(Vector<Vertex> currentLevelVertices) {
        Vector<Vertex> newChildren = new Vector<>();
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
        return newChildren;
    }

    public void locateAll(Vector<Vertex> currentLevelVertices, int width, int radius) {
        int currentLevelCount = currentLevelVertices.size();
        Vector<Vertex> nextLevel = findNextLevelChildren(currentLevelVertices);
        int nextLevelCount = nextLevel.size();
        double degree = 360 / currentLevelCount;
        int j = 0;
        if (currentLevelCount == 1 && currentLevelVertices.elementAt(0).equals(root)) {
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
        return "CircularDispatchVisualization";
    }

    public String getDescription() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }/*
     @param g
    */

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public HashMap<Vertex, GPoint> getNewVertexPlaces() {
        visitedVertices = new Vector<>();
        vertexPlaces = new HashMap<>();
        children = new Vector<>();
//        g = ((GraphModel) (blackboard.getData(GraphAttrSet.name)));
        try {
            Cycle c = FindMainCycle(g);
            visitedVertices.add(root);
            locateAll(visitedVertices, 800, 80);
//            GeneralAnimator t = new GeneralAnimator(vertexPlaces, g, blackboard);
//            t.start();
        } catch (NullPointerException e) {
            System.out.println("Graph is Empty");
//            ExceptionHandler.catchException(e);
        }
        return vertexPlaces;
    }

    public HashMap<Edge, GPoint> getNewEdgeCurveControlPoints() {return new HashMap<>();
    }
}
