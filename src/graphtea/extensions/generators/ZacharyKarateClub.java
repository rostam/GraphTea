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
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

/**
 * Generates Zachary's Karate Club network: 34 vertices, 78 edges.
 *
 * This is one of the most widely used benchmark networks in community detection
 * and social network analysis. It represents friendship ties between members of
 * a university karate club (Zachary, 1977). The club split into two factions,
 * making it a standard test case for graph partitioning algorithms.
 *
 * Reference: W. W. Zachary, "An information flow model for conflict and fission
 * in small groups," Journal of Anthropological Research, 33(4):452-473, 1977.
 */
@CommandAttitude(name = "generate_karate", abbreviation = "_g_karate",
    description = "Generates Zachary's Karate Club network (34 vertices, 78 edges)")
public class ZacharyKarateClub implements GraphGeneratorExtension, Parametrizable,
    SimpleGeneratorInterface {

    private static final int NUM_VERTICES = 34;

    private static final int[][] EDGE_LIST = {
        {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7}, {0, 8},
        {0, 10}, {0, 11}, {0, 12}, {0, 13}, {0, 17}, {0, 19}, {0, 21}, {0, 31},
        {1, 2}, {1, 3}, {1, 7}, {1, 13}, {1, 17}, {1, 19}, {1, 21}, {1, 30},
        {2, 3}, {2, 7}, {2, 8}, {2, 9}, {2, 13}, {2, 27}, {2, 28}, {2, 32},
        {3, 7}, {3, 12}, {3, 13},
        {4, 6}, {4, 10},
        {5, 6}, {5, 10}, {5, 16},
        {6, 16},
        {8, 30}, {8, 32}, {8, 33},
        {9, 33},
        {13, 33},
        {14, 32}, {14, 33},
        {15, 32}, {15, 33},
        {18, 32}, {18, 33},
        {19, 33},
        {20, 32}, {20, 33},
        {22, 32}, {22, 33},
        {23, 25}, {23, 27}, {23, 29}, {23, 32}, {23, 33},
        {24, 25}, {24, 27}, {24, 31},
        {25, 31},
        {26, 29}, {26, 33},
        {27, 33},
        {28, 31}, {28, 33},
        {29, 32}, {29, 33},
        {30, 32}, {30, 33},
        {31, 32}, {31, 33},
        {32, 33}
    };

    private Vertex[] v;

    @Override
    public Vertex[] getVertices() {
        v = new Vertex[NUM_VERTICES];
        for (int i = 0; i < NUM_VERTICES; i++) {
            v[i] = new Vertex();
        }
        return v;
    }

    @Override
    public Edge[] getEdges() {
        Edge[] ret = new Edge[EDGE_LIST.length];
        for (int i = 0; i < EDGE_LIST.length; i++) {
            ret[i] = new Edge(v[EDGE_LIST[i][0]], v[EDGE_LIST[i][1]]);
        }
        return ret;
    }

    @Override
    public GPoint[] getVertexPositions() {
        return PositionGenerators.circle(5, 5, 100000, 100000, NUM_VERTICES);
    }

    @Override
    public String checkParameters() {
        return null;
    }

    @Override
    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    @Override
    public String getName() {
        return "Zachary Karate Club";
    }

    @Override
    public String getDescription() {
        return "Zachary's Karate Club network: 34 vertices, 78 edges. "
            + "Classic social network benchmark for community detection (Zachary 1977).";
    }

    @Override
    public String getCategory() {
        return "Benchmark Graphs";
    }
}
