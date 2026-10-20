import graphtea.extensions.actions.TwoGraphAction;
import graphtea.extensions.actions.product.CartesianProduct;
import graphtea.extensions.actions.product.Union;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.Application;
import graphtea.platform.core.BlackBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the graph-name check that two-graph operations run before the parameter dialog closes.
 */
public class TwoGraphActionTest {

    private BlackBoard previous;

    @BeforeEach
    public void openTwoGraphs() {
        previous = Application.blackboard;
        BlackBoard bb = new BlackBoard();
        Application.blackboard = bb;
        GTabbedGraphPane tabs = new GTabbedGraphPane(bb);
        tabs.getGraphs().put("G0", CompleteGraphGenerator.generateCompleteGraph(3));
        tabs.getGraphs().put("G2", CompleteGraphGenerator.generateCompleteGraph(4));
    }

    @AfterEach
    public void restore() {
        Application.blackboard = previous;
    }

    @Test
    public void acceptsNamesOfOpenGraphs() {
        TwoGraphAction union = new Union();
        union.g0 = "G0";
        union.g1 = "G2";
        assertNull(union.checkParameters());
    }

    @Test
    public void acceptsTheSameGraphTwice() {
        TwoGraphAction product = new CartesianProduct();
        product.g0 = "G2";
        product.g1 = "G2";
        assertNull(product.checkParameters());
    }

    @Test
    public void defaultSecondGraphMissingIsReportedWithTheOpenGraphs() {
        // The default "G1" is not open here, which used to end in a NullPointerException.
        String message = new Union().checkParameters();
        assertNotNull(message);
        assertTrue(message.contains("\"G1\""), message);
        assertTrue(message.contains("G0, G2"), message);
    }

    @Test
    public void noBlackboardSkipsTheCheck() {
        Application.blackboard = null;
        assertNull(new Union().checkParameters());
    }
}
