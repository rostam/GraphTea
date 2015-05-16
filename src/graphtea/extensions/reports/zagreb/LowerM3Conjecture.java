// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.Vertex;
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

@CommandAttitude(name = "lowerm3conj", abbreviation = "_lm3conj")
public class LowerM3Conjecture implements GraphReportExtension{
    public String getName() {
        return "Lower M3 Conjecture";
    }

    public String getDescription() {
        return "Lower M3 Conjecture";
    }

    public Object calculate(GraphData gd) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(gd.getGraph());
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add(" M^3_1(G) ");
        ret.get(0).add(" 2.1 ");
        ret.get(0).add(" 2.2 ");
        ret.get(0).add(" 2.3 ");
        ret.get(0).add(" 2.4 ");

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
        if(maxDeg==minDeg) b=0;

        double m = gd.getGraph().getEdgesCount();
        double n = gd.getGraph().getVerticesCount();

        double M12=zif.getSecondZagreb(1);
        double M21=zif.getFirstZagreb(1);
        double M22=zif.getSecondZagreb(2);

        ret.add(new Vector<Object>());
        ret.get(1).add(zif.getFirstZagreb(2));
        //1
        ret.get(1).add(Math.pow(M21 - 2*m + Math.sqrt(n*M21),2)/(2*m));
        //2
        ret.get(1).add(Math.pow(M21,2)/(2*m));
        //3
        ret.get(1).add(((Math.pow(M21,2)/m) - 2*M12));
        //4
        ret.get(1).add(((2*m)/n)*M21);

        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
