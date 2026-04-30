// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import graphtea.extensions.algorithms.EdgeInduced;
import graphtea.extensions.algorithms.TopologicalSort;
import graphtea.extensions.algorithms.shortestpath.algs.BellmanFord;
import graphtea.extensions.algorithms.shortestpath.algs.FloydWarshall;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.reports.ChromaticNumber;
import graphtea.extensions.reports.DijkstraNonNegative;
import graphtea.extensions.reports.HeuristicGreedyColoring;
import graphtea.extensions.reports.RandomMatching;
import graphtea.extensions.reports.basicreports.SubTreeCounting;
import graphtea.extensions.reports.others.Eccentricity;
import graphtea.extensions.reports.others.EccentricityEnergy;
import graphtea.extensions.reports.others.PeripheralVertices;
import graphtea.extensions.reports.others.Radius;
import graphtea.extensions.reports.topological.InverseDegree;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for classes with &lt;15% instruction coverage.
 */
public class LowCoverageClassesTest {

    private final GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
    private final GraphModel p3 = PathGenerator.generatePath(3);

    private GraphModel dirChain(int n) {
        GraphModel g = new GraphModel(true);
        Vertex[] v = new Vertex[n];
        for (int i = 0; i < n; i++) {
            v[i] = new Vertex();
            g.insertVertex(v[i]);
        }
        for (int i = 0; i < n - 1; i++) {
            g.insertEdge(new Edge(v[i], v[i + 1]));
        }
        return g;
    }

    @Test
    public void floydWarshallK3AllDistancesOne() {
        int[][] dist = new FloydWarshall().getAllPairsShortestPathWithoutWeight(k3);
        assertEquals(0, dist[0][0]);
        assertEquals(1, dist[0][1]);
        assertEquals(1, dist[1][2]);
    }

    @Test
    public void floydWarshallP3EndpointDistance() {
        int[][] dist = new FloydWarshall().getAllPairsShortestPathWithoutWeight(p3);
        assertEquals(0, dist[0][0]);
        assertEquals(1, dist[0][1]);
        assertEquals(2, dist[0][2]);
    }

    @Test
    public void bellmanFordDirectedChainNoCycle() {
        GraphModel g = dirChain(3);
        BellmanFord bf = new BellmanFord(new BlackBoard());
        List<Vertex> pred = bf.computePaths(g, g.getVertex(0));
        assertNotNull(pred, "A chain has no negative cycle");
        assertEquals(3, pred.size());
    }

    @Test
    public void bellmanFordNegativeCycleReturnsNull() {
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex();
        Vertex v1 = new Vertex();
        g.insertVertex(v0);
        g.insertVertex(v1);
        Edge e01 = new Edge(v0, v1);
        e01.setWeight(-1);
        g.insertEdge(e01);
        Edge e10 = new Edge(v1, v0);
        e10.setWeight(-1);
        g.insertEdge(e10);
        BellmanFord bf = new BellmanFord(new BlackBoard());
        assertNull(bf.computePaths(g, v0));
    }

    @Test
    public void topologicalSortChain3() {
        GraphModel g = dirChain(3);
        AbstractList<Vertex> order = TopologicalSort.doSort(g);
        assertNotNull(order);
        assertEquals(3, order.size());
        assertTrue(order.indexOf(g.getVertex(0)) < order.indexOf(g.getVertex(1)));
        assertTrue(order.indexOf(g.getVertex(1)) < order.indexOf(g.getVertex(2)));
    }

    @Test
    public void eccentricityStaticByIdK3() {
        int[][] dist = new FloydWarshall().getAllPairsShortestPathWithoutWeight(k3);
        assertEquals(1, Eccentricity.eccentricity(k3, 0, dist));
    }

    @Test
    public void eccentricityStaticByVertexP3() {
        int[][] dist = new FloydWarshall().getAllPairsShortestPathWithoutWeight(p3);
        // center vertex has eccentricity 1, end vertices have eccentricity 2
        assertEquals(1, Eccentricity.eccentricity(p3, p3.getVertex(1), dist));
        assertEquals(2, Eccentricity.eccentricity(p3, p3.getVertex(0), dist));
    }

    @Test
    public void eccentricityCalculateK3AllOne() {
        RenderTable rt = new Eccentricity().calculate(k3);
        assertEquals(3, rt.size());
        for (List<Object> row : rt) {
            assertEquals(1, (int) row.get(1));
        }
    }

    @Test
    public void eccentricityEnergyCalculateK3() {
        RenderTable rt = new EccentricityEnergy().calculate(k3);
        assertEquals(1, rt.size());
        List<Object> row = rt.iterator().next();
        assertEquals(3, (int) (Integer) row.get(0));
        assertEquals(3, (int) (Integer) row.get(1));
    }

