package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.basicreports.GirthSize;
import graphtea.extensions.reports.basicreports.MaxOfIndSets;
import graphtea.extensions.reports.clique.MaxCliqueSize;
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

@CommandAttitude(name = "ZagrebEccentricity", abbreviation = "_ZagrebEccentricity")
public class ZagrebEccentricity implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Zagreb Eccentricities";
    }

    public String getDescription() {
        return " Zagreb Eccentricities ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();

        titles.add(" m ");
        titles.add(" n ");
        titles.add(" E1 ");
        titles.add( " Thm 1.6 ");
       //  titles.add(" E2 ");
      //  titles.add(" E1/n ");
      //  titles.add(" E2/m ");
       // titles.add(" E1-R.H.S ");
     //   titles.add(" E2-R.H.S ");
      //titles.add(" E_1-Bar / n ");
      //titles.add(" E_2-Bar / m-bar ");   
     // titles.add(" Eccen ");
    //  titles.add(" Diameter ");
    //    titles.add(" alpha ");
      //  titles.add("Max Clique Size");
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
        double mbar = (m-(n*(n-1)/2));
        double M12=zif.getSecondZagreb(1);
        double M21=zif.getFirstZagreb(1);
        double M22=zif.getSecondZagreb(2);
        double Mm11=zif.getFirstZagreb(-2);
        double M212=zif.getSecondMixZagrebIndex(1,2);
        double chi=zif.getGeneralSumConnectivityIndex(2);
        double Albertson=zif.getAlbertson();
        double irr=zif.getThirdZagreb();
        double R=zif.getSecondZagreb(-0.5);
        double H=zif.getHarmonicIndex();
        double SDD=zif.getSDDIndex();
        double GA=zif.getGAindex();
        double ISI=zif.getInverseSumIndegIndex();
        int girth = (int) new GirthSize().calculate(g);
        EccentricConnectiveIndex eci = new EccentricConnectiveIndex();
        int diameter = (int) new Diameter().calculate(g);
        double independenceNumber = (int)((new MaxOfIndSets()).calculate(g));

        Vector<Object> v = new Vector<>();


        v.add(m);
        v.add(n);
        
        // First zagreb Eccentricity Index
        v.add(zif.getFirstZagrebEccentricity(g));
        
        // Theorem 1.6
       // v.add((4*n)-(3*minDeg));
        
        // Second Eccentricity Index
      //  v.add(zif.getSecondZagrebEccentricity(g));
    
        // v.add(mbar);
       // v.add(nbar);
                
     //   v.add((zif.getFirstZagrebEccentricity(g))/n);
     //   v.add((zif.getSecondZagrebEccentricity(g))/m);
        
       // v.add((zif.getFirstZagrebEccentricity(AlgorithmUtils.createComplementGraph(g)))/n);
       // v.add((zif.getSecondZagrebEccentricity(AlgorithmUtils.createComplementGraph(g)))/mbar);
        
        
          //   eci.setA(0);
         //    double dd = eci.calculate(g);
        //     v.add(dd/(n*diameter));
       //      v.add(diameter);
        
        // Clique        
        v.add(MaxCliqueSize.maxCliqueSize(g));
       // Theorem 1.8 
      //  v.add(n+(3*independenceNumber));
        // Theorem 1.9 
      //  v.add((2*independenceNumber*(n-independenceNumber)) + (((n-independenceNumber)*(n-independenceNumber-1))/2.0) );
    //   v.add(independenceNumber);

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}






