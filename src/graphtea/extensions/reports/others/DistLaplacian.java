package graphtea.extensions.reports.others;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.spectralreports.DistanceEnergy;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "DistanceEnergyCompare", abbreviation = "_distance_energy_compare")
public class DistLaplacian implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Distance Signless laplacian";
    }

    public String getDescription() {
        return "Distance calculations";
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("m ");
        titles.add("n ");
        //titles.add("Distance Laplacian");
        titles.add("Eigen Values");
	//	titles.add("Eigen Values");
		
        ret.setTitles(titles);
        Matrix DLS = AlgorithmUtils.getDistanceSignlessLaplacianMatrix(g);
		Matrix DL= AlgorithmUtils.getDistanceLaplacianMatrix(g);
		Vector<Object> v = new Vector<>();
		
        v.add(g.getVerticesCount());
        v.add(g.getEdgesCount());
		
		// v.add(new DistanceEnergy().calculate(g));
		
		// Eigen values distance signless laplacian
	    v.add(AlgorithmUtils.getEigenValues(DLS));
		
		// Eigen values distance laplacian
       // v.add(AlgorithmUtils.getEigenValues(DL));
		
		
		
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
