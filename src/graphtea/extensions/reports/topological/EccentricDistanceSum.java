// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.topological;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;

/**
 * @author Ali Rostami
 */
@CommandAttitude(name = "eccentric_distance_sum", abbreviation = "_eccentric_distance_sum")
public class EccentricDistanceSum extends EccentricDistanceSumBase {

    public String getName() {
        return "Eccentric Distance Sum";
    }

    public String getDescription() {
        return "Eccentric Distance Sum";
    }

    @Override
    protected double weight(GraphModel g, Vertex v, int distanceSum, double eccentricity) {
        return distanceSum * eccentricity;
    }
}
