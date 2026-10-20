// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Lists every graph on a few vertices, one per isomorphism class, without nauty.
 *
 * <p>Graphs are kept as adjacency bit masks. The graphs on {@code k} vertices are made from
 * those on {@code k - 1} by adding a vertex joined to each subset of the old ones, and
 * duplicates are removed by a canonical form: the largest adjacency code over the vertex
 * orders that respect a colour refinement, found by a search that drops an order as soon as
 * its prefix is smaller than the best one so far. Trees are listed the same way, adding a
 * leaf at a time, with the classical centre-rooted encoding as the canonical form, which is
 * cheap enough for much larger trees.
 *
 * @author M. Ali Rostami
 */
public final class SmallGraphs {

    /** The most vertices {@link #all(int)} handles. */
    public static final int MAX_ALL = 8;

    /** The most vertices {@link #trees(int)} handles. */
    public static final int MAX_TREES = 18;

    /** Number of graphs on n vertices, up to isomorphism (OEIS A000088). */
    private static final long[] ALL = {1, 1, 2, 4, 11, 34, 156, 1044, 12346, 274668, 12005168, 1018997864};

    /** Number of connected graphs on n vertices (OEIS A001349). */
    private static final long[] CONNECTED = {1, 1, 1, 2, 6, 21, 112, 853, 11117, 261080, 11716571, 1006700565};

    /** Number of trees on n vertices (OEIS A000055). */
    private static final long[] TREES = {1, 1, 1, 1, 2, 3, 6, 11, 23, 47, 106, 235, 551, 1301, 3159, 7741,
        19320, 48629, 123867, 317955, 823065, 2144505, 5623756};

    private static final Map<Integer, List<int[]>> ALL_CACHE = new HashMap<>();

    private SmallGraphs() {
    }

    /**
     * @param cls a class of graphs
     * @param n   a number of vertices
     * @return how many graphs of that class there are on n vertices, or -1 if not known here
     */
    public static long count(GraphClass cls, int n) {
        long[] table = cls == GraphClass.TREES ? TREES : cls == GraphClass.CONNECTED ? CONNECTED : ALL;
        return n >= 0 && n < table.length ? table[n] : -1;
    }

    /**
     * @param n a number of vertices, at most {@link #MAX_ALL}
     * @return one adjacency-mask array per graph on n vertices, up to isomorphism
     */
    public static synchronized List<int[]> all(int n) {
        if (n < 0 || n > MAX_ALL) {
            throw new IllegalArgumentException("All graphs are listed here only up to " + MAX_ALL + " vertices.");
        }
        List<int[]> cached = ALL_CACHE.get(n);
        if (cached != null) {
            return cached;
        }
        List<int[]> result;
        if (n <= 1) {
            result = List.of(new int[n]);
        } else {
            result = extend(all(n - 1), n);
        }
        ALL_CACHE.put(n, result);
        return result;
    }

    private static List<int[]> extend(List<int[]> smaller, int n) {
        Map<Long, int[]> seen = new LinkedHashMap<>();
        int last = n - 1;
        for (int[] g : smaller) {
            for (int subset = 0; subset < 1 << last; subset++) {
                int[] h = Arrays.copyOf(g, n);
                h[last] = subset;
                for (int i = 0; i < last; i++) {
                    if ((subset >> i & 1) != 0) {
                        h[i] |= 1 << last;
                    }
                }
                seen.putIfAbsent(canonicalCode(h), h);
            }
        }
        return new ArrayList<>(seen.values());
    }

    /**
     * @param adj adjacency masks of a graph on at most 11 vertices
     * @return a code equal for two graphs exactly when they are isomorphic
     */
    static long canonicalCode(int[] adj) {
        int n = adj.length;
        int[] colour = refine(adj);
        Search s = new Search(adj, colour);
        s.run(0, 0L, false);
        return s.best;
    }

