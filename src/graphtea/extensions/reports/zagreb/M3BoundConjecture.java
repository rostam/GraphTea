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

@CommandAttitude(name = "m3boundconj", abbreviation = "_m3conj")
public class M3BoundConjecture implements GraphReportExtension{
    public String getName() {
        return "M3 Bound Conjecture";
    }

    public String getDescription() {
        return "M3 Bound Conjecture";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add(" M^3_1(G) ");
        ret.get(0).add(" 1 ");
        ret.get(0).add(" 2 ");
        ret.get(0).add(" 3 ");
        ret.get(0).add(" 4 ");
        ret.get(0).add(" 5 ");
        ret.get(0).add(" 6 ");
        ret.get(0).add(" 7 ");
        ret.get(0).add(" 8 ");
        ret.get(0).add(" 9 ");
        ret.get(0).add(" 10 ");
        ret.get(0).add(" 11 ");

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

        double M12=zif.getSecondZagreb(1);
        double M21=zif.getFirstZagreb(1);
        double M22=zif.getSecondZagreb(2);

        ret.add(new Vector<Object>());
        ret.get(1).add(zif.getFirstZagreb(2));
        //1
        ret.get(1).add(2*M12 + n*M21 - 4*m*m);
        //2
        ret.get(1).add((maxDeg+minDeg)*M21 - 2*m*minDeg*maxDeg);
        //3
        ret.get(1).add(((2*m-(maxDeg*maxDeg-minDeg*minDeg))*M21)/n +
                        (2*m*(n-1)*(maxDeg*maxDeg-minDeg*minDeg))/n);
        //4
        ret.get(1).add(n*n*(n-1)-2*M12);
        //5
        ret.get(1).add(m*n*n -2*M12);
        //6
        ret.get(1).add(M22+m);
        //7
        ret.get(1).add((n/m)*M22);
        //8
        ret.get(1).add((n/(2*m))*zif.getFirstZagreb(3));
        //9
        ret.get(1).add((Math.pow(Math.pow(maxDeg,3)+Math.pow(minDeg,3),2)*n*n)/
                        (4*Math.pow(maxDeg*minDeg,3)*zif.getFirstZagreb(-4)));
        //10
        ret.get(1).add((Math.pow(maxDeg+minDeg,2)
                *Math.pow(zif.getFirstZagreb(1),2))/
                (8*m*minDeg*maxDeg));
        //11
        ret.get(1).add((Math.pow(Math.pow(maxDeg,1.5) + Math.pow(minDeg,1.5),2)*
                        Math.pow(zif.getFirstZagreb(0.5),2))/
                        (4*n*Math.pow(maxDeg*minDeg,1.5)));

        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices-Conjectures";
	}
}
