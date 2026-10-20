// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import graphtea.extensions.reports.boundcheck.forall.filters.IntegralFilter;
import graphtea.extensions.reports.boundcheck.forall.filters.LaplacianIntegralFilter;
import graphtea.extensions.reports.boundcheck.forall.filters.QIntegralFilter;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.ToDoubleFunction;

/**
 * The invariants a conjecture's formula can name: a few built in, such as {@code n} and
 * {@code m}, and every report whose answer is a number, under a name made from its title
 * ("Wiener Index" becomes {@code wiener_index}).
 *
 * @author M. Ali Rostami
 */
public final class Invariants {

    /** One invariant: a name to type, a title to show, and how to compute it. */
    public static final class Invariant {
        private final String id;
        private final String title;
        private final String group;
        private final ToDoubleFunction<GraphModel> value;

        Invariant(String id, String title, String group, ToDoubleFunction<GraphModel> value) {
            this.id = id;
            this.title = title;
            this.group = group;
            this.value = value;
        }

        /** @return the name used in formulas */
        public String id() {
            return id;
        }

        /** @return the title shown to the user */
        public String title() {
            return title;
        }

        /** @return the menu category of the report it comes from, or "Basic" */
        public String group() {
            return group;
        }

        @Override
        public String toString() {
            return id + " — " + title;
        }
    }

    /** Thrown when an invariant cannot be computed for a graph, carrying a readable reason. */
    public static final class EvaluationException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        EvaluationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static final String BASIC = "Basic";

    private final Map<String, Invariant> byId = new LinkedHashMap<>();

    private Invariants() {
    }

    /** @return the built-in invariants only */
    public static Invariants builtIn() {
        Invariants inv = new Invariants();
        inv.add("n", "Number of vertices", BASIC, g -> g.getVerticesCount());
        inv.add("m", "Number of edges", BASIC, g -> g.getEdgesCount());
        inv.add("maxdeg", "Maximum degree (Δ)", BASIC, g -> degreeBound(g, true));
        inv.add("mindeg", "Minimum degree (δ)", BASIC, g -> degreeBound(g, false));
        inv.add("avgdeg", "Average degree (2m/n)", BASIC,
            g -> g.getVerticesCount() == 0 ? Double.NaN : 2.0 * g.getEdgesCount() / g.getVerticesCount());
        inv.add("is_integral", "1 if the adjacency spectrum is integral, else 0", BASIC,
            g -> new IntegralFilter().filter(g) ? 1 : 0);
        inv.add("is_laplacian_integral", "1 if the Laplacian spectrum is integral, else 0", BASIC,
            g -> new LaplacianIntegralFilter().filter(g) ? 1 : 0);
        inv.add("is_q_integral", "1 if the signless Laplacian spectrum is integral, else 0", BASIC,
            g -> new QIntegralFilter().filter(g) ? 1 : 0);
        return inv;
    }

    /**
     * @param reports the loaded reports; those whose answer is not a number are left out
     * @return the built-in invariants and one for each numeric report
     */
    public static Invariants withReports(Collection<? extends GraphReportExtension<?>> reports) {
        Invariants inv = builtIn();
        List<GraphReportExtension<?>> sorted = new ArrayList<>(reports);
        sorted.sort(Comparator.comparing(GraphReportExtension::getName));
        for (GraphReportExtension<?> r : sorted) {
            if (isNumeric(r)) {
                inv.add(inv.freeId(idFor(r.getName())), r.getName(), groupOf(r), g -> toNumber(r, r.calculate(g)));
            }
        }
        return inv;
    }

    /**
     * @param id a name used in formulas
     * @return the invariant, or null if there is none by that name
     */
    public Invariant get(String id) {
        return byId.get(id);
    }

    /** @return every invariant, built-in ones first */
    public Collection<Invariant> all() {
        return Collections.unmodifiableCollection(byId.values());
    }

    /**
     * Checks that every name in a formula is a known invariant.
     *
     * @param e a parsed formula
     * @return null if it is fine, otherwise a sentence saying which name is unknown and, when
     *         one is close, what was probably meant
     */
    public String unknownName(Expression e) {
        for (String name : e.names()) {
            if (!byId.containsKey(name)) {
                String guess = closest(name);
                return "There is no invariant called \"" + name + "\"."
                        + (guess == null ? "" : " Did you mean " + guess + "?");
            }
        }
        return null;
    }

    /**
     * @param g a graph
     * @return the invariants' values for g, each computed at most once
     */
    public Expression.Variables bind(GraphModel g) {
        Map<String, Double> cache = new HashMap<>();
        return name -> {
            Double known = cache.get(name);
            if (known != null) {
                return known;
            }
            Invariant inv = byId.get(name);
            if (inv == null) {
                throw new EvaluationException("There is no invariant called \"" + name + "\".", null);
            }
            double v;
            try {
                v = inv.value.applyAsDouble(g);
            } catch (EvaluationException e) {
                throw e;
            } catch (RuntimeException | StackOverflowError e) {
                throw new EvaluationException(inv.title + " failed: " + describe(e), e);
            }
            cache.put(name, v);
            return v;
        };
    }

