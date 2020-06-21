// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.topological.ZagrebIndexFunctions;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "newInvs", abbreviation = "_newInv")
public class Cograph implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Cograph";
    }

    public String getDescription() {
        return "Cograph";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(AlgorithmUtils.createLineGraph(g));
        ZagrebIndexFunctions zifC = new ZagrebIndexFunctions(AlgorithmUtils.createComplementGraph(g));
        ZagrebIndexFunctions zifCL = new ZagrebIndexFunctions(AlgorithmUtils.createComplementGraph(AlgorithmUtils.createLineGraph(g)));
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();

        titles.add(" m ");
        titles.add(" n ");
        titles.add(" LE values ");
   //     titles.add(" E(G) ");
      //  titles.add(" LE ");
     //   titles.add(" CE ");
     //   titles.add(" CLE ");
        ret.setTitles(titles);

        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double sum=0;
        double detA = Math.abs(A.det());

        //positiv RV
        Double[] prv = new Double[rv.length];
        for(int i=0;i<rv.length;i++) {
            prv[i] = Math.abs(rv[i]);
            prv[i] = (double)Math.round(prv[i] * 100000d) / 100000d;
            sum += prv[i];
        }

        Arrays.sort(prv, Collections.reverseOrder());

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
        double LE = zifL.getEnegry();
        double CE = zifC.getEnegry();
       // double CoLE = zifC.getLEigenValues();
        double CLE = zifCL.getEnegry();
        Vector<Object> v = new Vector<>();

        v.add(m);
        v.add(n);
    //    v.add(sum);
     //   v.add(LE);
    //    v.add(CE);
   //     v.add(CLE);
        //1
 
        v.add(AlgorithmUtils.getEigenValues(g));
 
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Graph Energy";
    }
}