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

@CommandAttitude(name = "finalm2conj", abbreviation = "_finalm2conj")
public class FinalNewM2Lower implements GraphReportExtension{
    public String getName() {
        return "Final New M2 Lower";
    }

    public String getDescription() {
        return " Final New M2 Lower ";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" M^2_1(G) ");
        titles.add(" SR4 max ");
        titles.add(" SR4 min ");
        titles.add(" S3 max  ");
        titles.add(" S3 min  ");
        titles.add(" My  FMC ");
        titles.add(" Das FMC ");
        titles.add(" S2 max ");
        titles.add(" S2 min ");
        titles.add(" My max ");
        titles.add(" Das max ");
        titles.add(" illc ");
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
        v.add(M21);

//SR4 max
        v.add(maxDeg*maxDeg +
                maxDeg2*maxDeg2
                + Math.pow((2*(m+1) - (n+maxDeg+maxDeg2)
                + Math.sqrt((2*m-maxDeg-maxDeg2)
                *(Mm11-((1/maxDeg)+(1/maxDeg2))))),2)/(n-2));

//SR4 min
        v.add(maxDeg*maxDeg +
                minDeg*minDeg
                + Math.pow((2*(m+1) - (n+maxDeg+minDeg)
                + Math.sqrt((2*m-maxDeg-minDeg)
                *(Mm11-((1/maxDeg)+(1/minDeg))))),2)/(n-2));
//S3 max
        v.add(maxDeg*maxDeg +
                maxDeg2*maxDeg2 +
                ((2*m-maxDeg-maxDeg2)/(n-2))
                        *(Mm11+2*m-(maxDeg+maxDeg2+(1/maxDeg) +(1/maxDeg2)))-(n-2));

//S3 min
        v.add(maxDeg*maxDeg +
                minDeg*minDeg +
                ((2*m-maxDeg-minDeg)/(n-2))
                        *(Mm11+2*m-(maxDeg+minDeg+(1/maxDeg) +(1/minDeg)))-(n-2));

//My  FMC
        v.add(Math.pow(maxDeg,2)+(Math.pow(2*m-maxDeg,2)/(n-1))
                + ((Math.pow(n-2,2)*Math.pow(maxDeg2-minDeg,2))/Math.pow(n-1,2)));

//Das FMC
        v.add(Math.pow(maxDeg,2)+(Math.pow(2*m-maxDeg,2)/(n-1))
                + ((2*(n-2)*Math.pow(maxDeg2-minDeg,2))/Math.pow(n-1,2)));

//S2 max
        v.add(maxDeg*maxDeg +
                maxDeg2*maxDeg2 +
                (((2*m-maxDeg-maxDeg2)*(n-2))/(Mm11-((1/maxDeg)+(1/maxDeg2)))));
//S2 min
        v.add(maxDeg*maxDeg +
                minDeg*minDeg +
                (((2*m-maxDeg-minDeg)*(n-2))/(Mm11-((1/maxDeg)+(1/minDeg)))));

//My max
        v.add(maxDeg*maxDeg +
                maxDeg2*maxDeg2 +
                (Math.pow(2*m-maxDeg-maxDeg2,2)/(n-2)));

//Das max
        v.add(maxDeg*maxDeg +
                minDeg*minDeg +
                (Math.pow(2*m-maxDeg-minDeg,2)/(n-2)));


        //illc
        v.add(Math.pow(2*m,2)/(n));
        ret.add(v);

        return ret;
    }

    @Override
    public String getCategory() {
        // TODO Auto-generated method stub
        return "Topological Indices-Conjectures";
    }
}








