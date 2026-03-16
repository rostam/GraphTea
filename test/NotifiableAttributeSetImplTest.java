import graphtea.platform.attribute.AttributeListener;
import graphtea.platform.attribute.NotifiableAttributeSetImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotifiableAttributeSetImplTest {

    private NotifiableAttributeSetImpl attrs;

    @BeforeEach
    public void setUp() {
        attrs = new NotifiableAttributeSetImpl();
    }

    // -------------------------------------------------------------------------
    // Basic put / get / contains
    // -------------------------------------------------------------------------

    @Test
    public void testPutAndGet() {
        attrs.put("color", "red");
        assertEquals("red", attrs.get("color"));
    }

    @Test
    public void testContainsTrueAfterPut() {
        attrs.put("x", 1);
        assertTrue(attrs.contains("x"));
    }

    @Test
    public void testContainsFalseForMissingKey() {
        assertFalse(attrs.contains("absent"));
    }

    @Test
    public void testNullKeyThrows() {
        assertThrows(RuntimeException.class, () -> attrs.put(null, "v"));
    }

    @Test
    public void testGetAttrsReflectsCurrentState() {
        attrs.put("a", 1);
        attrs.put("b", 2);
        assertEquals(2, attrs.getAttrs().size());
    }

    // -------------------------------------------------------------------------
    // Listener fires on put with correct old/new values
    // -------------------------------------------------------------------------

    @Test
    public void testListenerFiresOnPut() {
        List<Object[]> events = new ArrayList<>();
        attrs.addAttributeListener((name, oldVal, newVal) ->
            events.add(new Object[]{name, oldVal, newVal}));

        attrs.put("size", 42);

        assertEquals(1, events.size());
        assertEquals("size", events.get(0)[0]);
        assertNull(events.get(0)[1]);          // no previous value
        assertEquals(42, events.get(0)[2]);
    }

    @Test
    public void testListenerReceivesOldValueOnOverwrite() {
        attrs.put("k", "first");
        List<Object> oldVals = new ArrayList<>();
        attrs.addAttributeListener((name, oldVal, newVal) -> oldVals.add(oldVal));

        attrs.put("k", "second");

        assertEquals("first", oldVals.get(0));
    }

    @Test
    public void testMultipleListenersBothFire() {
        List<String> log = new ArrayList<>();
        attrs.addAttributeListener((n, o, v) -> log.add("L1"));
        attrs.addAttributeListener((n, o, v) -> log.add("L2"));

        attrs.put("k", "v");

        assertEquals(2, log.size());
        assertTrue(log.contains("L1"));
        assertTrue(log.contains("L2"));
    }

    // -------------------------------------------------------------------------
    // removeAttributeListener stops notifications
    // -------------------------------------------------------------------------

    @Test
    public void testRemovedListenerNoLongerFires() {
        List<Object> log = new ArrayList<>();
        AttributeListener listener = (name, oldVal, newVal) -> log.add(newVal);

        attrs.addAttributeListener(listener);
        attrs.put("k", "before");
        attrs.removeAttributeListener(listener);
        attrs.put("k", "after");

        assertEquals(1, log.size());
        assertEquals("before", log.get(0));
    }

    // -------------------------------------------------------------------------
    // clear fires listener for each entry, then empties the set
    // -------------------------------------------------------------------------

    @Test
    public void testClearEmptiesAttrs() {
        attrs.put("a", 1);
        attrs.put("b", 2);
        attrs.clear();
        assertFalse(attrs.contains("a"));
        assertFalse(attrs.contains("b"));
        assertEquals(0, attrs.getAttrs().size());
    }

    @Test
    public void testClearFiresListenerForEachEntry() {
        attrs.put("x", 10);
        attrs.put("y", 20);

        List<String[]> events = new ArrayList<>();
        attrs.addAttributeListener((name, oldVal, newVal) ->
            events.add(new String[]{name, String.valueOf(oldVal), String.valueOf(newVal)}));

        attrs.clear();

        // one event per entry
        assertEquals(2, events.size());
        // each event should have null as the new value
        assertTrue(events.stream().allMatch(e -> "null".equals(e[2])));
        // both keys should appear
        assertTrue(events.stream().anyMatch(e -> "x".equals(e[0])));
        assertTrue(events.stream().anyMatch(e -> "y".equals(e[0])));
    }

    @Test
    public void testClearOnEmptySetDoesNotFireListener() {
        List<Object> log = new ArrayList<>();
        attrs.addAttributeListener((n, o, v) -> log.add(v));

        attrs.clear();

        assertTrue(log.isEmpty());
    }

    // -------------------------------------------------------------------------
    // getAttributeListeners
    // -------------------------------------------------------------------------

    @Test
    public void testGetAttributeListenersContainsAdded() {
        AttributeListener l = (n, o, v) -> {};
        attrs.addAttributeListener(l);
        assertTrue(attrs.getAttributeListeners().contains(l));
    }

    @Test
    public void testGetAttributeListenersEmptyInitially() {
        assertTrue(attrs.getAttributeListeners().isEmpty());
    }
}
