package graphtea.ui;

import graphtea.extensions.actions.product.CartesianProduct;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.platform.lang.ArrayX;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that edits made in the parameter dialog reach the extension's fields.
 */
public class ParameterShowerTest {

    @Test
    public void inheritedParameterIsWritten() {
        // g0 and g1 are declared in TwoGraphAction, two classes above CartesianProduct.
        // getDeclaredField could not find them, so edits were silently dropped.
        CartesianProduct product = new CartesianProduct();
        ParameterShower.applyParameter(product, "g0", "G3");
        ParameterShower.applyParameter(product, "g1", "G4");
        assertEquals("G3", product.g0);
        assertEquals("G4", product.g1);
    }

    @Test
    public void ownParameterIsWritten() {
        // CompleteGraphGenerator.n is static, so put it back for the tests that follow.
        Integer before = CompleteGraphGenerator.n;
        try {
            ParameterShower.applyParameter(new CompleteGraphGenerator(), "n", 9);
            assertEquals(9, CompleteGraphGenerator.n);
        } finally {
            CompleteGraphGenerator.n = before;
        }
    }

    @Test
    public void unknownNameIsIgnored() {
        CartesianProduct product = new CartesianProduct();
        ParameterShower.applyParameter(product, "noSuchField", "x");
        assertEquals("G0", product.g0);
    }

    @Test
    public void choiceIsWrittenAsItsValue() {
        // A text parameter with choices is edited as an ArrayX drop-down; the field gets the
        // chosen text, not the ArrayX.
        CartesianProduct product = new CartesianProduct();
        ParameterShower.applyParameter(product, "g1", new ArrayX<>("G5", "G0", "G5"));
        assertEquals("G5", product.g1);
    }
}
