// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

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

@CommandAttitude(name = "conj34", abbreviation = "_conj34")
public class Conjecture34 implements GraphReportExtension,Parametrizable {
    public String getName() {
        return "ZIndices of Matching Conjecture 23";
    }

    @Parameter(name = "Starting Value of Alpha", description = "")
    public Double start_alpha = -10.0;

    @Parameter(name = "End Value of Alpha", description = "")
    public Double end_alpha = 10.0;

    @Parameter(name = "Incremental Value", description = "")
    public Double inc = 0.1;

    public String getDescription() {
        return "ZIndices of Matching Conjecture 23";
    }

    public Object calculate(GraphData gd) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(gd);
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add("Alpha");
        ret.get(0).add(" (n-1)^a M^{a}_1 (Matching) ");
        ret.get(0).add(" Delta^{a} M^{a}_1 (Matching) ");
        ret.get(0).add(" 2M^{a}_2 (Matching) ");

        double maxDeg = 0;
        for(Vertex v : gd.getGraph()) {
            if(gd.getGraph().getDegree(v) > maxDeg)
                maxDeg = gd.getGraph().getDegree(v);
        }

        int ind = 0;
        for(double alpha = start_alpha;alpha <= end_alpha;alpha=alpha+inc) {
            ind++;
            double first_zagreb = zif.getFirstZagrebSelectedEdges(alpha-1);
            double second_zagreb = zif.getSecondZagrebSelectedEdges(alpha);
            double nMinus1PowAlpha =
                    Math.pow(gd.getGraph().getVerticesCount()-1,alpha);

            double maxDegPowAlpha = Math.pow(maxDeg,alpha);
            ret.add(new Vector<Object>());
            ret.get(ind).add(alpha);
            ret.get(ind).add(nMinus1PowAlpha*first_zagreb);
            ret.get(ind).add(maxDegPowAlpha*first_zagreb);
            ret.get(ind).add(2*second_zagreb);
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
