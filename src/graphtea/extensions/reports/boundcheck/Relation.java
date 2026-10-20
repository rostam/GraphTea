// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

/**
 * The relation a conjecture claims between its two sides.
 *
 * <p>Invariants such as energy are computed in floating point, and the energy reports round
 * their answers to five decimals, so two sides that are equal in exact arithmetic rarely
 * compare equal. Sides closer than {@link #TOLERANCE}, or for large values than
 * {@link #RELATIVE_TOLERANCE} of their size, are treated as equal: such a graph attains a
 * {@code <=} bound, and breaks a {@code <}.
 *
 * @author M. Ali Rostami
 */
public enum Relation {
    AT_MOST("<=", "≤"),
    LESS("<", "<"),
    AT_LEAST(">=", "≥"),
    GREATER(">", ">"),
    EQUAL("=", "="),
    NOT_EQUAL("!=", "≠");

    /** Values closer than this count as equal; it covers rounding to five decimals. */
    public static final double TOLERANCE = 1e-5;

    /** Large values closer than this fraction of their size count as equal. */
    public static final double RELATIVE_TOLERANCE = 1e-12;

    private final String ascii;
    private final String symbol;

    Relation(String ascii, String symbol) {
        this.ascii = ascii;
        this.symbol = symbol;
    }

    /** @return the relation as typed, e.g. {@code <=} */
    public String ascii() {
        return ascii;
    }

    /** @return the relation as printed, e.g. ≤ */
    public String symbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    /**
     * @param a a value
     * @param b another value
     * @return whether a and b are equal up to the tolerances
     */
    public static boolean close(double a, double b) {
        if (a == b) {
            return true;
        }
        double scale = Math.max(Math.abs(a), Math.abs(b));
        return Math.abs(a - b) <= Math.max(TOLERANCE, RELATIVE_TOLERANCE * scale);
    }

    /**
     * @param left  the left side's value
     * @param right the right side's value
     * @return what the relation says about the two values
     */
    public Outcome compare(double left, double right) {
        if (Double.isNaN(left) || Double.isNaN(right)) {
            return Outcome.UNDEFINED;
        }
        boolean equal = close(left, right);
        switch (this) {
            case AT_MOST:
                return equal ? Outcome.TIGHT : verdict(left < right);
            case AT_LEAST:
                return equal ? Outcome.TIGHT : verdict(left > right);
            case LESS:
                return verdict(!equal && left < right);
            case GREATER:
                return verdict(!equal && left > right);
            case EQUAL:
                return verdict(equal);
            default:
                return verdict(!equal);
        }
    }

    private static Outcome verdict(boolean holds) {
        return holds ? Outcome.HOLDS : Outcome.FAILS;
    }
}
