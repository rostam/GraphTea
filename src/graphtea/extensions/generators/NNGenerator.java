// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.util.Vector;

/**
 * @author Ali Rostami
 */

//@CommandAttitude(name = "generate_tree" , abbreviation = "_g_t"
//        ,description = "generate a tree with depth and degree")
public class NNGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "Number of hidden levels")
    public static Integer hidden = 2;

    @Parameter(name = "Size of hidden levels")
    public static Integer hiddenSize = 2;


    public String getName() {
        return "Neural Network";
    }

    public String getDescription() {
        return "Generates a neural network";
    }

    Vertex[] v;

    public Vertex[] getVertices() {
        int size = hiddenSize * hidden + 2;
        Vertex[] ret = new Vertex[size];
        for (int i = 0; i < size; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Vector<Edge> rets = new Vector<>();
        for(int i=1;i<hiddenSize + 1;i++) {
            rets.add(new Edge(v[0], v[i]));
        }

        for(int i=0;i<hiddenSize;i++) {
            rets.add(new Edge(v[v.length - 1 - i], v[v.length - 1]));
        }

        for(int i = 1; i < hiddenSize + 1;i++) {
            for (int j = 1; j < hiddenSize + 1; j++) {
                if (i != j) {
                    rets.add(new Edge(v[i], v[j + hiddenSize]));
                }
            }
        }

        return rets.toArray(new Edge[v.length]);
    }

    public GPoint[] getVertexPositions() {
        int w = 100;
        int h = 100;
        GPoint[] ret = new GPoint[hiddenSize * hidden + 2];
        ret[0] = new GPoint(5,h/2);
        GPoint[] np = PositionGenerators.line(15, h / 4, 0, h, hiddenSize);
        GPoint[] mp = PositionGenerators.line(25, h / 4,   0, h, hiddenSize);
        System.arraycopy(np, 0, ret, 1, hiddenSize);
        System.arraycopy(mp, 0, ret, hiddenSize + 1, hiddenSize);
        ret[ret.length - 1] = new GPoint(35,h/2);
        return ret;
    }


    public String checkParameters() {
    	return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(true, this);
    }

    @Override
    public String getCategory() {
        return "Neural Network";
    }

    public static void main(String[] args) {
        new NNGenerator().generateGraph();
    }
}
