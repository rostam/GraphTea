package graphtea.extensions.reports.zagreb;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.ChromaticNumber;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.basicreports.NumOfVerticesWithDegK;
import graphtea.extensions.reports.clique.MaxCliqueExtension;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "PB", abbreviation = "_PB")
public class PB implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "PB";
    }
      

    public String getDescription() {
        return " PB ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(AlgorithmUtils.createLineGraph(g));
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" m ");
        titles.add(" n ");
       // titles.add(" GA ");
      //  titles.add(" R-min ");
      //  titles.add(" HM2 ");
      //  titles.add(" EM2 ");
     //   titles.add(" Hyper ");
    //    titles.add(" milo ");
    //    titles.add(" lower ");
          titles.add(" PB ");
     //     titles.add(" upper ");
       //   titles.add(" R.H.S ");
          
     //   titles.add(" max ");
    //    titles.add(" min ");
       //  titles.add(" Hyper ");
       // titles.add("Th 7 "); 
       // titles.add("check"); 
      //  titles.add("Correct"); 
       //    titles.add(" matching ");
        //      titles.add(" GA/x ");
     //         titles.add(" Wp ");
     //         titles.add(" chrome ");
    //          titles.add(" chrome ");
        //   titles.add(" Lz ");
        //   titles.add(" Lz-Bar ");

        //   titles.add(" R.H.S ");
        //titles.add("Diameter");
        //    titles.add("Clique Number");

      //  titles.add(" V. Degrees ");

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
        int p = NumOfVerticesWithDegK.numOfVerticesWithDegK(g, 1);

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
        
        
        
        double maxDel = 0;
        double maxDel2 = 0;
        double minDel = Integer.MAX_VALUE;

        ArrayList<Integer> all1 = new ArrayList<>();
        for(Edge e : g.getEdges()) {
            int f1 = ((2*(g.getDegree(e.source) * g.getDegree(e.target) ) )/((g.getDegree(e.source) + g.getDegree(e.target) )*(g.getDegree(e.source) + g.getDegree(e.target) ))) ;
            all1.add(f1);
        }
        Collections.sort(all1);
        maxDel = all1.get(all1.size()-1);
        if(all1.size()-2>=0) maxDel2 = all1.get(all1.size()-2);
        else maxDel2 = maxDel;
        minDel = all1.get(0);
        if(maxDel2 == 0) maxDel2=maxDel;
          
       
        double M12=zif.getSecondZagreb(1);
        double M21=zif.getFirstZagreb(1);
        double H=zif.getHarmonicIndex();
        double M31=zif.getFirstZagreb(2);
        double M41=zif.getFirstZagreb(3);
        double M22=zif.getSecondZagreb(2);
        double Mm31=zif.getFirstZagreb(-4);
        double Mm11=zif.getFirstZagreb(-2);
        double M3=zif.getThirdZagreb();
        double pb=zif.getPBIndex();
       // double EM1 = zifL.getFirstZagreb(1);
     //   double EM2 = zifL.getSecondZagreb(1);
        double SDD=zif.getSDDIndex();
        double R=zif.getSecondZagreb(-0.5);
        double R1=zif.getSecondZagreb(-1);
        double GA=zif.getGAindex();
        double ABC=zif.getABCindex();
        double HC=zif.getHarmonicCoindex();
        double chi11=zif.getGeneralSumConnectivityIndex(-1);
        double chi22=zif.getGeneralSumConnectivityIndex(-0.5);
        double chi33=zif.getGeneralSumConnectivityIndex(-0.1);
        double chi=zif.getGeneralSumConnectivityIndex(-0.5);
        double chi1=zif.getGeneralSumConnectivityIndex(1);
        double chi2=zif.getGeneralSumConnectivityIndex(2);
        double chi3=zif.getGeneralSumConnectivityIndex(3);
        double IED=zif.getEdgeDegree(-1);
        double ID=zif.getFirstZagreb(-2);
        double AZI=zif.getAugumentedZagrebIndex();
        double ISI=zif.getInverseSumIndegIndex();
        double chrome=ChromaticNumber.getChromaticNumber(g);
    //    double maxmat=MaxMatchingExtension.Max(g);
        double clique=MaxCliqueExtension.maxCliqueSize(g);
        double ZEnergy=zif.getRandicEnergy(g);
        double VR=zif.getVariationRandicIndex();
        double check=zif.getCheck();
        double S3=(M21-M3)/2;
        double RM2=zif.getRM2();
        double HM2=zif.getSecondHyperZagreb(1);
        double EM1 = zifL.getFirstZagreb(1);
        double EM2 = zifL.getSecondZagreb(1);

        List<Integer>[] gg = new List[g.getVerticesCount()];
        for (int i = 0; i < g.getVerticesCount(); i++) {
            gg[i] = new ArrayList();
        }

        for(Edge e : g.getEdges()) {
            gg[e.source.getId()].add(e.target.getId());
        }
        double alpha=(1/m)*(Math.floor(m))*(1-((1/m)*(Math.floor(m/2))));
        double alpha1=(m)*(Math.floor(m/2))*(1-((1/m)*(Math.floor(m/2))));

        int diameter = (int) new Diameter().calculate(g);
        WienerIndex wi = new WienerIndex();
        double Avg=(n*(n-1)/2);

        Vector<Object> v = new Vector<>();
        v.add(m);
        v.add(n);
       // v.add(R-minDeg);
      //  v.add((n/2)-((13-Math.sqrt(6)-(3*Math.sqrt(2)))/6));
     //   v.add(GA);
     //   v.add(M12);
      // v.add(HM2);
     //   v.add(EM2);
      //  v.add(H);
      //  v.add(maxDel);
       // v.add(minDel);
     //   v.add(chi2);
      //  v.add(Math.sqrt((2.0*minDeg)/(maxDeg-1))*R);
        v.add(pb);
        //v.add(2.0*IED);        
       // v.add((n*minDeg)/(2.0*maxDeg*(maxDeg-1)));
      // milo 1
        //v.add((M21*M21/M12)-(2*m));
    //    v.add((4*m*m*m/(GA*GA))-(2*m));
        // milo
     //   v.add((M21*M21/(4*m))*(Math.sqrt(maxEdge/minEdge)+Math.sqrt(minEdge/maxEdge))*(Math.sqrt(maxEdge/minEdge)+Math.sqrt(minEdge/maxEdge)) );
    // GA new bound Try

 
      //  v.add(RM2);
     //   v.add(maxMatching);
    //    v.add(RM2);
     //    v.add(H);
     // Error in Hyper Zagreb index paper
        //   v.add((M21*(m+(2*minDeg)-1))-(2*m*minDeg*(m-1)));
     //   v.add((M21*(m+(2*minDeg)+1)) -(2*m*(m-1)) -(2*(minDeg-1)*((3*m)-1)) );
    //    v.add(4*m*m*m/(n*n));
     // Wrong Wang
      //     v.add((M21*M21*M21)/(2*m*m));
    //    v.add( (M21-(2*m))*(M21-(2*m))*(M21-(2*m))/(2*m*m) );
             //    v.add(chrome/2);
    //     v.add( ((chrome-2)/2)+ (2*(chrome-1)/(n+chrome-2)) + (2*(n-chrome)/n));
        //   v.add(GA/chrome);
        // Lanzhouv
        //    v.add(((n-1)*M21)-M31);
        //   v.add(4*n*(n-1)*(n-1)*(n-1)/27);

        //   v.add((2*(n-1)*(n-1)*m) + M31-(2*(n-1)*M21) );
        //v.add(R);


        //  v.add(ID);
        //  v.add(wi.calculate(g));
        //  v.add(Avg);
        //  v.add(diameter);

        // v.add(MaxCliqueExtension.maxCliqueSize(g));


     //  v.add(al.toString());

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }
}




