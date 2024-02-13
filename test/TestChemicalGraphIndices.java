import graphtea.extensions.G6Format;
import graphtea.extensions.reports.others.PeripheralWienerIndex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestChemicalGraphIndices {
    @Test
    public void testPhericalIndex() {
        Assertions.assertEquals(new PeripheralWienerIndex().calculate(G6Format.stringToGraphModel("GnCG[?")),9);
    }
}
