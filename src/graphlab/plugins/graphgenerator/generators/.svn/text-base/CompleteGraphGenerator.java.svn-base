// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.graphgenerator.generators;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.lang.CommandAttitude;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.graphgenerator.GraphGenerator;
import graphlab.plugins.graphgenerator.core.PositionGenerators;
import graphlab.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphlab.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.awt.*;

/**
 * @author  azin azadi
 */
@CommandAttitude(name = "generate_kn", abbreviation = "_g_kn", description = "Generates a complete graph with n vertices")
public class CompleteGraphGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    GraphModel g;
    @Parameter(name = "n")
    public static Integer n = 3;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    VertexModel[] v;

    public VertexModel[] getVertices() {
        VertexModel[] ret = new VertexModel[n];
        for (int i = 0; i < n; i++)
            ret[i] = new VertexModel();
        v = ret;
        return ret;
    }

    public EdgeModel[] getEdges() {
        EdgeModel[] ret = new EdgeModel[n * (n - 1) / 2];
        int t = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i; j++) {
                ret[t++] = new EdgeModel(v[i], v[j]);
            }
        return ret;
    }

    public Point[] getVertexPositions() {
        return PositionGenerators.circle(5, 5, 100000, 100000, n);
    }

    public String getName() {
        return "Complete Graph";
    }

    public String getDescription() {
        return "Generates a Complete Graph";
    }


    public String checkParameters() {
    	if (n < 0) return "n must be positive";
        else
            return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    /**
     * generates a Complete Graph with given parameters
     */
    public static GraphModel generateCompleteGraph(int n) {
        CompleteGraphGenerator.n = n;
        return GraphGenerator.getGraph(false, new CompleteGraphGenerator());
    }
}