package graphtea.extensions.reports.zagreb;




import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
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
        titles.add("R");
        titles.add("Diameter");
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


        double R=zif.getSecondZagreb(-0.5);
        double H=zif.getHarmonicIndex();

        int diameter = (int) new Diameter().calculate(g);

        Vector<Object> v = new Vector<>();
        v.add(m);
        v.add(n);
        v.add(R);
        v.add(diameter);


        v.add(al.toString());
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "OurWorks-Conjectures";
    }
}







