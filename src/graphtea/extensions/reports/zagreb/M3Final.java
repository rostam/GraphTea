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
public class M3Final implements GraphReportExtension{
    public String getName() {
        return "M3 Final";
    }

    public String getDescription() {
        return "M3 Final";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(
                Utils.createLineGraph(g)
        );

        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" M^3_1(G) ");
        titles.add("1 ");
        titles.add("2");
        titles.add("3");
        titles.add("4");
        titles.add("5");
        titles.add("6");
        titles.add("7 ");
        titles.add("8");
        titles.add("9");
        titles.add("10");
        titles.add("11");
        titles.add("12");
        titles.add("13");
        titles.add("14");
        titles.add("15");
        titles.add("16");
        titles.add("BF");
        titles.add("Bf-");
        titles.add("Il");
        titles.add("Zh");
        titles.add("3.16");
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
        if(maxDeg==minDeg) b=0;

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

        v.add(zif.getFirstZagreb(2));

        //new1+-----------
        v.add(maxDeg * maxDeg * maxDeg + maxDeg2 * maxDeg2 * maxDeg2
                + 2 * m - maxDeg - maxDeg2
                + (M21 - maxDeg * maxDeg - maxDeg2 * maxDeg2 - n + 2)
                * (M21 - maxDeg * maxDeg - maxDeg2 * maxDeg2 - n + 2)
                / (2 * m - maxDeg - maxDeg2 - Mm11 + (1 / maxDeg) + (1 / maxDeg2)));


        //new2
        v.add((maxDeg * maxDeg * maxDeg) + (minDeg * minDeg * minDeg) + 2 * m - (maxDeg) - (minDeg) + Math.pow((M21 - (maxDeg * maxDeg) - (minDeg * minDeg) - (n - 2)), 2) / (2 * m - maxDeg - minDeg - Mm11 + (1 / maxDeg) + (1 / minDeg)));

        //new3
        v.add(maxDeg * maxDeg * maxDeg + maxDeg2 * maxDeg2 * maxDeg2 + M21 - maxDeg * maxDeg - maxDeg2 * maxDeg2 + (Math.pow((M21 - maxDeg * maxDeg - maxDeg2 * maxDeg2 - 2 * m + maxDeg + maxDeg2), 2) / (2 * m - maxDeg - maxDeg2 - n + 2)));


        //new4
        v.add(maxDeg * maxDeg * maxDeg + minDeg * minDeg * minDeg + M21 - maxDeg * maxDeg - minDeg * minDeg + (Math.pow((M21 - maxDeg * maxDeg - minDeg * minDeg - 2 * m + maxDeg + minDeg), 2) / (2 * m - maxDeg - minDeg - n + 2)));


//M1new1
        v.add((M31 - (3 * M21) + 4 * m) - (4 * m) + 3 * maxDeg * maxDeg +
                3 * maxDeg2 * maxDeg2
                + 3 * (Math.pow((2 * (m + 1) - (n + maxDeg + maxDeg2)
                + Math.sqrt((2 * m - maxDeg - maxDeg2)
                * (Mm11 - ((1 / maxDeg) + (1 / maxDeg2))))), 2) / (n - 2)));

//M1New2
        v.add((M31 - (3 * M21) + 4 * m) - (4 * m) + 3 * maxDeg * maxDeg +
                3 * minDeg * minDeg
                + 3 * (Math.pow((2 * (m + 1) - (n + maxDeg + minDeg)
                + Math.sqrt((2 * m - maxDeg - minDeg)
                * (Mm11 - ((1 / maxDeg) + (1 / minDeg))))), 2) / (n - 2)));

//M1New3
        v.add((M31 - (3 * M21) + 4 * m) - (4 * m) + 3 * maxDeg * maxDeg +
                3 * maxDeg2 * maxDeg2 +
                (3 * (2 * m - maxDeg - maxDeg2) * (2 * m - maxDeg - maxDeg2) / (n - 2)) +
                (3 * ((2 * m - maxDeg - maxDeg2) * (Mm11 - (1 / maxDeg) - (1 / maxDeg2))) / (n - 2)) - 3 * (n - 2));

//M1New4
        v.add((M31 - (3 * M21) + 4 * m) - (4 * m) + 3 * maxDeg * maxDeg +
                3 * minDeg * minDeg +
                (3 * (2 * m - maxDeg - minDeg) * (2 * m - maxDeg - minDeg) / (n - 2)) +
                (3 * ((2 * m - maxDeg - minDeg) * (Mm11 - (1 / maxDeg) - (1 / minDeg))) / (n - 2)) - 3 * (n - 2));

