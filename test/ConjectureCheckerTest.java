import graphtea.extensions.G6Format;
import graphtea.extensions.generators.CompleteGraphGenerator;
import graphtea.extensions.reports.ChromaticNumber;
import graphtea.extensions.reports.basicreports.IsBipartite;
import graphtea.extensions.reports.boundcheck.Conjecture;
import graphtea.extensions.reports.boundcheck.ConjectureRunner;
import graphtea.extensions.reports.boundcheck.Example;
import graphtea.extensions.reports.boundcheck.Expression;
import graphtea.extensions.reports.boundcheck.GraphClass;
import graphtea.extensions.reports.boundcheck.GraphSource;
import graphtea.extensions.reports.boundcheck.Invariants;
import graphtea.extensions.reports.boundcheck.Outcome;
import graphtea.extensions.reports.boundcheck.Relation;
import graphtea.extensions.reports.boundcheck.SmallGraphs;
import graphtea.extensions.reports.clique.MaxCliqueSize;
import graphtea.extensions.reports.energy.Energy;
import graphtea.extensions.reports.topological.M3BoundConjecture;
import graphtea.extensions.reports.topological.WienerIndex;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the conjecture checker's engine: formulas, graph enumeration, and that each built-in
 * example says what its explanation promises.
 */
public class ConjectureCheckerTest {

    private static final List<GraphReportExtension<?>> REPORTS = List.of(
            new Energy(), new WienerIndex(), new ChromaticNumber(), new MaxCliqueSize(), new IsBipartite());

    private static final Invariants INV = Invariants.withReports(REPORTS);

    private static double eval(String formula, Map<String, Double> vars) {
        return Expression.parse(formula).evaluate(vars::get);
    }

    @Test
    public void arithmeticFollowsUsualPrecedence() {
        Map<String, Double> v = Map.of("n", 5.0, "m", 4.0);
        assertEquals(14, eval("2 + 3*m", v), 1e-12);
        assertEquals(-25, eval("-n^2", v), 1e-12);
        assertEquals(2, eval("2^3^0", v), 1e-12);
        assertEquals(Math.sqrt(40), eval("sqrt(2*m*n)", v), 1e-12);
        assertEquals(10, eval("choose(n, 2)", v), 1e-12);
        assertEquals(3, eval("max(1, 3, 2)", v), 1e-12);
        assertEquals(Math.PI, eval("pi", v), 1e-12);
        assertEquals(1, eval("m < n and not n = 4", v), 1e-12);
        assertEquals(20, eval("2 × m · 2.5", v), 1e-12);
    }

    @Test
    public void divisionByZeroIsUndefinedNotInfinite() {
        assertTrue(Double.isNaN(eval("n / m", Map.of("n", 3.0, "m", 0.0))));
    }

    @Test
    public void aComparisonExposesItsSides() {
        Expression e = Expression.parse("  energy ≤ 2*sqrt(m) + n ");
        assertEquals(Relation.AT_MOST, e.relation());
        assertEquals("energy", e.left().text());
        assertEquals("2*sqrt(m) + n", e.right().text());
        assertEquals(List.of("energy"), new ArrayList<>(e.left().names()));
        assertEquals(List.of("m", "n"), new ArrayList<>(e.right().names()));
        assertNull(Expression.parse("m <= n and n <= 3").relation());
    }

    @Test
    public void syntaxErrorsSayWhere() {
        Expression.SyntaxException e = assertThrows(Expression.SyntaxException.class,
                () -> Expression.parse("energy <= 2*"));
        assertEquals(12, e.position());
        assertThrows(Expression.SyntaxException.class, () -> Expression.parse("sqroot(m) > 1"));
        assertThrows(Expression.SyntaxException.class, () -> Expression.parse("pow(m) > 1"));
        assertThrows(Expression.SyntaxException.class, () -> Expression.parse(""));
    }

