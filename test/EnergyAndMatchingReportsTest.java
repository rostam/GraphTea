// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.reports.energy.AllEnergies;
import graphtea.extensions.reports.energy.AllEnergies1;
import graphtea.extensions.reports.energy.Cograph;
import graphtea.extensions.reports.energy.Complement;
import graphtea.extensions.reports.energy.Conjecture;
import graphtea.extensions.reports.energy.KF_Wiener;
import graphtea.extensions.reports.energy.LaplacianEstrada;
import graphtea.extensions.reports.energy.LEL_vs_KF;
import graphtea.extensions.reports.energy.Linear;
import graphtea.extensions.reports.energy.MixSignlessLaplacianEnergy;
import graphtea.extensions.reports.energy.NewLowerBounds;
import graphtea.extensions.reports.energy.NormalizedLaplacianResolventEnergy;
import graphtea.extensions.reports.energy.ResolventEnergies;
import graphtea.extensions.reports.energy.ResolventEnergy;
import graphtea.extensions.reports.energy.ResolventLaplacianEnergy;
import graphtea.extensions.reports.energy.ResolventSignlessLaplacianEnergy;
import graphtea.extensions.reports.energy.SignlessLaplacianEstrada;
import graphtea.extensions.reports.energy.UpperBounds;
import graphtea.extensions.reports.matching.MaxMatchingExtension;
import graphtea.graph.graph.GraphModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for energy-package and matching-package report classes that had
 * near-zero or zero coverage. All tests use K_3 (complete graph, 3 vertices).
 */
public class EnergyAndMatchingReportsTest {

    private final GraphModel k3 = CompleteGraphGenerator.generateCompleteGraph(3);
    private final GraphModel p3 = PathGenerator.generatePath(3);

    // -----------------------------------------------------------------------
    // energy.AllEnergies / AllEnergies1
    // -----------------------------------------------------------------------

    @Test
    public void testAllEnergiesK3() {
        assertNotNull(new AllEnergies().calculate(k3));
    }

    @Test
    public void testAllEnergies1K3() {
        assertNotNull(new AllEnergies1().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // energy.Cograph
    // -----------------------------------------------------------------------

    @Test
    public void testCographK3() {
        assertNotNull(new Cograph().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // energy.Complement
    // -----------------------------------------------------------------------

    @Test
    public void testComplementK3() {
        assertNotNull(new Complement().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // energy.Conjecture
    // -----------------------------------------------------------------------

    @Test
    public void testConjectureK3() {
        assertNotNull(new Conjecture().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // energy.KF_Wiener
    // -----------------------------------------------------------------------

    @Test
    public void testKFWienerK3() {
        assertNotNull(new KF_Wiener().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // energy.LaplacianEstrada
    // -----------------------------------------------------------------------

    @Test
    public void testLaplacianEstradaK3() {
        assertNotNull(new LaplacianEstrada().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // energy.LEL_vs_KF
    // -----------------------------------------------------------------------

    @Test
    public void testLELvsKFK3() {
        assertNotNull(new LEL_vs_KF().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // energy.Linear
    // -----------------------------------------------------------------------

    @Test
    public void testLinearK3() {
        assertNotNull(new Linear().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // energy.MixSignlessLaplacianEnergy
    // -----------------------------------------------------------------------

    @Test
    public void testMixSignlessLaplacianEnergyK3() {
        List<String> result = new MixSignlessLaplacianEnergy().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // -----------------------------------------------------------------------
    // energy.NewLowerBounds
    // -----------------------------------------------------------------------

    @Test
    public void testNewLowerBoundsK3() {
        assertNotNull(new NewLowerBounds().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // energy.NormalizedLaplacianResolventEnergy
    // -----------------------------------------------------------------------

    @Test
    public void testNormalizedLaplacianResolventEnergyK3() {
        String result = new NormalizedLaplacianResolventEnergy().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    // -----------------------------------------------------------------------
    // energy.ResolventEnergies
    // -----------------------------------------------------------------------

    @Test
    public void testResolventEnergiesK3() {
        assertNotNull(new ResolventEnergies().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // energy.ResolventEnergy
    // -----------------------------------------------------------------------

    @Test
    public void testResolventEnergyK3() {
        String result = new ResolventEnergy().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    // -----------------------------------------------------------------------
    // energy.ResolventLaplacianEnergy
    // -----------------------------------------------------------------------

    @Test
    public void testResolventLaplacianEnergyK3() {
        String result = new ResolventLaplacianEnergy().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    // -----------------------------------------------------------------------
    // energy.ResolventSignlessLaplacianEnergy
    // -----------------------------------------------------------------------

    @Test
    public void testResolventSignlessLaplacianEnergyK3() {
        String result = new ResolventSignlessLaplacianEnergy().calculate(k3);
        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    // -----------------------------------------------------------------------
    // energy.SignlessLaplacianEstrada
    // -----------------------------------------------------------------------

    @Test
    public void testSignlessLaplacianEstradaK3() {
        assertNotNull(new SignlessLaplacianEstrada().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // energy.UpperBounds
    // -----------------------------------------------------------------------

    @Test
    public void testUpperBoundsK3() {
        assertNotNull(new UpperBounds().calculate(k3));
    }

    // -----------------------------------------------------------------------
    // matching.MaxMatchingExtension
    // -----------------------------------------------------------------------

    @Test
    public void testMaxMatchingK3() {
        List<Object> result = new MaxMatchingExtension().calculate(k3);
        assertNotNull(result);
    }

    @Test
    public void testMaxMatchingP3() {
        // P3 (0-1-2): maximum matching has 1 edge.
        List<Object> result = new MaxMatchingExtension().calculate(p3);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
