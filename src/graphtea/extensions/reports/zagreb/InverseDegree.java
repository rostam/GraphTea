// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "lowerm2conj", abbreviation = "_lm2conj")
public class InverseDegree implements GraphReportExtension{
    public String getName() {
        return "Inverse Degree";
    }

    public String getDescription() {
        return "Inverse Degree";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add(" M^-1_1(G) ");
        ret.get(0).add(" S3 Max ");
        ret.get(0).add(" S3 Min ");
        ret.get(0).add(" S2 Max ");
        ret.get(0).add(" S2 Min ");
        ret.get(0).add(" Base ");

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
        ret.get(1).add(Mm11);
        //S3 Max
        ret.get(1).add((1/maxDeg) + (1/maxDeg2)
                + (Math.pow(n-2,2)/(2*m-maxDeg-maxDeg2)));
      //S3 Max
        ret.get(1).add((1/maxDeg) + (1/minDeg)
                + (Math.pow(n-2,2)/(2*m-maxDeg-minDeg)));
        
        //S2 Max
        ret.get(1).add((1/maxDeg) + (1/maxDeg2)
                + ((n-2)*(2*m-maxDeg-maxDeg2)/
                (M21-maxDeg*maxDeg-maxDeg2*maxDeg2)));
        //S2 Min
        ret.get(1).add((1/maxDeg) + (1/minDeg)
                + ((n-2)*(2*m-maxDeg-minDeg)/
                (M21-maxDeg*maxDeg-minDeg*minDeg)));
        //3
        ret.get(1).add((2*m*n)/(M21));

        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
