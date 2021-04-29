import graphtea.graph.graph.RenderTable;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Vector;

public class RenderTableTest {

    @Test
    public void testVectorInit() {
        RenderTable ret = new RenderTable();
        ret.setTitles(new Vector<>(Arrays.asList("Test1", "Test2")));
        System.out.println(ret.getTitles().get(0));
    }
}
