// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RendTable;
import graphtea.library.algorithms.util.LibraryUtils;
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

@CommandAttitude(name = "ggcompconj", abbreviation = "_ggcconj")
public class GGcompConjecture implements GraphReportExtension{
    public String getName() {
        return "GGcomp Conjecture";
    }

    public String getDescription() {
        return "GGcomp Conjecture";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zifg = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifgc = new ZagrebIndexFunctions(
                (graphtea.graph.graph.GraphModel)
                        LibraryUtils.complement(g));

        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add(" M^2_1(G) + M^2_1(Gcomp)");
        ret.get(0).add(" RHS ");

        double maxDeg = 0;
        double maxDeg2 = 0;
        double minDeg = Integer.MAX_VALUE;

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        maxDeg = al.get(al.size()-1);
        minDeg = al.get(0);
        if(al.size()-2>=0) maxDeg2 = al.get(al.size()-2);
        else maxDeg2 = maxDeg;
        if(maxDeg2 == 0) maxDeg2=maxDeg;

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        ret.add(new Vector<Object>());
        ret.get(1).add(zifg.getFirstZagreb(1)+
                zifgc.getFirstZagreb(1));

        double res = Math.pow(n*(n-2)-2*m + minDeg + 1,2)
                    + Math.pow(2 * m - maxDeg, 2);
        res/=n-1;
        res+=Math.pow(maxDeg,2) + Math.pow(n-1-minDeg,2);
        res+= ((n-1)/4)*(Math.pow(maxDeg-minDeg,2)+Math.pow(maxDeg2-minDeg,2));
        ret.get(1).add(res);
        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
