// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.GraphModel;
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

@CommandAttitude(name = "lowerm2conj", abbreviation = "_lm2conj")
public class LowerM2Conjecture implements GraphReportExtension{
    public String getName() {
        return "Lower M2 Conjecture";
    }

    public String getDescription() {
        return "Lower M2 Conjecture";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add(" M^3_1(G) ");
        ret.get(0).add(" 1.1 ");
        ret.get(0).add(" 1.2 ");
        ret.get(0).add(" 1.3 ");
        ret.get(0).add(" 1.4 ");

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
        if(maxDeg==minDeg) b=0;

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double M12=zif.getSecondZagreb(1);
        double M21=zif.getFirstZagreb(1);
        double M22=zif.getSecondZagreb(2);
        double Mm11=zif.getFirstZagreb(-2);

        ret.add(new Vector<Object>());
        ret.get(1).add(M21);
        //1
        ret.get(1).add(Math.pow(2*m - n + Math.sqrt(2*m*Mm11),2)/n);
        //2
        ret.get(1).add(Math.pow(maxDeg,2)+(Math.pow(2*m-maxDeg,2)/(n-1))
                + ((Math.pow(n-2,2)*(maxDeg2-minDeg))/Math.pow(n-1,2)));
        //3
        ret.get(1).add(Math.pow(maxDeg,2)+(Math.pow(2*m-maxDeg,2)/(n-1))
                  + ((2*(n-2)*(maxDeg2-minDeg))/Math.pow(n-1,2)));
        //4
        ret.get(1).add((4*m*m)/n);

        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
