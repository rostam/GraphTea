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
        for (Vertex v : g.directNeighbors(given)) {
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
                if (i != j) {
                    boolean isEdge = false;
                    for (int k : mm.get(i)) {
                        if (mm.contains(j, k)) {
                            isEdge = true;
                            break;
                        }
                    }
                    if (isEdge)
                        gOfCol.addEdge(new Edge(gOfCol.getVertex(i), gOfCol.getVertex(j)));
                }
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
                if(i!=j) {
                    boolean isEdge = false;
                    boolean isReq = false;
                    for (int k : mm.get(i)) {
                        if (mm.contains(j, k)) {
                            isEdge = true;
                            if (mmRes.contains(i, k) || mmRes.contains(j, k)) {
                                isReq = true;
                                break;
                            }
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
        }

        int cnt = 0;
        for(Edge e : gOfCol.edges()) {
            if(e.getWeight() == 1) cnt++;
        }

        return gOfCol;
    }

    public static int ILUOneStep(GraphModel g, Vertex selected, int el, SpMat F) {
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
                                F.get(e.source.getId()).add(e.target.getId());
                                fillin++;
                            }
                        }
                    }
                }
            }
        }
        return fillin;
    }

    public static SpMat SILU(GraphModel g, int el, Vector<Integer> order, SpMat blockA) {
        System.out.println("size " + g.getEdgesCount());
        SpMat F = blockA.copy();
        for (Edge e : g.edges()) e.setWeight(0);
        for (int i : order) {
            Helper.ILUOneStep(g, g.getVertex(i), el, F);
        }
        return F;
    }

    public static SpMat getFillinMinDeg(GraphModel g, int el, Set<Integer> order, String ord, SpMat blockA) {
        SpMat F = blockA.copy();
        int fillin = 0;
        for (Edge e : g.edges()) e.setWeight(0);
        if(ord.equals("Normal")) {
            for (int i = 0; i < g.numOfVertices(); i++) {
                fillin += Helper.ILUOneStep(g, g.getVertex(i), el, F);
            }
        } else {
            for (int i : order) {
                fillin += Helper.ILUOneStep(g, g.getVertex(i), el,F);
            }
        }
        return F;
    }

    //the input graph should be already computed
    //P is the number of potentially required edges
    //mR=block matrix
    public static  SpMat getPotReqEdges(GraphModel g, SpMat m, SpMat mR) {
        SpMat potM = new SpMat(m.rows(), m.cols());
//        for(Vertex v : g) {
//            for (Vertex u : g.directNeighbors(v)) {
//                if (g.getEdge(v, u).getWeight() != 1) {
//                    if(v.getColor() != u.getColor()) {
//                        g.getEdge(v,u).setWeight(1);
//                        potM.set(v.getId(),u.getId());
//                    }
//                }
//            }
//        }

        for (int i = 0; i < m.rows(); i++) {
            if (g.getVertex(i).getColor() == 0) System.out.println(i + " test");
            for (int j : m.get(i)) {
                if (!mR.contains(i, j)) {
                    boolean isPot = true;
                    for (int k = 0; k < m.rows(); k++) {
                        if (k != i && m.contains(k, j) && !mR.contains(k, j)) {
                            if (g.getVertex(i).getColor() == g.getVertex(k).getColor()) {
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
    public static SpMat getAddReqEdges(GraphModel gILU, SpMat m, SpMat mR, SpMat mP,Vector<Integer> order) {
        SpMat addM = new SpMat(m.rows(),m.cols());
        //for (int i = 0; i < m.getRowDimension(); i++) {
        //    for (int j = 0; j < m.getColumnDimension(); j++) {
        for(int i : order) {
            for(int j : order) {
                if(mP.contains(i, j)) {
                    boolean isAdd = true;
                    if(i > j) {
                        for (Vertex v : gILU.directNeighbors(gILU.getVertex(j))) {
                            if (v.getId() > j) {
                                isAdd = false;
                                break;
                            }
                        }
                    } else {
                        for (Vertex v : gILU.directNeighbors(gILU.getVertex(i))) {
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

//    public static Matrix ILUR(Matrix m, SpMat m10,SpMat F) {
//        System.out.println("man " + F.nnz());
//        Matrix ret = new Matrix(m.getRowDimension(),m.getColumnDimension());
//        for(int i=0;i<m10.rows();i++) {
//            for(int j : m10.get(i)) {
//                ret.set(i,j,m.get(i,j));
//            }
//        }
//        for(int i=0;i<ret.getColumnDimension();i++) {
//            for(int k=0;k<i-1;k++) {
//                if(F.get(i).contains(k)) {
//                    ret.set(i,k,ret.get(i,k)/ret.get(k,k));
//                    for(int j=k;j<ret.getColumnDimension();j++) {
//                        if(F.get(i).contains(j)&&F.get(k).contains(j)) {
//                            double tmp = ret.get(i,j)-ret.get(i,k)*ret.get(k,j);
//                            ret.set(i, j, tmp);
//                        }
//                    }
//                }
//            }
//        }
//        return ret;
//    }
}
