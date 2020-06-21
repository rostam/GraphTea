import graphtea.extensions.actions.product.GStrongProduct;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.GeneralizedPetersonGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.extensions.reports.basicreports.*;
import graphtea.graph.graph.GraphModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

}