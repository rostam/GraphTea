// GraphTea Project:bvb   http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.energy;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.spectralreports.KirchhoffIndex;
import graphtea.extensions.reports.spectralreports.LaplacianEnergy;
import graphtea.extensions.reports.spectralreports.LaplacianEnergyLike;
import graphtea.extensions.reports.spectralreports.SignlessLaplacianEnergy;
import graphtea.extensions.reports.topological.WienerIndex;
import graphtea.extensions.reports.topological.ZagrebIndexFunctions;
import graphtea.graph.graph.Edge;
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
public class LEL_vs_KF implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "LEL_vs_KF";
    }

    public String getDescription() {
        return "LEL_vs_KF";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
       // titles.add(" LEL ");
        titles.add("m ");
        titles.add("n ");
        titles.add("KF");
        titles.add("Wiener");
        titles.add("Laplacian");
        titles.add("Signless Laplacian");
        titles.add("Diameter");
        //titles.add("check");
       // 
       // titles.add("n ");
        ret.setTitles(titles);
        

        double maxDeg = 0;
        double maxDeg2 = 0;
        double minDeg = Integer.MAX_VALUE;
        double minDeg2 = AlgorithmUtils.getMinNonPendentDegree(g);

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        maxDeg = al.get(al.size()-1);
        if(al.size()-2>=0) maxDeg2 = al.get(al.size()-2);
        else maxDeg2 = maxDeg;
        minDeg = al.get(0);
        if(maxDeg2 == 0) maxDeg2=maxDeg;

        double a=0;
        double b=0;
        double c=0;
        double d=0;


        for(Vertex v : g) {
            if(g.getDegree(v)==maxDeg) a++;
            if(g.getDegree(v)==minDeg) b++;
            if(g.getDegree(v)==maxDeg2) c++;
            if(g.getDegree(v)==minDeg2) d++;
        }
        if(maxDeg==minDeg) b=0;
        if(maxDeg==maxDeg2) c=0;

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();
        
        double maxEdge = 0;
        double maxEdge2 = 0;
        double minEdge = Integer.MAX_VALUE;
        
        ArrayList<Integer> all = new ArrayList<>();
        for(Edge e : g.getEdges()) {
                int f = g.getDegree(e.source) +
                g.getDegree(e.target) - 2;
                all.add(f);
        }
        Collections.sort(all);
        maxEdge = all.get(all.size()-1);
        if(all.size()-2>=0) maxEdge2 = all.get(all.size()-2);
        else maxEdge2 = maxEdge;
        minEdge = all.get(0);
        
        
        

        Matrix A = g.getWeightedAdjacencyMatrix();
        EigenvalueDecomposition ed = A.eig();
        double[] rv = ed.getRealEigenvalues();
        double sum = 0;

        //positiv RV
        Double[] prv = new Double[rv.length];
        for (int i = 0; i < rv.length; i++) {
            prv[i] = Math.abs(rv[i]);
            sum += prv[i];
        }

        Arrays.sort(prv, Collections.reverseOrder());



        Vector<Object> v = new Vector<>();
        LaplacianEnergyLike lel = new LaplacianEnergyLike();
        SignlessLaplacianEnergy  sl = new SignlessLaplacianEnergy();
        LaplacianEnergy le = new LaplacianEnergy();
        KirchhoffIndex kf = new KirchhoffIndex();
        WienerIndex wi = new WienerIndex();
        int diameter = new Diameter().calculate(g);
             //v.add(Double.parseDouble(lel.calculate(g).toString()));
           v.add(m);
           v.add(n);
           //Kirchhoff Index
           v.add(Double.parseDouble(kf.calculate(g)));
          // Wiener Index
           v.add(wi.calculate(g));
           //  Laplacian
            v.add(Double.parseDouble(le.calculate(g)));
            // Signless Laplacian
            v.add(Double.parseDouble(sl.calculate(g).toString()));
           v.add(diameter);
         //  v.add( (((n*n*(n-1))-(2*m))/(2*m))+(((n-1)*(Math.sqrt(maxDeg)-Math.sqrt(minDeg))*(Math.sqrt(maxDeg)-Math.sqrt(minDeg)))/(2*maxDeg*minDeg)));
           ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
       return "Verification- Energy";
    }
}
