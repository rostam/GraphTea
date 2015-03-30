// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.RendTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "conj10", abbreviation = "_conj10")
public class Conjecture10 implements GraphReportExtension,Parametrizable {
    public String getName() {
        return "ZIndices of Matching Conjecture 10";
    }

    public String getDescription() {
        return "ZIndices of Matching Conjecture 10";
    }

    public Object calculate(GraphData gd) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(gd.getGraph());
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add("n(n-1)^2-4mn+2d(K12)+6m");
        ret.get(0).add("n(n-1)^3-4mn+2d()+6m");
        ret.get(0).add("n(n-1)^4-4mn+2d(P3)+6m");
        ret.get(0).add("n(n-1)^5-4mn+2d(P3)+6m");
        int ind = 0;
        //for(double alpha = start_alpha;alpha <= end_alpha;alpha=alpha+inc) {
        //    ind++;
        //    double fZagreb=zif.getFirstZagrebSelectedEdges(2*alpha - 1);
        //    double fZagreb2=zif.getFirstZagrebSelectedEdges(2*alpha - 2);

         //   double coef = (2*am + bm + 2*k)/(2*k);
         //   ret.add(new Vector<Object>());
          //  ret.get(ind).add(alpha);
           // ret.get(ind).add(fZagreb);
           // ret.get(ind).add(2*k*Math.pow(coef,2*alpha));
           // ret.get(ind).add(coef*fZagreb2);
       // }
        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}

    @Override
    public String checkParameters() {
        return null;
    }
}
