// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

import graphtea.extensions.reports.basicreports.NumOfTriangles;
import graphtea.extensions.reports.basicreports.SubTreeCounting;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.goperators.GraphComplement;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "m3norconj", abbreviation = "_m3norconj")
public class LastQuery implements GraphReportExtension{
    public String getName() {
        return "Last Query";
    }

    public String getDescription() {
        return "Last Query";
    }

    public Object calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifc
        = new ZagrebIndexFunctions((graphtea.graph.graph.GraphModel)
                GraphComplement.complement(g));


        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());
        ret.get(0).add(" M^1_2(Gc) ");
        ret.get(0).add(" 3.1 ");
        ret.get(0).add(" 3.2 ");

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

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();

        double M21=zif.getFirstZagreb(1);
        double M21gc=zifc.getFirstZagreb(1);
        double M12=zif.getSecondZagreb(1);
        double M12gc=zifc.getSecondZagreb(1);
        double Mc12gc=zifc.getSecondZagrebCoindex(1);
        double Mc12=zif.getSecondZagrebCoindex(1);

        double M31=zif.getFirstZagreb(2);
        double M31gc=zifc.getFirstZagreb(2);
        double Mc31=zif.getFirstZagrebCoindex(2);
        double Mc31gc=zifc.getFirstZagrebCoindex(2);
        double Mm11=zif.getFirstZagreb(-2);


        double sigmaP4 =
                SubTreeCounting.countSubtrees(g,1,1);

        double numOfTri = NumOfTriangles.getNumOfTriangles(g);

        ret.add(new Vector<Object>());
        ret.get(1).add(M12);

        double Md2 = maxDeg*maxDeg;
        double M2d2 = maxDeg2*maxDeg2;
        double md2 = minDeg*minDeg;
        double MdMd2 = 2*(m+1)-n-maxDeg-maxDeg2;
        double Mdmd  = 2*(m+1)-n-maxDeg-minDeg;
        double MdMd22 = 2*m - maxDeg - maxDeg2;
        double Mdmd2 =  2*m - maxDeg - minDeg;
        double idg = 0;
        for(Vertex v : g) {
            idg += 1/g.getDegree(v);
        }
        double idgM=idg - (1/maxDeg) - (1/maxDeg2);
        double idgm=idg - (1/maxDeg) - (1/minDeg);

        int k=0;
        for(Vertex v : g.getVertexArray()) {
            if(g.getDegree(v)==1) k++;
        }
        //3.1
        ret.get(1).add(4*n+3*k);
        //3.2
        ret.get(1).add(4*Math.pow(m,3)/Math.pow(n,2));

        return ret;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
