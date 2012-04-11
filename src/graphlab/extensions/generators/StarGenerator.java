// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.extensions.generators;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
import graphlab.platform.lang.CommandAttitude;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.graphgenerator.GraphGenerator;
import graphlab.plugins.graphgenerator.core.PositionGenerators;
import graphlab.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphlab.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.awt.*;

/**
 * User: root
 */
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
        ret[0] = new Point(100 / 2, 100 / 2);
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

}
