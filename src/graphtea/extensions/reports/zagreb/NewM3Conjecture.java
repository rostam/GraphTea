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

@CommandAttitude(name = "m3boundconj", abbreviation = "_m3conj")
public class NewM3Conjecture implements GraphReportExtension{
    public String getName() {
        return "New M3 Conjecture";
    }

    public String getDescription() {
        return "New M3 Conjecture";
    }

    public Object calculate(GraphData gd) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(gd.getGraph());
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add(" M^3_1(G) ");
        ret.get(0).add(" 0 ");
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
        //0
        ret.get(1).add((maxDeg+minDeg)*M21 - 2*m*minDeg*maxDeg
                  - (2*m-a*maxDeg-b*minDeg)*(maxDeg-minDeg-1));
        //1
        ret.get(1).add(2*M12 + n*M21 - 4*m*m);
        //2
        ret.get(1).add((maxDeg+minDeg)*M21 - 2*m*minDeg*maxDeg);
        //3
        ret.get(1).add(Math.pow(maxDeg,3) + maxDeg
                - (m - maxDeg)*(2*((n-2)*minDeg-3)
                -(n+minDeg+1)*((2*(m-maxDeg)/(n-2))+n-3)));
        //4
        ret.get(1).add(Math.pow(maxDeg,3) + maxDeg
                - (m - maxDeg)*(2*((n-2)*(minDeg-1)-3)
                -(n+minDeg)*((2*(m-maxDeg)/(n-2))+n-3)));
        //5
        ret.get(1).add((n/(2*m))*zif.getFirstZagreb(3));
        //6
        ret.get(1).add((Math.pow(Math.pow(maxDeg,3)+Math.pow(minDeg,3),2)*n*n)/
                        (4*Math.pow(maxDeg*minDeg,3)*zif.getFirstZagreb(-4)));
        //7
        ret.get(1).add((Math.pow(maxDeg+minDeg,2)
                *Math.pow(zif.getFirstZagreb(1),2))/
                (8*m*minDeg*maxDeg));
        //8
        ret.get(1).add((Math.pow(Math.pow(maxDeg,1.5) + Math.pow(minDeg,1.5),2)*
                        Math.pow(zif.getFirstZagreb(0.5),2))/
                        (4*n*Math.pow(maxDeg*minDeg,1.5)));
        //9
        ret.get(1).add(Math.pow(maxDeg,3) + maxDeg + (m-maxDeg)*(n*n-n+1) +
         2*(m-maxDeg)*(((m-maxDeg)*(n*2)/(n-2))- Math.sqrt(2*(m-maxDeg)+(1/4))));

        double n2=Math.ceil(n/2.);
        double kn = n*n2*(1-(n2/n));
        double val = kn*Math.pow(maxDeg-minDeg,2)*(maxDeg+minDeg)
                + 2*m*M21;
        //10
        ret.get(1).add(val/n);
        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