        //1max
        v.add((maxDeg * maxDeg * maxDeg) + (maxDeg2 * maxDeg2 * maxDeg2) + (Math.pow((M21 - (maxDeg * maxDeg) - (maxDeg2 * maxDeg2)) + Math.sqrt((n - 2) * (M21 - (maxDeg * maxDeg) - (maxDeg2 * maxDeg2))) - (2 * m - maxDeg - maxDeg2), 2) / (2 * m - maxDeg - maxDeg2)));

        //1min
        v.add((maxDeg * maxDeg * maxDeg) + (minDeg * minDeg * minDeg) + (Math.pow((M21 - (maxDeg * maxDeg) - (minDeg * minDeg)) + Math.sqrt((n - 2) * (M21 - (maxDeg * maxDeg) - (minDeg * minDeg))) - (2 * m - maxDeg - minDeg), 2) / (2 * m - maxDeg - minDeg)));

        //2max
        v.add((maxDeg * maxDeg * maxDeg) + (maxDeg2 * maxDeg2 * maxDeg2) + (Math.pow((M21 - (maxDeg * maxDeg) - (maxDeg2 * maxDeg2)) + Math.sqrt((2 * m - maxDeg - maxDeg2) * (Mm11 - (1 / maxDeg) - (1 / maxDeg2))) - (n - 2), 2) / (2 * m - maxDeg - maxDeg2)));

        //2min
        v.add((maxDeg * maxDeg * maxDeg) + (minDeg * minDeg * minDeg) + (Math.pow((M21 - (maxDeg * maxDeg) - (minDeg * minDeg)) + Math.sqrt((2 * m - maxDeg - minDeg) * (Mm11 - (1 / maxDeg) - (1 / minDeg))) - (n - 2), 2) / (2 * m - maxDeg - minDeg)));

        //3max
        v.add(((maxDeg * maxDeg * maxDeg) + (maxDeg2 * maxDeg2 * maxDeg2) - (2 * m - maxDeg - maxDeg2)) + ((((M21 - (maxDeg * maxDeg) - (maxDeg2 * maxDeg2)) * (M21 - (maxDeg * maxDeg) - (maxDeg2 * maxDeg2))) + ((n - 2) * (M21 - (maxDeg * maxDeg) - (maxDeg2 * maxDeg2))))) / (2 * m - maxDeg - maxDeg2));

        //3min
        v.add(((maxDeg * maxDeg * maxDeg) + (minDeg * minDeg * minDeg) - (2 * m - maxDeg - minDeg)) + ((((M21 - (maxDeg * maxDeg) - (minDeg * minDeg)) * (M21 - (maxDeg * maxDeg) - (minDeg * minDeg))) + ((n - 2) * (M21 - (maxDeg * maxDeg) - (minDeg * minDeg))))) / (2 * m - maxDeg - minDeg));

        //4max
        v.add(((maxDeg * maxDeg * maxDeg) + (maxDeg2 * maxDeg2 * maxDeg2)) + ((((M21 - (maxDeg * maxDeg) - (maxDeg2 * maxDeg2)) * (M21 - (maxDeg * maxDeg) - (maxDeg2 * maxDeg2))) + ((2 * m - maxDeg - maxDeg2) * (Mm11 - (1 / maxDeg) - (1 / maxDeg2))) - ((n - 2) * (n - 2))) / (2 * m - maxDeg - maxDeg2)));


        //4min
        v.add(((maxDeg * maxDeg * maxDeg) + (minDeg * minDeg * minDeg))
                + ((((M21 - (maxDeg * maxDeg) - (minDeg * minDeg))
                * ((M21 - (maxDeg * maxDeg) - (minDeg * minDeg))))
                + ((2 * m - maxDeg - minDeg) * (Mm11 - (1 / maxDeg)
                - (1 / minDeg))) - ((n - 2) * (n - 2))) / (2 * m - maxDeg - minDeg)));


        //BF
        v.add((M21 * M21) / (2 * m));


        //BF-
        v.add(((M21 * M21) / (m)) - (2 * M12));



        //Ilic
        v.add((2 * m * M21) / n);


        //Zhou
        v.add(16 * ((m * m * m) / (n * n)) - (2 * M12));

        //3.16
        v.add(zifL.getFirstZagreb(1)
                + 4 * zif.getFirstZagreb(1)
                - 2 * zif.getSecondZagreb(1) - 4 * m);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Conjectures";
    }
}