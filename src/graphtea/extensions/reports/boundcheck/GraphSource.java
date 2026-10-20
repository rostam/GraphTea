// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import graphtea.extensions.G6Format;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.parameter.Parameter;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Where the graphs a conjecture is tested on come from.
 *
 * @author M. Ali Rostami
 */
public abstract class GraphSource {

    /** Receives the graphs one at a time. */
    public interface Sink {
        /**
         * @param g a graph to test
         * @return false to stop: the run was cancelled or has its answer
         */
        boolean accept(GraphModel g);
    }

    /** Names of the nauty program that lists graphs, as installed by various systems. */
    private static final String[] GENG_NAMES = {"geng", "nauty-geng", "geng.exe"};

    /** @return what will be tested, for the summary line, e.g. "connected graphs on 3–7 vertices" */
    public abstract String describe();

    /** @return how many graphs will be produced, or -1 if that is not known in advance */
    public abstract long expectedCount();

    /**
     * Produces the graphs.
     *
     * @param sink where to send them
     * @throws IOException if they are read from a file or another program, and that fails
     */
    public abstract void forEach(Sink sink) throws IOException;

    // ------------------------------------------------------------------ factories

    /**
     * Every graph of a class with a number of vertices in a range, one per isomorphism class.
     *
     * @param cls  the class
     * @param from the fewest vertices
     * @param to   the most vertices
     * @return the source
     */
    public static GraphSource exhaustive(GraphClass cls, int from, int to) {
        return new Exhaustive(cls, from, to);
    }

    /**
     * Members of a generator family, such as the cycles C<sub>n</sub>, for n in a range. The
     * generator's other parameters keep their current values.
     *
     * @param generator a generator with an integer parameter
     * @param parameter that parameter, normally n
     * @param from      the first value
     * @param to        the last value
     * @return the source
     */
    public static GraphSource family(GraphGeneratorExtension generator, Field parameter, int from, int to) {
        return new Family(generator, parameter, from, to);
    }

    /**
     * Random graphs: G(n, p) for all graphs and connected graphs (redrawing until connected),
     * and uniformly random labelled trees, from random Prüfer sequences.
     *
     * @param cls     the class
     * @param from    the fewest vertices
     * @param to      the most vertices
     * @param p       the edge probability, ignored for trees
     * @param perSize how many graphs to draw for each number of vertices
     * @param seed    the random seed, so that a run can be repeated
     * @return the source
     */
    public static GraphSource random(GraphClass cls, int from, int to, double p, int perSize, long seed) {
        return new RandomGraphs(cls, from, to, p, perSize, seed);
    }

    /**
     * The graphs in a graph6 file, one per line, as written by nauty or by GraphTea.
     *
     * @param file the file
     * @param cls  only graphs of this class are tested
     * @return the source
     */
    public static GraphSource file(File file, GraphClass cls) {
        return new G6File(file.toPath(), cls);
    }

    /**
     * Fixed graphs, such as those open in tabs.
     *
     * @param graphs      the graphs
     * @param description what they are, for the summary
     * @param cls         only graphs of this class are tested
     * @return the source
     */
    public static GraphSource of(List<GraphModel> graphs, String description, GraphClass cls) {
        return new Fixed(graphs, description, cls);
    }

    /**
     * Finds the parameter a generator family is indexed by: its integer parameter named n, or
     * failing that its first integer parameter.
     *
     * @param generator a generator
     * @return the parameter's field, or null if the generator has no integer parameter
     */
    public static Field integerParameter(GraphGeneratorExtension generator) {
        Field first = null;
        for (Field f : generator.getClass().getFields()) {
            Parameter p = f.getAnnotation(Parameter.class);
            if (p == null || (f.getType() != int.class && f.getType() != Integer.class)) {
                continue;
            }
            if ("n".equalsIgnoreCase(p.name()) || "n".equalsIgnoreCase(f.getName())) {
                return f;
            }
            if (first == null) {
                first = f;
            }
        }
        return first;
    }

