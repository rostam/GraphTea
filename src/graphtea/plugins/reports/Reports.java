// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.reports;

import graphtea.extensions.reports.MaxIndependentSetReport;
import graphtea.graph.graph.GraphModel;
import graphtea.extensions.reports.basicreports.*;

/**
 * @author Azin Azadi
 */
public class Reports {
    /**
     * @see graphtea.extensions.reports.MaxIndependentSetReport#getMaxIndependentSetSize(graphtea.graph.graph.GraphModel,boolean)
     */
    public static int getMaxIndependentSetSize(GraphModel graph, boolean putFirstVertexInSet) {
        return MaxIndependentSetReport.getMaxIndependentSetSize(graph, putFirstVertexInSet);
    }

    /**
     * @see graphtea.extensions.reports.basicreports.GirthSize#getgirthSize(graphtea.graph.graph.GraphModel)
     */
    public static int girthSize(GraphModel graph) {
        return GirthSize.getgirthSize(graph);
    }

    /**
     * @see graphtea.extensions.reports.basicreports.IsEulerian#isEulerian(graphtea.graph.graph.GraphModel)
     */
    public static boolean isEulerian(GraphModel graph) {
        return IsEulerian.isEulerian(graph);
    }

    /**
     * @see graphtea.extensions.reports.basicreports.NumOfConnectedComponents#getNumOfConnectedComponents(graphtea.graph.graph.GraphModel)
     */
    public static int getNumOfConnectedComponents(GraphModel graph) {
        return NumOfConnectedComponents.getNumOfConnectedComponents(graph);
    }

    /**
     * @see graphtea.extensions.reports.basicreports.NumOfQuadrangle#getNumOfQuadrangles(graphtea.graph.graph.GraphModel)
     */
    public static int getNumOfQuadrangles(GraphModel graph) {
        return NumOfQuadrangle.getNumOfQuadrangles(graph);
    }

    /**
     * @see graphtea.extensions.reports.basicreports.NumOfTriangles#getNumOfTriangles(graphtea.graph.graph.GraphModel)
     */
    public static int getNumOfTriangles(GraphModel graph) {
        return NumOfTriangles.getNumOfTriangles(graph);
    }
}