    /**
     * Colour refinement: start from degrees and split colour classes by the colours of
     * neighbours until nothing changes. Colours are ranks of sorted signatures, so they do not
     * depend on how the vertices happen to be numbered.
     */
    private static int[] refine(int[] adj) {
        int n = adj.length;
        int[] colour = new int[n];
        for (int v = 0; v < n; v++) {
            colour[v] = Integer.bitCount(adj[v]);
        }
        colour = rank(colour, n, null, adj);
        while (true) {
            int[] next = rank(colour, n, colour, adj);
            if (classes(next) == classes(colour)) {
                return next;
            }
            colour = next;
        }
    }

    private static int classes(int[] colour) {
        Set<Integer> s = new HashSet<>();
        for (int c : colour) {
            s.add(c);
        }
        return s.size();
    }

    private static int[] rank(int[] base, int n, int[] previous, int[] adj) {
        String[] sig = new String[n];
        for (int v = 0; v < n; v++) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%03d", base[v]));
            if (previous != null) {
                int[] nb = new int[Integer.bitCount(adj[v])];
                int k = 0;
                for (int u = 0; u < n; u++) {
                    if ((adj[v] >> u & 1) != 0) {
                        nb[k++] = previous[u];
                    }
                }
                Arrays.sort(nb);
                for (int c : nb) {
                    sb.append(',').append(String.format("%03d", c));
                }
            }
            sig[v] = sb.toString();
        }
        String[] sorted = sig.clone();
        Arrays.sort(sorted);
        int[] result = new int[n];
        for (int v = 0; v < n; v++) {
            result[v] = Arrays.binarySearch(sorted, sig[v]);
        }
        return result;
    }

    /**
     * Places vertices at positions 0, 1, ... in colour order, keeping the adjacency code
     * (pairs (i, j), i &lt; j, column by column) as large as possible.
     */
    private static final class Search {
        private final int[] adj;
        private final int n;
        private final int[] colourAt;
        private final int[] colour;
        private final int[] placed;
        private final int totalBits;
        private long best = -1;
        private int used;

        Search(int[] adj, int[] colour) {
            this.adj = adj;
            this.n = adj.length;
            this.colour = colour;
            this.colourAt = colour.clone();
            Arrays.sort(colourAt);
            this.placed = new int[n];
            this.totalBits = n * (n - 1) / 2;
        }

        void run(int position, long code, boolean ahead) {
            if (position == n) {
                if (code > best) {
                    best = code;
                }
                return;
            }
            for (int v = 0; v < n; v++) {
                if ((used >> v & 1) != 0 || colour[v] != colourAt[position]) {
                    continue;
                }
                long c = code;
                for (int i = 0; i < position; i++) {
                    c = c << 1 | (adj[placed[i]] >> v & 1);
                }
                boolean better = ahead;
                if (!ahead && best >= 0) {
                    int bits = position * (position + 1) / 2;
                    long prefix = best >>> (totalBits - bits);
                    if (c < prefix) {
                        continue;
                    }
                    better = c > prefix;
                }
                placed[position] = v;
                used |= 1 << v;
                run(position + 1, c, better);
                used &= ~(1 << v);
            }
        }
    }

    /**
     * @param n a number of vertices, from 1 to {@link #MAX_TREES}
     * @return one edge list per tree on n vertices, up to isomorphism; edge k joins
     *         vertex k + 1 to its parent, given at index k
     */
    public static List<int[]> trees(int n) {
        if (n < 1 || n > MAX_TREES) {
            throw new IllegalArgumentException("Trees are listed here from 1 to " + MAX_TREES + " vertices.");
        }
        List<int[]> level = new ArrayList<>();
        level.add(new int[0]);
        for (int k = 2; k <= n; k++) {
            Map<String, int[]> seen = new LinkedHashMap<>();
            for (int[] parent : level) {
                for (int attach = 0; attach < k - 1; attach++) {
                    int[] grown = Arrays.copyOf(parent, k - 1);
                    grown[k - 2] = attach;
                    seen.putIfAbsent(treeCode(grown), grown);
                }
            }
            level = new ArrayList<>(seen.values());
        }
        return level;
    }

    /** @return the centre-rooted canonical encoding of a tree given by parent links */
    static String treeCode(int[] parent) {
        int n = parent.length + 1;
        List<List<Integer>> nb = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nb.add(new ArrayList<>());
        }
        for (int k = 0; k < parent.length; k++) {
            nb.get(k + 1).add(parent[k]);
            nb.get(parent[k]).add(k + 1);
        }
        int[] centres = centres(nb);
        if (centres.length == 1) {
            return rooted(nb, centres[0], -1);
        }
        String a = rooted(nb, centres[0], centres[1]);
        String b = rooted(nb, centres[1], centres[0]);
        return a.compareTo(b) <= 0 ? a + "|" + b : b + "|" + a;
    }

    private static int[] centres(List<List<Integer>> nb) {
        int n = nb.size();
        if (n <= 2) {
            return n == 1 ? new int[] {0} : new int[] {0, 1};
        }
        int[] degree = new int[n];
        List<Integer> leaves = new ArrayList<>();
        for (int v = 0; v < n; v++) {
            degree[v] = nb.get(v).size();
            if (degree[v] <= 1) {
                leaves.add(v);
            }
        }
        int remaining = n;
        while (remaining > 2) {
            remaining -= leaves.size();
            List<Integer> next = new ArrayList<>();
            for (int leaf : leaves) {
                for (int u : nb.get(leaf)) {
                    if (--degree[u] == 1) {
                        next.add(u);
                    }
                }
            }
            leaves = next;
        }
        return leaves.stream().mapToInt(Integer::intValue).toArray();
    }

    private static String rooted(List<List<Integer>> nb, int v, int from) {
        List<String> children = new ArrayList<>();
        for (int u : nb.get(v)) {
            if (u != from) {
                children.add(rooted(nb, u, v));
            }
        }
        children.sort(null);
        return "(" + String.join("", children) + ")";
    }

    /**
     * @param adj adjacency masks
     * @return the graph, undirected, without vertex positions
     */
    public static GraphModel toGraph(int[] adj) {
        GraphModel g = new GraphModel(false);
        Vertex[] vs = new Vertex[adj.length];
        for (int i = 0; i < adj.length; i++) {
            vs[i] = new Vertex();
            g.addVertex(vs[i]);
        }
        for (int j = 1; j < adj.length; j++) {
            for (int i = 0; i < j; i++) {
                if ((adj[i] >> j & 1) != 0) {
                    g.addEdge(new Edge(vs[i], vs[j]));
                }
            }
        }
        return g;
    }

    /**
     * @param parent parent links as returned by {@link #trees(int)}
     * @return the tree as a graph
     */
    public static GraphModel treeToGraph(int[] parent) {
        GraphModel g = new GraphModel(false);
        Vertex[] vs = new Vertex[parent.length + 1];
        for (int i = 0; i < vs.length; i++) {
            vs[i] = new Vertex();
            g.addVertex(vs[i]);
        }
        for (int k = 0; k < parent.length; k++) {
            g.addEdge(new Edge(vs[parent[k]], vs[k + 1]));
        }
        return g;
    }

    /**
     * @param adj adjacency masks
     * @return whether the graph is connected; the empty graph is not
     */
    public static boolean isConnected(int[] adj) {
        if (adj.length == 0) {
            return false;
        }
        int seen = 1;
        int frontier = 1;
        while (frontier != 0) {
            int next = 0;
            for (int v = 0; v < adj.length; v++) {
                if ((frontier >> v & 1) != 0) {
                    next |= adj[v];
                }
            }
            frontier = next & ~seen;
            seen |= next;
        }
        return seen == (1 << adj.length) - 1;
    }
}
