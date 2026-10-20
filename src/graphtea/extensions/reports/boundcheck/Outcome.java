// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

/**
 * What a conjecture said about one graph.
 *
 * @author M. Ali Rostami
 */
public enum Outcome {
    /** The relation holds strictly. */
    HOLDS("Holds"),
    /** The relation holds, with both sides equal: the bound is attained. */
    TIGHT("Holds with equality"),
    /** The graph is a counterexample. */
    FAILS("Fails"),
    /** A side could not be evaluated, e.g. a division by zero, or a report failed. */
    UNDEFINED("Undefined");

    private final String label;

    Outcome(String label) {
        this.label = label;
    }

    /** @return the outcome as shown to the user */
    public String label() {
        return label;
    }

    /**
     * Combines the outcomes of several comparisons on one graph: one failure makes the graph a
     * counterexample, and it is tight when nothing failed and some bound was attained.
     *
     * @param outcomes the outcomes of each comparison
     * @return the outcome for the graph
     */
    public static Outcome overall(Outcome[] outcomes) {
        boolean undefined = false;
        boolean tight = false;
        for (Outcome o : outcomes) {
            if (o == FAILS) {
                return FAILS;
            }
            undefined |= o == UNDEFINED;
            tight |= o == TIGHT;
        }
        if (undefined) {
            return UNDEFINED;
        }
        return tight ? TIGHT : HOLDS;
    }
}