    /**
     * @return nauty's geng program if it is on the PATH, otherwise null
     */
    public static File findGeng() {
        String path = System.getenv("PATH");
        if (path == null) {
            return null;
        }
        for (String dir : path.split(File.pathSeparator)) {
            for (String name : GENG_NAMES) {
                File f = new File(dir, name);
                if (f.isFile() && f.canExecute()) {
                    return f;
                }
            }
        }
        return null;
    }

    /**
     * @param cls a class of graphs
     * @return the most vertices {@link #exhaustive} can list for it on this computer
     */
    public static int exhaustiveLimit(GraphClass cls) {
        if (cls == GraphClass.TREES) {
            return SmallGraphs.MAX_TREES;
        }
        return findGeng() != null ? 11 : SmallGraphs.MAX_ALL;
    }

    static String range(int from, int to, String unit) {
        return from == to ? from + " " + unit : from + "–" + to + " " + unit;
    }

    // ------------------------------------------------------------------ implementations

    private static final class Exhaustive extends GraphSource {
        private final GraphClass cls;
        private final int from;
        private final int to;

        Exhaustive(GraphClass cls, int from, int to) {
            this.cls = cls;
            this.from = from;
            this.to = to;
        }

        @Override
        public String describe() {
            String what = cls == GraphClass.ALL ? "all graphs" : cls.toString().toLowerCase(Locale.ROOT);
            return what + " on " + range(from, to, "vertices");
        }

        @Override
        public long expectedCount() {
            long total = 0;
            for (int n = from; n <= to; n++) {
                long c = SmallGraphs.count(cls, n);
                if (c < 0) {
                    return -1;
                }
                total += c;
            }
            return total;
        }

        @Override
        public void forEach(Sink sink) throws IOException {
            for (int n = from; n <= to; n++) {
                if (!level(n, sink)) {
                    return;
                }
            }
        }

        private boolean level(int n, Sink sink) throws IOException {
            if (cls == GraphClass.TREES) {
                for (int[] t : SmallGraphs.trees(n)) {
                    if (!sink.accept(SmallGraphs.treeToGraph(t))) {
                        return false;
                    }
                }
                return true;
            }
            if (n <= SmallGraphs.MAX_ALL) {
                for (int[] g : SmallGraphs.all(n)) {
                    if (cls == GraphClass.CONNECTED && !SmallGraphs.isConnected(g)) {
                        continue;
                    }
                    if (!sink.accept(SmallGraphs.toGraph(g))) {
                        return false;
                    }
                }
                return true;
            }
            return geng(n, sink);
        }

