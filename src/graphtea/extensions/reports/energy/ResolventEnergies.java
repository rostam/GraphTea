// GraphTea Project:bvb   http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.basicreports.NumOfConnectedComponents;
import graphtea.extensions.reports.topological.ZagrebIndexFunctions;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.library.util.Complex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.library.algorithms.util.BipartiteChecker;
import graphtea.extensions.reports.spectralreports.DistanceEnergy;
import graphtea.extensions.reports.spectralreports.DistanceLaplacianEnergy;
import graphtea.extensions.reports.spectralreports.DistanceSignlessLaplacianEnergy;
import graphtea.extensions.reports.spectralreports.LaplacianEnergy;
import graphtea.extensions.reports.spectralreports.SignlessLaplacianEnergy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "newInvs", abbreviation = "_newInv")
public class ResolventEnergies implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "ResolventEnergies";
    }

    public String getDescription() {
        return "Resolvent-Energies";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();

    titles.add("m ");
    titles.add("n ");
	titles.add("RE ");
	titles.add("RLE ");
	titles.add("RSLE ");
	titles.add("NLRE ");
  //  titles.add("E ");
  // titles.add("check ");
  //  titles.add("LE ");
  //  titles.add("SLE ");
 //   titles.add("LE-Bar ");
  //  titles.add("SLE-Bar ");
 //   titles.add("DE");
 // 	titles.add("DLE");
 //   titles.add("DLSE");
  // 	titles.add("DLE-Bar");
 //   titles.add("DLSE-Bar");
 //   titles.add("Bipartite");
 //   titles.add(" Components ");
  //  titles.add(" Resolvent-Energy ");
  //  titles.add(" Diameter ");   
  //  titles.add(" Matching "); 
  //  titles.add("R.H.S");
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
        double M22 = zif.getSecondZagreb(2);
        double Mm11 = zif.getFirstZagreb(-2);
        int comp = (int) new NumOfConnectedComponents().calculate(g);
        
        Matrix de = AlgorithmUtils.getDistanceAdjacencyMatrix(g);
		Matrix dle = AlgorithmUtils.getDistanceLaplacianMatrix(g);
		Matrix dlse = AlgorithmUtils.getDistanceSignlessLaplacianMatrix(g);
		
		double DE   = new DistanceEnergy().calculate(g);
		double DLE  = new DistanceLaplacianEnergy().calculate(g);
        double DSLE = new DistanceSignlessLaplacianEnergy().calculate(g);
        double DLEC  = new DistanceLaplacianEnergy().calculate(AlgorithmUtils.createComplementGraph(g));
        double DSLEC = new DistanceSignlessLaplacianEnergy().calculate(AlgorithmUtils.createComplementGraph(g));
        Boolean t = BipartiteChecker.isBipartite(g);
        LaplacianEnergy le = new LaplacianEnergy();
		SignlessLaplacianEnergy sle = new SignlessLaplacianEnergy();
		ResolventEnergy re = new ResolventEnergy();
		ResolventLaplacianEnergy rle = new ResolventLaplacianEnergy();
		ResolventSignlessLaplacianEnergy rsle = new ResolventSignlessLaplacianEnergy();
		NormalizedLaplacianResolventEnergy nlre = new NormalizedLaplacianResolventEnergy ();
		
        int diameter = (int) new Diameter().calculate(g);
        
		 double LE = Double.parseDouble(le.calculate(g));
		 double SLE = Double.parseDouble(sle.calculate(g));
		 double RE = Double.parseDouble(re.calculate(g));
		 double RLE = Double.parseDouble(rle.calculate(g));
		 double RSLE = Double.parseDouble(rsle.calculate(g));
         double NLRE = Double.parseDouble(nlre.calculate(g));
		 // Complements
		 double LEC = Double.parseDouble(le.calculate(AlgorithmUtils.createComplementGraph(g)));
		 double SLEC = Double.parseDouble(sle.calculate(AlgorithmUtils.createComplementGraph(g)));
        
        
        Vector<Object> v = new Vector<>();

         v.add(m);
         v.add(n);
		 v.add(RE);
		 v.add(RLE);
		 v.add(RSLE);
		 v.add(NLRE);
		// if ((LE+LEC)==(SLE+SLEC)) v.add(1);
		// else return null;
        //  v.add(Energy(g));  
        //  v.add(LE);
        //  v.add(SLE);
        //  v.add(LE+LEC);
        //  v.add(SLE+SLEC);
        // v.add(LaplacianEnergy(AlgorithmUtils.createComplementGraph(g)) + LaplacianEnergy(g));
       //  v.add(SignlessLaplacianEnergy(AlgorithmUtils.createComplementGraph(g)) + SignlessLaplacianEnergy(g));
        // v.add(DE);
       //  v.add(DLE);
       //  v.add(DSLE);
       //  v.add(DLE+DLEC);
      //   v.add(DSLE+DSLEC);
       //  v.add(t);  
    //     v.add(comp);
     //   v.add(ResolventEnergy(g));
     //   v.add(diameter);
        
        //v.add(Double.parseDouble(tmp));
      //  v.add(Double.parseDouble(tmp));
        //1
       // v.add(Math.sqrt(2 * m * n));
        //2
       // v.add(prv[0]
      //          + Math.sqrt((n - 1) * (2 * m - Math.pow(prv[0], 2))));
        //3
      //  v.add(n * Math.sqrt(M21 / (2 * m)));
        //4
      //  double up = (n - 1) * Math.sqrt((M21 - maxDeg * maxDeg) * (2 * m - prv[0] * prv[0]));
     //   double down = 2 * m - maxDeg;
     //   v.add(prv[0] + up / down);
        //5
     //   double tmp = Math.sqrt((n - 1) * (2 * m - Math.pow(prv[0], 2))
    //            + (Math.pow(n - 2, 2) / (n - 1)) * Math.pow(prv[1] - prv[prv.length - 1], 2));

   //    v.add(prv[0] + tmp);
        //6
    //    tmp = Math.sqrt((n - 1) * (2 * m - Math.pow(prv[0], 2))
   //             + (((n - 2) * 2) / (n - 1)) * Math.pow(prv[1] - prv[prv.length - 1], 2));
//
   //     v.add(prv[0] + tmp);
        
        
   //     v.add(Math.sqrt((expp*n)- (Math.pow(n*((Math.exp(prv[0]))-(Math.exp(prv[prv.length-1]))),2)/4)));


        //Laplacian Energy

     //   v.add(sum);
     //   v.add((2*m/n) + ((2/(n-1))*(Math.sqrt(((n-1)*((2*m) + M21)) - (4*m*m) ) )) );

     //   v.add(Math.sqrt((2/(n-1))*(((n-1)*((2*m) + M21)) - (4*m*m) ) )  );

       // v.add((2*m/n) + ((2/(n-1))*(Math.sqrt( (2*m*((n*(n-1) - (2*m)) )) / n ) )) );
        
       // v.add(new MaxMatchingExtension().numOfMatching(g));
        //v.add((1.21)*n-3.23);
        ret.add(v);

        return ret;
    }

    @Override
    public String getCategory() {
        // TODO Auto-generated method stub
        return "Verification- Energy";
    }
    
    public Object  SignlessLaplacianEnergy(GraphModel g) {
        double power = 1;
        try {
            double m = g.getEdgesCount();
            double n = g.getVerticesCount();
            Matrix B = g.getWeightedAdjacencyMatrix();
            Matrix A = AlgorithmUtils.getSignlessLaplacian(B);
            EigenvalueDecomposition ed = A.eig();
            double[] rv = ed.getRealEigenvalues();
            double[] iv = ed.getImagEigenvalues();
            double maxrv=0;
            double minrv=1000000;
            for(double value : rv) {
                double tval = Math.abs(value);
                if(maxrv < tval) maxrv=tval;
                if(minrv > tval) minrv=tval;
            }
            
 
            double sum = 0;
            double sum_i = 0;
            //  sum += Math.pow(Math.abs(rv[i]),power);
            for (double value : rv) sum += Math.pow(Math.abs(value - ((2 * m) / n)), power);
            for (double v : iv) sum_i += Math.abs(v);

            if (sum_i != 0) {
                //here is completely false
                System.out.println("imaginary part is available. So this function does not work.");
                sum_i=0;
                Complex num = new Complex(0,0);
//                for(int i=0;i < iv.length;i++) {
//                    Complex tmp = new Complex(rv[i], iv[i]);
//                    System.out.println(tmp);
//                    tmp.pow(new Complex(power,0));
//                    System.out.println(power);
//                    System.out.println(tmp);
//                    num.plus(tmp);
//                }
                return "" + AlgorithmUtils.round(num.re(), 12) + " + "
                        + AlgorithmUtils.round(num.im(), 12) + "i";
            } else {
                return "" + AlgorithmUtils.round(sum, 12);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public Object Energy(GraphModel g) {
        double power = 1;
        try {
            double m = g.getEdgesCount();
            double n = g.getVerticesCount();
            Matrix A = g.getWeightedAdjacencyMatrix();
            EigenvalueDecomposition ed = A.eig();
            double[] rv = ed.getRealEigenvalues();
            double[] iv = ed.getImagEigenvalues();
            double maxrv=0;
            double minrv=1000000;
            for(double value : rv) {
                double tval = Math.abs(value);
                if(maxrv < tval) maxrv=tval;
                if(minrv > tval) minrv=tval;
            }
            double sum = 0;
            double sum_i = 0;
            for (double value : rv) sum += Math.abs(value);
              //  sum += Math.pow(Math.abs(rv[i]),power);
            for (double v : iv) sum_i += Math.abs(v);

            if (sum_i != 0) {
                //here is completely false
                System.out.println("imaginary part is available. So this function does not work.");
                sum_i=0;
                Complex num = new Complex(0,0);
//                for(int i=0;i < iv.length;i++) {
//                    Complex tmp = new Complex(rv[i], iv[i]);
//                    System.out.println(tmp);
//                    tmp.pow(new Complex(power,0));
//                    System.out.println(power);
//                    System.out.println(tmp);
//                    num.plus(tmp);
//                }
                return "" + AlgorithmUtils.round(num.re(), 22) + " + "
                        + AlgorithmUtils.round(num.im(), 22) + "i";
            } else {
                return "" + AlgorithmUtils.round(sum, 12);
            }
        } catch (Exception ignored) {
        }
        return null;
    }
    
    
    public Object ResolventEnergy(GraphModel g) {
    	 double power = 1;
         try {
             double m = g.getEdgesCount();
             double n = g.getVerticesCount();
             Matrix A = g.getWeightedAdjacencyMatrix();
             EigenvalueDecomposition ed = A.eig();
             double[] rv = ed.getRealEigenvalues();
             double[] iv = ed.getImagEigenvalues();
             double maxrv=0;
             double minrv=1000000;
             for(double value : rv) {
                 double tval = Math.abs(value);
                 if(maxrv < tval) maxrv=tval;
                 if(minrv > tval) minrv=tval;
             }
             double sum = 0;
             double sum_i = 0;
             for (double value : rv) sum += 1.0 / (n - value);
               //  sum += Math.pow(Math.abs(rv[i]),power);
             for (double v : iv) sum_i += v;

             if (sum_i != 0) {
                 //here is completely false
                 System.out.println("imaginary part is available. So this function does not work.");
                 sum_i=0;
                 Complex num = new Complex(0,0);
//                 for(int i=0;i < iv.length;i++) {
//                     Complex tmp = new Complex(rv[i], iv[i]);
//                     System.out.println(tmp);
//                     tmp.pow(new Complex(power,0));
//                     System.out.println(power);
//                     System.out.println(tmp);
//                     num.plus(tmp);
//                 }
                 return "" + AlgorithmUtils.round(num.re(), 12) + " + "
                         + AlgorithmUtils.round(num.im(), 12) + "i";
             } else {
                 return "" + AlgorithmUtils.round(sum, 12);
             }
         } catch (Exception ignored) {
         }
         return null;
    }
    
    


    
    
    
    
    
    
    
    public Object LaplacianEnergy(GraphModel g) {
        double power = 1;
        try {
            double m = g.getEdgesCount();
            double n = g.getVerticesCount();
            Matrix B = g.getWeightedAdjacencyMatrix();
            Matrix A = AlgorithmUtils.getLaplacian(B);
            EigenvalueDecomposition ed = A.eig();
            double[] rv = ed.getRealEigenvalues();
            double[] iv = ed.getImagEigenvalues();
            double maxrv=0;
            double minrv=1000000;
            for(double value : rv) {
                double tval = Math.abs(value);
                if(maxrv < tval) maxrv=tval;
                if(minrv > tval) minrv=tval;
            }
            double sum = 0;
            double sum_i = 0;
            for (double value : rv) sum += Math.pow(Math.abs(value - ((2 * m) / n)), power);
              //  sum += Math.pow(Math.abs(rv[i]),power);
            for (double v : iv) sum_i += Math.abs(v);

            if (sum_i != 0) {
                //here is completely false
                System.out.println("imaginary part is available. So this function does not work.");
                sum_i=0;
                Complex num = new Complex(0,0);
//                for(int i=0;i < iv.length;i++) {
//                    Complex tmp = new Complex(rv[i], iv[i]);
//                    System.out.println(tmp);
//                    tmp.pow(new Complex(power,0));
//                    System.out.println(power);
//                    System.out.println(tmp);
//                    num.plus(tmp);
//                }
                return "" + AlgorithmUtils.round(num.re(), 12) + " + "
                        + AlgorithmUtils.round(num.im(), 12) + "i";
            } else {
                return "" + AlgorithmUtils.round(sum, 12);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

}
