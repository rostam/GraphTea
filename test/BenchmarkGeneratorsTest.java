// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import graphtea.extensions.generators.*;
import graphtea.extensions.reports.basicreports.Diameter;
import graphtea.extensions.reports.basicreports.GirthSize;
import graphtea.extensions.reports.basicreports.MaxAndMinDegree;
import graphtea.graph.graph.GraphModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for benchmark/named graph generators that previously had 0% coverage.
 *
 * Known properties used as expected values are sourced from published graph
 * theory literature (vertex/edge counts, regularity, girth, diameter).
 */
public class BenchmarkGeneratorsTest {

    @Test
    public void testGrotzschGraphVertexCount() {
        GraphModel g = new GrotzschGraph().generateGraph();
        assertEquals(11, g.getVerticesCount());
    }

    @Test
    public void testGrotzschGraphEdgeCount() {
        GraphModel g = new GrotzschGraph().generateGraph();
        assertEquals(20, g.getEdgesCount());
    }

    @Test
    public void testGrotzschGraphIsTriangleFree() {
        // The Grotzsch graph is triangle-free: girth = 4
        GraphModel g = new GrotzschGraph().generateGraph();
        assertEquals(4, new GirthSize().calculate(g));
    }

    @Test
    public void testHeawoodGraphVertexCount() {
        GraphModel g = new HeawoodGraph().generateGraph();
        assertEquals(14, g.getVerticesCount());
    }

    @Test
    public void testHeawoodGraphEdgeCount() {
        GraphModel g = new HeawoodGraph().generateGraph();
        assertEquals(21, g.getEdgesCount());
    }

    @Test
    public void testHeawoodGraphIsRegular() {
        // Heawood graph is 3-regular
        GraphModel g = new HeawoodGraph().generateGraph();
        ArrayList<Integer> deg = new MaxAndMinDegree().calculate(g);
        assertEquals(3, (int) deg.get(0), "max degree");
        assertEquals(3, (int) deg.get(1), "min degree");
    }

    @Test
    public void testHeawoodGraphGirth() {
        // Heawood graph is the unique (3,6)-cage: girth = 6
        GraphModel g = new HeawoodGraph().generateGraph();
        assertEquals(6, new GirthSize().calculate(g));
    }

    @Test
    public void testHeawoodGraphDiameter() {
        GraphModel g = new HeawoodGraph().generateGraph();
        assertEquals(3, new Diameter().calculate(g));
    }

    @Test
    public void testPappusGraphVertexCount() {
        GraphModel g = new PappusGraph().generateGraph();
        assertEquals(18, g.getVerticesCount());
    }

    @Test
    public void testPappusGraphEdgeCount() {
        GraphModel g = new PappusGraph().generateGraph();
        assertEquals(27, g.getEdgesCount());
    }

    @Test
    public void testPappusGraphIsRegular() {
        // Pappus graph is 3-regular
        GraphModel g = new PappusGraph().generateGraph();
        ArrayList<Integer> deg = new MaxAndMinDegree().calculate(g);
        assertEquals(3, (int) deg.get(0), "max degree");
        assertEquals(3, (int) deg.get(1), "min degree");
    }

    @Test
    public void testPappusGraphGirth() {
        // Pappus graph has girth 6
        GraphModel g = new PappusGraph().generateGraph();
        assertEquals(6, new GirthSize().calculate(g));
    }

    @Test
    public void testFruchtGraphVertexCount() {
        GraphModel g = new FruchtGraph().generateGraph();
        assertEquals(12, g.getVerticesCount());
    }

    @Test
    public void testFruchtGraphEdgeCount() {
        GraphModel g = new FruchtGraph().generateGraph();
        assertEquals(18, g.getEdgesCount());
    }

    @Test
    public void testFruchtGraphIsRegular() {
        // Frucht graph is 3-regular
        GraphModel g = new FruchtGraph().generateGraph();
        ArrayList<Integer> deg = new MaxAndMinDegree().calculate(g);
        assertEquals(3, (int) deg.get(0), "max degree");
        assertEquals(3, (int) deg.get(1), "min degree");
    }

    @Test
    public void testZacharyKarateClubVertexCount() {
        GraphModel g = new ZacharyKarateClub().generateGraph();
        assertEquals(34, g.getVerticesCount());
    }

    @Test
    public void testZacharyKarateClubEdgeCount() {
        GraphModel g = new ZacharyKarateClub().generateGraph();
        assertEquals(78, g.getEdgesCount());
    }

    @Test
    public void testBarabasiAlbertVertexCount() {
        BarabasiAlbertGenerator.n = 10;
        BarabasiAlbertGenerator.m = 2;
        GraphModel g = new BarabasiAlbertGenerator().generateGraph();
        assertEquals(10, g.getVerticesCount());
    }

