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
 * @author azin azadi

 */
@CommandAttitude(name = "generate_pn", abbreviation = "_g_pn")
public class PathGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "N")
    public static Integer n = 10;
    Vertex[] v;
    GraphModel g;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public String getName() {
        return "Path";
    }

    public String getDescription() {
        return "Generates a path with n vertices";
    }

    public Vertex[] getVertices() {

        Vertex[] ret = new Vertex[n];
        for (int i = 0; i < n; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Edge[] ret = new Edge[n - 1];
        for (int i = 0; i < n - 1; i++) {
            ret[i] = new Edge(v[i], v[i + 1]);
        }
        return ret;
    }

    public Point[] getVertexPositions() {
        return PositionGenerators.line(5, 5, 10000, 10000, n);
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
     * generates a Path Graph with given parameters
     */
    public static GraphModel generatePath(int n) {
        PathGenerator.n = n;
        return GraphGenerator.getGraph(false, new PathGenerator());
    }

    @Override
    public String getCategory() {
        return "Trees";
    }
}
