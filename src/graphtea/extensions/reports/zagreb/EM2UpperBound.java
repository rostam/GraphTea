// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.extensions.Utils;
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

@CommandAttitude(name = "m3finalconj", abbreviation = "_m3conj")
public class EM2UpperBound implements GraphReportExtension{
    public String getName() {
        return "EM2 Upper";
    }

    public String getDescription() {
        return "EM2 Upper";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(
                Utils.createLineGraph(g)
        );

        RenderTable ret = new RenderTable();
        Vector<String> titles = new Vector<>();
        titles.add(" EM2 ");   
        titles.add("Zeta1");        
        titles.add("Zeta2");
        titles.add("Zeta3");
        titles.add("Zeta4");
        titles.add("Eps3");
        titles.add("Eps4");
        titles.add("Zhou1 ");
        titles.add("Zhou2");
        titles.add("Zhou3");
        titles.add("N4");
        titles.add("N2");
        titles.add("N1");
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
        double M31=zif.getFirstZagreb(2);
        double M41=zif.getFirstZagreb(3);
        double M22=zif.getSecondZagreb(2);
        double Mm31=zif.getFirstZagreb(-4);
        double Mm11=zif.getFirstZagreb(-2);
        double EM1=zifL.getFirstZagreb(1);
        double EM2=zifL.getSecondZagreb(1);
        
        double D12=((EM2-(M41/2)+(3*M31/2)-M21)/2);
        
        double K13=((M31-(3*M21)+(4*m))/6);
        
        double K14=((M41-(6*M31)+(11*M21)-(12*m))/24);
        
        double Psi1=(Math.pow((2*(m+1) - (n+maxDeg+maxDeg2)
                + Math.sqrt((2*m-maxDeg-maxDeg2)
                *(Mm11-((1/maxDeg)+(1/maxDeg2))))),2)/(n-2));

        double Psi2=(Math.pow((2*(m+1) - (n+maxDeg+minDeg)
                + Math.sqrt((2*m-maxDeg-minDeg)
                *(Mm11-((1/maxDeg)+(1/minDeg))))),2)/(n-2));

        double Zeta1=2*m-maxDeg-maxDeg2
                + (M21-maxDeg*maxDeg-maxDeg2*maxDeg2-n+2)
                *(M21-maxDeg*maxDeg-maxDeg2*maxDeg2-n+2)
                /(2*m-maxDeg-maxDeg2-Mm11+ (1/maxDeg) + (1/maxDeg2));


        double Zeta2=2*m-(maxDeg)-(minDeg)
                + Math.pow((M21-(maxDeg*maxDeg)-(minDeg*minDeg)-(n-2)),2)
                /(2*m-maxDeg-minDeg-Mm11+(1/maxDeg)+(1/minDeg));

        double Zeta3=M21-maxDeg*maxDeg
                - maxDeg2*maxDeg2 +(Math.pow((M21-maxDeg*maxDeg - maxDeg2*maxDeg2-2*m
                + maxDeg + maxDeg2),2)/(2*m-maxDeg-maxDeg2-n+2));

        double Zeta4=M21-maxDeg*maxDeg - minDeg*minDeg
                +(Math.pow((M21-maxDeg*maxDeg - minDeg*minDeg-2*m + maxDeg + minDeg),2)
                /(2*m-maxDeg-minDeg-n+2));

        double Eps3=( Math.pow((M21-(maxDeg*maxDeg)-(maxDeg2*maxDeg2))
                +Math.sqrt((n-2)* (M21-(maxDeg*maxDeg)-(maxDeg2*maxDeg2)))
                -(2*m-maxDeg-maxDeg2),2)/(2*m-maxDeg-maxDeg2));

        double Eps4=( Math.pow((M21-(maxDeg*maxDeg)-(minDeg*minDeg))
                +Math.sqrt((n-2)*(M21-(maxDeg*maxDeg)-(minDeg*minDeg)))
                -(2*m-maxDeg-minDeg),2)/(2*m-maxDeg-minDeg));
        
        
        
        Vector<Object> v = new Vector<>();
        v.add(zifL.getSecondZagreb(1));
        

        
        //Zeta1
        v.add((2*D12)+(12*K14)+(15*K13)+(3*M21)-(maxDeg*maxDeg*maxDeg)-(maxDeg2*maxDeg2*maxDeg2)-Zeta1-(4*m));
        
        //Zeta2
        v.add((2*D12)+(12*K14)+(15*K13)+(3*M21)-(maxDeg*maxDeg*maxDeg)-(minDeg*minDeg*minDeg)-Zeta2-(4*m));
        
        //Zeta3
        v.add((2*D12)+(12*K14)+(15*K13)+(3*M21)-(maxDeg*maxDeg*maxDeg)-(maxDeg2*maxDeg2*maxDeg2)-Zeta3-(4*m));
        
        //Zeta4
        v.add((2*D12)+(12*K14)+(15*K13)+(3*M21)-(maxDeg*maxDeg*maxDeg)-(minDeg*minDeg*minDeg)-Zeta4-(4*m));

        //Eps3
        v.add((2*D12)+(12*K14)+(15*K13)+(3*M21)-(maxDeg*maxDeg*maxDeg)-(maxDeg2*maxDeg2*maxDeg2)-Eps3-(4*m));
        
        //Eps4
        v.add((2*D12)+(12*K14)+(15*K13)+(3*M21)-(maxDeg*maxDeg*maxDeg)-(minDeg*minDeg*minDeg)-Eps4-(4*m));
        
      //Zhou1
        v.add(Math.sqrt(M21-(3*m)+1)*(EM1/2));

        
        //Zhou2
        v.add(((M21/2)-m)*Math.pow(Math.sqrt(M21-(2*m)+(1/4))-(1/2),2)); 
        
        //Zhou3
        v.add(Math.pow(M21-(2*m),2)*(maxDeg-1)/(2*maxDeg)); 
        
       //N3
        v.add((Math.pow(M21-(2*m),2)/2)-(m-1)*(minDeg-1)*(M21-(2*m))+((2*minDeg-3)*EM1/2)); 
        //N2
        v.add(2*(M21-(2*m))*(maxDeg-1)*(maxDeg-1)); 
        
        //N1
        v.add(EM1*(maxDeg-1)); 
        ret.add(v);

        return ret;
    }

    @Override
    public String getCategory() {
        return "Topological Indices-Conjectures";
    }
}