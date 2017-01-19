// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;
import graphtea.extensions.Utils;
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
public class EM2LowerBound implements GraphReportExtension {
    public String getName() {
        return "EM2 Lower";
    }

    public String getDescription() {
        return "EM2 Lower";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(
                Utils.createLineGraph(g)
        );

        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" EM2(G) ");
        titles.add("Psi1");
        titles.add("Psi2");
        titles.add("My1");
        titles.add("My2");
        titles.add("N2");
        titles.add("N1");
        ret.setTitles(titles);

        double maxDeg = 0;
        double maxDeg2 = 0;
        double minDeg = Integer.MAX_VALUE;

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        maxDeg = al.get(al.size() - 1);
        if (al.size() - 2 >= 0) maxDeg2 = al.get(al.size() - 2);
        else maxDeg2 = maxDeg;
        minDeg = al.get(0);

        if (maxDeg2 == 0) maxDeg2 = maxDeg;

        double a = 0;
        double b = 0;

        for (Vertex v : g) {
            if (g.getDegree(v) == maxDeg) a++;
            if (g.getDegree(v) == minDeg) b++;
        }
        if (maxDeg == minDeg) b = 0;

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double M12 = zif.getSecondZagreb(1);
        double M21 = zif.getFirstZagreb(1);
        double M31 = zif.getFirstZagreb(2);
        double M41 = zif.getFirstZagreb(3);
        double M22 = zif.getSecondZagreb(2);
        double Mm31 = zif.getFirstZagreb(-4);
        double Mm11 = zif.getFirstZagreb(-2);
        double EM1 = zifL.getFirstZagreb(1);
        double EM2 = zifL.getSecondZagreb(1);

        double D12 = ((EM2 - (M41 / 2) + (3 * M31 / 2) - M21) / 2);

        double K13 = ((M31 - (3 * M21) + (4 * m)) / 6);

        double K14 = ((M41 - (6 * M31) + (11 * M21) - (12 * m)) / 24);

        double Psi1 = (Math.pow((2 * (m + 1) - (n + maxDeg + maxDeg2)
                + Math.sqrt((2 * m - maxDeg - maxDeg2)
                * (Mm11 - ((1 / maxDeg) + (1 / maxDeg2))))), 2) / (n - 2));

        double Psi2 = (Math.pow((2 * (m + 1) - (n + maxDeg + minDeg)
                + Math.sqrt((2 * m - maxDeg - minDeg)
                * (Mm11 - ((1 / maxDeg) + (1 / minDeg))))), 2) / (n - 2));

        Vector<Object> v = new Vector<>();

        v.add(zifL.getSecondZagreb(1));

//Psi1
        v.add((2 * D12) + (12 * K14) + (15 * K13) - M31 + (3 * maxDeg * maxDeg)
                + (3 * maxDeg2 * maxDeg2) + (3 * Psi1) - (4 * m));

//Psi1
        v.add((2 * D12) + (12 * K14) + (15 * K13) - M31 + (3 * maxDeg * maxDeg)
                + (3 * minDeg * minDeg) + (3 * Psi2) - (4 * m));

        //My new1
        v.add((2 * D12) + ((M31 * M31) / (2 * M21)) - (3 * M31 / 2) + M21);

        //My new1
        v.add((2 * D12) + ((M21 * M21) / (2 * m)) - (3 * M31 / 2) + M21);


        //nilanjan de 2
        v.add(Math.pow(M21 - (2 * m), 2) / (2 * m * m));

        //nilanjan de 1
        v.add(2 * (M21 - (2 * m)) * (minDeg - 1) * (minDeg - 1));

        //nilanjan de 0
        v.add(EM1 * (minDeg - 1));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Conjectures";
    }
}