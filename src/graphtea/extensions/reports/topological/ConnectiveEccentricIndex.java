// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.reports.DijkstraNonNegative;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "randic_index", abbreviation = "_ri")
public class ConnectiveEccentricIndex implements GraphReportExtension<ArrayList<String>> {
    public String getName() {
        return "Connective Eccentric Index";
    }

    public String getDescription() {
        return "Connective Eccentric Index";
    }


    public ArrayList<String> calculate(GraphModel g) {
        ArrayList<String> out = new ArrayList<>();

        double sum = 0;
        for(Vertex it_v : g.getVertexArray()) {
            DijkstraNonNegative.dijkstra(g, it_v);
            double max = 0;
            for (Vertex v : g.getVertexArray()) {
                double value = v.getUserDefinedAttribute(DijkstraNonNegative.Dist);
                if (value > max) {
                    max = value;
                }
            }
            sum += g.getDegree(it_v)/(1.000*max);
        }

        out.add("Connective Eccentric Index: "+ sum);

        return out;
    }


    @Override
    public String getCategory() {
        return "Topological Indices-Distance";
    }
}