// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.visualization.circular;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.library.BaseVertexProperties;
import graphlab.plugins.visualization.corebasics.basics.Cycle;
import graphlab.plugins.visualization.corebasics.basics.PathProperties;
import graphlab.plugins.visualization.corebasics.basics.VertexCycleLengthComparator;
import graphlab.plugins.visualization.corebasics.extension.VisualizationExtension;
import graphlab.ui.UIUtils;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Rouzbeh Ebrahimi
 */
public class CircularDispatchVisualization implements VisualizationExtension {
    String event = UIUtils.getUIEventKey("CircularTreeVisualization");
    public Vector<VertexModel> visitedVertices = new Vector<VertexModel>();
    public HashMap<VertexModel, Point2D> vertexPlaces = new HashMap<VertexModel, Point2D>();
    VertexModel root;
    public Vector<VertexModel> children = new Vector<VertexModel>();
    public HashMap<VertexModel, Integer> vertexHeights = new HashMap<VertexModel, Integer>();

    public HashMap<VertexModel, Integer> vertexCycleLength = new HashMap<VertexModel, Integer>();


    private Cycle FindMainCycle(GraphModel g) {
        VertexModel root = g.getAVertex();
        Iterator<VertexModel> ei = g.iterator();
        for (; ei.hasNext();) {
            VertexModel e = ei.next();
            root = findHigherVertex(e, root);
        }

        Vector<VertexModel> t1 = new Vector<VertexModel>();
        t1.add(root);
        findCycle(t1, (int) vertexHeights.get(root), 0);
        for (VertexModel v : g) {
            int firstColor = ((PathProperties) v.getProp().obj).getFirstColor();
            int secColor = ((PathProperties) v.getProp().obj).getSecondColor();
            if (secColor != -1) {
                int i = firstColor + secColor;
                vertexCycleLength.put(v, i);
            }
        }
        Object[] verticeArray = vertexCycleLength.keySet().toArray();
        Arrays.sort(verticeArray, new VertexCycleLengthComparator());
        VertexModel maxLengthCycle = (VertexModel) verticeArray[0];
        return new Cycle();
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

    private void findCycle(Vector<VertexModel> currentLevel, int minLength, int color) {
        Vector<VertexModel> nextLevel = new Vector<VertexModel>();
        for (VertexModel v : currentLevel) {
            v.setMark(true);
            Iterator<EdgeModel> em = g.edgeIterator(v);

            for (; em.hasNext();) {
                EdgeModel e = em.next();
                VertexModel v2 = e.source;
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
        System.out.println("hello");
        visitedVertices=new Vector<VertexModel>();
        vertexPlaces=new HashMap<VertexModel, Point2D>();
        children=new Vector<VertexModel>();
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

    public HashMap<VertexModel, Point2D> getNewVertexPlaces() {
        visitedVertices = new Vector<VertexModel>();
        vertexPlaces = new HashMap<VertexModel, Point2D>();
        children = new Vector<VertexModel>();
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

    public HashMap<EdgeModel, Point2D> getNewEdgeCurveControlPoints() {
        return new HashMap<EdgeModel, Point2D>();
    }
}
