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
public class EM1Lower implements GraphReportExtension{
    public String getName() {
        return "EM1 Low";
    }

    public String getDescription() {
        return "EM1 Low";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(
                Utils.createLineGraph(g)
        );

        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" M^3_1(G) ");
        // titles.add("Z1");
        //  titles.add("Z2");
        // titles.add("Z3");
        //  titles.add("Z4");
        //  titles.add("Ep3");
        // titles.add("Ep4");
        // titles.add("Psi1");
        //  titles.add("Psi2");
        titles.add("Best");
        titles.add("SD");
        titles.add("M2");
        titles.add("SD");
        titles.add("New");
        titles.add("SD");
        titles.add("nM1");
        titles.add("SD");
        titles.add("nM");
        titles.add("SD");
        titles.add("ID1");
        titles.add("SD");
        titles.add("ID");
        titles.add("SD");
        titles.add("Milo");
        titles.add("SD");
        titles.add("IED1");
        titles.add("SD");
        titles.add("IED");
        titles.add("SD");
        titles.add("H1");
        titles.add("SD");
        titles.add("H");
        titles.add("SD");
        titles.add("Fort");
        titles.add("SD");
        titles.add("illc");
        titles.add("SD");
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
        double EM1=zifL.getFirstZagreb(1);
        double H=zif.getHarmonicIndex();
        double IED=zif.getEdgeDegree(-1);


        double Ilic=(M31 - 4 * ((2*m)*(maxDeg + minDeg) - (n * maxDeg * minDeg) - ( n - a - b ) * (maxDeg - minDeg - 1)) + (2 * M12) + (4 * m));

        double Best=(M31 - 4 * ((a*maxDeg*maxDeg) + (b*minDeg*minDeg) - (((maxDeg*minDeg) + (2*maxDeg) -1) * (n - a -b))
                + ((maxDeg + minDeg + 1)*((2*m) - (a * maxDeg) - (b * minDeg))) + ((Mm11 - (a/maxDeg) - (b/minDeg)) * ((maxDeg * minDeg) - minDeg + maxDeg -1)))
                + (2 * M12) + (4 * m));

        double mynew=( (Math.pow(M21 - (2 *m), 2) / ((2*m) - n) ) + (2 * M12) - (3 * M21) + (4 * m));

        double nM1=( (Math.pow(M21-(2*m),2) / ((2 * m) - n)) + (2 * M12) - (3 * M21) + (4 * m));

        double nM=(((M21*M21) / (2 * m) ) + (1/(2*m))*( (n * M21) -(4 * m  * m)) + (2 * M12) - (4 * M21) + (4 * m));

        double ID1=( (Math.pow(M21-n,2) / ((2 * m) - Mm11)) + (2 * M12) - (4 * M21) + (6 * m));

        double ID2=(((M21*M21) / (2 * m) ) + (1/(2*m))*( (2 * m * Mm11) -(n * n)) + (2 * M12) - (4 * M21) + (4 * m));

        double Milo=(((M21*M21) / (2 * m) ) + (4 * m) + (2 * M12) - (4 * M21));

        double IED1=((Math.pow(M21 - (3 * m), 2 )) / ((m - IED)) + M21 - (2 * m));

        double IED2=(((M21*M21) / (m) ) + (((M21 - (2 * m))*IED) / (m)) + (3 * m) - (4 * M21));

        double H1=(((2*Math.pow(M21 - m, 2 )) / ((2 * m) - H)) - (3 * M21) + (4 * m) );

        double H2=(((M21*M21) / (m) ) + ((H*M21) / (2 * m)) + (3 * m) - (4 * M21));

        double Fort=(((M21*M21) / ( m) ) + (4 * m)  - (4 * M21));

        double Il=(((2 * m / n) * M21) + (4 * m) + (2 * M12) - (4 * M21));



        Vector<Object> v = new Vector<>();
        v.add(EM1);

        // Best
        v.add(Best);

        v.add(Math.pow(EM1-Best,2));

        // Ilic
        v.add(Ilic);

        v.add(Math.pow(EM1-Ilic,2));

        // mynew
        v.add(mynew);

        v.add(Math.pow(EM1-mynew,2));

        // nM1
        v.add(nM1);

        v.add(Math.pow(EM1-nM1,2));

        //nM
        v.add(nM);

        v.add(Math.pow(EM1-nM,2));

        //ID1
        v.add(ID1);

        v.add(Math.pow(EM1-ID1,2));

        //ID2
        v.add(ID2);

        v.add(Math.pow(EM1-ID2,2));

        //Milo
        v.add(Milo);

        v.add(Math.pow(EM1-Milo,2));

        //IED1
        v.add(IED1);

        v.add(Math.pow(EM1-IED1,2));

        //IED
        v.add(IED2);

        v.add(Math.pow(EM1-IED2,2));

        //H1
        v.add(H1);

        v.add(Math.pow(EM1-H1,2));

        //H
        v.add(H2);

        v.add(Math.pow(EM1-H2,2));

        //Fort
        v.add(Fort);

        v.add(Math.pow(EM1-Fort,2));

        //illc
        v.add(Il);

        v.add(Math.pow(EM1-Il,2));

        //new3
        //    v.add((maxDeg * maxDeg * maxDeg) + (maxDeg2 * maxDeg2 * maxDeg2) + (Zeta3) - (4 * M21) + (2 * M12) + (4 * m));

        //new4
        //    v.add((maxDeg * maxDeg * maxDeg) + (minDeg * minDeg * minDeg) + (Zeta4) - (4 * M21) + (2 * M12) + (4 * m));

        //new1 
        //    v.add((maxDeg * maxDeg * maxDeg) + (maxDeg2 * maxDeg2 * maxDeg2) + (Zeta1) - (4 * M21) + (2 * M12) + (4 * m));

        //new2
        //   v.add((maxDeg * maxDeg * maxDeg) + (minDeg * minDeg * minDeg) + (Zeta2) - (4 * M21) + (2 * M12) + (4 * m));


        //Eps3
        //   v.add((maxDeg * maxDeg * maxDeg) + (maxDeg2 * maxDeg2 * maxDeg2) + (Eps3) - (4 * M21) + (2 * M12) + (4 * m));

        //Eps4
        //      v.add((maxDeg * maxDeg * maxDeg) + (minDeg * minDeg * minDeg) + (Eps4) - (4 * M21) + (2 * M12) + (4 * m));

        //Psi1
        //      v.add(M31 - (4 * M21) + (2 * maxDeg * maxDeg) + (2 * maxDeg2 * maxDeg2) + (2 * Psi1) + 2 * (M12 - M21 + m) + 2 * m);

        //Psi2
        //    v.add(M31 - (4 * M21) + (2 * maxDeg * maxDeg) + (2 * minDeg * minDeg) + (2 * Psi2) + 2 * (M12 - M21 + m) + 2 * m);






        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Conjectures";
    }
}