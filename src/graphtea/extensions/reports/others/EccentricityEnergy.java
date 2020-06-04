package graphtea.extensions.reports.others;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Iterator;
import java.util.Vector;

@CommandAttitude(name = "EccentricityEnergy", abbreviation = "_eccentricity_index")
public class EccentricityEnergy implements GraphReportExtension {
    public String getName() {
        return "Eccentricity Energy";
    }

    public String getDescription() {
        return "Eccentricity Energy";
    }

    public int eccentricity(GraphModel g, int v, Integer[][] dist) {
        int max_dist = 0;
        for(int j=0;j < g.getVerticesCount();j++) {
            if(max_dist < dist[v][j]) {
                max_dist = dist[v][j];
            }
        }
        return max_dist;
    }

    public Matrix eccentricityMatrix(GraphModel g, Integer[][] dist) {
        Matrix m = new Matrix(g.getVerticesCount(),g.getVerticesCount());
        for(int i=0;i < g.getVerticesCount();i++) {
            for(int j=0;j < g.getVerticesCount();j++) {
                int ecc_i = eccentricity(g, i, dist);
                int ecc_j = eccentricity(g, j, dist);
                if(dist[i][j] == Math.min(ecc_i, ecc_j)) {
                    m.set(i,j,dist[i][j]);
                }
            }
        }
        return m;
    }

    public Integer[][] getAllPairsShortestPathWithoutWeight(final GraphModel g) {
        final Integer dist[][] = new Integer[g.numOfVertices()][g.numOfVertices()];
        Iterator<Edge> iet = g.edgeIterator();
        for (int i = 0; i < g.getVerticesCount(); i++)
            for (int j = 0; j < g.getVerticesCount(); j++)
                dist[i][j] = g.numOfVertices();

        for (Vertex v : g)
            dist[v.getId()][v.getId()] = 0;

        while (iet.hasNext()) {
            Edge edge = iet.next();
            dist[edge.target.getId()][edge.source.getId()] = 1;
            dist[edge.source.getId()][edge.target.getId()] = 1;
        }

        for (Vertex v : g)
            for (Vertex u : g)
                for (Vertex w : g) {
                    if ((dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()]) < dist[v.getId()][u.getId()])
                        dist[v.getId()][u.getId()] = dist[v.getId()][w.getId()] + dist[w.getId()][u.getId()];
                }

        return dist;
    }

    @Override
    public Object calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("m ");
        titles.add("n ");
        titles.add("Eccentricity Energy");
        ret.setTitles(titles);
        Integer[][] dist = getAllPairsShortestPathWithoutWeight(g);

        Matrix m = eccentricityMatrix(g, dist);
        EigenvalueDecomposition ed = m.eig();
        double rv[] = ed.getRealEigenvalues();
        double sum = 0;

        //positiv RV
        Double[] prv = new Double[rv.length];
        for (int i = 0; i < rv.length; i++) {
            prv[i] = Math.abs(rv[i]);
            prv[i] = (double)Math.round(prv[i] * 100000d) / 100000d;
            sum += prv[i];
        }
        Vector<Object> v = new Vector<>();
        v.add(g.getVerticesCount());
        v.add(g.getEdgesCount());
        v.add(sum);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }

}
;