package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.GirthSize;
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

@CommandAttitude(name = "ISIBound", abbreviation = "_ISIBound")
public class ISIUpper implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "ISIUpper";
    }

    public String getDescription() {
        return " ISIUpper ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();

        titles.add(" m ");
        titles.add(" n ");
        titles.add(" Alb ");
        titles.add(" irr ");
        titles.add(" M2 ");
        titles.add(" R ");
        titles.add(" H ");
        titles.add(" SDD ");
        titles.add(" GA ");
        titles.add(" I-Sum ");
        titles.add(" Identity ");
        titles.add(" girth ");
      
      //  titles.add(" Mo.Alb ");
      //  titles.add(" ISI ");
      //  titles.add("1 ");
     //   titles.add(" 2 ");
        //   titles.add(" My-mix2 ");
        //   titles.add(" Th-13 ");
        //  titles.add(" Th-13.1 ");
        //  titles.add(" Corollary 15 ");
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
        
        Vector<Object> v = new Vector<>();


        v.add(m);
        v.add(n);
       // v.add(zif.getInverseSumIndegIndex());
        v.add(Albertson);
        v.add(irr);
        v.add(M12);
        v.add(R);
        v.add(H);
        v.add(SDD); 
        v.add(GA); 
        v.add(ISI); 
        v.add(M21-(4*ISI)); 
      v.add(girth); 

        // my cauchy
        //v.add(1);
       // v.add(2 );

        // my mixed simple 
        // v.add( ( M12 * H ) / m  );

        //v.add( ( M21 ) / 4  );

        // my mixed double 
        //   v.add( (( (m*chi) + (M12*M12) - (M21*M21) )) / M212);

        //Theorem 13
        //  double val = 0.0;
        //   if(minDeg == 1) val=n+(1/n)-2;
        //    else if(minDeg >= 2) {
        //       if((n*minDeg)%2 == 0) {
        //           val = (n*Math.pow(minDeg,2))/4.0;
        //      } else {
        //        val = (n*Math.pow(minDeg,2))/4.0;
        //         val += minDeg/2.0;
        //        val += (2*minDeg)/(8.0*(2*minDeg + 1.0));
        //     }
        //    }
        //  v.add(val);


        // ISI THeorem 13 proof point...
        //  v.add(m*minDeg/2);

        //Corollary 15
        //   val = 0.0;
        //  if((n*maxDeg)%2==0) val = (n*maxDeg*maxDeg)/4.0;
        //    else {
        //       val = ((n-1)*maxDeg*maxDeg)/4.0;
        //        val += (maxDeg*(maxDeg-1)*(maxDeg-1.0))/(2.0*(2*maxDeg-1.0));
        //     }
        //    v.add(val);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}







