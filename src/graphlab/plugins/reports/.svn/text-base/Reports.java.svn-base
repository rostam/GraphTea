// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.reports;

import graphlab.extensions.reports.MaxIndependentSetReport;
import graphlab.graph.graph.GraphModel;
import graphlab.plugins.reports.basicreports.*;

/**
 * @author Azin Azadi
 */
public class Reports {
    /**
     * @see graphlab.extensions.reports.MaxIndependentSetReport#getMaxIndependentSetSize(graphlab.graph.graph.GraphModel,boolean)
     */
    public static int getMaxIndependentSetSize(GraphModel graph, boolean putFirstVertexInSet) {
        return MaxIndependentSetReport.getMaxIndependentSetSize(graph, putFirstVertexInSet);
    }

    /**
     * @see graphlab.plugins.reports.basicreports.GirthSize#getgirthSize(graphlab.graph.graph.GraphModel)
     */
    public static int girthSize(GraphModel graph) {
        return GirthSize.getgirthSize(graph);
    }

    /**
     * @see graphlab.plugins.reports.basicreports.IsEulerian#isEulerian(graphlab.graph.graph.GraphModel)
     */
    public static boolean isEulerian(GraphModel graph) {
        return IsEulerian.isEulerian(graph);
    }

    /**
     * @see graphlab.plugins.reports.basicreports.NumOfConnectedComponents#getNumOfConnectedComponents(graphlab.graph.graph.GraphModel)
     */
    public static int getNumOfConnectedComponents(GraphModel graph) {
        return NumOfConnectedComponents.getNumOfConnectedComponents(graph);
    }

    /**
     * @see graphlab.plugins.reports.basicreports.NumOfQuadrangle#getNumOfQuadrangles(graphlab.graph.graph.GraphModel)
     */
    public static int getNumOfQuadrangles(GraphModel graph) {
        return NumOfQuadrangle.getNumOfQuadrangles(graph);
    }

    /**
     * @see graphlab.plugins.reports.basicreports.NumOfTriangles#getNumOfTriangles(graphlab.graph.graph.GraphModel)
     */
    public static int getNumOfTriangles(GraphModel graph) {
        return NumOfTriangles.getNumOfTriangles(graph);
    }
}
