// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.generators;

import graphtea.extensions.Utils;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.awt.*;

/**
 * User: root
 */
@CommandAttitude(name = "generate_random", abbreviation = "_g_rand")
public class RandomGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    GraphModel g;
    @Parameter(name = "Vertices", description = "Num of Vertices")
    public static Integer numOfVertices = 30;
    @Parameter(name = "Edges", description = "Num of Edges")
    private static Integer numOfEdges = 80;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public String getName() {
        return "Random Graph";
    }

    public String getDescription() {
        return "Generates a random graph with N Vertices and E Edges";
    }

    Vertex v[];

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[numOfVertices];
        for (int i = 0; i < numOfVertices; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Edge[] ret = new Edge[numOfEdges];
        for (int i = 0; i < numOfEdges; i++)
            ret[i] = randomEdge();
        return ret;
    }

    private Edge randomEdge() {
        Vertex v1 = randomVertex();
        Vertex v2 = randomVertex();
        if (v1 != v2)
            return new Edge(v1, v2);
        else
            return randomEdge();
    }

    private Vertex randomVertex() {
        return v[(int) (Math.random() * numOfVertices)];
    }

    public Point[] getVertexPositions() {
        return Utils.computeRandomPositions(numOfVertices);
    }

    public String checkParameters() {
    	if( numOfEdges<0 || numOfVertices<0) return "Both Edges & Vertices must be positive!";
    	else
    		return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    /**
     * generates a Random Graph with given parameters
     */
    public static GraphModel generateRandomGraph(int numOfVertices, int numOfEdges) {
        RandomGenerator.numOfVertices = numOfVertices;
        RandomGenerator.numOfEdges = numOfEdges;
        return GraphGenerator.getGraph(false, new RandomGenerator());
    }

    @Override
    public String getCategory() {
        return "Random Graphs";
    }
}