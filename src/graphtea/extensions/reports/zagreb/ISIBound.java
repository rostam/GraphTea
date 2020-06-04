package graphtea.extensions.reports.zagreb;


import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.ChromaticNumber;
import graphtea.extensions.reports.Utils;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.basicreports.GirthSize;
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
import java.util.Vector;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "ISIBound", abbreviation = "_ISIBound")
public class ISIBound implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "ISIBound";
    }

    public String getDescription() {
        return " ISIBound ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" m ");
        titles.add(" n ");
       //   titles.add(" VR ");
   //      titles.add(" R ");
   //     titles.add("H");
      //    titles.add("(n/2)- R ");
     //     titles.add(" H ");
    //      titles.add(" f(G) ");
    //            titles.add(" VR ");
      //          titles.add("(n/2)- VR ");
     //           titles.add(" GA "); 
    //    titles.add(" Multiplicative "); 
    //    titles.add(" SDD-co "); 
    //    titles.add(" RHS "); 
    //    titles.add(" RHS-2  ");  
     //   titles.add(" RHS-3  "); 
       titles.add(" SDD-co "); 
        titles.add(" LHS1 "); 
        titles.add(" alb "); 
   //     titles.add(" GA+GA-co "); 
   //     titles.add(" RHS "); 
      //   titles.add(" L.H.S> ");  

      //   titles.add(" SDD ");  
    //     titles.add(" f(G) "); 
     //   titles.add(" ISI ");  
   //     titles.add(" RHS ");  
     //   titles.add(" Rao Sir ");  
   //      titles.add(" Upper "); 
   //      titles.add(" p "); 

       // titles.add(" Lemma "); 
   //     titles.add(" 1 "); 
    //    titles.add(" 2 "); 
     //  titles.add("(n/2)- chi ");
    //   titles.add(" W_p ");  
       
   //     titles.add("cr");
       // titles.add("f(G)");
      //  titles.add("Hyper");
        //titles.add("T-1");
      //  titles.add("T-2");
       //   titles.add("Zenergy");
      //  titles.add("H");
      //  titles.add("check Shi");
      //   titles.add("(n/2)-R");
     //    titles.add("(n/2)-SCI");
   //        titles.add("ISI..");
  //         titles.add("Our");
    //       titles.add("New");
   //        titles.add("our Old");
     //   titles.add("HC");
      //titles.add("m-GA");
      //titles.add("R");
     // titles.add("AZI");
   //    titles.add("shi");
  //     titles.add("our");
   //     titles.add("H check.");
        // titles.add(" milo1 ");
      //  titles.add("R1 check");
    //     titles.add(" GA ");
      //    titles.add(" H ");
    //     titles.add(" chi ");
       //  titles.add(" check1 ");
      //   titles.add(" clique.check ");
     //      titles.add(" R1 ");
    //     titles.add(" chi-1 ");
   //      titles.add(" chi-0.5 ");
  //       titles.add(" chi-0.1 ");
      //   titles.add(" ISI ");
     //      titles.add("Chromatic number");
         //   titles.add(" L.H.S ");
     //       titles.add(" R.H.S even ");
        //    titles.add(" R.H.S odd "); 

   //  titles.add("ID");
        //titles.add("Wiener");
        //titles.add("Avg");
   //     titles.add("Diameter");
     //    titles.add("Clique Number");
    //    titles.add("Girth");
   //     titles.add(" V. Degrees ");

        ret.setTitles(titles);

        double maxDeg = 0;
        double maxDeg2 = 0;
        double minDeg = Integer.MAX_VALUE;
        double minDeg2 = Utils.getMinNonPendentDegree(g);

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
        
        ArrayList<Integer> all = new ArrayList<Integer>();
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
        double MM21=zif.getMultiplicativeFirstZagreb(1);
        double H=zif.getHarmonicIndex();
        double M31=zif.getFirstZagreb(2);
        double M41=zif.getFirstZagreb(3);
        double M22=zif.getSecondZagreb(2);
        double Mm31=zif.getFirstZagreb(-4);
        double Mm11=zif.getFirstZagreb(-2);
        double irr=zif.getThirdZagreb();
 

        double R=zif.getSecondZagreb(-0.5);
        double R1=zif.getSecondZagreb(-1);
        double GA=zif.getGAindex();
        double ABC=zif.getABCindex();
        double Hcoindex=zif.getHarmonicCoindex();
        double Albcoindex=zif.getAlbCoindex();
        double SDDcoindex=zif.getSDDCoindex();
        double GAcoindex=zif.getGACoindex();
        double inversesumcoindex= zif.getInverseSumIndegCoindex();
        double chi11=zif.getGeneralSumConnectivityIndex(-1);
        double chi22=zif.getGeneralSumConnectivityIndex(-0.5);
        double chi33=zif.getGeneralSumConnectivityIndex(-0.1);
        double chi=zif.getGeneralSumConnectivityIndex(-0.5);
        double chi1=zif.getGeneralSumConnectivityIndex(1);
        double chi2=zif.getGeneralSumConnectivityIndex(2);
        double chi3=zif.getGeneralSumConnectivityIndex(3);
        double IED=zif.getEdgeDegree(-1);
        double SDD=zif.getSDDIndex();
        double Albertson=zif.getAlbertson();
        double ID=zif.getFirstZagreb(-2);
        double AZI=zif.getAugumentedZagrebIndex();
        double ISI=zif.getInverseSumIndegIndex();
        double chrome=ChromaticNumber.getChromaticNumber(g); 

        double clique=MaxCliqueExtension.maxCliqueSize(g);
        double ZEnergy=zif.getRandicEnergy(g);
        double VR=zif.getVariationRandicIndex();
        double check=zif.getCheck();
        //double S3=(M21-M3)/2;
        double RM2=zif.getRM2();
        
        double alpha=(1/m)*(Math.floor(m))*(1-((1/m)*(Math.floor(m/2))));
        double alpha1=(m)*(Math.floor(m/2))*(1-((1/m)*(Math.floor(m/2))));
        
        int girth = (int) new GirthSize().calculate(g);
        
        int diameter = (int) new Diameter().calculate(g);
        WienerIndex wi = new WienerIndex();
        double Avg=(n*(n-1)/2);

        Vector<Object> v = new Vector<>();
        v.add(m);
        v.add(n);
        //v.add(VR);
        // Hyper Zagreb Index.
      //  v.add(chi2);
    //     v.add(R);
   //      v.add((n/2)-R);
    //    v.add((n/2)-H);
   //     v.add(MM21);
  //       v.add(VR);
   ////      v.add((n/2)-VR);
      // v.add(R);
   //    v.add(H);
 
  //      
        // R.H.S
        v.add( SDDcoindex );
            v.add( ((4*m*m)-M21-(2*M12))*(SDDcoindex-(n*(n-1))+(2*m)) );
            v.add(2*Albcoindex*Albcoindex);
      //    v.add( 2*(((2*m)*(n-1))-M21)*(((2*m)*(n-1))-M21)*1.0 );
     //   v.add( SDDcoindex );
     //   v.add( (n*(n-1)) - (2*m) );
    //    v.add( (n-1)*(n-2) );
    //    v.add( n*(n-3) );
    //    v.add( SDD+SDDcoindex );
     //   v.add( n*(n-1)  );
     //   v.add( GAcoindex );
      //  v.add( (n-2)*(n-1)/2  );
     //   v.add( n*(n-3)/2  );
      //  v.add( GA+GAcoindex );
      //  v.add( n*(n-1)/2);
      //  v.add(2*(SDDcoindex+(n*(n-1))-(2*m))*(GAcoindex*GAcoindex));
     //   v.add( ((n*(n-1))-(2*m))*((n*(n-1))-(2*m))*((n*(n-1))-(2*m)) );
        
      //  v.add( (n-2)*(n-1)  );
        
   //    v.add(GA);
   //    v.add( (n*n*(maxDeg+1)/(n+1+maxDeg))  +  (m*maxDeg*((7*maxDeg)+5)/((3*maxDeg)+1))  );
    // v.add(n+m);
    // v.add(SDD);
    // v.add(SDD-(2*m));
     // error 
    // v.add(((27*n)+1)/8);
     //v.add( p );
  //   v.add( (2.0*m)+((2.0*p)/3) );
   
     // Theorem 3 SDD index.
    //v.add( (3.0*n)+(m/2) );
     // Theorem 3 SDD index.
    //  v.add((3.0*n)+(m/2)-0.5 );
 
     //  v.add(ISI);
    //   v.add((maxDeg*maxDeg/2)*(1+((1/minDeg)*( ((n*n*(clique-1))  /(2*clique))-maxDeg ) )  )  );
   //    v.add( (n*n*(minDeg+1)/(n+1+minDeg))  +  (m*minDeg*((7*minDeg)+5)/((3*minDeg)+1))  );
  //     v.add(chi);

    //    v.add(SDD-(2*m));
        //rao sir
   //     v.add(((n-5)*((n*n)-(2*n)+2)/(n-1)) +(2*((n*n)-(2*n)+5)/(n-1)) +4 );
     //   v.add(((p*minDeg2)/(maxDeg+1))+((m-p)*(Math.sqrt(  (2*maxDeg*Math.pow(minDeg2,6))/((Math.pow(maxDeg,6))+(2*Math.pow(minDeg2,5))+(4*Math.pow(minDeg2,2)*Math.pow(maxDeg,3))  )   ))));
      //  v.add(RM2);
      //  v.add(chi-(n/2));
      
        
      //   v.add(ISI);
       //  v.add((H*M12)/(2*m));
       //  v.add( ((H*M12)/(2*m)) + ((alpha*m*((maxEdge+2)-(minEdge+2))*((maxDeg*maxDeg)-(minDeg*minDeg))) /((maxEdge+2)*(minEdge+2))) );
      //   v.add( ((H*M12)/(2*m)) + (alpha1*(maxDeg-minDeg)*(maxDeg-minDeg)*(maxDeg+minDeg)/(2*m*maxDeg*minDeg)) );
         
        // v.add((H*M12)/(2*m));
       //   v.add(ABC);
      //    v.add(n*(Math.sqrt(((chrome*(n-1))-n)/(2*chrome))));
      //  v.add(AZI);
      //   v.add(chrome);
       //  v.add(chrome);
       // v.add(HC);
       // v.add(chi2);
       // v.add((2*(maxDeg+minDeg)*M21) -(4*m*maxDeg*minDeg));
       // v.add((2*(maxDeg+minDeg)*M21) -(4*m*maxDeg*minDeg) + M21 +(2*maxDeg*minDeg*H)-(2*m*(maxDeg+minDeg)));
       // v.add(M21 +(2*maxDeg*minDeg*H)-(2*m*(maxDeg+minDeg)));
       // v.add(GA);
      //  v.add(HC);
      // v.add(m-GA);
      //   v.add((n/2)-R);
      //  v.add(-(n/2)+chi);
        // v.add(n*Math.sqrt(maxDeg*minDeg)/(minDeg+maxDeg));
       //  v.add((n*minDeg)/(minDeg+maxDeg));
       //  v.add((2*n)/(maxDeg-minDeg+4));        
       //   v.add(H);
       //   v.add((2/(maxEdge+2)) + (2*(chi-(1/Math.sqrt((maxEdge+2))))*(chi-(1/Math.sqrt((maxEdge+2))))/(m-1)) + ((Math.sqrt((minEdge+2))-Math.sqrt((minEdge+2)))*(Math.sqrt((minEdge+2))-Math.sqrt((minEdge+2)))/(maxEdge2*minEdge))  );
      //    v.add(ZEnergy);
     //      v.add(GA);
          // v.add(((n*(maxDeg+minDeg))-(2*m)) / (2*maxDeg*minDeg));
         //v.add(( (a/maxDeg) + (b/minDeg) + ((((n-b)*maxDeg)+((n-a)*minDeg)-(2*m) ) / ((maxDeg-1)*(minDeg+1))) )/2 );
     //  v.add(AZI);
    //   v.add((n*Math.sqrt(minDeg*maxDeg))/(maxDeg+minDeg));
     //  v.add((2*maxDeg*minDeg*n)/((maxDeg+minDeg)*(maxDeg+minDeg)));
     //      v.add(chi11);
    //       v.add(chi22);
   //        v.add(chi33);
       //   v.add(ID);
      //  v.add((2*m*R)/n);
     //   v.add((Math.pow(n-3,2)/2) + (Math.sqrt(n-1)*2/n) + (((2*(n-2)*Math.sqrt((n-1)*(n-2))))/((2*n)-3)) - ((n-2)/Math.sqrt((n-1)*(n-2))) -(1/Math.sqrt((n-1))) );
        //v.add((n*(maxDeg+minDeg)-(2*m))/(2*maxDeg*minDeg));
    //    v.add((1/(2*maxDeg2))+(((n-1)*(maxDeg2+minDeg)-((2*m)-n+1))/(2*maxDeg2*minDeg)) );
   //     v.add(chi);
      //  v.add(ISI);
        //  v.add(chi);
        // Randic check incomplete equality
       //    v.add( ((chrome-2)/2) + (((n-chrome)+Math.sqrt(chrome-1))/(Math.sqrt(n-1))) );
   //     v.add( ((clique-2)/2) + (((n-clique)+Math.sqrt(clique-1))/(Math.sqrt(n-1))) );
    //        v.add(chrome);
    //       v.add(chrome*m/n);
         //  v.add(0.88*(n-1)/2);
  //       v.add(clique);
        //  v.add(n*n/8);
       //   v.add(Math.pow(((n*n)-1), 1.5)/(8*n));

      //  v.add(ID);
        //  v.add(wi.calculate(g));
        //  v.add(Avg);
   //      v.add(diameter);
  //      v.add(girth); 
       // v.add(MaxCliqueExtension.maxCliqueSize(g));


     //   v.add(al.toString());

         ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }
}





