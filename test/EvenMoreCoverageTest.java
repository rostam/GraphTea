// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.io.LatexWriter;
import graphtea.extensions.reports.basicreports.SpectraofAvgTransmissionMatrix;
import graphtea.extensions.reports.connectivity.KConnected;
import graphtea.extensions.reports.others.AllEccen;
import graphtea.extensions.reports.others.DistLaplacian;
import graphtea.extensions.reports.others.DistanceEnergyCompare;
import graphtea.extensions.reports.others.DistanceEnergyCompare1;
import graphtea.extensions.reports.others.Hosoya;
import graphtea.extensions.reports.others.MaxDegreeEnergyCompare;
import graphtea.extensions.reports.spectralreports.maxflowmincut.MinCut;
import graphtea.extensions.reports.spectralreports.maxflowmincut.PushRelabel;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the next batch of low-coverage classes: DistLaplacian,
 * MaxDegreeEnergyCompare, DistanceEnergyCompare(1), Hosoya, AllEccen,
 * KConnected, SpectraofAvgTransmissionMatrix, PushRelabel, MinCut, LatexWriter.
 */
public class EvenMoreCoverageTest {

    private final GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);

    @Test
    public void distLaplacianK3ReturnsResult() {
        RenderTable rt = new DistLaplacian().calculate(k3);
        assertNotNull(rt);
        assertFalse(rt.isEmpty());
    }

    @Test
    public void maxDegreeEnergyCompareK3() {
        RenderTable rt = new MaxDegreeEnergyCompare().calculate(k3);
        assertNotNull(rt);
        assertFalse(rt.isEmpty());
    }

    @Test
    public void distanceEnergyCompare1K3() {
        RenderTable rt = new DistanceEnergyCompare1().calculate(k3);
        assertNotNull(rt);
        assertFalse(rt.isEmpty());
    }

    @Test
    public void distanceEnergyCompareK3EqualEnergies() {
        // For K3, DLE == DLSE (both = 6), so calculate returns a non-null table
        RenderTable rt = new DistanceEnergyCompare().calculate(k3);
        assertNotNull(rt);
    }

    @Test
    public void hosoyaK3ReturnsResult() {
        RenderTable rt = new Hosoya().calculate(k3);
        assertNotNull(rt);
        assertFalse(rt.isEmpty());
    }

    @Test
    public void allEccenK3ReturnsResult() {
        // K3 has 3 edges (not 10), so calculate proceeds normally
        RenderTable rt = new AllEccen().calculate(k3);
        assertNotNull(rt);
        assertFalse(rt.isEmpty());
    }

    @Test
    public void allEccenTenEdgesReturnsNull() {
        // K5 has exactly 10 edges — the early-return guard returns null
        GraphModel k5 = CompleteGraphGenerator.generateCompleteGraph(5);
        assertNull(new AllEccen().calculate(k5));
    }

    @Test
    public void kConnectedK3IsPositive() {
        int k = KConnected.kconn(k3);
        assertTrue(k > 0, "K3 should have positive vertex connectivity");
    }

    @Test
    public void kDisconnZeroRemovalsNull() {
        // Removing 0 vertices cannot disconnect any connected graph
        assertNull(KConnected.kdisconn(k3, 0));
    }

    @Test
    public void spectraAvgTransmissionK3() {
        ArrayList<String> res = new SpectraofAvgTransmissionMatrix().calculate(k3);
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertEquals("Spectra", res.get(0));
    }

    private GraphModel flowChain() {
        // Directed v0 --(5)--> v1 --(3)--> v2; max flow from v0 to v2 = 3
        GraphModel g = new GraphModel(true);
        Vertex v0 = new Vertex();
        Vertex v1 = new Vertex();
        Vertex v2 = new Vertex();
        g.insertVertex(v0);
        g.insertVertex(v1);
        g.insertVertex(v2);
        Edge e01 = new Edge(v0, v1);
        e01.setWeight(5);
        g.insertEdge(e01);
        Edge e12 = new Edge(v1, v2);
        e12.setWeight(3);
        g.insertEdge(e12);
        return g;
    }

    @Test
    public void pushRelabelMaxFlowIsThree() {
        GraphModel g = flowChain();
        Vertex src = g.getVertex(0);
        Vertex snk = g.getVertex(2);
        int flow = new PushRelabel(g, src, snk, false).perform();
        assertEquals(3, flow);
    }

    @Test
    public void minCutMaxFlowIsThree() {
        GraphModel g = flowChain();
        Vertex src = g.getVertex(0);
        Vertex snk = g.getVertex(2);
        int flow = new MinCut(g, src, snk, false).perform();
        assertEquals(3, flow);
    }

    @Test
    public void latexWriterProducesNonEmptyFile(@TempDir Path tmp) throws Exception {
        File f = tmp.resolve("graph.tex").toFile();
        new LatexWriter().write(f, k3);
        assertTrue(f.exists(), "LatexWriter must create the output file");
        assertTrue(f.length() > 0, "Output file must not be empty");
    }
}
