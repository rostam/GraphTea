// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * A claim that one quantity is related to one or more others on every graph, e.g.
 * {@code energy <= 2*m}, or "column 0 of the M3 Bound Conjecture report is at most each of
 * its other columns".
 *
 * @author M. Ali Rostami
 */
public abstract class Conjecture {

    /** Small connected graphs to find a table report's columns with: C6 with two chords, and K4. */
    private static final int[][] SAMPLES = {
        {0b101110, 0b000101, 0b001011, 0b010101, 0b101000, 0b010001},
        {0b1110, 0b1101, 0b1011, 0b0111},
    };

    /** @return the claim, for the result summary */
    public abstract String statement();

    /** @return what the left side is */
    public abstract String leftLabel();

    /** @return what each right side is; one comparison is made per entry */
    public abstract List<String> rightLabels();

    /** @return the claimed relation between the left side and each right side */
    public abstract Relation relation();

    /**
     * Computes both sides for one graph.
     *
     * @param g    the graph
     * @param vars the graph's invariants
     * @param out  receives the left side at index 0 and the right sides after it; NaN where a
     *             side is undefined
     */
    public abstract void evaluate(GraphModel g, Expression.Variables vars, double[] out);

    /**
     * @param formula a single comparison, such as {@code energy >= 2*sqrt(m)}
     * @return the conjecture
     * @throws IllegalArgumentException if the formula is not a single comparison
     */
    public static Conjecture formula(Expression formula) {
        if (formula.relation() == null) {
            throw new IllegalArgumentException("A conjecture compares two sides, such as energy <= 2*m. "
                    + "Write it with one of <=, <, >=, >, = or !=.");
        }
        return new Formula(formula);
    }

    /**
     * @param report       a report that answers with a table of one row
     * @param titles       the report's column titles
     * @param leftColumn   the column with the quantity being bounded
     * @param rightColumns the columns with the candidate bounds
     * @param relation     the claimed relation
     * @return the conjecture
     */
    public static Conjecture columns(GraphReportExtension<RenderTable> report, List<String> titles, int leftColumn,
                                     int[] rightColumns, Relation relation) {
        return new Columns(report, titles, leftColumn, rightColumns, relation);
    }

    /**
     * Finds out a table report's column titles, which are only known once it has run, by
     * running it on small sample graphs until one gives an answer.
     *
     * @param report a report that answers with a table
     * @return its column titles
     * @throws IllegalStateException if it gave no answer on any sample
     */
    public static List<String> columnTitles(GraphReportExtension<RenderTable> report) {
        String problem = "it gave no answer";
        for (int[] sample : SAMPLES) {
            try {
                RenderTable t = report.calculate(SmallGraphs.toGraph(sample));
                if (t != null && !t.getTitles().isEmpty()) {
                    return new ArrayList<>(t.getTitles());
                }
            } catch (RuntimeException e) {
                problem = Invariants.describe(e);
            }
        }
        throw new IllegalStateException("Could not find the columns of " + report.getName() + ": " + problem);
    }

    /**
     * Reads a table cell as a number.
     *
     * @param cell a value from a report's table
     * @return the number, or NaN when the cell holds something else
     */
    public static double number(Object cell) {
        if (cell instanceof Number num) {
            return num.doubleValue();
        }
        if (cell instanceof Boolean b) {
            return b ? 1 : 0;
        }
        if (cell instanceof String s) {
            try {
                return Double.parseDouble(s.trim());
            } catch (NumberFormatException e) {
                return Double.NaN;
            }
        }
        return Double.NaN;
    }

    private static final class Formula extends Conjecture {
        private final Expression formula;

        Formula(Expression formula) {
            this.formula = formula;
        }

        @Override
        public String statement() {
            return formula.left().text() + " " + formula.relation().symbol() + " " + formula.right().text();
        }

        @Override
        public String leftLabel() {
            return formula.left().text();
        }

        @Override
        public List<String> rightLabels() {
            return List.of(formula.right().text());
        }

        @Override
        public Relation relation() {
            return formula.relation();
        }

        @Override
        public void evaluate(GraphModel g, Expression.Variables vars, double[] out) {
            out[0] = formula.left().evaluate(vars);
            out[1] = formula.right().evaluate(vars);
        }
    }

    private static final class Columns extends Conjecture {
        private final GraphReportExtension<RenderTable> report;
        private final List<String> titles;
        private final int leftColumn;
        private final int[] rightColumns;
        private final Relation relation;

        Columns(GraphReportExtension<RenderTable> report, List<String> titles, int leftColumn, int[] rightColumns,
                Relation relation) {
            this.report = report;
            this.titles = titles;
            this.leftColumn = leftColumn;
            this.rightColumns = rightColumns.clone();
            this.relation = relation;
        }

        @Override
        public String statement() {
            return leftLabel() + " " + relation.symbol() + " each of " + rightColumns.length + " columns of "
                    + report.getName();
        }

        @Override
        public String leftLabel() {
            return titles.get(leftColumn).trim();
        }

        @Override
        public List<String> rightLabels() {
            List<String> labels = new ArrayList<>();
            for (int c : rightColumns) {
                labels.add(titles.get(c).trim());
            }
            return labels;
        }

        @Override
        public Relation relation() {
            return relation;
        }

        @Override
        public void evaluate(GraphModel g, Expression.Variables vars, double[] out) {
            RenderTable table = report.calculate(g);
            List<Object> row = table == null ? null : table.peek();
            if (row == null) {
                throw new Invariants.EvaluationException(report.getName() + " gave no answer.", null);
            }
            out[0] = cell(row, leftColumn);
            for (int i = 0; i < rightColumns.length; i++) {
                out[i + 1] = cell(row, rightColumns[i]);
            }
        }

        private static double cell(List<Object> row, int column) {
            return column < row.size() ? number(row.get(column)) : Double.NaN;
        }
    }
}
