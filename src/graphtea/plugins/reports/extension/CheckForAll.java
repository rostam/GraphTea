package graphtea.plugins.reports.extension;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.Vertex;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by rostam on 21.07.15.
 */
public class CheckForAll {

    public static RendTable forall(GraphReportExtension mr) {
        RendTable ret = new RendTable();
        HashMap<Integer,Integer> vSizes = new HashMap<>();
        vSizes.put(2,1);
        vSizes.put(3,2);
        vSizes.put(4,6);
        vSizes.put(5,21);
        vSizes.put(6,112);
        vSizes.put(7,853);
        vSizes.put(8,11117);
        vSizes.put(9, 261080);

        int size=0;

        if(GraphReportExtensionAction.upto) {
            for(int i=2;i<=GraphReportExtensionAction.Size;i++) {
                size += vSizes.get(i);
            }
        } else {
            size =vSizes.get(GraphReportExtensionAction.Size);
        }

        int[] res = null;
        JFrame jd = new JFrame();

        JProgressBar pb = new JProgressBar(0, size-2);
        pb.setValue(0);
        pb.setStringPainted(true);
        jd.add(pb);

        if(GraphReportExtensionAction.upto)
            jd.setTitle("All Connected Graphs upto " + GraphReportExtensionAction.Size + " Vertices");
        else jd.setTitle("All Connected Graphs with " + GraphReportExtensionAction.Size + " Vertices");
        jd.setSize(600, 100);
        jd.setLocation(200, 200);
        jd.setVisible(true);
        jd.setAlwaysOnTop(true);
        int start = 0;
        if (GraphReportExtensionAction.upto) start = 2;
        else start = GraphReportExtensionAction.Size;
        int gcount = 0;
        int filterCount = 0;

        for(int cnt=start;cnt<=GraphReportExtensionAction.Size;cnt++) {
            try {
                Scanner sc = new Scanner(new File("g"+cnt+"c.txt"));
                while (sc.hasNext()) {
                    pb.setValue(gcount);
                    pb.validate();
                    jd.validate();
                    GraphModel g = new GraphModel();
                    sc.nextLine();
                    String order = sc.nextLine();
                    order = order.substring(order.lastIndexOf("r") + 1,
                            order.lastIndexOf("."));
                    order = order.trim();
                    int ord = Integer.parseInt(order);
                    for (int i = 0; i < ord; i++) g.addVertex(new Vertex());
                    for (int i = 0; i < ord; i++) {
                        String tmp = sc.nextLine();
                        tmp = tmp.substring(tmp.indexOf(":") + 1);
                        Scanner sc2 = new Scanner(tmp.trim());
                        while (sc2.hasNext()) {
                            String num = sc2.next();
                            if (num.contains(";")) num = num.substring(0, num.indexOf(";"));
                            int id = Integer.parseInt(num);
                            g.addEdge(new Edge(g.getVertex(i), g.getVertex(id)));
                        }
                    }
                    gcount++;


                    if(GraphReportExtensionAction.Integral) {
                        if(!isIntegral(g)) continue;
                    }

                    if(GraphReportExtensionAction.LaplacianIntegral) {
                        if(!isLaplacianIntegral(g)) continue;
                    }

                    if(GraphReportExtensionAction.SignlessLaplacianIntegral) {
                        if(!isSignlessLaplacianIntegral(g)) continue;
                    }

                    filterCount++;

                    ret = (RendTable) mr.calculate(g);
                    if (ret.get(0).size() <= 2) return null;
                    if (res == null) {
                        res = new int[ret.get(0).size()];
                    }
                    for (int i = 1; i < ret.get(0).size(); i++) {
                        if ((Double) ret.get(1).get(0) < (Double) ret.get(1).get(i)) {
                            res[i]++;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        jd.setVisible(false);
        RendTable retForm = new RendTable();
        retForm.add(new Vector<Object>());
        retForm.add(new Vector<Object>());
        retForm.get(0).addAll(ret.get(0));
        for(int iii=0;iii<ret.get(0).size();iii++) {
            retForm.get(1).add(res[iii]);
        }
        retForm.get(0).add("Num of Filtered Graphs");
        retForm.get(1).add(filterCount);
        return retForm;
    }

    public static boolean isIntegral(GraphModel g) {
        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double rrv[] = ed.getRealEigenvalues();
        double rv[] = round(rrv, 3);
        for (int i = 0; i < rv.length; i++) {
            if (Math.floor(rv[i]) != rv[i]) return false;
        }

        return true;
    }

    public static boolean isLaplacianIntegral(GraphModel g) {
        Matrix B = g.getWeightedAdjacencyMatrix();
        Matrix A = getLaplacian(B);
        EigenvalueDecomposition ed = A.eig();
        double rrv[] = ed.getRealEigenvalues();
        double rv[] = round(rrv,3);
        for(int i=0; i< rv.length;i++) {
            if(Math.floor(rv[i])!= rv[i]) return false;
        }

        return true;
    }

    public static boolean isSignlessLaplacianIntegral(GraphModel g) {
        Matrix B = g.getWeightedAdjacencyMatrix();
        Matrix A = getSignlessLaplacian(B);
        EigenvalueDecomposition ed = A.eig();
        double rrv[] = ed.getRealEigenvalues();
        double rv[] = round(rrv,3);
        for(int i=0; i< rv.length;i++) {
            if(Math.floor(rv[i])!= rv[i]) return false;
        }
        return true;
    }

    /**
     * Undirected Laplacian.
     * @param A the Adjacency matrix of the graph
     * @return Laplacian of the graph
     */
    private static Matrix getLaplacian(Matrix A)
    {
        //double[][] res=new double[g.numOfVertices()][g.numOfVertices()];


        int n=A.getArray().length;
        double[][] ATemp = A.getArray();

        Matrix D = new Matrix(n,n);
        double[][] DTemp = D.getArray();
        int sum;
        for(int i=0; i<n ; i++ ) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                sum += ATemp[j][i];
            }
            DTemp[i][i] = sum;
        }

        return D.minus(A);
    }

    private static Matrix getSignlessLaplacian(Matrix A)
    {
        //double[][] res=new double[g.numOfVertices()][g.numOfVertices()];


        int n=A.getArray().length;
        double[][] ATemp = A.getArray();

        Matrix D = new Matrix(n,n);
        double[][] DTemp = D.getArray();
        int sum;
        for(int i=0; i<n ; i++ ) {
            sum = 0;
            for (int j = 0; j < n; j++) {
                sum += ATemp[j][i];
            }
            DTemp[i][i] = sum;
        }

        return D.plus(A);
    }

    public static double round(double value, int decimalPlace) {
        double power_of_ten = 1;
        while (decimalPlace-- > 0)
            power_of_ten *= 10.0;
        return Math.round(value * power_of_ten)
                / power_of_ten;
    }

    private static double[] round (double[] array, int prec)
    {
        double[] res=array;
        for(int i=0;i<array.length;i++)
            res[i]=round(res[i],prec);
        return res;

    }

}
