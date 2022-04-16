package graphtea.extensions.reports.topological.Irr;

import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.reports.basicreports.NumOfTriangles;
import graphtea.extensions.reports.basicreports.NumOfVerticesWithDegK;
import graphtea.extensions.reports.topological.ZagrebIndexFunctions;
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

@CommandAttitude(name = "Irr_t_G", abbreviation = "_Irr_t_G")
public class Irr_t_G implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Irr_t_G";
    }


    public String getDescription() {
        return "Irr_t_G";
    }

    public static int deg_e(GraphModel g, Vertex u) {
        int ret = 0;
        Vector<Vertex> neighbors = new Vector<>();
        for (Vertex v : g.directNeighbors(u)) {
            ret += g.getDegree(v);
            neighbors.add(v);
        }

        int conn = 0;
        for (int i = 0; i < neighbors.size(); i++) {
            for (int j = i + 1; j < neighbors.size(); j++) {
                if (g.isEdge(neighbors.get(i),neighbors.get(j))) {
                    conn++;
                }
            }
        }

        return ret - conn/2;
    }

    public static int irr_t_ev_G(GraphModel graph) {
        int sum = 0;
        for (Vertex i : graph) {
            for (Vertex j : graph.directNeighbors(i)) {
                sum += Math.abs(deg_e(graph,i) - deg_e(graph,j));
            }
        }
        return sum/2;
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(AlgorithmUtils.createLineGraph(g));
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" m ");
        // titles.add(" Max Planar ");
        titles.add(" n ");
        titles.add(" Zagreb ");
        titles.add(" Ve ");
        titles.add(" t ");
        titles.add(" Irr_t_G ");
        titles.add(" V. Degrees ");

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
        int t = NumOfTriangles.getNumOfTriangles(g);
        int irr_t_ev_g = irr_t_ev_G(g);
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



        List<Integer>[] gg = new List[g.getVerticesCount()];
        for (int i = 0; i < g.getVerticesCount(); i++) {
            gg[i] = new ArrayList<>();
        }

        for(Edge e : g.getEdges()) {
            gg[e.source.getId()].add(e.target.getId());
        }


        Vector<Object> v = new Vector<>();
        v.add(m);

        v.add(n);
        v.add(M21);
        v.add(M21-(3*t));

        v.add(t);
        v.add(irr_t_ev_g);
        v.add(al.toString());

        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }

    public static void main(String[] args) {
        GraphModel g= new GraphModel();
        for (int i=0;i < 8;i++) {
            g.addVertex(new Vertex());
        }

        g.addEdge(new Edge(g.getVertex(0), g.getVertex(1)));
        g.addEdge(new Edge(g.getVertex(0), g.getVertex(2)));
        g.addEdge(new Edge(g.getVertex(0), g.getVertex(3)));
        g.addEdge(new Edge(g.getVertex(0), g.getVertex(7)));

        g.addEdge(new Edge(g.getVertex(1), g.getVertex(2)));
        g.addEdge(new Edge(g.getVertex(1), g.getVertex(3)));
        g.addEdge(new Edge(g.getVertex(1), g.getVertex(6)));

        g.addEdge(new Edge(g.getVertex(2), g.getVertex(4)));
        g.addEdge(new Edge(g.getVertex(2), g.getVertex(6)));

        g.addEdge(new Edge(g.getVertex(3), g.getVertex(5)));

        System.out.println(deg_e(g, g.getVertex(0)));

    }
}



