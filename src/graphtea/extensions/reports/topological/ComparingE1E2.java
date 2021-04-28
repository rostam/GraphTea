package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.GirthSize;
import graphtea.extensions.reports.clique.MaxCliqueSize;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.extensions.reports.ChromaticNumber;
import graphtea.platform.lang.CommandAttitude;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.extensions.reports.topological.EccentricConnectiveIndex;
import graphtea.extensions.reports.basicreports.MaxOfIndSets;

import graphtea.extensions.reports.topological.WienerIndex;
import graphtea.extensions.reports.others.PeripheralWienerIndex;
import graphtea.extensions.reports.topological.EccentricWienerIndex;

import graphtea.extensions.reports.topological.WienerPolarityIndex;
import graphtea.extensions.reports.topological.WienerIndex;
import graphtea.extensions.reports.topological.Harary;
import graphtea.extensions.reports.topological.MWienerIndex;
import graphtea.extensions.reports.topological.DegreeDistance;
import graphtea.extensions.reports.topological.ReciprocalDegreeDistance;
import graphtea.extensions.reports.topological.ConnectiveEccentricIndex;
import graphtea.extensions.reports.topological.EccentricConnectiveIndex;
import graphtea.extensions.reports.topological.EccentricDistanceSum;
import graphtea.extensions.reports.topological.EccentricConnectiveComplexity;
import graphtea.extensions.reports.topological.ConnectiveEccentricComplexity;
import graphtea.extensions.reports.RandomMatching;
import graphtea.extensions.reports.basicreports.NumOfIndSets;
import graphtea.extensions.reports.basicreports.MaxOfIndSets;
import graphtea.extensions.reports.others.PeripheralWienerIndex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;


