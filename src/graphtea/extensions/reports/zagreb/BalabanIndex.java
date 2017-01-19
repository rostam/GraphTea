// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.extensions.Utils;
import graphtea.extensions.reports.DijkstraNonNegative;
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
public class BalabanIndex implements GraphReportExtension{
    public String getName() {
        return "Balaban Index";
    }

    public String getDescription() {
        return "Balaban Index";
    }

    public Object calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" Balaban Index ");

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


        Vector<Object> v = new Vector<>();

        double tmp = m/(m-n+2.);

        double allSum = 0;

        for(Vertex fV : g) {
            for(Vertex sV : g) {
                if(g.isEdge(fV,sV)) {
                    DijkstraNonNegative.dijkstra(g,sV);
                    double sum1 = 0;
                    for(Vertex temp : g) sum1+=(Double)temp.getUserDefinedAttribute(DijkstraNonNegative.Dist);
                    DijkstraNonNegative.dijkstra(g,fV);
                    double sum2 = 0;
                    for(Vertex temp : g) sum2+=(Double)temp.getUserDefinedAttribute(DijkstraNonNegative.Dist);
                    allSum += 1/Math.sqrt(sum1*sum2);
                }
            }
        }
        v.add(tmp*allSum);
        ret.setTitles(titles);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        // TODO Auto-generated method stub
        return "Topological Indices";
    }
}