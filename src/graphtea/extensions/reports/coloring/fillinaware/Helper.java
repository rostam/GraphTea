package graphtea.extensions.reports.coloring.fillinaware;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.Set;
import java.util.Vector;

/**
 * Created by rostam on 30.12.15.
 * Helper functions
 */
public class Helper {

    public static Vertex getMinDegVertex(GraphModel g) {
        Vertex ret = g.getAVertex();
        for (Vertex v : g) {
            if (g.getDegree(v) < g.getDegree(ret)) ret = v;
        }
        return ret;
    }

    public static Vertex getMaxDegVertex(GraphModel g) {
        Vertex ret = g.getAVertex();
        for (Vertex v : g) {
            if (g.getDegree(v) > g.getDegree(ret)) ret = v;
        }
        return ret;
    }

    public static int minPossibleColor(GraphModel g, Vertex given) {
        Vector<Integer> forbiddenCols = new Vector<>();
        for (int i = 0; i < 100; i++) forbiddenCols.add(0);
        for (Vertex v : g.getNeighbors(given)) {
            forbiddenCols.set(v.getColor(), -1);
        }
        for (int i = 0; i < forbiddenCols.size(); i++) {
            if (forbiddenCols.get(i) != -1) return i;
        }
        return 0;
    }

    public static int numberOfColors(GraphModel g) {
        int max = 0;
        for (Vertex v : g) {
            if (v.getColor() > max) max = v.getColor();
        }
        return max;
    }

    public static GraphModel getGraphOfILU(SpMat mm) {
        int rows = mm.rows();
        int cols = mm.cols();
        //directed graph for ILU
        GraphModel gOfILU = new GraphModel(true);
        for (int i = 0; i < cols; i++) {
            gOfILU.addVertex(new Vertex());
        }

        for (int i = 0; i < rows; i++) {
            for(int j : mm.get(i)) {
                    gOfILU.addEdge(new Edge(
                            gOfILU.getVertex(i), gOfILU.getVertex(j)
                    ));
            }
        }
        return gOfILU;
    }

    public static GraphModel getGraphOfColoring(SpMat mm) {
        int rows = mm.rows();
        int cols = mm.cols();

        //undirected graph for one sided coloring
        GraphModel gOfCol = new GraphModel(false);

        for (int i = 0; i < rows; i++) {
            Vertex v = new Vertex();
            v.setColor(-1);
            gOfCol.addVertex(v);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                boolean isEdge = false;
                for (int k = 0; k < cols; k++) {
                    if (mm.contains(i, k) && mm.contains(j, k)) {
                        isEdge = true;
                        break;
                    }
                }
                if (isEdge) gOfCol.addEdge(new Edge(
                        gOfCol.getVertex(i), gOfCol.getVertex(j)));
            }
        }

        return gOfCol;
    }

    public static GraphModel getGraphOfColoringRestricted(SpMat mm, SpMat mmRes) {
        int rows = mm.rows();
        int cols = mm.cols();

        //undirected graph for one sided coloring
        GraphModel gOfCol = new GraphModel(false);
        for (int i = 0; i < rows; i++) {
            Vertex v = new Vertex();
            v.setColor(0);
            gOfCol.addVertex(v);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                boolean isEdge = false;
                boolean isReq = false;
                for (int k = 0; k < cols; k++) {
                    if (mm.contains(i, k) && mm.contains(j, k)) {
                        isEdge = true;
                        if (mmRes.contains(i, k) || mmRes.contains(j, k)) isReq = true;
                        //break;
                    }
                }
                if (isEdge) {
                    Edge e = new Edge(gOfCol.getVertex(i), gOfCol.getVertex(j));
                    if (isReq) e.setWeight(1);
                    else e.setWeight(0);
                    gOfCol.addEdge(e);
                }

            }
        }

        return gOfCol;
    }

    public static int ILUOneStep(GraphModel g, Vertex selected, int el) {
        Vector<Vertex> inVer = new Vector<>();
        Vector<Vertex> outVer = new Vector<>();
        int fillin = 0;
        for (Vertex v : g) {
            if (g.isEdge(v, selected)) inVer.add(v);
            if (g.isEdge(selected, v)) outVer.add(v);
        }

        for (Vertex anInVer : inVer) {
            for (Vertex anOutVer : outVer) {
                if (anInVer.getId() != anOutVer.getId()) {
                    if (selected.getId() < anInVer.getId() &&
                            selected.getId() < anOutVer.getId()) {
                        if (!g.isEdge(anInVer, anOutVer)) {
                            Edge e = new Edge(anInVer, anOutVer);
                            Edge e1 = g.getEdge(anInVer, selected);
                            Edge e2 = g.getEdge(selected, anOutVer);

                            e.setWeight(e1.getWeight() + e2.getWeight() + 1);
                            if (e1.getWeight() + e2.getWeight() + 1 <= el) {
                                g.addEdge(e);
                                fillin++;
                            }
                        }
                    }
                }
            }
        }
        return fillin;
    }

    public static int getFillinMinDeg(GraphModel g, int el, Set<Integer> order, String ord) {
        int fillin = 0;
        for (Edge e : g.edges()) e.setWeight(0);
        //for (int i : order) {
        if(ord.equals("Normal")) {
            for (int i = 0; i < g.numOfVertices(); i++) {
                fillin += Helper.ILUOneStep(g, g.getVertex(i), el);
            }
        } else {
            for (int i : order) {
                fillin += Helper.ILUOneStep(g, g.getVertex(i), el);
            }
        }

        return fillin;
    }

    //the input graph should be already computed
    //P is the number of potentially required edges
    //mR=block matrix
    public static  SpMat getPotReqEdges(GraphModel g, SpMat m, SpMat mR) {
        SpMat potM = new SpMat(m.rows(),m.cols());
        for (int i = 0; i < m.rows(); i++) {
            for(int j : m.get(i)) {
                if (!mR.contains(i, j)) {
                    boolean isPot = true;
                    for (int k = 0; k < m.rows(); k++) {
                        if (k != i && m.contains(k, j) && !mR.contains(k, j)) {
                            if (g.getVertex(i).getColor() == g.getVertex(k).getColor()) {
                                potM.set(i, k);
                                isPot = false;
                                break;
                            }
                        }
                    }
                    if (isPot) potM.set(i, j);
                }
            }
        }
        return potM;
    }

    //the input graph should be already computed
    //P is the number of potentially required edges
    //mR=block matrix mP= pot req matrix
    public static SpMat getAddReqEdges(GraphModel gILU, SpMat m, SpMat mR, SpMat mP,Set<Integer> order) {
        SpMat addM = new SpMat(m.rows(),m.cols());
        //for (int i = 0; i < m.getRowDimension(); i++) {
        //    for (int j = 0; j < m.getColumnDimension(); j++) {
        for(int i : order) {
            for(int j : order) {
                if(mP.contains(i, j)) {
                    boolean isAdd = true;
                    if(i > j) {
                        for (Vertex v : gILU.neighbors(gILU.getVertex(j))) {
                            if (v.getId() > j) {
                                isAdd = false;
                                break;
                            }
                        }
                    } else {
                        for (Vertex v : gILU.neighbors(gILU.getVertex(i))) {
                            if (v.getId() > i) {
                                isAdd=false;
                                break;
                            }
                        }
                    }
                    if(isAdd) {
                        addM.set(i, j);
                        gILU.addEdge(new Edge(gILU.getVertex(i), gILU.getVertex(j)));
                    }
                }
            }
        }
        return addM;
    }
}
