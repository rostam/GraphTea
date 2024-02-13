package graphtea.extensions.reports.coloring;

import Jama.Matrix;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

public class ColumnIntersectionGraph {
    public static GraphModel from(GraphModel g) {
        return from(g.getAdjacencyMatrix());
    }

    public static GraphModel from(Matrix m) {
        GraphModel g = new GraphModel();
        for(int i=0;i < m.getColumnDimension();i++) g.addVertex(new Vertex());
        for (int i = 0; i < m.getColumnDimension(); i++) {
            for (int j = 0; j < m.getColumnDimension(); j++) {
                for (int k = 0; k < m.getRowDimension(); k++) {
                    if (m.get(i, k) != 0 && m.get(j, k) != 0) {
                        g.addEdge(new Edge(g.getVertex(i), g.getVertex(j)));
                        break;
                    }
                }
            }
        }
        return g;
    }

    public static GraphModel from(SpMat m) {
        GraphModel g = new GraphModel();
        for(int i=0;i < m.cols();i++) g.addVertex(new Vertex());
        for (int i = 0; i < m.cols(); i++) {
            for (int j = 0; j < m.cols(); j++) {
                for (int k = 0; k < m.rows(); k++) {
                    if (m.contains(i, k)  && m.contains(j, k)) {
                        g.addEdge(new Edge(g.getVertex(i), g.getVertex(j)));
                        break;
                    }
                }
            }
        }
        return g;
    }

    public static GraphModel sparsify(Matrix m, int k) {
        SpMat smat = new SpMat(m);
        smat = smat.sparsify(k);
        return from(smat);
    }

    public static GraphModel sparsify(GraphModel g, int k) {
        Matrix m = g.getAdjacencyMatrix();
        return sparsify(m,k);
    }
}
