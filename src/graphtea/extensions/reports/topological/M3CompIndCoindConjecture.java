// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.algorithms.GraphComplement;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "m3norconj", abbreviation = "_m3norconj")
public class M3CompIndCoindConjecture implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "M3 Comp Index + Coindex Conj";
    }

    public String getDescription() {
        return "M3 Comp Index + Coindex Conj";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifc
        = new ZagrebIndexFunctions(GraphComplement.complement(g));


        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" M^3_1(G) + Mco^3_1(G) ");
        titles.add(" 1 ");
        titles.add(" 2 ");
        ret.setTitles(titles);

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

        double M21=zif.getFirstZagreb(1);
        double M12=zif.getSecondZagreb(1);
        double M31=zif.getFirstZagreb(2);
        double M31gc=zifc.getFirstZagreb(2);
        double Mc31=zif.getFirstZagrebCoindex(2);
        double Mc31gc=zifc.getFirstZagrebCoindex(2);

        Vector<Object> v = new Vector<>();
        v.add(M31gc + Mc31gc);
        //1
        v.add(n * Math.pow(n - 1, 3) - 4 * m * (n - 1) * (n - 1)
                + (n - 1) * (2 * m * (maxDeg + minDeg) + n * maxDeg * minDeg * (n - a - b) * (maxDeg - minDeg - 1)));
        //2
        v.add(n * Math.pow(n - 1, 3) - 4 * m * (n - 1) * (n - 1)
                        + (n - 1) * (maxDeg * maxDeg + (Math.pow(2 * m - maxDeg, 2) / (n - 1)))
                        + (2 * (n - 2) / Math.pow(n - 1, 2)) * Math.pow(maxDeg2 - minDeg, 2)
        );
        ret.add(v);

        return ret;
    }

    @Override
	public String getCategory() {
        return "Verification-Degree";
	}
}
