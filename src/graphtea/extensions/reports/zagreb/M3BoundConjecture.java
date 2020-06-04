// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.extensions.AlgorithmUtils;
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

@CommandAttitude(name = "m3boundconj", abbreviation = "_m3conj")
public class M3BoundConjecture implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "M3 Bound Conjecture";
    }

    public String getDescription() {
        return "M3 Bound Conjecture";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" M^3_1(G) ");
        titles.add(" 1 ");
        titles.add(" 2 ");
        titles.add(" 3 ");
        titles.add(" 4 ");
        titles.add(" 5 ");
        titles.add(" 6 ");
        titles.add(" 7 ");
        titles.add(" 8 ");
        titles.add(" 9 ");
        titles.add(" 10 ");
        titles.add(" 11 ");
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

        double M12=zif.getSecondZagreb(1);
        double M21=zif.getFirstZagreb(1);
        double M22=zif.getSecondZagreb(2);

        Vector<Object> v = new Vector<>();
        v.add(zif.getFirstZagreb(2));
        //1
        v.add(2 * M12 + n * M21 - 4 * m * m);
        //2
        v.add((maxDeg + minDeg) * M21 - 2 * m * minDeg * maxDeg);
        //3
        v.add(((2 * m - (maxDeg * maxDeg - minDeg * minDeg)) * M21) / n +
                (2 * m * (n - 1) * (maxDeg * maxDeg - minDeg * minDeg)) / n);
        //4
        v.add(n * n * (n - 1) - 2 * M12);
        //5
        v.add(m * n * n - 2 * M12);
        //6
        v.add(M22 + m);
        //7
        v.add((n / m) * M22);
        //8
        v.add((n / (2 * m)) * zif.getFirstZagreb(3));
        //9
        v.add((Math.pow(Math.pow(maxDeg, 3) + Math.pow(minDeg, 3), 2) * n * n) /
                (4 * Math.pow(maxDeg * minDeg, 3) * zif.getFirstZagreb(-4)));
        //10
        v.add((Math.pow(maxDeg + minDeg, 2)
                * Math.pow(zif.getFirstZagreb(1), 2)) /
                (8 * m * minDeg * maxDeg));
        //11
        v.add((Math.pow(Math.pow(maxDeg, 1.5) + Math.pow(minDeg, 1.5), 2) *
                Math.pow(zif.getFirstZagreb(0.5), 2)) /
                (4 * n * Math.pow(maxDeg * minDeg, 1.5)));
        ret.add(v);
        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "OurWorks-Conjectures";
	}
}
