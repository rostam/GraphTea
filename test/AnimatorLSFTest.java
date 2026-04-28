// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.localsfvis;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Local Spring Force layout algorithm.
 *
 * The algorithm combines:
 *   - Coulomb-style repulsion between nearby vertices: force ∝ q1q2k / d² (pushes apart)
 *   - Hooke-style attraction along edges: force ∝ springK * d (pulls together)
 *
 * Implementation note: forces are truncated to int and divided by e=5 before being
 * applied, so vertices must be close enough that the resulting int force is ≥ 5.
 * At Manhattan distance D the force magnitude is 50000/D², which must be ≥ 5,
 * i.e. D ≤ 100.  Tests use D ≈ 50 to stay safely in this range.
 */
public class AnimatorLSFTest {

    /** Creates a headless animator — gv and blackboard are only needed in run(). */
    private animatorLSF makeAnimator(GraphModel g) {
        return new animatorLSF(null, g, null);
    }

    // -----------------------------------------------------------------------
    // Force direction tests
    // -----------------------------------------------------------------------

    @Test
    public void testRepulsionPushesNonConnectedVerticesApart() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex();
        Vertex v1 = new Vertex();
        // Distance 50 — gives force = 50000/50² = 20; displacement = 20/5 = 4 per step.
        GPoint p0 = new GPoint(50, 0);
        GPoint p1 = new GPoint(100, 0);
        v0.setLocation(p0);
        v1.setLocation(p1);
        g.insertVertex(v0);
        g.insertVertex(v1);

        animatorLSF a = makeAnimator(g);
        a.refreshNeighbors();
        a.refreshPositioning();

        assertTrue(p0.x < 50, "v0 should move left (repulsion), got x=" + p0.x);
        assertTrue(p1.x > 100, "v1 should move right (repulsion), got x=" + p1.x);
    }

    @Test
    public void testSpringForceAttractsConnectedVertices() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex();
        Vertex v1 = new Vertex();
        GPoint p0 = new GPoint(0, 100);
        GPoint p1 = new GPoint(1000, 100);
        v0.setLocation(p0);
        v1.setLocation(p1);
        g.insertVertex(v0);
        g.insertVertex(v1);
        g.insertEdge(new Edge(v0, v1));

        animatorLSF a = makeAnimator(g);
        a.refreshNeighbors();
        a.refreshPositioning();

        // At distance 1000 the spring dominates: v0 moves right, v1 moves left
        assertTrue(p0.x > 0, "v0 should move toward v1, got x=" + p0.x);
        assertTrue(p1.x < 1000, "v1 should move toward v0, got x=" + p1.x);
    }

    @Test
    public void testRepulsionDirectionIsAlongDisplacementVector() {
        // Verify that two isolated vertices repel along the line connecting them.
        // Place them on the x-axis: no y-force should develop.
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex();
        Vertex v1 = new Vertex();
        GPoint p0 = new GPoint(50, 200);
        GPoint p1 = new GPoint(100, 200);
        v0.setLocation(p0);
        v1.setLocation(p1);
        g.insertVertex(v0);
        g.insertVertex(v1);

        animatorLSF a = makeAnimator(g);
        a.refreshNeighbors();
        a.refreshPositioning();

        assertEquals(200.0, p0.y, 1e-6, "y-coordinate of v0 should not change");
        assertEquals(200.0, p1.y, 1e-6, "y-coordinate of v1 should not change");
    }

    // -----------------------------------------------------------------------
    // Bug regression tests
    // -----------------------------------------------------------------------

    /**
     * Bug fixed: when two vertices were at the same location the x/y jitter
     * components were swapped, so the intended push-vector was rotated 90°.
     * The fix: verPos[j].x -= x and verPos[j].y -= y (not crossed).
     * After the fix the two vertices must end up separated.
     */
    @Test
    public void testColocationJitterCreatesSeparation() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex();
        Vertex v1 = new Vertex();
        GPoint p0 = new GPoint(300, 300);
        GPoint p1 = new GPoint(300, 300);   // intentionally identical
        v0.setLocation(p0);
        v1.setLocation(p1);
        g.insertVertex(v0);
        g.insertVertex(v1);
        g.insertEdge(new Edge(v0, v1));

        animatorLSF a = makeAnimator(g);
        a.refreshNeighbors();
        a.refreshPositioning();

        // refreshNeighbors jitters by up to 5 px per axis; threshold > 0 is reliable
        double dist = p0.distance(p1);
        assertTrue(dist > 0.1,
                "Co-located vertices should be pushed apart; distance=" + dist);
    }

    /**
     * Bug fixed: refreshNeighbors() was jittering verPos[t] (a sort-rank index)
     * instead of verPos[dists[t].node] (the actual overlapping vertex).
     *
     * Setup: v0 far away, v1 and v2 almost on top of each other.
     * Only v1 and v2 should be jittered; v0 must remain untouched.
     */
    @Test
    public void testNeighborJitterTargetsCorrectVertex() {
        GraphModel g = new GraphModel(false);
        Vertex v0 = new Vertex();
        Vertex v1 = new Vertex();
        Vertex v2 = new Vertex();

        GPoint p0 = new GPoint(0, 0);
        GPoint p1 = new GPoint(500, 0);
        GPoint p2 = new GPoint(500.5, 0);   // within distance 2 of v1
        v0.setLocation(p0);
        v1.setLocation(p1);
        v2.setLocation(p2);

        g.insertVertex(v0);
        g.insertVertex(v1);
        g.insertVertex(v2);

        animatorLSF a = makeAnimator(g);
        a.getVertices();
        a.refreshNeighbors();

        // v0 is far from everyone and must not have been jittered
        assertEquals(0.0, p0.x, 1e-9,
                "v0 (isolated) must not be jittered; x=" + p0.x);
        assertEquals(0.0, p0.y, 1e-9,
                "v0 (isolated) must not be jittered; y=" + p0.y);

        // v1/v2 are near each other — at least one of them must have moved
        boolean nearPairMoved = p1.distance(new GPoint(500, 0)) > 1e-9
                             || p2.distance(new GPoint(500.5, 0)) > 1e-9;
        assertTrue(nearPairMoved,
                "The near pair (v1/v2) should have been jittered");
    }
}
