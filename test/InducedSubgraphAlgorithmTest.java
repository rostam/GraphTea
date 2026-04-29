// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import graphtea.extensions.algorithms.subgraphs.InducedSubgraphs;
import graphtea.extensions.algorithms.GreedyColoring;
import graphtea.extensions.algorithms.KruskalAlgorithm;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseEdgeProperties;
import graphtea.platform.core.BlackBoard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for InducedSubgraphs (vertex- and edge-induced), GreedyColoring, and KruskalAlgorithm.
 * All algorithm tests wire the graph via BlackBoard using GraphAttrSet.name.
 */
public class InducedSubgraphAlgorithmTest {

    private BlackBoard bbWith(GraphModel g) {
        BlackBoard bb = new BlackBoard();
        bb.setData(GraphAttrSet.name, g);
        return bb;
    }

    @Test
    public void testVertexInducedSubgraphFromK4TwoVertices() {
        // K_4 with vertices {0,1,2,3}: induce on {0,1} → K_2 (1 edge)
        GraphModel k4 = CompleteGraphGenerator.generateCompleteGraph(4);
        List<Vertex> subset = new ArrayList<>();
        subset.add(k4.getVertex(0));
        subset.add(k4.getVertex(1));
        GraphModel sub = InducedSubgraphs.getVertexInducedSubgraph(k4, (ArrayList<Vertex>) subset);
        assertEquals(2, sub.getVerticesCount());
        assertEquals(1, sub.getEdgesCount());
    }

    @Test
    public void testVertexInducedSubgraphFromK4ThreeVertices() {
        // Induce K_4 on {0,1,2} → K_3 (3 edges)
        GraphModel k4 = CompleteGraphGenerator.generateCompleteGraph(4);
        ArrayList<Vertex> subset = new ArrayList<>();
        subset.add(k4.getVertex(0));
        subset.add(k4.getVertex(1));
        subset.add(k4.getVertex(2));
        GraphModel sub = InducedSubgraphs.getVertexInducedSubgraph(k4, subset);
        assertEquals(3, sub.getVerticesCount());
        assertEquals(3, sub.getEdgesCount());
    }

    @Test
    public void testVertexInducedSubgraphOnPath() {
        // P_4 (0-1-2-3): induce on {0,1,2} → P_3 (2 edges)
        GraphModel p4 = PathGenerator.generatePath(4);
        ArrayList<Vertex> subset = new ArrayList<>();
        subset.add(p4.getVertex(0));
        subset.add(p4.getVertex(1));
        subset.add(p4.getVertex(2));
        GraphModel sub = InducedSubgraphs.getVertexInducedSubgraph(p4, subset);
        assertEquals(3, sub.getVerticesCount());
        assertEquals(2, sub.getEdgesCount());
    }

    @Test
    public void testVertexInducedSubgraphSingleVertex() {
        GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
        ArrayList<Vertex> subset = new ArrayList<>();
        subset.add(k3.getVertex(0));
        GraphModel sub = InducedSubgraphs.getVertexInducedSubgraph(k3, subset);
        assertEquals(1, sub.getVerticesCount());
        assertEquals(0, sub.getEdgesCount());
    }

    @Test
    public void testEdgeInducedSubgraphOnePath() {
        // P_3 (0-1-2): induce on edge {0-1} → 2 vertices, 1 edge
        GraphModel p3 = PathGenerator.generatePath(3);
        ArrayList<Edge> subset = new ArrayList<>(p3.getEdges(p3.getVertex(0), p3.getVertex(1)));
        GraphModel sub = InducedSubgraphs.getEdgeInducedSubgraph(p3, subset);
        assertEquals(2, sub.getVerticesCount());
        assertEquals(1, sub.getEdgesCount());
    }

