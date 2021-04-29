package graphtea.extensions.reports.others;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "EccentricityEnergy", abbreviation = "_eccentricity_index")
public class EccentricityEnergy implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Eccentricity Energy";
    }

    public String getDescription() {
        return "Eccentricity Energy";
    }

    public int eccentricity(GraphModel g, int v, int[][] dist) {
        int max_dist = 0;
        for(int j=0;j < g.getVerticesCount();j++) {
            if(max_dist < dist[v][j]) {
                max_dist = dist[v][j];
            }
        }
        return max_dist;
    }

    public Matrix eccentricityMatrix(GraphModel g, int[][] dist) {
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

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("m ");
        titles.add("n ");
        titles.add("Eccentricity Energy");
        titles.add("Eigen Values");
		titles.add("Diameter");
        ret.setTitles(titles);
        FloydWarshall fw = new FloydWarshall();
        int[][] dist = fw.getAllPairsShortestPathWithoutWeight(g);

        Matrix m = eccentricityMatrix(g, dist);
        EigenvalueDecomposition ed = m.eig();
        double[] rv = ed.getRealEigenvalues();
        double sum = 0;
		
		int diameter = (int) new Diameter().calculate(g);

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
        v.add(AlgorithmUtils.getEigenValues(m));
		v.add(diameter);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
