// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

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

    Vertex[] v;

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[n];
        for (int i = 0; i < n; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Edge[] ret = new Edge[n * (n - 1) / 2];
        int t = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i; j++) {
                ret[t++] = new Edge(v[i], v[j]);
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

    @Override
    public String getCategory() {
        return "General Graphs";
    }
}