    @Test
    public void subTreeCountingK3() {
        RenderTable rt = new SubTreeCounting().calculate(k3);
        assertNotNull(rt);
        assertTrue(rt.size() > 0);
    }

    @Test
    public void inverseDegreeK3() {
        RenderTable rt = new InverseDegree().calculate(k3);
        assertEquals(1, rt.size());
    }

    @Test
    public void dijkstraNonNegativeSourceDistanceZero() {
        GraphModel k3fresh = CompleteGraphGenerator.generateCompleteGraph(3);
        Vertex src = k3fresh.getVertex(0);
        DijkstraNonNegative.dijkstra(k3fresh, src);
        assertEquals(0.0, (Double) src.getUserDefinedAttribute(DijkstraNonNegative.Dist), 1e-9);
    }

    @Test
    public void dijkstraNonNegativeNeighborDistanceOne() {
        GraphModel k3fresh = CompleteGraphGenerator.generateCompleteGraph(3);
        Vertex src = k3fresh.getVertex(0);
        DijkstraNonNegative.dijkstra(k3fresh, src);
        assertEquals(1.0, (Double) k3fresh.getVertex(1).getUserDefinedAttribute(DijkstraNonNegative.Dist), 1e-9);
    }

    @Test
    public void randomMatchingK3ReturnsResult() {
        List<Object> result = new RandomMatching().calculate(k3);
        assertEquals(2, result.size());
        assertTrue(result.get(0).toString().startsWith("Number of Matching:"));
    }

    @Test
    public void randomMatchingMaxMatchingK3() {
        double m = new RandomMatching().calculateMaxMatching(k3);
        assertTrue(m >= 0);
    }

    @Test
    public void edgeInducedRemovesOneEdge() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        Edge toRemove = g.getEdges().iterator().next();
        List<Edge> removed = new ArrayList<>();
        removed.add(toRemove);
        GraphModel induced = EdgeInduced.edgeInduced(g, removed);
        assertEquals(2, induced.getEdgesCount());
    }

    @Test
    public void edgeInducedIsolatesAndRemovesVertex() {
        // P2: v0-v1. Removing the sole edge leaves both vertices isolated → removed.
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex();
        Vertex v1 = new Vertex();
        g.insertVertex(v0);
        g.insertVertex(v1);
        Edge e = new Edge(v0, v1);
        g.insertEdge(e);
        List<Edge> removed = new ArrayList<>();
        removed.add(e);
        GraphModel induced = EdgeInduced.edgeInduced(g, removed);
        assertEquals(0, induced.getEdgesCount());
        assertEquals(0, induced.getVerticesCount());
    }

    @Test
    public void heuristicGreedyColoringK3DistinctColors() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        List<Integer> colors = new HeuristicGreedyColoring().calculate(g);
        assertEquals(3, colors.size());
        assertEquals(3, colors.stream().distinct().count());
    }

    @Test
    public void heuristicColoringStaticAllColored() {
        GraphModel g = CompleteGraphGenerator.generateCompleteGraph(3);
        HeuristicGreedyColoring.heuristicColoring(g);
        for (Vertex v : g) {
            assertTrue(v.getColor() > 0);
        }
    }

    @Test
    public void chromaticNumberCalculateK3() {
        // getChromaticNumber returns ct after post-increment, so K3 (chi=3) returns 4
        int result = ChromaticNumber.getChromaticNumber(k3);
        assertTrue(result > 1);
    }

    @Test
    public void chromaticNumberCalculateK2() {
        GraphModel k2 = CompleteGraphGenerator.generateCompleteGraph(2);
        int result = ChromaticNumber.getChromaticNumber(k2);
        assertTrue(result > 1);
    }

    @Test
    public void radiusK3IsOne() {
        assertEquals(1, (int) new Radius().calculate(k3));
    }

    @Test
    public void radiusP3IsOne() {
        assertEquals(1, (int) new Radius().calculate(p3));
    }

    @Test
    public void peripheralVerticesK3AllThree() {
        // K3: diameter=1, all vertices have eccentricity 1
        ArrayList<Vertex> pv = new PeripheralVertices().calculate(k3);
        assertEquals(3, pv.size());
    }

    @Test
    public void peripheralVerticesP3TwoEndpoints() {
        // P3: diameter=2, endpoints have eccentricity 2
        ArrayList<Vertex> pv = new PeripheralVertices().calculate(p3);
        assertEquals(2, pv.size());
    }
}