    @Test
    public void unknownNamesGetASuggestion() {
        String msg = INV.unknownName(Expression.parse("enrgy <= 2*m"));
        assertNotNull(msg);
        assertTrue(msg.contains("energy"), msg);
        assertNull(INV.unknownName(Expression.parse("wiener_index >= n*(n-1) - m")));
    }

    @Test
    public void reportsAreNamedFromTheirTitles() {
        assertEquals("number_of_triangles", Invariants.idFor("Number of Triangles"));
        assertEquals("r_2_connected", Invariants.idFor("2-Connected"));
        assertNotNull(INV.get("energy"));
        assertNotNull(INV.get("is_bipartite"));
        assertNull(INV.get("number_of_independent_sets"), "only the reports given are listed");
    }

    @Test
    public void tableReportsAreNotInvariants() {
        Invariants inv = Invariants.withReports(List.of(new M3BoundConjecture()));
        assertNull(inv.get("m3_bound_conjecture"));
    }

    @Test
    public void relationsTolerateRoundingButNotMore() {
        assertEquals(Outcome.TIGHT, Relation.AT_MOST.compare(2.0000000000001, 2));
        assertEquals(Outcome.TIGHT, Relation.AT_MOST.compare(2.828427, 2.82843), "energies are rounded");
        assertEquals(Outcome.FAILS, Relation.AT_MOST.compare(2.001, 2));
        assertEquals(Outcome.FAILS, Relation.EQUAL.compare(1_000_000_000, 1_000_000_001), "integers stay exact");
        assertEquals(Outcome.FAILS, Relation.LESS.compare(2, 2));
        assertEquals(Outcome.HOLDS, Relation.EQUAL.compare(Math.sqrt(2) * Math.sqrt(2), 2));
        assertEquals(Outcome.UNDEFINED, Relation.GREATER.compare(Double.NaN, 1));
    }

    @Test
    public void enumerationMatchesKnownCounts() {
        for (int n = 0; n <= 7; n++) {
            assertEquals(SmallGraphs.count(GraphClass.ALL, n), SmallGraphs.all(n).size(), "all graphs on " + n);
            long connected = SmallGraphs.all(n).stream().filter(SmallGraphs::isConnected).count();
            assertEquals(SmallGraphs.count(GraphClass.CONNECTED, n), n == 0 ? 1 : connected, "connected on " + n);
        }
        for (int n = 1; n <= 14; n++) {
            assertEquals(SmallGraphs.count(GraphClass.TREES, n), SmallGraphs.trees(n).size(), "trees on " + n);
        }
    }

    @Test
    public void eightVertexEnumerationIsComplete() {
        assertEquals(12346, SmallGraphs.all(8).size());
    }

    @Test
    public void randomTreesAreTrees() throws IOException {
        GraphSource src = GraphSource.random(GraphClass.TREES, 2, 12, 0, 5, 7);
        List<GraphModel> seen = new ArrayList<>();
        src.forEach(g -> seen.add(g));
        assertEquals(55, seen.size());
        for (GraphModel g : seen) {
            assertTrue(GraphClass.TREES.contains(g));
        }
    }

    @Test
    public void graph6FilesAreRead() throws IOException {
        File f = File.createTempFile("conj", ".g6");
        f.deleteOnExit();
        Files.writeString(f.toPath(), ">>graph6<<Dhc\n\nCF\n");
        GraphSource src = GraphSource.file(f, GraphClass.ALL);
        assertEquals(2, src.expectedCount());
        List<Integer> sizes = new ArrayList<>();
        src.forEach(g -> sizes.add(g.getVerticesCount()));
        assertEquals(List.of(5, 4), sizes);
    }

    @Test
    public void runnerCountsAndKeepsCounterexamples() throws IOException {
        Conjecture c = Conjecture.formula(Expression.parse("m <= n"));
        ConjectureRunner.Result r = ConjectureRunner.run(c, INV, GraphSource.exhaustive(GraphClass.ALL, 1, 5),
                new ConjectureRunner.Settings());
        assertEquals(1 + 2 + 4 + 11 + 34, r.tested());
        assertEquals(r.tested(), r.count(Outcome.HOLDS) + r.count(Outcome.TIGHT) + r.count(Outcome.FAILS));
        assertTrue(r.count(Outcome.FAILS) > 0);
        for (ConjectureRunner.Row row : r.rows()) {
            assertEquals(row.m() > row.n(), row.overall() == Outcome.FAILS);
            assertEquals(row.m() == row.n(), row.overall() == Outcome.TIGHT);
        }
    }

