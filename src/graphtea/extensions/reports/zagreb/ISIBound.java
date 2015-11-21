package graphtea.extensions.reports.zagreb;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Ali Rostami
 */

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
        titles.add(" ISI(G) ");
        titles.add(" Theorem 13 ");
        titles.add(" Corollary 15 ");
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

        Vector<Object> v = new Vector<>();

        v.add(zif.getInverseSumIndegIndex());

        //Theorem 13
        double val = 0.0;
        if(minDeg == 1) val=n+(1/n)-2;
        else if(minDeg >= 2) {
            if((n*minDeg)%2 == 0) {
                val = (n*Math.pow(minDeg,2))/4.0;
            } else {
                val = (n*Math.pow(minDeg,2))/4.0;
                val += minDeg/2.0;
                val += (2*minDeg)/(8.0*(2*minDeg + 1.0));
            }
        }
        v.add(val);

        //Corollary 15
        val = 0.0;
        if((n*maxDeg)%2==0) val = (n*maxDeg*maxDeg)/4.0;
        else {
            val = ((n-1)*maxDeg*maxDeg)/4.0;
            val += (maxDeg*(maxDeg-1)*(maxDeg-1.0))/(2.0*(2*maxDeg-1.0));
        }
        v.add(val);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Conjectures";
    }
}








