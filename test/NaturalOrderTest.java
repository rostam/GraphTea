import graphtea.platform.lang.NaturalOrder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the ordering used for extension menus.
 */
public class NaturalOrderTest {

    private static List<String> sorted(String... items) {
        List<String> list = new ArrayList<>(List.of(items));
        list.sort(NaturalOrder.INSTANCE);
        return list;
    }

    @Test
    public void numbersCompareByValue() {
        assertEquals(List.of("Chain Graph 1", "Chain Graph 2", "Chain Graph 10"),
                sorted("Chain Graph 10", "Chain Graph 2", "Chain Graph 1"));
    }

    @Test
    public void caseIsIgnored() {
        assertEquals(List.of("number of quadrangle", "Number Of Triangles", "Radius"),
                sorted("Radius", "Number Of Triangles", "number of quadrangle"));
    }

    @Test
    public void leadingZerosDoNotChangeTheValue() {
        assertTrue(NaturalOrder.INSTANCE.compare("K07", "K7") != 0);
        assertTrue(NaturalOrder.INSTANCE.compare("K07", "K8") < 0);
    }

    @Test
    public void prefixComesFirst() {
        assertTrue(NaturalOrder.INSTANCE.compare("Exp", "Exponential") < 0);
        assertTrue(NaturalOrder.INSTANCE.compare("Km,n", "Km,n,o") < 0);
    }

    @Test
    public void isConsistentForEqualIgnoringCase() {
        int ab = NaturalOrder.INSTANCE.compare("PI", "Pi");
        int ba = NaturalOrder.INSTANCE.compare("Pi", "PI");
        assertNotEquals(0, ab);
        assertEquals(-Integer.signum(ab), Integer.signum(ba));
    }

    @Test
    public void hugeNumbersDoNotOverflow() {
        assertTrue(NaturalOrder.INSTANCE.compare("n99999999999999999999", "n100000000000000000000") < 0);
    }
}
