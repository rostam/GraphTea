// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.util.Arrays;
import java.util.Vector;

/**
 * @author Ali Rostami

 */
@CommandAttitude(name = "generate_pn", abbreviation = "_g_pn")
public class ExampleChainGraph7 implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "N")
    public static Integer n = 5;
    Vertex[] v;

    public String getName() {
        return "Chain Graph 7";
    }

    public String getDescription() {
        return "Chain Graph 7";
    }

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[3 * n + 1];
        for (int i = 0; i < 3 * n + 1; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Vector<Edge> ret = new Vector<>();
        for (int i = 0; i < 2*n - 1; i++) {
            ret.add(new Edge(v[i], v[i + 1]));
        }

        for (int i = 0; i < n; i++) {
            ret.add(new Edge(v[i + 2*n], v[i + 1 + 2*n]));
        }

        for (int i = 0; i < n - 1; i++) {
            ret.add(new Edge(v[i*2 + 1], v[i + 1 + 2*n]));
            ret.add(new Edge(v[i*2 + 1 + 1], v[i + 1 + 2*n]));
        }

        ret.add(new Edge(v[0], v[2*n]));
        ret.add(new Edge(v[2*n-1], v[3*n]));

        Edge[] ee = new Edge[ret.size()];

        return ret.toArray(ee);
    }

    static <T> T[] concatWithArrayCopy(T[] array1, T[] array2) {
        T[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    public GPoint[] getVertexPositions() {
        GPoint[] p1 = PositionGenerators.line(5, 5, 10000, 10000, 2*n);
        GPoint[] p2 = PositionGenerators.line(4000, 5, 10000, 10000, n + 1);
        return concatWithArrayCopy(p1,p2);
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
        ExampleChainGraph7.n = n;
        return GraphGenerator.getGraph(false, new ExampleChainGraph7());
    }

    @Override
    public String getCategory() {
        return "Maxwell Examples";
    }

    public static void main(String[] args) {
        new ExampleChainGraph7().generateGraph();
    }
}
