// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Ali Rostami 

 */

@CommandAttitude(name = "m3finalconj", abbreviation = "_m3conj")
public class EM1UpperBound implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "EM1 Upper";
    }

    public String getDescription() {
        return "EM1 Upper";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(
                AlgorithmUtils.createLineGraph(g)
        );

        RenderTable ret = new RenderTable();

        Vector<String> titles = new Vector<>();
       //  titles.add(" M^3_1(G) ");
      //  titles.add("Psi1 ");
     //   titles.add("Psi2");
    //    titles.add("Zhou");
   //     titles.add("N4");
  //      titles.add("N2");
 //       titles.add("N1");
//        titles.add("Theorem 1");
       //   titles.add("3R.H.S");
        //  titles.add("2R.H.S");
        //  titles.add("1L.H.S");
          titles.add("EF");
          titles.add("R.H.S");
        //  titles.add("2R.H.S");
        //  titles.add("3R.H.S");
        
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
     //   int p = NumOfVerticesWithDegK.numOfVerticesWithDegK(g, 1);

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

        double M12=zif.getSecondZagreb(1);
        double M21=zif.getFirstZagreb(1);
        double M31=zif.getFirstZagreb(2);
        double M41=zif.getFirstZagreb(3);
        double M22=zif.getSecondZagreb(2);
        double Mm31=zif.getFirstZagreb(-4);
        double Mm11=zif.getFirstZagreb(-2);
        double EM1=zifL.getFirstZagreb(1);
        double EM31=zifL.getFirstZagreb(2);
        double sum=zif.getGeneralSumConnectivityIndex(-1);

        double Psi1=(Math.pow((2*(m+1) - (n+maxDeg+maxDeg2)
                + Math.sqrt((2*m-maxDeg-maxDeg2)
                *(Mm11-((1/maxDeg)+(1/maxDeg2))))),2)/(n-2));

        double Psi2=(Math.pow((2*(m+1) - (n+maxDeg+minDeg)
                + Math.sqrt((2*m-maxDeg-minDeg)
                *(Mm11-((1/maxDeg)+(1/minDeg))))),2)/(n-2));

        Vector<Object> v = new Vector<>();
        
      // Milo Errors 2018
       // v.add(m*minEdge);
      //  v.add(M21*minEdge);
      //  v.add(4*M12*minEdge);
       // v.add((M31+(2*M12))*minEdge);
     //   v.add(m*minEdge/(maxEdge*maxEdge));
        v.add(EM31);
        v.add( (((M31+(2*M12)-(3*M21))*(M31+(2*M12)-(3*M21)) ) +(M21*((3*M21-(8*m)))) + (maxEdge*maxEdge2*(maxEdge-maxEdge2)*(maxEdge-maxEdge2)) )/M21 );
      //  v.add(m*maxEdge/(minEdge*minEdge));
      //  v.add((M31+(2*M12))*maxEdge);
     //   v.add(2*M31*maxEdge);
      //  v.add(M21*minEdge);
       // v.add(m*maxEdge);

       // v.add(zifL.getFirstZagreb(1));

        //new1+-----------
       // v.add(M31-(4*maxDeg*maxDeg)-(4*maxDeg2*maxDeg2)-(4*Psi1)+(2*M12)+(4*m));


        //new2
      //  v.add(M31-(4*maxDeg*maxDeg)-(4*minDeg*minDeg)-(4*Psi1)+(2*M12)+(4*m));

        //new3
     //   v.add((M21*(n-4))+(4*M12)-(4*m*m)+(4*m));

        //N4
   //     v.add((M21-(2*m))*(m+(2*minDeg)-3)-(2*m*(m-1)*(minDeg-1)));

        //N3
        //v.add(((maxDeg+minDeg-2)*(maxDeg+minDeg-2)*(M21-2*m)*(M21-2*m))/(4*m*(maxDeg-1)*(minDeg-1)));

        //N2
    //    v.add((2*(maxDeg+minDeg-2)*M21)-(4*m*(maxDeg*minDeg-1)));

        //N1
     //   v.add(4*m*(maxDeg-1)*(maxDeg-1));
        
        // Milo Error.
        // v.add( ((M21 - (2*m))*(M21 - (2*m))/m) +( (maxEdge-minEdge)*(maxEdge-minEdge)/2) );
        //Theorem 1
    //    int k = KConnected.kconn(g);
    //    v.add(k*Math.pow(k+n-3,2)
       //         + 4*(n-2)*(n-2)* Utils.choose(k,2).intValue()
       //         + 4*(n-3)*(n-3)*Utils.choose((int) (n-k-1),2).intValue()
        //        + k*(n-k-1)*Math.pow(2*n-5,2));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }
}