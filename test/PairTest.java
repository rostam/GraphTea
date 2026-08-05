import graphtea.platform.lang.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * graphtea.platform.lang.Pair used to hand-roll equals, and the null guards fell through to
 * first.equals(...), so any pair with a null component threw NullPointerException when compared
 * — including a pair compared with itself. It is now a record, which generates the three
 * Object methods correctly. These cases pin that down.
 */
public class PairTest {

    @Test
    public void equalPairsAreEqual() {
        assertEquals(new Pair<>("a", "b"), new Pair<>("a", "b"));
        assertEquals(new Pair<>("a", "b").hashCode(), new Pair<>("a", "b").hashCode());
    }

    @Test
    public void differingPairsAreNotEqual() {
        assertNotEquals(new Pair<>("a", "b"), new Pair<>("a", "c"));
        assertNotEquals(new Pair<>("a", "b"), new Pair<>("z", "b"));
    }

    @Test
    public void nullComponentsCompareWithoutThrowing() {
        assertEquals(new Pair<String, String>(null, null), new Pair<String, String>(null, null));
        assertEquals(new Pair<String, String>(null, "x"), new Pair<String, String>(null, "x"));
        assertEquals(new Pair<String, String>("x", null), new Pair<String, String>("x", null));
        assertNotEquals(new Pair<String, String>(null, "x"), new Pair<String, String>("x", "x"));
    }

    @Test
    public void aPairEqualsItselfEvenWhenNull() {
        Pair<String, String> p = new Pair<>(null, null);
        assertEquals(p, p);
    }

    @Test
    public void isNotEqualToOtherTypes() {
        assertNotEquals("a, b", new Pair<>("a", "b"));
    }

    @Test
    public void worksAsAHashKey() {
        Set<Pair<String, String>> set = new HashSet<>();
        set.add(new Pair<>("a", "b"));
        set.add(new Pair<>("a", "b"));
        set.add(new Pair<>(null, null));
        set.add(new Pair<>(null, null));
        assertEquals(2, set.size());
        assertTrue(set.contains(new Pair<>("a", "b")));
        assertTrue(set.contains(new Pair<>(null, null)));
    }

    @Test
    public void accessorsReturnComponents() {
        Pair<Integer, String> p = new Pair<>(7, "seven");
        assertEquals(7, p.first());
        assertEquals("seven", p.second());
    }
}
