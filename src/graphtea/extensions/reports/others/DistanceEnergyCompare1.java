package graphtea.extensions.reports.others;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.spectralreports.DistanceEnergy;
import graphtea.extensions.reports.spectralreports.DistanceLaplacianEnergy;
import graphtea.extensions.reports.spectralreports.DistanceSignlessLaplacianEnergy;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

@CommandAttitude(name = "DistanceEnergyCompare1", abbreviation = "_distance_energy_compare")
public class DistanceEnergyCompare1 implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Dist  Energy Compare";
    }

    public String getDescription() {
        return "Distance Energy based on" +
                "Gopalapillai Indulal,a Ivan Gutmanb and Vijayakumarc\n" +
                "      ON DISTANCE ENERGY OF GRAPHS\n" +
                "      MATCH Commun. Math. Comput. Chem. 60 (2008) 461-472. ";
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add("m ");
        titles.add("n ");
	  //   titles.add("check");
	  
        titles.add("DE");
	  	titles.add("DLE");
        titles.add("DLSE");
     // titles.add("Eigen Values");
        ret.setTitles(titles);
        Matrix de = AlgorithmUtils.getDistanceAdjacencyMatrix(g);
		Matrix dle = AlgorithmUtils.getDistanceLaplacianMatrix(g);
		Matrix dlse = AlgorithmUtils.getDistanceSignlessLaplacianMatrix(g);
		
		double DE   = new DistanceEnergy().calculate(g);
		double DLE  = new DistanceLaplacianEnergy().calculate(g);
        double DLSE = new DistanceSignlessLaplacianEnergy().calculate(g);	
        Vector<Object> v = new Vector<>();
        v.add(g.getVerticesCount());
        v.add(g.getEdgesCount());
		// if(DLE==DLSE) v.add(1);
	//	 else return null;
         v.add(DE);
         v.add(DLE);
         v.add(DLSE);
	   // Distance Energy Eignevalues	
      //  v.add(AlgorithmUtils.getEigenValues(de));
	   // Distance Laplacian Energy Eignevalues	
      //  v.add(AlgorithmUtils.getEigenValues(dle));
	   // Distance Signless Laplacian Energy Eignevalues	
      //  v.add(AlgorithmUtils.getEigenValues(dlse));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification- Energy";
    }
}

 



