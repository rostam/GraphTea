package graphtea.extensions.reports.zagreb;




import graphtea.extensions.reports.ChromaticNumber;
import graphtea.extensions.reports.clique.MaxCliqueExtension;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.graph.graph.Edge;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.extensions.reports.basicreports.NumOfVerticesWithDegK;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.zagreb.WienerIndex;
import graphtea.extensions.reports.Utils;


import java.util.ArrayList;
import java.util.Vector;
import java.util.List;
import java.util.Collections;


/**
 * @author Ali Rostami
 */
import java.util.Collections;

@CommandAttitude(name = "ISIBound", abbreviation = "_ISIBound")
public class ISIBound implements GraphReportExtension{
    public String getName() {
        return "ISIBound";
    }

    public String getDescription() {
        return " ISIBound ";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" m ");
        titles.add(" n ");
        //  titles.add("Hyper");
        //titles.add("T-1");
        //  titles.add("T-2");
        titles.add("Zenergy");
        //  titles.add(" R");
        // titles.add(" milo1 ");
        //  titles.add("R1 check");
        //   titles.add(" R1 ");
        //    titles.add(" H ");
        //  titles.add(" chi ");
        //  titles.add(" check1 ");
        //   titles.add(" clique.check ");
        //       titles.add(" GA ");
        //   titles.add(" ISI ");
        //      titles.add("Chromatic number");
        //   titles.add(" L.H.S ");
        //       titles.add(" R.H.S even ");
        //    titles.add(" R.H.S odd ");

        //   titles.add("R");
        //  titles.add("ID");
        //titles.add("Wiener");
        //titles.add("Avg");
        //titles.add("Diameter");
        //    titles.add("Clique Number");

        titles.add(" V. Degrees ");

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
        double H=zif.getHarmonicIndex();
        double M31=zif.getFirstZagreb(2);
        double M41=zif.getFirstZagreb(3);
        double M22=zif.getSecondZagreb(2);
        double Mm31=zif.getFirstZagreb(-4);
        double Mm11=zif.getFirstZagreb(-2);

        double R=zif.getSecondZagreb(-0.5);
        double R1=zif.getSecondZagreb(-1);
        double GA=zif.getGAindex();
        double chi=zif.getGeneralSumConnectivityIndex(-0.5);
        double chi2=zif.getGeneralSumConnectivityIndex(2);
        double IED=zif.getEdgeDegree(-1);
        double ID=zif.getFirstZagreb(-2);
        double ISI=zif.getInverseSumIndegIndex();
        double chrome=ChromaticNumber.getChromaticNumber(g);
        double clique=MaxCliqueExtension.maxCliqueSize(g);
        double ZEnergy=zif.getZagrebEnergyZ1(g);

        int diameter = (int) new Diameter().calculate(g);
        WienerIndex wi = new WienerIndex();
        double Avg=(n*(n-1)/2);

        Vector<Object> v = new Vector<>();
        v.add(m);
        v.add(n);
        // v.add(chi2);
        // v.add((2*(maxDeg+minDeg)*M21) -(4*m*maxDeg*minDeg));
        // v.add((2*(maxDeg+minDeg)*M21) -(4*m*maxDeg*minDeg) + M21 +(2*maxDeg*minDeg*H)-(2*m*(maxDeg+minDeg)));
        // v.add(M21 +(2*maxDeg*minDeg*H)-(2*m*(maxDeg+minDeg)));
        // v.add(R);
        //   v.add(H);
        //   v.add((2/(maxEdge+2)) + (2*(chi-(1/Math.sqrt((maxEdge+2))))*(chi-(1/Math.sqrt((maxEdge+2))))/(m-1)) + ((Math.sqrt((minEdge+2))-Math.sqrt((minEdge+2)))*(Math.sqrt((minEdge+2))-Math.sqrt((minEdge+2)))/(maxEdge2*minEdge))  );
        v.add(ZEnergy);
        // v.add(R1);
        // v.add(((n*(maxDeg+minDeg))-(2*m)) / (2*maxDeg*minDeg));
        //v.add(( (a/maxDeg) + (b/minDeg) + ((((n-b)*maxDeg)+((n-a)*minDeg)-(2*m) ) / ((maxDeg-1)*(minDeg+1))) )/2 );
        // v.add(GA);
        //   v.add(ID);
        //  v.add((2*m*R)/n);
        //   v.add((Math.pow(n-3,2)/2) + (Math.sqrt(n-1)*2/n) + (((2*(n-2)*Math.sqrt((n-1)*(n-2))))/((2*n)-3)) - ((n-2)/Math.sqrt((n-1)*(n-2))) -(1/Math.sqrt((n-1))) );
        //v.add((n*(maxDeg+minDeg)-(2*m))/(2*maxDeg*minDeg));
        //    v.add((1/(2*maxDeg2))+(((n-1)*(maxDeg2+minDeg)-((2*m)-n+1))/(2*maxDeg2*minDeg)) );
        //  v.add(chi);
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
        //  v.add(diameter);

        // v.add(MaxCliqueExtension.maxCliqueSize(g));


        v.add(al.toString());

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Conjectures";
    }
}





