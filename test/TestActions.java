import graphtea.extensions.AlgorithmUtils;
import graphtea.extensions.generators.CircleGenerator;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.generators.GeneralizedPetersonGenerator;
import graphtea.extensions.generators.PathGenerator;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestActions {
    GraphModel peterson = GeneralizedPetersonGenerator.generateGeneralizedPeterson(5,2);
    GraphModel circle4 = CircleGenerator.generateCircle(4);
    GraphModel circle5 = CircleGenerator.generateCircle(5);
    GraphModel complete4 = CompleteGraphGenerator.generateCompleteGraph(4);
    GraphModel complete5 = CompleteGraphGenerator.generateCompleteGraph(5);
    GraphModel path3 = PathGenerator.generatePath(3);
    GraphModel path4 = PathGenerator.generatePath(4);

    /**
     * degree of line graph is equal to the degrees of its end vertices in g - 2
     * d_e_L == (d_v_g + d_u_g - 2)
     */
    @Test
    public void testLineGraph() {
        GraphModel L = AlgorithmUtils.createLineGraph(peterson);
        boolean isCorrect = false;
        for(Vertex v : L) {
            // e = vu
            int d_e_L = L.getDegree(v);
            Edge e = (Edge)v.getProp().obj;
            int d_v_g = peterson.getDegree(e.source);
            int d_u_g = peterson.getDegree(e.target);
            isCorrect = (d_e_L == (d_v_g + d_u_g - 2));
            if(!isCorrect)
                break;
        }
        Assertions.assertTrue(isCorrect);
    }
}
