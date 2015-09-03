// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RendTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "num_of_stars", abbreviation = "_noss")
public class SubTreeCounting implements GraphReportExtension {

    //@Parameter(name = "k", description = "")
    //public Integer k = 1;

    public Object calculate(GraphModel g) {
        ArrayList<String> out = new ArrayList<String>();
        RendTable ret = new RendTable();
        ret.add(new Vector<Object>());

        double maxDeg = 0;
        for(Vertex v : g) {
            if(g.getDegree(v) > maxDeg)
                maxDeg = g.getDegree(v);
        }

        ret.get(0).add("D(i,j)");
        for(int i=1;i<=maxDeg;i++) {
            ret.get(0).add("" + i);
        }

        for(int i=1;i<=maxDeg;i++) {
            ret.add(new Vector<Object>());
            ret.get(i).add(i+"");
            for(int j=1;j<=maxDeg;j++) {
                int sum = countSubtrees(g, i, j);
                ret.get(i).add(sum);
            }
        }


        return ret;
    }

    public static int countSubtrees(GraphModel g, int i, int j) {
        int sum = 0;
        for(Edge e : g.getEdges()) {
            if (i == j) {
                sum += choose(g.getDegree(e.source)-1,i).intValue()*
                        choose(g.getDegree(e.target)-1,j).intValue();
            } else {
                sum += choose(g.getDegree(e.source)-1,i).intValue()*
                        choose(g.getDegree(e.target)-1,j).intValue();
                sum += choose(g.getDegree(e.source)-1,j).intValue()*
                        choose(g.getDegree(e.target)-1,i).intValue();
            }
        }
        return sum;
    }

    public static BigInteger choose(int x, int y) {
        if (y < 0 || y > x) return BigInteger.ZERO;
        if (y == 0 || y == x) return BigInteger.ONE;

        BigInteger answer = BigInteger.ONE;
        for (int i = x - y + 1; i <= x; i++) {
            answer = answer.multiply(BigInteger.valueOf(i));
        }
        for (int j = 1; j <= y; j++) {
            answer = answer.divide(BigInteger.valueOf(j));
        }
        return answer;
    }


    public String getName() {
        return "Number of Subtrees";
    }

    public String getDescription() {
        return "Number of Subtrees";
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Subtree Counting ";
	}

}
