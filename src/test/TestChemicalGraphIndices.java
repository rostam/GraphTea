package test;

import graphtea.extensions.G6Format;
import graphtea.extensions.reports.others.PeripheralWienerIndex;
import org.junit.Assert;
import org.junit.Test;

public class TestChemicalGraphIndices {
    @Test
    public void testPhericalIndex() {
        Assert.assertEquals(9, new PeripheralWienerIndex().calculate(G6Format.stringToGraphModel("GnCG[?")).intValue());
    }
}
