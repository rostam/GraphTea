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
@CommandAttitude(name = "generate_random_tree", abbreviation = "_g_rand_t")
public class RandomTreeGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    GraphModel g;
    @Parameter(name = "Number of Vertices")
    public static Integer n = 50;
    @Parameter(name = "Height")
    public static Integer h = 5;
    @Parameter(name = "Maximum Degree")
    public static Integer d = 5;

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
        Edge[] ret = new Edge[n - 1];
        int[][] ver = new int[2][n]; //o->deg 1->height

        ver[1][0] = 0;
//the new one
        for (int i = 0; i < n - 1; i++) {
            int ran = (int) (Math.random() * (i + 1));
            while (!(ver[0][ran] < d && ver[1][ran] < h)) {
                ran = (ran + 1) % (i + 1);
            }
            ret[i] = new Edge(v[i + 1], v[ran]);
            ver[0][i + 1]++;
            ver[0][ran]++;
            ver[1][i + 1] = ver[1][ran] + 1;
        }
        return ret;
    }

    public Point[] getVertexPositions() {
        return Utils.computeRandomPositions(n);
    }

    public String getName() {
        return "Random Tree";
    }

    public String getDescription() {
        return "Generates a random tree";
    }

    public String checkParameters() {
        if ( n<0 || h<0|| d<0)return "Values must be positive!";
    	if (n > Math.pow(d, h) - 1) return "N is to small!";
        else
        	return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    /**
     * generates a random tree with given parameters
     */
    public static GraphModel generateRandomTree(int n, int h, int d) {
        RandomTreeGenerator.h = h;
        RandomTreeGenerator.d = d;
        RandomTreeGenerator.n = n;
        return GraphGenerator.getGraph(false, new RandomTreeGenerator());
    }

    @Override
    public String getCategory() {
        return "Trees";
    }
}