        private boolean geng(int n, Sink sink) throws IOException {
            File geng = findGeng();
            if (geng == null) {
                throw new IOException("Listing all graphs on more than " + SmallGraphs.MAX_ALL
                        + " vertices needs nauty's geng program, which was not found on the PATH.");
            }
            List<String> cmd = new ArrayList<>();
            cmd.add(geng.getPath());
            cmd.add(cls == GraphClass.CONNECTED ? "-cq" : "-q");
            cmd.add(Integer.toString(n));
            Process p = new ProcessBuilder(cmd).redirectError(ProcessBuilder.Redirect.DISCARD).start();
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream(), StandardCharsets.US_ASCII))) {
                String line;
                while ((line = in.readLine()) != null) {
                    if (!line.isBlank() && !sink.accept(G6Format.stringToGraphModel(line.trim()))) {
                        return false;
                    }
                }
                return true;
            } finally {
                p.destroy();
            }
        }
    }

    private static final class Family extends GraphSource {
        private final GraphGeneratorExtension generator;
        private final Field parameter;
        private final int from;
        private final int to;

        Family(GraphGeneratorExtension generator, Field parameter, int from, int to) {
            this.generator = generator;
            this.parameter = parameter;
            this.from = from;
            this.to = to;
        }

        @Override
        public String describe() {
            return generator.getName() + " for " + parameter.getName() + " = " + range(from, to, "").trim();
        }

        @Override
        public long expectedCount() {
            return to - from + 1L;
        }

        @Override
        public void forEach(Sink sink) {
            Object target = Modifier.isStatic(parameter.getModifiers()) ? null : generator;
            Object original;
            try {
                original = parameter.get(target);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
            try {
                for (int v = from; v <= to; v++) {
                    parameter.set(target, v);
                    if (!sink.accept(generator.generateGraph())) {
                        return;
                    }
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            } finally {
                // The generator is the one in the menu; leave its parameter as the user set it.
                try {
                    parameter.set(target, original);
                } catch (IllegalAccessException ignored) {
                    // set() succeeded above, so it cannot fail here.
                }
            }
        }
    }

    private static final class RandomGraphs extends GraphSource {
        private static final int CONNECT_ATTEMPTS = 1000;

        private final GraphClass cls;
        private final int from;
        private final int to;
        private final double p;
        private final int perSize;
        private final long seed;

        RandomGraphs(GraphClass cls, int from, int to, double p, int perSize, long seed) {
            this.cls = cls;
            this.from = from;
            this.to = to;
            this.p = p;
            this.perSize = perSize;
            this.seed = seed;
        }

        @Override
        public String describe() {
            String what = cls == GraphClass.TREES ? "random trees" : cls == GraphClass.CONNECTED
                    ? "random connected graphs G(n, " + p + ")" : "random graphs G(n, " + p + ")";
            return perSize + " " + what + " for each n = " + range(from, to, "").trim() + " (seed " + seed + ")";
        }

        @Override
        public long expectedCount() {
            return (long) perSize * (to - from + 1);
        }

        @Override
        public void forEach(Sink sink) {
            Random rnd = new Random(seed);
            for (int n = from; n <= to; n++) {
                for (int k = 0; k < perSize; k++) {
                    if (!sink.accept(draw(n, rnd))) {
                        return;
                    }
                }
            }
        }

        private GraphModel draw(int n, Random rnd) {
            if (cls == GraphClass.TREES) {
                return SmallGraphs.treeToGraph(pruferTree(n, rnd));
            }
            for (int attempt = 0; attempt < CONNECT_ATTEMPTS; attempt++) {
                GraphModel g = gnp(n, rnd);
                if (cls != GraphClass.CONNECTED || GraphClass.isConnected(g)) {
                    return g;
                }
            }
            throw new IllegalStateException("No connected graph on " + n + " vertices came up in "
                    + CONNECT_ATTEMPTS + " tries with edge probability " + p + ". Try a larger probability.");
        }

        private GraphModel gnp(int n, Random rnd) {
            GraphModel g = new GraphModel(false);
            Vertex[] vs = new Vertex[n];
            for (int i = 0; i < n; i++) {
                vs[i] = new Vertex();
                g.addVertex(vs[i]);
            }
            for (int j = 1; j < n; j++) {
                for (int i = 0; i < j; i++) {
                    if (rnd.nextDouble() < p) {
                        g.addEdge(new Edge(vs[i], vs[j]));
                    }
                }
            }
            return g;
        }

        /** @return parent links, as {@link SmallGraphs#treeToGraph} takes, of a uniform labelled tree */
        static int[] pruferTree(int n, Random rnd) {
            if (n <= 1) {
                return new int[0];
            }
            int[] seq = new int[n - 2];
            for (int i = 0; i < seq.length; i++) {
                seq[i] = rnd.nextInt(n);
            }
            int[] degree = new int[n];
            Arrays.fill(degree, 1);
            for (int s : seq) {
                degree[s]++;
            }
            int[][] edges = new int[n - 1][];
            int e = 0;
            for (int s : seq) {
                for (int leaf = 0; leaf < n; leaf++) {
                    if (degree[leaf] == 1) {
                        edges[e++] = new int[] {leaf, s};
                        degree[leaf]--;
                        degree[s]--;
                        break;
                    }
                }
            }
            int u = -1;
            for (int v = 0; v < n; v++) {
                if (degree[v] == 1) {
                    if (u < 0) {
                        u = v;
                    } else {
                        edges[e++] = new int[] {u, v};
                    }
                }
            }
            return toParentLinks(edges, n);
        }

        /** Re-roots an edge list at vertex 0 and numbers the vertices in BFS order. */
        private static int[] toParentLinks(int[][] edges, int n) {
            List<List<Integer>> nb = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                nb.add(new ArrayList<>());
            }
            for (int[] ed : edges) {
                nb.get(ed[0]).add(ed[1]);
                nb.get(ed[1]).add(ed[0]);
            }
            int[] order = new int[n];
            int[] newIndex = new int[n];
            Arrays.fill(newIndex, -1);
            int[] parent = new int[n - 1];
            newIndex[0] = 0;
            int head = 0;
            int tail = 1;
            while (head < tail) {
                int v = order[head++];
                for (int w : nb.get(v)) {
                    if (newIndex[w] < 0) {
                        newIndex[w] = tail;
                        order[tail] = w;
                        parent[tail - 1] = newIndex[v];
                        tail++;
                    }
                }
            }
            return parent;
        }
    }

    private static final class G6File extends GraphSource {
        private final Path path;
        private final GraphClass cls;

        G6File(Path path, GraphClass cls) {
            this.path = path;
            this.cls = cls;
        }

        @Override
        public String describe() {
            String what = cls == GraphClass.ALL ? "the graphs" : "the " + cls.toString().toLowerCase(Locale.ROOT);
            return what + " in " + path.getFileName();
        }

        @Override
        public long expectedCount() {
            try (var lines = Files.lines(path, StandardCharsets.US_ASCII)) {
                return lines.filter(l -> !l.isBlank()).count();
            } catch (IOException | java.io.UncheckedIOException e) {
                return -1;
            }
        }

        @Override
        public void forEach(Sink sink) throws IOException {
            try (BufferedReader in = Files.newBufferedReader(path, StandardCharsets.US_ASCII)) {
                String line;
                int lineNo = 0;
                while ((line = in.readLine()) != null) {
                    lineNo++;
                    GraphModel g = parse(line.trim(), lineNo);
                    if (g != null && cls.contains(g) && !sink.accept(g)) {
                        return;
                    }
                }
            }
        }

        private GraphModel parse(String line, int lineNo) throws IOException {
            String s = line.startsWith(">>graph6<<") ? line.substring(">>graph6<<".length()) : line;
            if (s.isEmpty()) {
                return null;
            }
            if (s.startsWith(":") || s.startsWith(">>sparse6<<")) {
                throw new IOException(path.getFileName() + " is in sparse6 format, which is not supported. "
                        + "Convert it with nauty's copyg -g.");
            }
            try {
                return G6Format.stringToGraphModel(s);
            } catch (RuntimeException e) {
                throw new IOException("Line " + lineNo + " of " + path.getFileName()
                        + " is not a graph in graph6 format: " + s, e);
            }
        }
    }

    private static final class Fixed extends GraphSource {
        private final List<GraphModel> graphs;
        private final String description;
        private final GraphClass cls;

        Fixed(List<GraphModel> graphs, String description, GraphClass cls) {
            this.graphs = graphs;
            this.description = description;
            this.cls = cls;
        }

        @Override
        public String describe() {
            return description;
        }

        @Override
        public long expectedCount() {
            return graphs.size();
        }

        @Override
        public void forEach(Sink sink) {
            for (GraphModel g : graphs) {
                if (cls.contains(g) && !sink.accept(g)) {
                    return;
                }
            }
        }
    }
}
