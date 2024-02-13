// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "num_of_stars", abbreviation = "_noss")
public class NumOfStars implements GraphReportExtension<Integer>, Parametrizable {

    @Parameter(name = "k", description = "The size of star")
    public Integer k = 1;

    public Integer calculate(GraphModel g) {
        int sum = 0;
        for(Vertex v : g) {
            int deg = g.getDegree(v);
            sum += AlgorithmUtils.choose(deg,k).intValue();
        }
        return sum;
    }

    public String getName() {
        return "Number of Stars";
    }

    public String getDescription() {
        return "Number of Stars";
    }

	@Override
	public String getCategory() {
		return "General";
	}


    @Override
    public String checkParameters() {
        return null;
    }
}