    @Test
    public void conditionsSkipGraphs() throws IOException {
        Conjecture c = Conjecture.formula(Expression.parse("m >= n - 1"));
        ConjectureRunner.Result r = ConjectureRunner.run(c, INV, GraphSource.exhaustive(GraphClass.ALL, 1, 6),
                new ConjectureRunner.Settings().condition(Expression.parse("m = n")));
        assertEquals(0, r.count(Outcome.FAILS));
        assertTrue(r.excluded() > 0);
        assertEquals(0, r.count(Outcome.TIGHT), "m = n graphs never have m = n - 1");
    }

    @Test
    public void cancellingStops() throws IOException {
        int[] calls = {0};
        Conjecture c = Conjecture.formula(Expression.parse("m >= 0"));
        ConjectureRunner.Result r = ConjectureRunner.run(c, INV, GraphSource.exhaustive(GraphClass.ALL, 1, 7),
                new ConjectureRunner.Settings().cancelled(() -> ++calls[0] > 10));
        assertTrue(r.cancelled());
        assertEquals(10, r.tested());
    }

    @Test
    public void reportFailuresAreUndefinedNotFatal() throws IOException {
        GraphReportExtension<Integer> broken = new GraphReportExtension<>() {
            public String getName() {
                return "Broken";
            }

            public String getDescription() {
                return "";
            }

            public Integer calculate(GraphModel g) {
                throw new IllegalStateException("boom");
            }

            public String getCategory() {
                return null;
            }
        };
        Invariants inv = Invariants.withReports(List.of(broken));
        ConjectureRunner.Result r = ConjectureRunner.run(Conjecture.formula(Expression.parse("broken >= 0")), inv,
                GraphSource.exhaustive(GraphClass.ALL, 1, 3), new ConjectureRunner.Settings());
        assertEquals(r.tested(), r.count(Outcome.UNDEFINED));
        assertTrue(r.firstError().contains("boom"), r.firstError());
    }

    // ------------------------------------------------------------------ the examples

    private static ConjectureRunner.Result runExample(String titleStart) throws IOException {
        Example ex = Example.all().stream().filter(e -> e.title().startsWith(titleStart)).findFirst().orElseThrow();
        Conjecture c;
        if (ex.formula() != null) {
            c = Conjecture.formula(Expression.parse(ex.formula()));
            assertNull(INV.unknownName(Expression.parse(ex.formula())), ex.title());
        } else {
            M3BoundConjecture report = new M3BoundConjecture();
            assertEquals(report.getName(), ex.report());
            List<String> titles = Conjecture.columnTitles(report);
            int[] rights = new int[titles.size() - 1];
            for (int i = 0; i < rights.length; i++) {
                rights[i] = i + 1;
            }
            c = Conjecture.columns(report, titles, 0, rights, ex.relation());
        }
        GraphSource src;
        switch (ex.source()) {
            case FAMILY:
                CompleteGraphGenerator gen = new CompleteGraphGenerator();
                assertEquals(gen.getName(), ex.generator());
                src = GraphSource.family(gen, GraphSource.integerParameter(gen), ex.from(), ex.to());
                break;
            case RANDOM:
                src = GraphSource.random(ex.graphClass(), ex.from(), ex.to(), ex.probability(), ex.perSize(), 1);
                break;
            default:
                src = GraphSource.exhaustive(ex.graphClass(), ex.from(), ex.to());
        }
        ConjectureRunner.Settings s = new ConjectureRunner.Settings().stopAtFirst(ex.stopAtFirst());
        if (!ex.condition().isEmpty()) {
            s.condition(Expression.parse(ex.condition()));
        }
        ConjectureRunner.Result r = ConjectureRunner.run(c, INV, src, s);
        assertNull(r.firstError(), ex.title());
        return r;
    }

