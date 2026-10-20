// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import java.util.List;

/**
 * A ready-made conjecture check, offered in the Conjecture Checker's Examples menu to show
 * what the checker can do.
 *
 * @author M. Ali Rostami
 */
public final class Example {

    /** Where an example's graphs come from. */
    public enum Source {
        /** Every graph of a class on a range of vertex counts. */
        EXHAUSTIVE,
        /** A generator family. */
        FAMILY,
        /** Random graphs. */
        RANDOM
    }

    private final String title;
    private final String explanation;
    private final String formula;
    private final String report;
    private final String condition;
    private final Source source;
    private final GraphClass graphClass;
    private final int from;
    private final int to;
    private final String generator;
    private final double probability;
    private final int perSize;
    private final boolean stopAtFirst;
    private final Relation relation;

    private Example(Builder b) {
        this.title = b.title;
        this.explanation = b.explanation;
        this.formula = b.formula;
        this.report = b.report;
        this.condition = b.condition;
        this.source = b.source;
        this.graphClass = b.graphClass;
        this.from = b.from;
        this.to = b.to;
        this.generator = b.generator;
        this.probability = b.probability;
        this.perSize = b.perSize;
        this.stopAtFirst = b.stopAtFirst;
        this.relation = b.relation;
    }

    /** @return the examples, in menu order */
    public static List<Example> all() {
        return List.of(
            new Builder("Energy is at most 2m")
                .explain("A known upper bound, so it holds on every graph. It is attained exactly by graphs "
                    + "whose edges form a matching, which are the graphs listed.")
                .formula("energy <= 2*m").exhaustive(GraphClass.ALL, 1, 7).build(),
            new Builder("Energy is at least 2√m")
                .explain("Also true. Equality holds exactly for complete bipartite graphs, plus isolated "
                    + "vertices; open a few of the tight cases to see them.")
                .formula("energy >= 2*sqrt(m)").exhaustive(GraphClass.CONNECTED, 2, 7).build(),
            new Builder("Chromatic number equals clique number")
                .explain("False. The checker stops at the smallest counterexample, the 5-cycle, which "
                    + "needs three colours but has no triangle. Double-click it to open it.")
                .formula("chromatic_number = max_clique_size").exhaustive(GraphClass.CONNECTED, 1, 7)
                .stopAtFirst().build(),
            new Builder("Among trees, the path has the largest Wiener index")
                .explain("True. The right side is the Wiener index of the path on n vertices, so the "
                    + "tight cases are exactly the paths, one for each n.")
                .formula("wiener_index <= (n^3 - n)/6").exhaustive(GraphClass.TREES, 2, 14).build(),
            new Builder("Bipartite graphs are 2-colourable (using a condition)")
                .explain("The condition is_bipartite = 1 keeps only bipartite graphs; the others are "
                    + "counted as skipped. Every condition is an ordinary formula, e.g. m = n for unicyclic "
                    + "graphs or mindeg >= 2.")
                .formula("chromatic_number <= 2").condition("is_bipartite = 1")
                .exhaustive(GraphClass.CONNECTED, 2, 7).build(),
            new Builder("Energy of the complete graph is 2(n − 1)")
                .explain("Tests a graph family instead of all graphs: K2 to K30 from the Complete Graph "
                    + "generator. An equation holds when the sides differ by less than 0.00001, since reports round their answers.")
                .formula("energy = 2*(n - 1)").family("Complete Graph", 2, 30).build(),
            new Builder("Wiener index of a connected graph is at least n(n − 1) − m")
                .explain("Too many graphs to list them all at 10 to 30 vertices, so this samples 20 random "
                    + "connected graphs for each n. Equality holds exactly when the diameter is at most 2, "
                    + "as it is for most graphs with edge probability 0.5.")
                .formula("wiener_index >= n*(n - 1) - m").random(GraphClass.CONNECTED, 10, 30, 0.5, 20).build(),
            new Builder("Which bounds of a report hold? (M3 Bound Conjecture)")
                .explain("Reports in the Verification categories compute a quantity and many candidate "
                    + "bounds for it at once. This compares the first column with each of the others and "
                    + "counts, per bound, where it holds and where it fails.")
                .report("M3 Bound Conjecture", Relation.AT_MOST).exhaustive(GraphClass.CONNECTED, 3, 7).build()
        );
    }

    /** @return the menu title */
    public String title() {
        return title;
    }

    /** @return what the example shows and what to look for */
    public String explanation() {
        return explanation;
    }

    /** @return the formula, or null for a report example */
    public String formula() {
        return formula;
    }

    /** @return the report's title, or null for a formula example */
    public String report() {
        return report;
    }

    /** @return the relation to claim between a report's columns */
    public Relation relation() {
        return relation;
    }

    /** @return the condition, or an empty string */
    public String condition() {
        return condition;
    }

    /** @return where the graphs come from */
    public Source source() {
        return source;
    }

    /** @return the class of graphs */
    public GraphClass graphClass() {
        return graphClass;
    }

    /** @return the smallest n */
    public int from() {
        return from;
    }

    /** @return the largest n */
    public int to() {
        return to;
    }

    /** @return the generator's title, for a family example */
    public String generator() {
        return generator;
    }

    /** @return the edge probability, for a random example */
    public double probability() {
        return probability;
    }

    /** @return graphs per n, for a random example */
    public int perSize() {
        return perSize;
    }

    /** @return whether to stop at the first counterexample */
    public boolean stopAtFirst() {
        return stopAtFirst;
    }

    @Override
    public String toString() {
        return title;
    }

    private static final class Builder {
        private final String title;
        private String explanation = "";
        private String formula;
        private String report;
        private String condition = "";
        private Source source = Source.EXHAUSTIVE;
        private GraphClass graphClass = GraphClass.CONNECTED;
        private int from = 1;
        private int to = 7;
        private String generator;
        private double probability = 0.5;
        private int perSize = 10;
        private boolean stopAtFirst;
        private Relation relation = Relation.AT_MOST;

        Builder(String title) {
            this.title = title;
        }

        Builder explain(String text) {
            this.explanation = text;
            return this;
        }

        Builder formula(String f) {
            this.formula = f;
            return this;
        }

        Builder report(String name, Relation r) {
            this.report = name;
            this.relation = r;
            return this;
        }

        Builder condition(String c) {
            this.condition = c;
            return this;
        }

        Builder exhaustive(GraphClass cls, int lo, int hi) {
            this.source = Source.EXHAUSTIVE;
            this.graphClass = cls;
            this.from = lo;
            this.to = hi;
            return this;
        }

        Builder family(String name, int lo, int hi) {
            this.source = Source.FAMILY;
            this.generator = name;
            this.graphClass = GraphClass.ALL;
            this.from = lo;
            this.to = hi;
            return this;
        }

        Builder random(GraphClass cls, int lo, int hi, double p, int count) {
            this.source = Source.RANDOM;
            this.graphClass = cls;
            this.from = lo;
            this.to = hi;
            this.probability = p;
            this.perSize = count;
            return this;
        }

        Builder stopAtFirst() {
            this.stopAtFirst = true;
            return this;
        }

        Example build() {
            return new Example(this);
        }
    }
}