    @Test
    public void testBarabasiAlbertEdgeCount() {
        // Seed K_m contributes C(m,2) edges; each of (n-m) new vertices adds exactly m edges.
        // For m=2, n=10: C(2,2) + (10-2)*2 = 1 + 16 = 17
        BarabasiAlbertGenerator.n = 10;
        BarabasiAlbertGenerator.m = 2;
        GraphModel g = new BarabasiAlbertGenerator().generateGraph();
        assertEquals(17, g.getEdgesCount());
    }

    @Test
    public void testBarabasiAlbertM3EdgeCount() {
        // m=3, n=12: C(3,2) + (12-3)*3 = 3 + 27 = 30
        BarabasiAlbertGenerator.n = 12;
        BarabasiAlbertGenerator.m = 3;
        GraphModel g = new BarabasiAlbertGenerator().generateGraph();
        assertEquals(30, g.getEdgesCount());
    }

    @Test
    public void testWattsStrogatzRegularLattice() {
        // beta=0 → no rewiring, stays a ring lattice with n*k/2 edges
        WattsStrogatzGenerator.n = 10;
        WattsStrogatzGenerator.k = 4;
        WattsStrogatzGenerator.betaPercent = 0;
        GraphModel g = new WattsStrogatzGenerator().generateGraph();
        assertEquals(10, g.getVerticesCount());
        assertEquals(20, g.getEdgesCount());   // 10 * 4/2 = 20
    }

    @Test
    public void testWattsStrogatzFullRewiring() {
        // beta=100 → all edges rewired; vertex count must still be n
        WattsStrogatzGenerator.n = 10;
        WattsStrogatzGenerator.k = 4;
        WattsStrogatzGenerator.betaPercent = 100;
        GraphModel g = new WattsStrogatzGenerator().generateGraph();
        assertEquals(10, g.getVerticesCount());
        // Edge count may vary due to random rewiring but must be ≤ n*k/2
        assertTrue(g.getEdgesCount() <= 20);
    }

    @Test
    public void testRandomGeneratorVertexCount() {
        RandomGenerator.n = 8;
        RandomGenerator rg = new RandomGenerator();
        GraphModel g = rg.generateGraph();
        assertEquals(8, g.getVerticesCount());
    }

    @Test
    public void testRandomTreeGeneratorVertexAndEdgeCount() {
        RandomTreeGenerator.n = 7;
        RandomTreeGenerator.h = 10;
        RandomTreeGenerator.d = 5;
        GraphModel g = new RandomTreeGenerator().generateGraph();
        assertEquals(7, g.getVerticesCount());
        assertEquals(6, g.getEdgesCount(), "A tree on n vertices has exactly n-1 edges");
    }

    @Test
    public void testNNGeneratorVertexCount() {
        NNGenerator.hidden = 2;
        NNGenerator.hiddenSize = 2;
        GraphModel g = new NNGenerator().generateGraph();
        assertEquals(6, g.getVerticesCount(), "hidden*hiddenSize + 2 = 2*2+2 = 6");
    }

    @Test
    public void testExampleChainGraph1VertexCount() {
        ExampleChainGraph1.n = 3;
        GraphModel g = new ExampleChainGraph1().generateGraph();
        assertEquals(6, g.getVerticesCount(), "ChainGraph1 n=3: 2*n = 6 vertices");
    }

    @Test
    public void testExampleChainGraph1EdgeCount() {
        ExampleChainGraph1.n = 3;
        GraphModel g = new ExampleChainGraph1().generateGraph();
        assertEquals(5, g.getEdgesCount(), "ChainGraph1 n=3: 2*n-1 = 5 edges");
    }

    @Test
    public void testExampleChainGraph2NotNull() {
        ExampleChainGraph2.n = 3;
        GraphModel g = new ExampleChainGraph2().generateGraph();
        assertNotNull(g);
        assertTrue(g.getVerticesCount() > 0);
    }

    @Test
    public void testExampleChainGraph4NotNull() {
        ExampleChainGraph4.n = 3;
        GraphModel g = new ExampleChainGraph4().generateGraph();
        assertNotNull(g);
        assertTrue(g.getVerticesCount() > 0);
    }

    @Test
    public void testExampleChainGraph5NotNull() {
        ExampleChainGraph5.n = 3;
        GraphModel g = new ExampleChainGraph5().generateGraph();
        assertNotNull(g);
        assertTrue(g.getVerticesCount() > 0);
    }

    @Test
    public void testExampleChainGraph6NotNull() {
        ExampleChainGraph6.n = 3;
        GraphModel g = new ExampleChainGraph6().generateGraph();
        assertNotNull(g);
        assertTrue(g.getVerticesCount() > 0);
    }

    @Test
    public void testExampleChainGraph7NotNull() {
        ExampleChainGraph7.n = 3;
        GraphModel g = new ExampleChainGraph7().generateGraph();
        assertNotNull(g);
        assertTrue(g.getVerticesCount() > 0);
    }
}
