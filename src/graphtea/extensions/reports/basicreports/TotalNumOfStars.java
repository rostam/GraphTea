// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.extensions.reports.Utils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;
import java.util.Vector;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "total_num_of_stars", abbreviation = "_tnoss")
public class TotalNumOfStars implements GraphReportExtension {

    public Object calculate(GraphModel g) {
        Vector<String> ret = new Vector<>();
        for(int i=0;i<Utils.getMaxDegree(g);i++) {
            int sum = 0;
            for (Vertex v : g) {
                int deg = g.getDegree(v);
                sum += Utils.choose(deg, i+1).intValue();
            }
            if(i==0) sum /= 2;
            ret.add("NumOf(K1," + (i+1) + ") = "+sum );
        }

        return ret;
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
		return "General";
	}

}
