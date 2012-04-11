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
import graphlab.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphlab.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

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
        Point[] ret = new Point[n];
        int w = 100;
        int h = 100;
        for (int i = 0; i < n; i++) {
            int x = (int) (Math.random() * w);
            int y = (int) (Math.random() * h);
            ret[i] = new Point(x, y);
        }
        return ret;
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

}