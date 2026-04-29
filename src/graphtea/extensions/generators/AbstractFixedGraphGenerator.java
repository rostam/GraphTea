// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

/**
 * Base class for fixed-topology graph generators (named graphs with a hard-coded
 * edge list and no parameters). Subclasses provide the vertex count and edge list;
 * all structural boilerplate is handled here.
 */
abstract class AbstractFixedGraphGenerator
        implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {

    private Vertex[] v;

    protected abstract int numVertices();

    protected abstract int[][] edgeList();

    @Override
    public Vertex[] getVertices() {
        v = new Vertex[numVertices()];
        for (int i = 0; i < numVertices(); i++) {
            v[i] = new Vertex();
        }
        return v;
    }

    @Override
    public Edge[] getEdges() {
        int[][] edges = edgeList();
        Edge[] ret = new Edge[edges.length];
        for (int i = 0; i < edges.length; i++) {
            ret[i] = new Edge(v[edges[i][0]], v[edges[i][1]]);
        }
        return ret;
    }

    @Override
    public GPoint[] getVertexPositions() {
        return PositionGenerators.circle(5, 5, 100000, 100000, numVertices());
    }

    @Override
    public String checkParameters() {
        return null;
    }

    @Override
    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }
}
