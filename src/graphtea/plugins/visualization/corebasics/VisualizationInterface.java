// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.visualization.corebasics;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import java.util.HashMap;

/**
 * @author Rouzbeh Ebrahimi
 * Email: ruzbehus@gmail.com
 */
public interface VisualizationInterface {
    /*
      @param g
     */
    void setWorkingGraph(GraphModel g);

    HashMap<Vertex, GPoint> getNewVertexPlaces();

    HashMap<Edge, GPoint> getNewEdgeCurveControlPoints();
}
