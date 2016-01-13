package graphtea.extensions.reports.coloring.fillinaware;

import Jama.Matrix;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.Vector;

/**
 * Created by rostam on 30.12.15.
 * Helper functions
 */
public class Helper {

    public static Vertex getMinDegVertex(GraphModel g) {
        Vertex ret = g.getAVertex();
        for(Vertex v:g) {
            if(g.getDegree(v) < g.getDegree(ret)) ret = v;
        }
        return ret;
    }

    public static Vertex getMaxDegVertex(GraphModel g) {
        Vertex ret = g.getAVertex();
        for(Vertex v:g) {
            if(g.getDegree(v) > g.getDegree(ret)) ret = v;
        }
        return ret;
    }

    public static int minPossibleColor(GraphModel g, Vertex given) {
        Vector<Integer> forbiddenCols = new Vector<>();
        for(int i=0;i<100;i++) forbiddenCols.add(0);
        for(Vertex v : g.getNeighbors(given)) {
            forbiddenCols.set(v.getColor(),-1);
        }
        for(int i=0;i<forbiddenCols.size();i++) {
            if(forbiddenCols.get(i) != -1 ) return i;
        }
        return 0;
    }

    public static int numberOfColors(GraphModel g) {
        int max = 0;
        for(Vertex v:g) {
            if(v.getColor()>max) max = v.getColor();
        }
        return max+1;
    }

    public static GraphModel getGraphOfILU(Matrix mm) {
        int rows = mm.getRowDimension();
        int cols = mm.getColumnDimension();
        //directed graph for ILU
        GraphModel gOfILU =new GraphModel(true);
        for(int i=0;i<cols;i++) {
            gOfILU.addVertex(new Vertex());
        }
        for(int i=0;i<rows;i++) {
            for(int j=0;j<cols;j++) {
                if(mm.get(i,j) != 0)
                    gOfILU.addEdge(new Edge(
                        gOfILU.getVertex(i),gOfILU.getVertex(j)
                ));
            }
        }
        return gOfILU;
    }

    public static GraphModel getGraphOfColoring(Matrix mm) {
        int rows = mm.getRowDimension();
        int cols = mm.getColumnDimension();

        //undirected graph for one sided coloring
        GraphModel gOfCol = new GraphModel(false);

        for(int i=0;i<cols;i++) {
            Vertex v = new Vertex();
            v.setColor(0);
            gOfCol.addVertex(v);
        }

        for(int i=0;i<rows;i++) {
            for(int j=0;j<rows;j++) {
                boolean isEdge = false;
                for(int k=0;k<cols;k++) {
                    if(mm.get(i,k) != 0 && mm.get(j,k) !=0) {
                        isEdge=true;
                        break;
                    }
                }
                if(isEdge) gOfCol.addEdge(new Edge(
                        gOfCol.getVertex(i),gOfCol.getVertex(j)));
            }
        }

        return gOfCol;
    }

    public static GraphModel getGraphOfColoringRestricted(Matrix mm,Matrix mmRes) {
        int rows = mm.getRowDimension();
        int cols = mm.getColumnDimension();

        //undirected graph for one sided coloring
        GraphModel gOfCol = new GraphModel(false);
        for(int i=0;i<rows;i++) {
            Vertex v = new Vertex();
            v.setColor(0);
            gOfCol.addVertex(v);
        }

        for(int i=0;i<rows;i++) {
            for(int j=0;j<rows;j++) {
                boolean isEdge = false;
                boolean isReq = false;
                for(int k=0;k<cols;k++) {
                    if(mm.get(i,k) != 0 && mm.get(j,k) !=0) {
                        isEdge=true;
                        if(mmRes.get(i,k)!=0 || mmRes.get(j,k)!=0) isReq = true;
                        //break;
                    }
                }
                if(isEdge) {
                    Edge e = new Edge(gOfCol.getVertex(i), gOfCol.getVertex(j));
                    if(isReq) e.setWeight(1);
                    else      e.setWeight(0);
                    gOfCol.addEdge(e);
                }

            }
        }

        return gOfCol;
    }

    public static int ILUOneStep(GraphModel g, Vertex selected, int el) {
        Vector<Vertex> inVer=new Vector<>();
        Vector<Vertex> outVer=new Vector<>();
        int fillin=0;
        for(Vertex v:g) {
            if(g.isEdge(v,selected)) inVer.add(v);
            if(g.isEdge(selected,v)) outVer.add(v);
        }

        for (Vertex anInVer : inVer) {
            for (Vertex anOutVer : outVer) {
                if (anInVer.getId() != anOutVer.getId()) {
                    if(selected.getId()<anInVer.getId() &&
                            selected.getId()<anOutVer.getId()) {
                        if (!g.isEdge(anInVer, anOutVer)) {
                            Edge e = new Edge(anInVer, anOutVer);
                            Edge e1 = g.getEdge(anInVer,selected);
                            Edge e2 = g.getEdge(selected,anOutVer);

                            e.setWeight(e1.getWeight()+e2.getWeight()+1);
                            if(e1.getWeight()+e2.getWeight()+1 <= el) {
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

    public static int getFillinMinDeg(GraphModel g, int el) {
        int fillin = 0;
//        while(g.numOfVertices()!=1) {
//            Vertex v = Helper.getMinDegVertex(g);
//            fillin+=Helper.ILUOneStep(g, v);
//        }
        for(Edge e : g.edges()) e.setWeight(0);
        for(int i=0;i<g.numOfVertices();i++) {
            fillin+=Helper.ILUOneStep(g,g.getVertex(i), el);
        }
        return fillin;
    }

    public static int numOfNonzeros(Matrix mm) {
        int rows = mm.getRowDimension();
        int cols = mm.getColumnDimension();
        int nnz=0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(mm.get(i,j)!=0) nnz++;
            }
        }
        return nnz;
    }

}
