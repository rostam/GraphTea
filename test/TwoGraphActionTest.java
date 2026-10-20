import graphtea.extensions.actions.TwoGraphAction;
import graphtea.extensions.actions.product.CartesianProduct;
import graphtea.extensions.actions.product.Union;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.Application;
import graphtea.platform.core.BlackBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the graph-name check that two-graph operations run before the parameter dialog closes,
 * and the open graphs they offer in the dialog.
 */
public class TwoGraphActionTest {

    private BlackBoard previous;

    @BeforeEach
    public void openTwoGraphs() {
        previous = Application.blackboard;
        BlackBoard bb = new BlackBoard();
        Application.blackboard = bb;
        GTabbedGraphPane tabs = new GTabbedGraphPane(bb);
        open(tabs, "G0", CompleteGraphGenerator.generateCompleteGraph(3));
        open(tabs, "G2", CompleteGraphGenerator.generateCompleteGraph(4));
    }

    private static void open(GTabbedGraphPane tabs, String label, GraphModel g) {
        g.setLabel(label);
        tabs.addGraph(g);
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

    @Test
    public void offersTheOpenGraphs() {
        TwoGraphAction union = new Union();
        assertArrayEquals(new String[]{"G0", "G2"}, union.parameterChoices("g0"));
        assertArrayEquals(new String[]{"G0", "G2"}, union.parameterChoices("g1"));
    }

    @Test
    public void secondGraphDefaultsToADifferentOpenGraph() {
        TwoGraphAction union = new Union();
        union.parameterChoices("g1");
        assertEquals("G2", union.g1, "the default G1 is not open");
        union.g1 = "G0";
        union.parameterChoices("g1");
        assertEquals("G2", union.g1, "the same graph as the first was offered by default");
    }

    @Test
    public void closedTabsAreNotOffered() {
        GTabbedGraphPane tabs = Application.blackboard.getData(GTabbedGraphPane.NAME);
        tabs.getTabedPane().remove(0);
        assertArrayEquals(new String[]{"G2"}, new Union().parameterChoices("g0"));
    }
}
