// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.extensions.reports.basicreports.SubTreeCounting;
import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.goperators.GraphComplement;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "m3norconj", abbreviation = "_m3norconj")
public class NewM3CompIndCoindConjecture implements GraphReportExtension{
    public String getName() {
        return "New M3 Comp Coindex Conj";
    }

    public String getDescription() {
        return "New M3 Comp Coindex Conj";
    }

    public Object calculate(GraphData gd) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(gd.getGraph());
        ZagrebIndexFunctions zifc
        = new ZagrebIndexFunctions((graphtea.graph.graph.GraphModel)
                GraphComplement.complement(gd.getGraph()));


        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add(" M^3_1(Gc) + Mco^3_1(Gc) ");
        ret.get(0).add(" 1 ");
        ret.get(0).add(" 2 ");

        double maxDeg = 0;
        double maxDeg2 = 0;
        double minDeg = Integer.MAX_VALUE;

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(gd.getGraph());
        Collections.sort(al);
        maxDeg = al.get(al.size()-1);
        if(al.size()-2>=0) maxDeg2 = al.get(al.size()-2);
        else maxDeg2 = maxDeg;
        minDeg = al.get(0);

        if(maxDeg2 == 0) maxDeg2=maxDeg;

        double a=0;
        double b=0;

        for(Vertex v : gd.getGraph()) {
            if(gd.getGraph().getDegree(v)==maxDeg) a++;
            if(gd.getGraph().getDegree(v)==minDeg) b++;
        }

        double m = gd.getGraph().getEdgesCount();
        double n = gd.getGraph().getVerticesCount();

        double M21=zif.getFirstZagreb(1);
        double M12=zif.getSecondZagreb(1);
        double M31=zif.getFirstZagreb(2);
        double M31gc=zifc.getFirstZagreb(2);
        double Mc31=zif.getFirstZagrebCoindex(2);
        double Mc31gc=zifc.getFirstZagrebCoindex(2);

        double sigmaP3 =
             SubTreeCounting.countSubtrees(gd.getGraph(),1,1);

        ret.add(new Vector<Object>());
        ret.get(1).add(M31gc+Mc31gc);
        //1
        ret.get(1).add(n*Math.pow(n-1,4) - 6*m*Math.pow(n-1,3)
           + 3*(n-1)*(n-1)*M21
           - ((((n-1)/(2*m))*Math.pow(2*sigmaP3 + Math.sqrt(n*M21),2))/(2*m)));
        //2
        ret.get(1).add(n*Math.pow(n-1,4) - 6*m*Math.pow(n-1,3)
        + (n-1)*(3*n-maxDeg-minDeg-3)*M21
        + (n-1)*(2*m*maxDeg*minDeg + (2*m-a*maxDeg-b*minDeg)*(maxDeg-minDeg-1)));

        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