    /**
     * Turns a report's title into a name that can be typed in a formula.
     *
     * @param title a report title, e.g. "Number of Triangles"
     * @return e.g. {@code number_of_triangles}
     */
    public static String idFor(String title) {
        String id = title.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "_").replaceAll("^_+|_+$", "");
        if (id.isEmpty() || Character.isDigit(id.charAt(0))) {
            id = "r_" + id;
        }
        return id;
    }

    static String describe(Throwable e) {
        String msg = e.getMessage();
        String type = e.getClass().getSimpleName();
        return msg == null || msg.isBlank() ? type : type + ": " + msg;
    }

    private void add(String id, String title, String group, ToDoubleFunction<GraphModel> value) {
        byId.put(id, new Invariant(id, title, group, value));
    }

    private String freeId(String id) {
        String candidate = id;
        for (int i = 2; byId.containsKey(candidate) || Expression.functionNames().contains(candidate)
                || "pi".equals(candidate) || "e".equals(candidate); i++) {
            candidate = id + "_" + i;
        }
        return candidate;
    }

    private static String groupOf(GraphReportExtension<?> r) {
        String c = r.getCategory();
        return c == null || c.isBlank() ? "General" : c.trim();
    }

    private static double degreeBound(GraphModel g, boolean max) {
        if (g.getVerticesCount() == 0) {
            return Double.NaN;
        }
        int best = max ? 0 : Integer.MAX_VALUE;
        for (Vertex v : g) {
            int d = g.getDegree(v);
            best = max ? Math.max(best, d) : Math.min(best, d);
        }
        return best;
    }

    private static double toNumber(GraphReportExtension<?> r, Object answer) {
        if (answer instanceof Number num) {
            return num.doubleValue();
        }
        if (answer instanceof Boolean b) {
            return b ? 1 : 0;
        }
        if (answer instanceof String s) {
            try {
                return Double.parseDouble(s.trim());
            } catch (NumberFormatException e) {
                throw new EvaluationException(r.getName() + " gave \"" + s + "\", which is not a number.", e);
            }
        }
        if (answer == null) {
            return Double.NaN;
        }
        throw new EvaluationException(r.getName() + " did not give a number.", null);
    }

    /**
     * @param r a report
     * @return whether its declared answer type is a number, a truth value or text (which may
     *         hold a number, as the energies do)
     */
    static boolean isNumeric(GraphReportExtension<?> r) {
        Class<?> c = answerClass(r);
        if (c == null) {
            return false;
        }
        return Number.class.isAssignableFrom(c) || c == Boolean.class || c == String.class;
    }

    /**
     * @param r a report
     * @return the class of its answer, from its declaration, or null if that is not a plain class
     */
    public static Class<?> answerClass(GraphReportExtension<?> r) {
        Type t = answerType(r.getClass());
        return t instanceof Class<?> c ? c : null;
    }

    /** Finds T in {@code GraphReportExtension<T>} for a concrete report class. */
    private static Type answerType(Class<?> cls) {
        Map<TypeVariable<?>, Type> bindings = new HashMap<>();
        for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
            List<Type> supers = new ArrayList<>();
            Collections.addAll(supers, c.getGenericInterfaces());
            supers.add(c.getGenericSuperclass());
            for (Type s : supers) {
                Type found = answerTypeIn(s, bindings);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private static Type answerTypeIn(Type s, Map<TypeVariable<?>, Type> bindings) {
        if (!(s instanceof ParameterizedType p)) {
            return null;
        }
        Class<?> raw = (Class<?>) p.getRawType();
        Type[] args = p.getActualTypeArguments();
        TypeVariable<?>[] vars = raw.getTypeParameters();
        for (int i = 0; i < args.length; i++) {
            bindings.put(vars[i], args[i] instanceof TypeVariable<?> tv ? bindings.getOrDefault(tv, tv) : args[i]);
        }
        if (raw == GraphReportExtension.class) {
            return bindings.get(vars[0]);
        }
        for (Type up : raw.getGenericInterfaces()) {
            Type found = answerTypeIn(up, bindings);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private String closest(String name) {
        String best = null;
        int bestDistance = Integer.MAX_VALUE;
        for (String id : byId.keySet()) {
            int d = distance(name.toLowerCase(Locale.ROOT), id);
            if (d < bestDistance) {
                bestDistance = d;
                best = id;
            }
        }
        return bestDistance <= Math.max(2, name.length() / 3) ? best : null;
    }

    private static int distance(String a, String b) {
        int[] prev = new int[b.length() + 1];
        int[] cur = new int[b.length() + 1];
        for (int j = 0; j <= b.length(); j++) {
            prev[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            cur[0] = i;
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                cur[j] = Math.min(Math.min(cur[j - 1] + 1, prev[j] + 1), prev[j - 1] + cost);
            }
            int[] t = prev;
            prev = cur;
            cur = t;
        }
        return prev[b.length()];
    }
}
