package graphtea.extensions.reports.topological;


import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.ChromaticNumber;
import graphtea.extensions.reports.RandomMatching;
import graphtea.extensions.reports.basicreports.*;
import graphtea.extensions.reports.clique.MaxCliqueSize;
import graphtea.extensions.reports.others.Radius;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.extensions.reports.others.PeripheralWienerIndex;
import graphtea.extensions.reports.topological.WienerIndex;
import graphtea.extensions.reports.topological.WienerPolarityIndex;
import graphtea.extensions.reports.topological.WienerIndex;
import graphtea.extensions.reports.topological.Harary;
import graphtea.extensions.reports.topological.MWienerIndex;
import graphtea.extensions.reports.topological.DegreeDistance;
import graphtea.extensions.reports.topological.ReciprocalDegreeDistance;
import graphtea.extensions.reports.topological.ConnectiveEccentricIndex;
import graphtea.extensions.reports.topological.EccentricConnectiveIndex;
import graphtea.extensions.reports.topological.EccentricDistanceSum;
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

@CommandAttitude(name = "Complex", abbreviation = "_Complex")
public class Complex implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Complex";
    }

    public String getDescription() {
        return " Complex ";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();


          titles.add(" vertices ");
		      titles.add(" edges ");
    // titles.add(" count ");
    

        //     titles.add(" V. Degrees ");

         

       
       ret.setTitles(titles);

        double maxDeg = 0;
        double maxDeg2 = 0;
        double minDeg = Integer.MAX_VALUE;
        double minDeg2 = AlgorithmUtils.getMinNonPendentDegree(g);

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        maxDeg = al.get(al.size() - 1);
        if (al.size() - 2 >= 0) maxDeg2 = al.get(al.size() - 2);
        else maxDeg2 = maxDeg;
        minDeg = al.get(0);
        if (maxDeg2 == 0) maxDeg2 = maxDeg;

        double a = 0;
        double b = 0;
        double c = 0;
        double d = 0;
        int p = NumOfVerticesWithDegK.numOfVerticesWithDegK(g, 1);

        for (Vertex v : g) {
            if (g.getDegree(v) == maxDeg) a++;
            if (g.getDegree(v) == minDeg) b++;
            if (g.getDegree(v) == maxDeg2) c++;
            if (g.getDegree(v) == minDeg2) d++;
        }
        if (maxDeg == minDeg) b = 0;
        if (maxDeg == maxDeg2) c = 0;

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double maxEdge = 0;
        double maxEdge2 = 0;
        double minEdge = Integer.MAX_VALUE;

        ArrayList<Integer> all = new ArrayList<>();
        for (Edge e : g.getEdges()) {
            int f = g.getDegree(e.source) +
                    g.getDegree(e.target) - 2;
            all.add(f);
        }
        Collections.sort(all);
        maxEdge = all.get(all.size() - 1);
        if (all.size() - 2 >= 0) maxEdge2 = all.get(all.size() - 2);
        else maxEdge2 = maxEdge;
        minEdge = all.get(0);


 
        Vector<Object> v = new Vector<>();

        v.add(n);
        v.add(m);
		
       
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}

