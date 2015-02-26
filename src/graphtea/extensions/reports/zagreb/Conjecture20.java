// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "conj20", abbreviation = "_conj20")
public class Conjecture20 implements GraphReportExtension,Parametrizable {
    public String getName() {
        return "ZIndices of Matching Conjecture 20";
    }

    @Parameter(name = "Starting Value of Alpha", description = "")
    public Double start_alpha = 0.0;

    @Parameter(name = "End Value of Alpha", description = "")
    public Double end_alpha = 10.0;

    @Parameter(name = "Incremental Value", description = "")
    public Double inc = 0.1;

    public String getDescription() {
        return "ZIndices of Matching Conjecture 20";
    }

    public Object calculate(GraphData gd) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(gd);
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add("Alpha");
        ret.get(0).add(" M^{a}_2 (G) ");
        ret.get(0).add(" 1/2n M^{a}_1(G) M^{a+1}_1(G) ");

        int ind = 0;
        for(double alpha = start_alpha;alpha <= end_alpha;alpha=alpha+inc) {
            ind++;
            double secondZagreb = zif.getSecondZagreb(alpha);
            double rhs = zif.getFirstZagreb(alpha-1)
                    *zif.getFirstReZagreb(alpha);
            rhs=rhs/(2*gd.getGraph().getVerticesCount());
            ret.add(new Vector<Object>());
            ret.get(ind).add(alpha);
            ret.get(ind).add(secondZagreb);
            ret.get(ind).add(rhs);
        }
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
