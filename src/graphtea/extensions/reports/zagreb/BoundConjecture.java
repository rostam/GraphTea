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

@CommandAttitude(name = "boundconj", abbreviation = "_bconj")
public class BoundConjecture implements GraphReportExtension{
    public String getName() {
        return "Bound Conjecture";
    }

    public String getDescription() {
        return "Bound Conjecture";
    }

    public Object calculate(GraphData gd) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(gd);
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add(" M^2_1(G) ");
        ret.get(0).add(" 1 ");
        ret.get(0).add(" 2 ");
        ret.get(0).add(" 3 ");
        ret.get(0).add(" 4 ");
        ret.get(0).add(" 5 ");
        ret.get(0).add(" 6 ");
        ret.get(0).add(" 7 ");

        double maxDeg = 0;
        double minDeg = Integer.MAX_VALUE;
        for(Vertex v : gd.getGraph()) {
            if(gd.getGraph().getDegree(v) > maxDeg)
                maxDeg = gd.getGraph().getDegree(v);
            if(gd.getGraph().getDegree(v) < minDeg)
                minDeg = gd.getGraph().getDegree(v);
        }

        double m = gd.getGraph().getEdgesCount();
        double n = gd.getGraph().getVerticesCount();

        ret.add(new Vector<Object>());
        ret.get(1).add(zif.getFirstZagreb(2));
        ret.get(1).add(m*(m+1));
        ret.get(1).add(n*(2*m-n+1));
        ret.get(1).add((2*m/(n-1)) + n - 2);
        ret.get(1).add((2*m/(n-1))
                + ((n-2)/(n-1)*maxDeg)
                + (maxDeg-minDeg)*(1 - (maxDeg/(n-1))));
        ret.get(1).add(
                Math.max(
                m*(maxDeg+minDeg-1
                +((2*m-(minDeg*(n-1)))/maxDeg)),
                m*(minDeg+1
                +((2*m-(minDeg*(n-1)))/2))
                ));
        ret.get(1).add((Math.pow(maxDeg+minDeg,2)/(n*maxDeg*minDeg))*m*m);
        ret.get(1).add((n/m)*zif.getSecondZagreb(1));
        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
