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
import graphlab.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphlab.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

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
    public static Integer numOfEdges = 80;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public String getName() {
        return "Random Graph";
    }

    public String getDescription() {
        return "Generates a random graph with N Vertices and E Edges";
    }

    VertexModel v[];

    public VertexModel[] getVertices() {
        VertexModel[] ret = new VertexModel[numOfVertices];
        for (int i = 0; i < numOfVertices; i++)
            ret[i] = new VertexModel();
        v = ret;
        return ret;
    }

    public EdgeModel[] getEdges() {
        EdgeModel[] ret = new EdgeModel[numOfEdges];
        for (int i = 0; i < numOfEdges; i++)
            ret[i] = randomEdge();
        return ret;
    }

    private EdgeModel randomEdge() {
        VertexModel v1 = randomVertex();
        VertexModel v2 = randomVertex();
        //System.out.println(v1.getID()+","+v2.getID());
        if (v1 != v2)
            return new EdgeModel(v1, v2);
        else
            return randomEdge();
    }

    private VertexModel randomVertex() {
        return v[(int) (Math.random() * numOfVertices)];
    }

    public Point[] getVertexPositions() {
        Point[] ret = new Point[numOfVertices];
        int w = 100;
        int h = 100;
        for (int i = 0; i < numOfVertices; i++) {
            int x = (int) (Math.random() * w);
            int y = (int) (Math.random() * h);
            ret[i] = new Point(x, y);
        }
        return ret;
    }

    public String checkParameters() {
    	if( numOfEdges<0 || numOfVertices<0)return "Both Edges & Vertices must be positive!";
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

}