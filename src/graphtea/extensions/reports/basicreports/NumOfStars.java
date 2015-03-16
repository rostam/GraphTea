// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseGraph;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.reports.extension.GraphReportExtension;

import graphtea.platform.parameter.Parametrizable;

import java.math.BigInteger;

import java.util.ArrayList;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "num_of_stars", abbreviation = "_noss")
public class NumOfStars implements GraphReportExtension, Parametrizable {

    @Parameter(name = "k", description = "")
    public Integer k = 1;

    public Integer calculate(GraphData gd) {
        GraphModel g = gd.getGraph();
        int sum = 0;
        for(Vertex v : g) {
            int deg = g.getDegree(v);
            sum += choose(deg,k).intValue();
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
        return "Number of Stars";
    }

    public String getDescription() {
        return "Number of Stars";
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}


    @Override
    public String checkParameters() {
        return null;
    }
}
