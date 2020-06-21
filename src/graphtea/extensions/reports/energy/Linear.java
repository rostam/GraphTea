// GraphTea Project:bvb   http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.topological.ZagrebIndexFunctions;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.library.util.Complex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "newInvs", abbreviation = "_newInv")
public class Linear implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Linear";
    }

    public String getDescription() {
        return "Linear";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();

    titles.add("m ");
    titles.add("n ");
    //titles.add(" Energy ");
   // titles.add(" Laplacian Energy ");
    titles.add(" Estrada ");
    titles.add(" Denergy");
    titles.add(" Check ");
    titles.add(" Check 2");
    titles.add(" Check 3");
   // titles.add(" Gauss Estrada ");
    //titles.add(" Signless-Laplacian Energy ");
    //titles.add(" Resolvent-Energy ");
  //  titles.add(" Diameter ");   
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
        
        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double energy=0;
        double estra=0;
        double expp=0;
        double detA = Math.abs(A.det());

      //positiv RV
        Double[] prv = new Double[rv.length];
        for(int i=0;i<rv.length;i++) {
            prv[i] = Math.abs(rv[i]);
            prv[i] = (double)Math.round(prv[i] * 10000000000d) / 10000000000d;
             rv[i] = (double)Math.round(rv[i] * 10000000000d) / 10000000000d;
            energy += prv[i];
             estra +=Math.exp(rv[i]);
             expp +=Math.exp(2*rv[i]);
        }
        

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

        int diameter = (int) new Diameter().calculate(g);
        
        
        Vector<Object> v = new Vector<>();

        v.add(m);
        v.add(n);
        v.add(Estrada(g));
        v.add(DEnergy(g));
   // Rodrigz check
 //       v.add(Math.pow(n*(Math.exp(rv[0])-Math.exp(rv[rv.length-1])) ,2)/4);
    
        // polya
        v.add((2.0/(Math.exp(rv[0])+Math.exp(rv[rv.length-1])) )*Math.sqrt(n*(Math.exp(rv[0])*Math.exp(rv[rv.length-1])))  );
     // Diaz-Metcalf
        v.add( (n*Math.exp(rv[0]+rv[rv.length-1]))/(Math.exp(rv[0])+Math.exp(rv[rv.length-1])) );
        v.add( 1.0/(Math.exp(rv[0])+Math.exp(rv[rv.length-1])) );
        //  v.add(GaussEstrada(g));
       // v.add(LaplacianEnergy(g));
     //   v.add(SignlessLaplacianEnergy(g));
   //     v.add(ResolventEnergy(g));
   //     v.add(diameter);
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
        
        
   //     v.add (Math.sqrt( (expp*n)- (Math.pow(n*((Math.exp(prv[0]))-(Math.exp(prv[prv.length-1]))),2)/4) ) );


        //Laplacian Energy

     //   v.add(sum);
     //   v.add((2*m/n) + ((2/(n-1))*(Math.sqrt(((n-1)*((2*m) + M21)) - (4*m*m) ) )) );

     //   v.add(Math.sqrt((2/(n-1))*(((n-1)*((2*m) + M21)) - (4*m*m) ) )  );

       // v.add((2*m/n) + ((2/(n-1))*(Math.sqrt( (2*m*((n*(n-1) - (2*m)) )) / n ) )) );
        ret.add(v);

        return ret;
    }

    @Override
    public String getCategory() {
        // TODO Auto-generated method stub
        return "OurWorks-Graph Energy";
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
    
    public Object GaussEstrada(GraphModel g) {
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
            for (double v : rv) sum += Math.exp(-(v * v));
              //  sum += Math.pow(Math.abs(rv[i]),power);
         //   for(int i=0;i < iv.length;i++)
         //       sum_i +=  Math.exp(iv[i]);

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
    
    

    
    public Object Estrada(GraphModel g) {
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
            for (double v : rv) sum += Math.exp(v);
              //  sum += Math.pow(Math.abs(rv[i]),power);
         //   for(int i=0;i < iv.length;i++)
         //       sum_i +=  Math.exp(iv[i]);

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
    
    public Object DEnergy(GraphModel g) {
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
            for (double v : rv) sum += Math.exp(2 * v);
              //  sum += Math.pow(Math.abs(rv[i]),power);
          
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
            for (double value : rv) sum += Math.exp(value);
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