    @Test
    public void energyAtMostTwoM() throws IOException {
        ConjectureRunner.Result r = runExample("Energy is at most");
        assertEquals(0, r.count(Outcome.FAILS));
        for (ConjectureRunner.Row row : r.rows()) {
            if (row.overall() == Outcome.TIGHT) {
                for (var v : row.graph()) {
                    assertTrue(row.graph().getDegree(v) <= 1, "tight graphs are matchings");
                }
            }
        }
        assertTrue(r.count(Outcome.TIGHT) > 0);
    }

    @Test
    public void energyAtLeastTwoRootM() throws IOException {
        ConjectureRunner.Result r = runExample("Energy is at least");
        assertEquals(0, r.count(Outcome.FAILS));
        // Connected complete bipartite graphs on 2..7 vertices: K(a,b) with 1 <= a <= b, a+b = n.
        assertEquals(1 + 1 + 2 + 2 + 3 + 3, r.count(Outcome.TIGHT));
    }

    @Test
    public void chromaticVersusCliqueStopsAtC5() throws IOException {
        ConjectureRunner.Result r = runExample("Chromatic number equals");
        assertTrue(r.stoppedAtFirst());
        ConjectureRunner.Row first = r.rows().stream().filter(x -> x.overall() == Outcome.FAILS).findFirst()
                .orElseThrow();
        assertEquals(5, first.n());
        assertEquals(5, first.m());
        assertEquals(3, first.left(), 0);
        assertEquals(2, first.right(0), 0);
    }

    @Test
    public void pathMaximisesTreeWiener() throws IOException {
        ConjectureRunner.Result r = runExample("Among trees");
        assertEquals(0, r.count(Outcome.FAILS));
        assertEquals(13, r.count(Outcome.TIGHT), "one path for each n from 2 to 14");
    }

    @Test
    public void bipartiteConditionExample() throws IOException {
        ConjectureRunner.Result r = runExample("Bipartite graphs");
        assertEquals(0, r.count(Outcome.FAILS));
        assertTrue(r.excluded() > 0);
    }

    @Test
    public void completeGraphEnergy() throws IOException {
        ConjectureRunner.Result r = runExample("Energy of the complete");
        assertEquals(29, r.tested());
        assertEquals(29, r.count(Outcome.HOLDS));
    }

    @Test
    public void wienerLowerBoundOnRandomGraphs() throws IOException {
        ConjectureRunner.Result r = runExample("Wiener index of a connected");
        assertEquals(21 * 20, r.tested());
        assertEquals(0, r.count(Outcome.FAILS));
        assertTrue(r.count(Outcome.TIGHT) > r.tested() / 2, "most have diameter 2");
    }

    @Test
    public void reportColumnsExample() throws IOException {
        ConjectureRunner.Result r = runExample("Which bounds");
        assertEquals(11, r.conjecture().rightLabels().size());
        assertEquals(853 + 112 + 21 + 6 + 2, r.tested());
        long valid = 0;
        for (int i = 0; i < 11; i++) {
            if (r.count(i, Outcome.FAILS) == 0) {
                valid++;
            }
        }
        System.out.println("M3 bounds valid on connected graphs, n = 3..7: " + valid + " of 11");
    }

    @Test
    public void graph6RoundTrip() {
        for (int[] adj : SmallGraphs.all(5)) {
            GraphModel g = SmallGraphs.toGraph(adj);
            GraphModel back = G6Format.stringToGraphModel(G6Format.graphToG6(g));
            assertEquals(g.getEdgesCount(), back.getEdgesCount());
        }
    }

    @Test
    public void columnTitlesComeFromASample() {
        GraphReportExtension<RenderTable> report = new M3BoundConjecture();
        assertEquals(12, Conjecture.columnTitles(report).size());
    }
}
