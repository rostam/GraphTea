// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
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
public class LaplacianEstrada implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Laplacian Estrada";
    }

    public String getDescription() {
        return "Laplacian Estrada";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" m ");
        titles.add(" n ");
        titles.add(" L.Estrada ");
       // titles.add(" 1.1 ");
      //  titles.add(" 1.2 ");
       // titles.add(" 1.3 ");
      //  titles.add(" 1.4 ");
     ///  titles.add(" 1.5 ");
    //    titles.add(" 1.6 ");
     //   titles.add(" 1.7 ");
     //   titles.add(" Laplacian Estarda ");
     //   titles.add(" Eigenvalues ");
   //     titles.add(" 2-degree sum ");
   //     titles.add("new query");
        ret.setTitles(titles);

        Matrix B = g.getWeightedAdjacencyMatrix();
        Matrix A = AlgorithmUtils.getLaplacian(B);
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double Lsum=0;
        double Lapestra=0;
        double detA = Math.abs(A.det());

        //positiv RV
        Double[] prv = new Double[rv.length];
        for(int i=0;i<rv.length;i++) {
            prv[i] = Math.abs(rv[i]);
            prv[i] = (double) Math.round(prv[i] * 100000d) / 100000d;
            rv[i] = (double) Math.round(rv[i] * 100000d) / 100000d;
            Lsum += prv[i];
            Lapestra += Math.exp(rv[i]);
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

        Vector<Object> v = new Vector<>();
        v.add(m);
        v.add(n);
      
        v.add(Lapestra);
        //1
     //   v.add(Math.sqrt(2*m));
        //2
      //  v.add((2*Math.sqrt(2*m*n)*Math.sqrt(prv[0]*prv[prv.length-1]))
      //          /(prv[0] + prv[prv.length-1]));
        //3
      //  v.add((prv[0]*prv[prv.length-1]*n + 2*m)/(prv[0] + prv[prv.length-1]));
        //4
      //  v.add(Math.sqrt(2*m*n
     //           - (Math.pow(n*(prv[0]-prv[prv.length-1]),2)/4)));
        //5
     //   double alpha=n*Math.floor(n/2)*(1-(1/n)*Math.floor(n/2));
    //    v.add(Math.sqrt(2*m*n
    //            - (Math.pow((prv[0]-prv[prv.length-1]),2)*alpha)));
        //6
     //   if(detA==0) v.add(0);
      //  else v.add(Math.sqrt(2*m + n*(n-1)*Math.pow(detA,2/n)));

        //7
    //    double up=n*Math.pow(prv[0]-prv[prv.length-1],2);
    //    double down=4*(prv[0] + prv[prv.length-1]);
    //    v.add(Math.sqrt(2*m*n) - (up/down));

        //eigenvalues
      //   v.add(getEigenValues(g));

        //2-degree sum
     //   v.add(Utils.getDegreeSum(g,1));
        


         // new query
   //     double eigenVal_k=prv[prv.length-1];
     //   int cnt = prv.length-1;

       // if(eigenVal_k==0) {
         //   while(eigenVal_k==0) {
           //     eigenVal_k=prv[--cnt];
           // }
      //  }

       // int numOfNZEigenValue = 0;
      //  for(int i=0;i<prv.length;i++) {
       //     if(prv[i] != 0) numOfNZEigenValue++;
      //  }

       // double alpha_k=numOfNZEigenValue*Math.floor(numOfNZEigenValue/2)
         //       *(1-(1/numOfNZEigenValue)*Math.floor(numOfNZEigenValue/2));
       // System.out.println(alpha_k + "  " + numOfNZEigenValue);
     //   System.out.println(prv[0] + "  " + eigenVal_k);
    //    v.add(Math.sqrt(2*m*numOfNZEigenValue
   //             - (Math.pow((prv[0]-eigenVal_k),2)*alpha_k)));

        ret.add(v);
        return ret;
    }


    @Override
    public String getCategory() {
        return "OurWorks-Graph Energy";
    }
}