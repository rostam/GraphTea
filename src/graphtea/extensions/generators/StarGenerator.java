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

@CommandAttitude(name = "generate_sn", abbreviation = "_g_sn")
public class StarGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "S(N) Generator's N")
    public static Integer n = 10;
    GraphModel g;
    Vertex[] v;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
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
        for (int i = 1; i < n; i++) {
            ret[i - 1] = new Edge(v[0], v[i]);
        }
        return ret;
    }

    public Point[] getVertexPositions() {
        Point[] ret = new Point[n];
        Point[] points = PositionGenerators.circle(5, 5, 10000, 10000, n - 1);
        System.arraycopy(points, 0, ret, 1, n - 1);
        ret[0] = new Point(10000 / 2, 10000 / 2);
        return ret;
    }

    public String getName() {
        return "Star";
    }

    public String getDescription() {
        return "Generates a star";
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
     * generates a Star Graph with given parameters
     */
    public static GraphModel generateStar(int n) {
        StarGenerator.n = n;
        return GraphGenerator.getGraph(false, new StarGenerator());
    }

    @Override
    public String getCategory() {
        return "Trees";
    }
}