/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "ZagrebEccentricity", abbreviation = "_ComparingE1E2")
public class ComparingE1E2 implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Comparing E1 E2";
    }

    public String getDescription() {
        return " Comparing E1 E2 ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" m ");
        titles.add(" n ");
      // titles.add(" ECI ");
     //  titles.add(" E1 ");
    //   titles.add(" E2 ");
     //  titles.add(" EDS ");
     //  titles.add(" EDS-ECI");
         titles.add(" EDS-E1");
         titles.add(" EDS-E2");
   //    titles.add(" ECI/EDS ");
      // titles.add(" EAI ");
        //titles.add(" i(G) ");
        //titles.add(" i(G-Bar) ");
       // titles.add(" i(G)+i(G-Bar) ");
      //    titles.add(" EDS-E1 ");
     //   titles.add(" E1/EDS ");
     //     titles.add(" EDS-E2 ");
//        titles.add(" E2/EDS ");
        //titles.add(" G-comp ");
       // titles.add(" E1/n ");
      //  titles.add(" E2/m ");
     //   titles.add(" CE-comp ");
    //    titles.add(" EC-comp ");
   //     titles.add(" E1 ");
  //      titles.add(" E2 ");
 //       titles.add( " Thm 1.6 ");
//         titles.add( " Thm 1.6 ");

      //  titles.add(" E1-R.H.S ");
     //   titles.add(" E2-R.H.S ");
    //    titles.add(" E_1-Bar / n ");
   //     titles.add(" E_2-Bar / m-bar ");   
  //      titles.add(" Eccen ");
 //         titles.add(" Diameter ");
  //      titles.add(" DiamComp ");        
    //    titles.add(" alpha ");
     //   titles.add("Chromatic number");
    //      titles.add("Alpha");
     //     titles.add("MinDeg");
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
        double mbar = ((n*(n-1)/2)-m);
        double M12=zif.getSecondZagreb(1);
        double M21=zif.getFirstZagreb(1);
        double M22=zif.getSecondZagreb(2);
        double Mm11=zif.getFirstZagreb(-2);
        double chrome=ChromaticNumber.getChromaticNumber(g); 
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
             int diameter = (int) new Diameter().calculate(g);
        int diacomp  = (int) new Diameter().calculate(AlgorithmUtils.createComplementGraph(g));
        double independenceNumber = (int)((new MaxOfIndSets()).calculate(g));
        
        
        MWienerIndex mwi = new MWienerIndex();
        ConnectiveEccentricIndex cei = new ConnectiveEccentricIndex();
        EccentricConnectiveIndex eci = new EccentricConnectiveIndex();
        EccentricityComplexityIndex   ecompi = new EccentricityComplexityIndex();
        EccentricityAdjacencyIndex    eai = new EccentricityAdjacencyIndex();
        DegreeDistance   			  ddist = new DegreeDistance();
        ReciprocalDegreeDistance   	  RDD = new ReciprocalDegreeDistance();
        GutmanIndex                   gutman = new GutmanIndex();
        WienerPolarityIndex           wp = new WienerPolarityIndex();
        PeripheralWienerIndex         Pw = new PeripheralWienerIndex();
        Harary                        Harary = new Harary();
		EccentricDistanceSum          EDS = new EccentricDistanceSum();
		EccentricWienerIndex          EWI = new EccentricWienerIndex();
		ConnectiveEccentricComplexity CEcomp = new ConnectiveEccentricComplexity();
		EccentricConnectiveComplexity ECcomp = new EccentricConnectiveComplexity();

        Vector<Object> v = new Vector<>();

        v.add(m);
        v.add(n);
        
        
        // First zagreb Eccentricity Index


        
        double E1 = zif.getFirstZagrebEccentricity(g);
        double E2 = zif.getSecondZagrebEccentricity(g);
        
        double E1bar = zif.getFirstZagrebEccentricity(AlgorithmUtils.createComplementGraph(g));
        double E2bar = zif.getSecondZagrebEccentricity(AlgorithmUtils.createComplementGraph(g));
        
       // v.add(zif.getFirstZagrebEccentricity(g));
      //  v.add(zif.getSecondZagrebEccentricity(g)); 
        
       //   if((E1/n) < (E2/m)    )  v.add(2);
     //     else if((E1/n) == (E2/m)) v.add(0);
      //    else  return null;
        
        double    i = new  NumOfIndSets().calculate(g);
        double ibar = new  NumOfIndSets().calculate(AlgorithmUtils.createComplementGraph(g));
        // Mereffield-Simmons (independent set)
        //   v.add(i);
           
           //
         ///  v.add(E1);
        //   v.add(E2);
        
           // Mereffield-Simmons (independent set- Complement)
         //  v.add(ibar); 
           
        //   v.add(i+ibar); 
        
       // if((E1/n) < (E2/m) && (E1bar/n) < (E2bar/mbar) )  v.add(2);
      //  else if((E1/n) == (E2/m) && (E1bar/n) == (E2bar/mbar)) v.add(0);
      //  else  return null;
        //else if((E1/n) < (E2/m) && (E1bar/n) > (E2bar/mbar) ) return null;
      //  else if((E1/n) == (E2/m) && (E1bar/n) == (E2bar/mbar)) v.add(0);
      //  else if((E1/n) > (E2/m) && (E1bar/n) == (E2bar/mbar) ) return null;
     //   else if((E1/n) > (E2/m) && (E1bar/n) > (E2bar/mbar) ) return null;
      
        
        // E1-E2 Comparision Checking.
     // if((E1/n) == (E2/m)) v.add(0);
     //   else if((E1/n) > (E2/m)) v.add(1);
     //   else if((E1/n) < (E2/m)) v.add(2);
        
        
	     eci.setA(1);
	     double ECI = eci.calculate(g);
        
        
          double EDSS = EDS.calculate(g);
      //  double diff = (EDSS - E1);
    //  v.add(diff);
          
          double sol00 = (EDSS-ECI);
     //     v.add(sol00); 
          
          double sol010 = (ECI/EDSS);
      //    v.add(sol010); 
          
          double sol0 = (EDSS-E1);
          v.add(sol0); 
        
      //    double sol1 = (E1/EDSS);
       //   v.add(sol1);
        
          double sol2 = (EDSS-E2);
          v.add(sol2); 
          
      //    double sol3 = (E2/EDSS);
     //     v.add(sol3);
          

    //    if((E1bar/n) == (E2bar/mbar)) v.add(0);
    //    else if((E1bar/n) > (E2bar/mbar)) v.add(1);
    //    else if((E1bar/n) < (E2bar/mbar)) v.add(2);
        
       // v.add((E1)/n);
      //  v.add((E2)/m);
          
          
          //  Eccentricity Connectivity  Index

 	     
 	   //  v.add(ECI);
 	  //   v.add(E1);
 	 //    v.add(E2);    
 	     
 	    // v.add(EDSS);
 	   //  v.add(EDSS-ECI);
 	  //   v.add(ECI/EDSS);

        // Theorem 1.6
       // v.add((4*n)-(3*minDeg));
        
        // Second Eccentricity Index

    
        // v.add(mbar);
        
        // v.add(nbar);
         
        // Eccentricity Adjacency Index
         // v.add(eai.calculate(g));
        //v.add((E1-bar)/n);
       // v.add((E2-bar)/mbar);
        
        // Connective Eccentricity Complexity
       //  v.add(CEcomp.calculate(g));
        
        // Eccentric Connectivity Complexity
       //  v.add(ECcomp.calculate(g));
        
         
          //   eci.setA(0);
         //    double dd = eci.calculate(g);
        //     v.add(dd/(n*diameter));
       //   v.add(diameter);
      //  v.add(diacomp);        
       // Clique        
       // v.add(MaxCliqueSize.maxCliqueSize(g));
       // Theorem 1.8 
      //  v.add(n+(3*independenceNumber));
       // Theorem 1.9 
      //  v.add((2*independenceNumber*(n-independenceNumber)) + (((n-independenceNumber)*(n-independenceNumber-1))/2.0) );
     //     v.add(independenceNumber);
 	//      v.add(minDeg);
          
      //  v.add(chrome);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}