    @Test
    public void testEdgeInducedSubgraphTwoEdgesPath() {
        // P_3 (0-1-2): induce on both edges → 3 vertices, 2 edges = P_3 itself
        GraphModel p3 = PathGenerator.generatePath(3);
        ArrayList<Edge> allEdges = new ArrayList<Edge>();
        for (Edge e : p3.getEdges()) allEdges.add(e);
        GraphModel sub = InducedSubgraphs.getEdgeInducedSubgraph(p3, allEdges);
        assertEquals(3, sub.getVerticesCount());
        assertEquals(2, sub.getEdgesCount());
    }

    @Test
    public void testEdgeInducedSubgraphFromK3OneEdge() {
        // K_3: any single edge gives 2 vertices, 1 edge
        GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
        ArrayList<Edge> oneEdge = new ArrayList<>();
        oneEdge.add(k3.getEdges().iterator().next());
        GraphModel sub = InducedSubgraphs.getEdgeInducedSubgraph(k3, oneEdge);
        assertEquals(2, sub.getVerticesCount());
        assertEquals(1, sub.getEdgesCount());
    }

    @Test
    public void testGreedyColoringK3UsesTHREEColors() {
        // K_3 is 3-chromatic; greedy (in any order) must use ≥ 3 colors
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        new GreedyColoring(bbWith(g)).doAlgorithm();
        int maxColor = 0;
        for (Vertex v : g) {
            maxColor = Math.max(maxColor, v.getColor());
        }
        assertEquals(3, maxColor, "Greedy coloring of K_3 must use exactly 3 colors");
    }

    @Test
    public void testGreedyColoringPathNeedsTwoColors() {
        // P_4 is bipartite; greedy colors it with 2 colors
        GraphModel g = PathGenerator.generatePath(4);
        new GreedyColoring(bbWith(g)).doAlgorithm();
        int maxColor = 0;
        for (Vertex v : g) {
            maxColor = Math.max(maxColor, v.getColor());
        }
        assertEquals(2, maxColor, "Greedy coloring of P_4 must use exactly 2 colors");
    }

    @Test
    public void testGreedyColoringAllVerticesColored() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(4);
        new GreedyColoring(bbWith(g)).doAlgorithm();
        for (Vertex v : g) {
            assertTrue(v.getColor() > 0, "Every vertex must be assigned a non-zero color");
        }
    }

    @Test
    public void testKruskalRunsOnK3() {
        // K_3 with unit weights: MST has 2 edges (any spanning tree of K_3)
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex(), v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v0); g.insertVertex(v1); g.insertVertex(v2);
        Edge e01 = new Edge(v0, v1, new BaseEdgeProperties(0, 1, false));
        Edge e12 = new Edge(v1, v2, new BaseEdgeProperties(0, 2, false));
        Edge e02 = new Edge(v0, v2, new BaseEdgeProperties(0, 3, false));
        g.insertEdge(e01); g.insertEdge(e12); g.insertEdge(e02);

        new KruskalAlgorithm(bbWith(g)).doAlgorithm();

        long markedEdges = 0;
        for (Edge e : g.getEdges()) { if (e.getMark()) markedEdges++; }
        assertEquals(2, markedEdges, "Kruskal MST of K_3 must mark exactly 2 edges");
    }

    @Test
    public void testKruskalPicksLightestEdges() {
        // Path 0-1-2 with weights 1, 10: MST is the path itself; both edges marked
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex(), v1 = new Vertex(), v2 = new Vertex();
        g.insertVertex(v0); g.insertVertex(v1); g.insertVertex(v2);
        Edge light = new Edge(v0, v1, new BaseEdgeProperties(0, 1, false));
        Edge heavy = new Edge(v1, v2, new BaseEdgeProperties(0, 10, false));
        g.insertEdge(light); g.insertEdge(heavy);

        new KruskalAlgorithm(bbWith(g)).doAlgorithm();

        assertTrue(light.getMark(), "Light edge must be in MST");
        assertTrue(heavy.getMark(), "Heavy edge must be in MST (only spanning tree)");
    }

}
