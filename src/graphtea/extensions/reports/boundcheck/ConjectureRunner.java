// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import graphtea.extensions.G6Format;
import graphtea.graph.graph.GraphModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * Tests a conjecture on every graph from a source and keeps the graphs worth looking at.
 *
 * <p>Counts are exact however many graphs are tested. Of the graphs themselves, only the first
 * {@link #KEEP} of each outcome are kept, so that a run over millions of graphs does not fill
 * the memory with graphs on which the conjecture simply holds.
 *
 * @author M. Ali Rostami
 */
public final class ConjectureRunner {

    /** How many graphs of each outcome a result keeps. */
    public static final int KEEP = 5000;

    /** The minimum time between two progress reports, in milliseconds. */
    private static final long PROGRESS_INTERVAL_MS = 100;

    /** One tested graph and what the conjecture said about it. */
    public static final class Row {
        private final long index;
        private final GraphModel graph;
        private final double[] values;
        private final Outcome[] outcomes;
        private final Outcome overall;
        private String g6;

        Row(long index, GraphModel graph, double[] values, Outcome[] outcomes) {
            this.index = index;
            this.graph = graph;
            this.values = values;
            this.outcomes = outcomes;
            this.overall = Outcome.overall(outcomes);
        }

        /** @return the graph's position among those tested, from 1 */
        public long index() {
            return index;
        }

        /** @return the graph */
        public GraphModel graph() {
            return graph;
        }

        /** @return the number of vertices */
        public int n() {
            return graph.getVerticesCount();
        }

        /** @return the number of edges */
        public int m() {
            return graph.getEdgesCount();
        }

        /** @return the left side's value */
        public double left() {
            return values[0];
        }

        /**
         * @param i which comparison
         * @return that comparison's right side
         */
        public double right(int i) {
            return values[i + 1];
        }

        /**
         * @param i which comparison
         * @return what the relation said for that comparison
         */
        public Outcome outcome(int i) {
            return outcomes[i];
        }

        /** @return the outcome for the graph as a whole */
        public Outcome overall() {
            return overall;
        }

        /** @return the graph in graph6 format */
        public synchronized String g6() {
            if (g6 == null) {
                g6 = graph.getVerticesCount() == 0 ? "?" : G6Format.graphToG6(graph);
            }
            return g6;
        }
    }

    /** How far a run has got. */
    public static final class Progress {
        private final long tested;
        private final long expected;
        private final long failures;
        private final int vertices;

        Progress(long tested, long expected, long failures, int vertices) {
            this.tested = tested;
            this.expected = expected;
            this.failures = failures;
            this.vertices = vertices;
        }

        /** @return graphs tested so far */
        public long tested() {
            return tested;
        }

        /** @return graphs the source will produce, or -1 if unknown */
        public long expected() {
            return expected;
        }

        /** @return counterexamples found so far */
        public long failures() {
            return failures;
        }

        /** @return vertices of the graph being tested */
        public int vertices() {
            return vertices;
        }
    }

    /** What a run found. */
    public static final class Result {
        private final Conjecture conjecture;
        private final String graphs;
        private final long[][] counts;
        private final long[] overall = new long[Outcome.values().length];
        private final List<Row> rows = new ArrayList<>();
        private final boolean[] truncated = new boolean[Outcome.values().length];
        private long tested;
        private long excluded;
        private boolean cancelled;
        private boolean stoppedAtFirst;
        private String firstError;
        private long millis;

        Result(Conjecture conjecture, String graphs) {
            this.conjecture = conjecture;
            this.graphs = graphs;
            this.counts = new long[conjecture.rightLabels().size()][Outcome.values().length];
        }

        /** @return the conjecture that was tested */
        public Conjecture conjecture() {
            return conjecture;
        }

        /** @return what it was tested on */
        public String graphs() {
            return graphs;
        }

        /** @return how many graphs were tested */
        public long tested() {
            return tested;
        }

        /** @return how many graphs did not satisfy the condition and were skipped */
        public long excluded() {
            return excluded;
        }

        /**
         * @param o an outcome
         * @return on how many graphs the conjecture as a whole had that outcome
         */
        public long count(Outcome o) {
            return overall[o.ordinal()];
        }

        /**
         * @param comparison which comparison
         * @param o          an outcome
         * @return on how many graphs that comparison had that outcome
         */
        public long count(int comparison, Outcome o) {
            return counts[comparison][o.ordinal()];
        }

        /** @return the kept graphs, in the order they were tested */
        public List<Row> rows() {
            return Collections.unmodifiableList(rows);
        }

        /**
         * @param o an outcome
         * @return whether some graphs with that outcome were counted but not kept
         */
        public boolean truncated(Outcome o) {
            return truncated[o.ordinal()];
        }

        /** @return whether the run was stopped before the source ran out */
        public boolean cancelled() {
            return cancelled;
        }

        /** @return whether the run stopped because it found a counterexample, as asked */
        public boolean stoppedAtFirst() {
            return stoppedAtFirst;
        }

        /** @return the first error raised while evaluating a graph, or null */
        public String firstError() {
            return firstError;
        }

        /** @return how long the run took */
        public long millis() {
            return millis;
        }
    }

    /** Everything a run needs besides the graphs. */
    public static final class Settings {
        private Expression condition;
        private boolean stopAtFirst;
        private BooleanSupplier cancelled = () -> false;
        private Consumer<Progress> progress = p -> { };

        /**
         * @param c graphs on which this is false or undefined are skipped; null tests all
         * @return this
         */
        public Settings condition(Expression c) {
            this.condition = c;
            return this;
        }

        /**
         * @param stop whether to stop at the first counterexample
         * @return this
         */
        public Settings stopAtFirst(boolean stop) {
            this.stopAtFirst = stop;
            return this;
        }

        /**
         * @param c polled between graphs; true stops the run
         * @return this
         */
        public Settings cancelled(BooleanSupplier c) {
            this.cancelled = c;
            return this;
        }

        /**
         * @param p told how far the run has got, from the running thread, a few times a second
         * @return this
         */
        public Settings progress(Consumer<Progress> p) {
            this.progress = p;
            return this;
        }
    }

    private ConjectureRunner() {
    }

    /**
     * Tests a conjecture. Runs in the calling thread.
     *
     * @param conjecture the conjecture
     * @param invariants the invariants its formulas may name
     * @param source     the graphs
     * @param settings   condition, stopping and progress
     * @return what was found
     * @throws IOException if the graphs could not be read
     */
    public static Result run(Conjecture conjecture, Invariants invariants, GraphSource source, Settings settings)
            throws IOException {
        Result result = new Result(conjecture, source.describe());
        long expected = source.expectedCount();
        long start = System.currentTimeMillis();
        long[] lastReport = {0};
        int k = conjecture.rightLabels().size();

        source.forEach(g -> {
            if (settings.cancelled.getAsBoolean()) {
                result.cancelled = true;
                return false;
            }
            Expression.Variables vars = invariants.bind(g);
            if (settings.condition != null && !holds(settings.condition, vars, result)) {
                result.excluded++;
                return true;
            }
            Row row = test(conjecture, g, vars, k, ++result.tested, result);
            record(result, row);
            long now = System.currentTimeMillis();
            if (now - lastReport[0] >= PROGRESS_INTERVAL_MS) {
                lastReport[0] = now;
                settings.progress.accept(new Progress(result.tested, expected, result.count(Outcome.FAILS),
                        g.getVerticesCount()));
            }
            if (settings.stopAtFirst && row.overall() == Outcome.FAILS) {
                result.stoppedAtFirst = true;
                return false;
            }
            return true;
        });
        result.millis = System.currentTimeMillis() - start;
        return result;
    }

    private static boolean holds(Expression condition, Expression.Variables vars, Result result) {
        try {
            double v = condition.evaluate(vars);
            return v != 0 && !Double.isNaN(v);
        } catch (RuntimeException e) {
            noteError(result, e);
            return false;
        }
    }

    private static Row test(Conjecture conjecture, GraphModel g, Expression.Variables vars, int k, long index,
                            Result result) {
        double[] values = new double[k + 1];
        Outcome[] outcomes = new Outcome[k];
        try {
            conjecture.evaluate(g, vars, values);
            for (int i = 0; i < k; i++) {
                outcomes[i] = conjecture.relation().compare(values[0], values[i + 1]);
            }
        } catch (RuntimeException | StackOverflowError e) {
            noteError(result, e);
            Arrays.fill(values, Double.NaN);
            Arrays.fill(outcomes, Outcome.UNDEFINED);
        }
        return new Row(index, g, values, outcomes);
    }

    private static void noteError(Result result, Throwable e) {
        if (result.firstError == null) {
            result.firstError = e instanceof Invariants.EvaluationException ? e.getMessage() : Invariants.describe(e);
        }
    }

    private static void record(Result result, Row row) {
        for (int i = 0; i < result.counts.length; i++) {
            result.counts[i][row.outcome(i).ordinal()]++;
        }
        int o = row.overall().ordinal();
        result.overall[o]++;
        if (result.overall[o] <= KEEP) {
            result.rows.add(row);
        } else {
            result.truncated[o] = true;
        }
    }
}
