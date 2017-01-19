// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;
import graphtea.extensions.Utils;
import graphtea.extensions.reports.basicreports.NumOfVerticesWithDegK;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
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

@CommandAttitude(name = "m3finalconj", abbreviation = "_m3conj")
public class HyperCheck implements GraphReportExtension{
    public String getName() {
        return "Hyper Check";
    }

    public String getDescription() {
        return "Hyper Check";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(Utils.createLineGraph(g));

        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" p ");

        double maxDeg = 0;
        double maxDeg2 = 0;
        double minDeg = Integer.MAX_VALUE;
        double minDeg2 = Utils.getMinNonPendentDegree(g); 

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        maxDeg = al.get(al.size()-1);
        if(al.size()-2>=0) maxDeg2 = al.get(al.size()-2);
        else maxDeg2 = maxDeg;
        minDeg = al.get(0);
        if(maxDeg2 == 0) maxDeg2=maxDeg;

        double a=0;
        double b=0;
        double c=0;
        double d=0;
        int p = NumOfVerticesWithDegK.numOfVerticesWithDegK(g, 1);

        for(Vertex v : g) {
            if(g.getDegree(v)==maxDeg) a++;
            if(g.getDegree(v)==minDeg) b++;
            if(g.getDegree(v)==maxDeg2) c++;
            if(g.getDegree(v)==minDeg2) d++;
         }
        if(maxDeg==minDeg) b=0;
        if(maxDeg==maxDeg2) c=0;

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double M12=zif.getSecondZagreb(1);
        double M21=zif.getFirstZagreb(1);
        double M31=zif.getFirstZagreb(2);
        double M41=zif.getFirstZagreb(3);
        double M22=zif.getSecondZagreb(2);
        double Mm31=zif.getFirstZagreb(-4);
        double Mm11=zif.getFirstZagreb(-2);


        Vector<Object> v = new Vector<>();
        v.add(p);
        ret.add(v);


        //  v.add(2*M12+(a*maxDeg*maxDeg*maxDeg)+(c*maxDeg2*maxDeg2*maxDeg2)
        //	+((maxDeg+maxDeg2)*(M21-a*maxDeg*maxDeg-c*maxDeg2*maxDeg2))
        //		+((maxDeg-maxDeg2-1-maxDeg*maxDeg2)*(2*m-a*maxDeg-c*maxDeg2)));

        // v.add(zif.getFirstZagreb(1));

        // v.add((2*m*(maxDeg+minDeg))
        //       - (n*minDeg*maxDeg)
        //     - (n-a-b)*(maxDeg-minDeg-1));


        return ret;
    }

    @Override
    public String getCategory() {
        // TODO Auto-generated method stub
        return "Topological Indices-Conjectures";
    }
}
