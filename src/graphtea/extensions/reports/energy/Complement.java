// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.Utils;
import graphtea.extensions.reports.zagreb.ZagrebIndexFunctions;
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
public class Complement implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Complement";
    }

    public String getDescription() {
        return "Complement";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(Utils.createLineGraph(g));
        ZagrebIndexFunctions zifC = new ZagrebIndexFunctions(Utils.createComplementGraph(g));
        ZagrebIndexFunctions zifCL = new ZagrebIndexFunctions(Utils.createComplementGraph(Utils.createLineGraph(g)));
        //ZagrebIndexFunctions zifLC = new ZagrebIndexFunctions(Utils.createLineGraph(Utils.createComplementGraph(g)));
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();

        titles.add(" m ");
        titles.add(" n ");
        titles.add(" E(G) ");
        titles.add(" CE ");
        titles.add(" LE ");
      //  titles.add(" LCE ");
        titles.add(" CLE ");
        ret.setTitles(titles);

        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double rv[] = ed.getRealEigenvalues();
        double sum=0;
        double detA = Math.abs(A.det());

        //positiv RV
        Double[] prv = new Double[rv.length];
        for(int i=0;i<rv.length;i++) {
            prv[i] = Math.abs(rv[i]);
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
        double CLE = zifCL.getEnegry();
       //double LCE = zifLC.getEnegry();
        Vector<Object> v = new Vector<>();

        v.add(m);
        v.add(n);
        v.add(sum);
        v.add(CE);
        v.add(LE);
        v.add(CLE);

      //  v.add(CLE);
        //1
 
 
 
        ret.add(v);
        return ret;
    }

    public static String getEigenValues(GraphModel g) {
        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double rv[] = ed.getRealEigenvalues();
        double iv[] = ed.getImagEigenvalues();
        String res = "";
        for (int i = 0; i < rv.length; i++) {
            if (iv[i] != 0)
                res +="" + rv[i] + " + " + iv[i] + "i";
            else
                res += "" + rv[i];
            if(i!=rv.length-1) {
                res += ",";
            }
        }
        return res;
    }

    static double round(double value, int decimalPlace) {
        double power_of_ten = 1;
        while (decimalPlace-- > 0)
            power_of_ten *= 10.0;
        return Math.round(value * power_of_ten)
                / power_of_ten;
    }



    @Override
    public String getCategory() {
        return "OurWorks-Graph Energy";
    }
}
