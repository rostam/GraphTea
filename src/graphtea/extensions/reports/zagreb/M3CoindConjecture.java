// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.GraphModel;
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
public class M3CoindConjecture implements GraphReportExtension{
    public String getName() {
        return "M3 Coindex Conjecture";
    }

    public String getDescription() {
        return "M3 Coindex Conjecture";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifc
        = new ZagrebIndexFunctions((graphtea.graph.graph.GraphModel)
                GraphComplement.complement(g));


        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add(" Mc^3_1(G) ");
        ret.get(0).add(" 0 ");
        ret.get(0).add(" 1 ");
        ret.get(0).add(" 2 ");
        ret.get(0).add(" 3 ");


        double maxDeg = 0;
        double maxDeg2 = 0;
        double minDeg = Integer.MAX_VALUE;

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        maxDeg = al.get(al.size()-1);
        if(al.size()-2>=0) maxDeg2 = al.get(al.size()-2);
        else maxDeg2 = maxDeg;
        minDeg = al.get(0);

        if(maxDeg2 == 0) maxDeg2=maxDeg;

        double a=0;
        double b=0;

        for(Vertex v : g) {
            if(g.getDegree(v)==maxDeg) a++;
            if(g.getDegree(v)==minDeg) b++;
        }

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double M21=zif.getFirstZagreb(1);
        double M12=zif.getSecondZagreb(1);
        double M31=zif.getFirstZagreb(2);
        double M31gc=zifc.getFirstZagreb(2);
        double Mc31=zif.getFirstZagrebCoindex(2);

        ret.add(new Vector<Object>());
        ret.get(1).add(Mc31);
        double tmp0=2*m*(n-1)*M21 -
                Math.pow(M21-2*m+Math.sqrt(n*M21),2);
        //0
        ret.get(1).add(tmp0/(2*m));
        //1
        ret.get(1).add((m*(n-1)-M21)*(M21/m) + 2*M12);
        //2
        ret.get(1).add((2*m*(n-1)-M21)*(M21/(2*m)));
        //3
        ret.get(1).add((n-maxDeg-minDeg-1)*M21 + 2*m*maxDeg*minDeg);

        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
