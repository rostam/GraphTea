// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "total_num_of_stars", abbreviation = "_tnoss")
public class TotalNumOfStars implements GraphReportExtension {

    public Object calculate(GraphData gd) {
        GraphModel g = gd.getGraph();
        ArrayList<Integer> ar = new ArrayList<Integer>();
        Vector<String> ret = new Vector<String>();
        for(int i=0;i<getMaxDegree(g);i++) {
            int sum = 0;
            for (Vertex v : g) {
                int deg = g.getDegree(v);
                sum += choose(deg, i+1).intValue();
            }
            if(i==0) sum /= 2;
            ret.add("NumOf(K1," + (i+1) + ") = "+sum );
        }

        return ret;
    }

    int getMaxDegree(GraphModel g) {
        int maxDegree = 0;
        for (Vertex v : g) {
            if(maxDegree < g.getDegree(v)) {
                maxDegree = g.getDegree(v);
            }
        }
        return maxDegree;
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
        return "Total Number of Stars";
    }

    public String getDescription() {
        return "Total Number of Stars";
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Property";
	}

}
