import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlackBoardTest {

    private BlackBoard bb;

    @BeforeEach
    public void setUp() {
        bb = new BlackBoard();
    }

    // -------------------------------------------------------------------------
    // setData / getData / contains
    // -------------------------------------------------------------------------

    @Test
    public void testSetAndGetData() {
        bb.setData("key", "value");
        assertEquals("value", bb.getData("key"));
    }

    @Test
    public void testGetMissingKeyReturnsNull() {
        assertNull(bb.getData("nonexistent"));
    }

    @Test
    public void testContainsTrueAfterSet() {
        bb.setData("k", 42);
        assertTrue(bb.contains("k"));
    }

    @Test
    public void testContainsFalseBeforeSet() {
        assertFalse(bb.contains("absent"));
    }

    @Test
    public void testOverwriteValue() {
        bb.setData("k", "first");
        bb.setData("k", "second");
        assertEquals("second", bb.getData("k"));
    }

    @Test
    public void testStoreArbitraryTypes() {
        bb.setData("int", 7);
        bb.setData("list", List.of(1, 2, 3));
        assertEquals(7, (int) bb.getData("int"));
        assertEquals(3, ((List<?>) bb.getData("list")).size());
    }

    // -------------------------------------------------------------------------
    // Listener notification
    // -------------------------------------------------------------------------

    @Test
    public void testListenerCalledOnSetData() {
        List<Object> received = new ArrayList<>();
        bb.addListener("x", (key, value) -> received.add(value));

        bb.setData("x", "hello");

        assertEquals(1, received.size());
        assertEquals("hello", received.get(0));
    }

    @Test
    public void testListenerReceivesCorrectKey() {
        List<String> keys = new ArrayList<>();
        bb.addListener("myKey", (key, value) -> keys.add(key));

        bb.setData("myKey", "v");

        assertEquals("myKey", keys.get(0));
    }

    @Test
    public void testListenerNotCalledForDifferentKey() {
        List<Object> received = new ArrayList<>();
        bb.addListener("a", (key, value) -> received.add(value));

        bb.setData("b", "irrelevant");

        assertTrue(received.isEmpty());
    }

    @Test
    public void testMultipleListenersOnSameKey() {
        List<String> log = new ArrayList<>();
        bb.addListener("k", (key, v) -> log.add("L1:" + v));
        bb.addListener("k", (key, v) -> log.add("L2:" + v));

        bb.setData("k", "ping");

        assertEquals(2, log.size());
        assertTrue(log.contains("L1:ping"));
        assertTrue(log.contains("L2:ping"));
    }

    @Test
    public void testListenerCalledOnEachUpdate() {
        List<Object> received = new ArrayList<>();
        bb.addListener("k", (key, value) -> received.add(value));

        bb.setData("k", 1);
        bb.setData("k", 2);
        bb.setData("k", 3);

        assertEquals(List.of(1, 2, 3), received);
    }

    // -------------------------------------------------------------------------
    // removeListener
    // -------------------------------------------------------------------------

    @Test
    public void testRemoveListenerStopsNotifications() {
        List<Object> received = new ArrayList<>();
        Listener<Object> listener = (key, value) -> received.add(value);

        bb.addListener("k", listener);
        bb.setData("k", "before");
        bb.removeListener("k", listener);
        bb.setData("k", "after");

        assertEquals(1, received.size());
        assertEquals("before", received.get(0));
    }

    @Test
    public void testRemoveNonExistentListenerDoesNotThrow() {
        assertDoesNotThrow(() ->
            bb.removeListener("k", (key, value) -> {})
        );
    }

    // -------------------------------------------------------------------------
    // Listener added / removed during firing (deferred mutation)
    // -------------------------------------------------------------------------

    @Test
    public void testListenerAddedDuringFiringIsCalledOnNextSet() {
        List<String> log = new ArrayList<>();
        Listener<Object> inner = (key, value) -> log.add("inner:" + value);

        // outer listener adds 'inner' while firing
        bb.addListener("k", (key, value) -> {
            log.add("outer:" + value);
            bb.addListener("k", inner);
        });

        bb.setData("k", "first");   // outer fires; inner gets registered
        bb.setData("k", "second");  // both outer and inner fire

        assertTrue(log.contains("outer:first"));
        assertTrue(log.contains("outer:second"));
        assertTrue(log.contains("inner:second"));
        // inner should NOT have been called on "first" (added during that firing)
        assertFalse(log.contains("inner:first"));
    }

    @Test
    public void testListenerRemovedDuringFiringDoesNotFireAgain() {
        List<String> log = new ArrayList<>();
        Listener<Object>[] selfRef = new Listener[1];
        selfRef[0] = (key, value) -> {
            log.add("fired:" + value);
            bb.removeListener("k", selfRef[0]);
        };

        bb.addListener("k", selfRef[0]);
        bb.setData("k", "once");
        bb.setData("k", "twice");

        // listener removes itself on first fire, so only fires once
        assertEquals(1, log.size());
        assertEquals("fired:once", log.get(0));
    }

    // -------------------------------------------------------------------------
    // getListeners
    // -------------------------------------------------------------------------

    @Test
    public void testGetListenersNullBeforeAnyAdded() {
        assertNull(bb.getListeners("neverUsed"));
    }

    @Test
    public void testGetListenersReturnsAddedListeners() {
        Listener<Object> l = (key, value) -> {};
        bb.addListener("k", l);
        assertTrue(bb.getListeners("k").contains(l));
    }
}
