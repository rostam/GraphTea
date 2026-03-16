import graphtea.extensions.actions.product.GCartesianProduct;
import graphtea.extensions.actions.product.GStrongProduct;
import graphtea.extensions.actions.product.GTensorProduct;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.GeneralizedPetersonGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.reports.basicreports.*;
import graphtea.graph.graph.GraphModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductsTest {
    GraphModel peterson = GeneralizedPetersonGenerator.generateGeneralizedPeterson(5,2);
    GraphModel circle4 = CircleGenerator.generateCircle(4);
    GraphModel circle5 = CircleGenerator.generateCircle(5);
    GraphModel complete4 = CompleteGraphGenerator.generateCompleteGraph(4);
    GraphModel complete5 = CompleteGraphGenerator.generateCompleteGraph(5);
    GraphModel path3 = PathGenerator.generatePath(3);
    GraphModel path4 = PathGenerator.generatePath(4);

    @Test
    public void testStrongProduct() {
        GStrongProduct gStrongProduct = new GStrongProduct();
        int n = 3, m = 4;
        GraphModel g = gStrongProduct.multiply(path3, path4);
        int numOfVertices = g.numOfVertices();
        int numOfEdges = g.getEdgesCount();
        ArrayList<Integer> maxAndMinDegree = new MaxAndMinDegree().calculate(g);
        int maxDegree = maxAndMinDegree.get(0);
        int minDegree = maxAndMinDegree.get(1);
        int girth = new GirthSize().calculate(g);
        int diameter = new Diameter().calculate(g);
        Assertions.assertEquals(girth, 3);
        Assertions.assertEquals(numOfVertices, n*m);
        Assertions.assertEquals(numOfEdges, 4*m*n - 3*(n+m) + 2);
        Assertions.assertEquals(maxDegree, 8);
        Assertions.assertEquals(minDegree, 3);
    }

    // ---- Cartesian product ----
    // P_m □ P_n: vertices = m*n, edges = m*(n-1) + n*(m-1)

    @Test
    public void testCartesianProductP2P2() {
        // P_2 □ P_2: 4 vertices, 4 edges (a 4-cycle)
        GraphModel p2a = PathGenerator.generatePath(2);
        GraphModel p2b = PathGenerator.generatePath(2);
        GraphModel g = new GCartesianProduct().multiply(p2a, p2b);
        assertEquals(4, g.getVerticesCount());
        assertEquals(4, g.getEdgesCount());
    }

    @Test
    public void testCartesianProductP3P3() {
        // P_3 □ P_3: 9 vertices, 3*(3-1) + 3*(3-1) = 12 edges
        GraphModel p3a = PathGenerator.generatePath(3);
        GraphModel p3b = PathGenerator.generatePath(3);
        GraphModel g = new GCartesianProduct().multiply(p3a, p3b);
        assertEquals(9, g.getVerticesCount());
        assertEquals(12, g.getEdgesCount());
    }

    @Test
    public void testCartesianProductP2P3() {
        // P_2 □ P_3: 6 vertices, 2*(3-1) + 3*(2-1) = 4+3 = 7 edges
        GraphModel p2 = PathGenerator.generatePath(2);
        GraphModel p3 = PathGenerator.generatePath(3);
        GraphModel g = new GCartesianProduct().multiply(p2, p3);
        assertEquals(6, g.getVerticesCount());
        assertEquals(7, g.getEdgesCount());
    }

    @Test
    public void testCartesianProductP3P4() {
        // P_3 □ P_4: 12 vertices, 3*(4-1) + 4*(3-1) = 9+8 = 17 edges
        GraphModel g = new GCartesianProduct().multiply(path3, path4);
        assertEquals(12, g.getVerticesCount());
        assertEquals(17, g.getEdgesCount());
    }

    @Test
    public void testCartesianProductVertexCountFormula() {
        // For any Pm □ Pn, vertex count = m*n
        for (int m = 2; m <= 5; m++) {
            for (int n = 2; n <= 5; n++) {
                GraphModel pm = PathGenerator.generatePath(m);
                GraphModel pn = PathGenerator.generatePath(n);
                GraphModel g = new GCartesianProduct().multiply(pm, pn);
                assertEquals(m * n, g.getVerticesCount(),
                        "Vertex count mismatch for P_" + m + " □ P_" + n);
            }
        }
    }

    // ---- Tensor product ----
    // For undirected simple graphs, edge count = 2 * |E(G)| * |E(H)|

    @Test
    public void testTensorProductP2P2() {
        // P_2 × P_2: 4 vertices, 2*1*1 = 2 edges
        GraphModel p2a = PathGenerator.generatePath(2);
        GraphModel p2b = PathGenerator.generatePath(2);
        GraphModel g = new GTensorProduct().multiply(p2a, p2b);
        assertEquals(4, g.getVerticesCount());
        assertEquals(2, g.getEdgesCount());
    }

    @Test
    public void testTensorProductP3P3() {
        // P_3 × P_3: 9 vertices, 2*2*2 = 8 edges
        GraphModel p3a = PathGenerator.generatePath(3);
        GraphModel p3b = PathGenerator.generatePath(3);
        GraphModel g = new GTensorProduct().multiply(p3a, p3b);
        assertEquals(9, g.getVerticesCount());
        assertEquals(8, g.getEdgesCount());
    }

    @Test
    public void testTensorProductP2P3() {
        // P_2 × P_3: 6 vertices, 2*1*2 = 4 edges
        GraphModel p2 = PathGenerator.generatePath(2);
        GraphModel p3 = PathGenerator.generatePath(3);
        GraphModel g = new GTensorProduct().multiply(p2, p3);
        assertEquals(6, g.getVerticesCount());
        assertEquals(4, g.getEdgesCount());
    }

    @Test
    public void testTensorProductVertexCountFormula() {
        // For any Pm × Pn, vertex count = m*n
        for (int m = 2; m <= 5; m++) {
            for (int n = 2; n <= 5; n++) {
                GraphModel pm = PathGenerator.generatePath(m);
                GraphModel pn = PathGenerator.generatePath(n);
                GraphModel g = new GTensorProduct().multiply(pm, pn);
                assertEquals(m * n, g.getVerticesCount(),
                        "Vertex count mismatch for P_" + m + " × P_" + n);
            }
        }
    }

    @Test
    public void testTensorProductEdgeCountFormula() {
        // |E(Pm × Pn)| = 2 * (m-1) * (n-1)
        for (int m = 2; m <= 5; m++) {
            for (int n = 2; n <= 5; n++) {
                GraphModel pm = PathGenerator.generatePath(m);
                GraphModel pn = PathGenerator.generatePath(n);
                GraphModel g = new GTensorProduct().multiply(pm, pn);
                int expected = 2 * (m - 1) * (n - 1);
                assertEquals(expected, g.getEdgesCount(),
                        "Edge count mismatch for P_" + m + " × P_" + n);
            }
        }
    }

}