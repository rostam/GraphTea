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

@CommandAttitude(name = "m3lowerconj", abbreviation = "_m3conj")
public class M3Lower implements GraphReportExtension{
    public String getName() {
        return "M3 Lower";
    }

    public String getDescription() {
        return "M3 Lower";
    }

    public Object calculate(GraphData gd) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(gd.getGraph());
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add(" M^3_1(G) ");
        ret.get(0).add(" 1max");
        ret.get(0).add(" 1min");
        ret.get(0).add(" 2max");
        ret.get(0).add(" 2min");
        ret.get(0).add(" 3max");
        ret.get(0).add(" 3min");
        ret.get(0).add(" 4max");
        ret.get(0).add(" 4min");
        ret.get(0).add(" BF");
        ret.get(0).add(" Ilic ");
        ret.get(0).add(" BF-");
        ret.get(0).add(" Zhou ");

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
        double Mm31=zif.getFirstZagreb(-4);
        double Mm11=zif.getFirstZagreb(-2);

        ret.add(new Vector<Object>());

        ret.get(1).add(zif.getFirstZagreb(2));
        //1max
        ret.get(1).add((maxDeg*maxDeg*maxDeg)+(maxDeg2*maxDeg2*maxDeg2)+( Math.pow((M21-(maxDeg*maxDeg)-(maxDeg2*maxDeg2))+Math.sqrt((n-2)* (M21-(maxDeg*maxDeg)-(maxDeg2*maxDeg2)))-(2*m-maxDeg-maxDeg2),2)/(2*m-maxDeg-maxDeg2)));
        //1min
        ret.get(1).add((maxDeg*maxDeg*maxDeg)+(minDeg*minDeg*minDeg)+( Math.pow((M21-(maxDeg*maxDeg)-(minDeg*minDeg))+Math.sqrt((n-2)*(M21-(maxDeg*maxDeg)-(minDeg*minDeg)))-(2*m-maxDeg-minDeg),2)/(2*m-maxDeg-minDeg)));
        //2max
        ret.get(1).add((maxDeg*maxDeg*maxDeg)+(maxDeg2*maxDeg2*maxDeg2)+( Math.pow((M21-(maxDeg*maxDeg)-(maxDeg2*maxDeg2))+Math.sqrt((2*m-maxDeg-maxDeg2)*(Mm11-(1/maxDeg)-(1/maxDeg2)))-(n-2),2)/(2*m-maxDeg-maxDeg2)));
        //2min
        ret.get(1).add((maxDeg*maxDeg*maxDeg)+(minDeg*minDeg*minDeg)+( Math.pow((M21-(maxDeg*maxDeg)-(minDeg*minDeg))+Math.sqrt((2*m-maxDeg-minDeg)*(Mm11-(1/maxDeg)-(1/minDeg)))-(n-2),2)/(2*m-maxDeg-minDeg)));
        //3max
        ret.get(1).add(((maxDeg*maxDeg*maxDeg)+(maxDeg2*maxDeg2*maxDeg2)-(2*m-maxDeg-maxDeg2))+((((M21-(maxDeg*maxDeg)-(maxDeg2*maxDeg2))*(M21-(maxDeg*maxDeg)-(maxDeg2*maxDeg2)))+((n-2)*(M21-(maxDeg*maxDeg)-(maxDeg2*maxDeg2)))))/(2*m-maxDeg-maxDeg2));
        //3min
        ret.get(1).add(((maxDeg*maxDeg*maxDeg)+(minDeg*minDeg*minDeg)-(2*m-maxDeg-minDeg))+((((M21-(maxDeg*maxDeg)-(minDeg*minDeg))*(M21-(maxDeg*maxDeg)-(minDeg*minDeg)))+((n-2)*(M21-(maxDeg*maxDeg)-(minDeg*minDeg)))))/(2*m-maxDeg-minDeg));
        //4max
        ret.get(1).add(((maxDeg*maxDeg*maxDeg)+(maxDeg2*maxDeg2*maxDeg2))+((((M21-(maxDeg*maxDeg)-(maxDeg2*maxDeg2))*(M21-(maxDeg*maxDeg)-(maxDeg2*maxDeg2)))+((2*m-maxDeg-maxDeg2)*(Mm11-(1/maxDeg)-(1/maxDeg2)))-((n-2)*(n-2)))/(2*m-maxDeg-maxDeg2)));
        //4min
        ret.get(1).add(((maxDeg*maxDeg*maxDeg)+(minDeg*minDeg*minDeg))+((((M21-(maxDeg*maxDeg)-(minDeg*minDeg))*((M21-(maxDeg*maxDeg)-(minDeg*minDeg))))+((2*m-maxDeg-minDeg)*(Mm11-(1/maxDeg)-(1/minDeg)))-((n-2)*(n-2)))/(2*m-maxDeg-minDeg)));
        //BF1
        ret.get(1).add((M21*M21)/(2*m));
        //Ilic
        ret.get(1).add((2*m*M21)/n);
        //BF2
        ret.get(1).add(((M21*M21)/(m))-(2*M12));
        //Zhou
        ret.get(1).add(16*((m*m*m)/(n*n))-(2*M12));
        return ret;
    }

    @Override
    public String getCategory() {
        // TODO Auto-generated method stub
        return "Topological Indices";
    }
}