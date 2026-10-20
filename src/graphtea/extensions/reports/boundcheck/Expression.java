// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * A formula over graph invariants, such as {@code energy <= 2*sqrt(m*n)}.
 *
 * <p>The grammar, loosest binding first:
 * <pre>
 *   or       := and ( "or" and )*
 *   and      := not ( "and" not )*
 *   not      := "not" not | relation
 *   relation := sum ( ("&lt;=" | "&lt;" | "&gt;=" | "&gt;" | "=" | "!=") sum )?
 *   sum      := product ( ("+" | "-") product )*
 *   product  := unary ( ("*" | "/" | "%") unary )*
 *   unary    := "-" unary | power
 *   power    := atom ( "^" unary )?
 *   atom     := number | name | name "(" or ( "," or )* ")" | "(" or ")"
 * </pre>
 * ≤, ≥, ≠, × and · are accepted as well. A name is an invariant, resolved per graph by
 * {@link Variables}, or one of the constants {@code pi} and {@code e}. Comparisons and the
 * logical operators give 1 for true and 0 for false.
 *
 * @author M. Ali Rostami
 */
public final class Expression {

    /** Supplies the value of each invariant a formula names, for one graph. */
    public interface Variables {
        /**
         * @param name an invariant's name
         * @return its value
         */
        double value(String name);
    }

    /** A formula that cannot be parsed; {@link #position()} says where the problem is. */
    public static final class SyntaxException extends IllegalArgumentException {
        private static final long serialVersionUID = 1L;
        private final int position;

        SyntaxException(String message, int position) {
            super(message);
            this.position = position;
        }

        /** @return the offset into the formula at which the problem was found */
        public int position() {
            return position;
        }
    }

    private interface Node {
        double eval(Variables vars);
    }

    private interface Function {
        double apply(double[] args);
    }

    private static final Map<String, Integer> ARITY = new TreeMap<>();
    private static final Map<String, Function> FUNCTIONS = new TreeMap<>();

    static {
        unary("sqrt", Math::sqrt);
        unary("abs", Math::abs);
        unary("ln", Math::log);
        unary("log2", x -> Math.log(x) / Math.log(2));
        unary("log10", Math::log10);
        unary("exp", Math::exp);
        unary("floor", Math::floor);
        unary("ceil", Math::ceil);
        unary("round", x -> (double) Math.round(x));
        binary("pow", Math::pow);
        binary("choose", Expression::choose);
        variadic("log", a -> a.length == 1 ? Math.log(a[0]) : Math.log(a[0]) / Math.log(a[1]));
        variadic("min", a -> fold(a, Math::min));
        variadic("max", a -> fold(a, Math::max));
    }

    private final String text;
    private final Node root;
    private final Relation relation;
    private final Expression left;
    private final Expression right;
    private final Set<String> names;

    private Expression(String text, Node root, Relation relation, Expression left, Expression right,
                       Set<String> names) {
        this.text = text;
        this.root = root;
        this.relation = relation;
        this.left = left;
        this.right = right;
        this.names = names;
    }

    /**
     * @param text a formula
     * @return the parsed formula
     * @throws SyntaxException if the formula is not well formed
     */
    public static Expression parse(String text) {
        return new Parser(text).parseAll();
    }

    /** @return the formula as written, trimmed */
    public String text() {
        return text;
    }

    /** @return the names of the invariants the formula uses, in order of first use */
    public Set<String> names() {
        return Collections.unmodifiableSet(names);
    }

    /** @return the relation, when the whole formula is a single comparison; otherwise null */
    public Relation relation() {
        return relation;
    }

    /** @return the left side of a comparison; null when {@link #relation()} is */
    public Expression left() {
        return left;
    }

    /** @return the right side of a comparison; null when {@link #relation()} is */
    public Expression right() {
        return right;
    }

    /**
     * @param vars the invariants' values
     * @return the formula's value; NaN where it is undefined
     */
    public double evaluate(Variables vars) {
        return root.eval(vars);
    }

    /** @return the names of the functions a formula may call */
    public static Set<String> functionNames() {
        return Collections.unmodifiableSet(FUNCTIONS.keySet());
    }

    @Override
    public String toString() {
        return text;
    }

    private static void unary(String name, java.util.function.DoubleUnaryOperator f) {
        ARITY.put(name, 1);
        FUNCTIONS.put(name, a -> f.applyAsDouble(a[0]));
    }

    private static void binary(String name, java.util.function.DoubleBinaryOperator f) {
        ARITY.put(name, 2);
        FUNCTIONS.put(name, a -> f.applyAsDouble(a[0], a[1]));
    }

    private static void variadic(String name, Function f) {
        ARITY.put(name, -1);
        FUNCTIONS.put(name, f);
    }

    private static double fold(double[] a, java.util.function.DoubleBinaryOperator f) {
        double r = a[0];
        for (int i = 1; i < a.length; i++) {
            r = f.applyAsDouble(r, a[i]);
        }
        return r;
    }

    private static double choose(double n, double k) {
        if (k < 0 || k > n || k != Math.floor(k) || n != Math.floor(n)) {
            return 0;
        }
        double r = 1;
        for (int i = 1; i <= k; i++) {
            r = r * (n - k + i) / i;
        }
        return Math.rint(r);
    }

    private static double truth(boolean b) {
        return b ? 1 : 0;
    }

    private static boolean isTrue(double v) {
        return v != 0 && !Double.isNaN(v);
    }

    /** A comparison node, which remembers where its sides are in the formula. */
    private static final class Comparison implements Node {
        private final Relation relation;
        private final Node left;
        private final Node right;
        private final int leftStart;
        private final int leftEnd;
        private final int rightStart;
        private final int rightEnd;

        Comparison(Relation relation, Node left, Node right, int[] span) {
            this.relation = relation;
            this.left = left;
            this.right = right;
            this.leftStart = span[0];
            this.leftEnd = span[1];
            this.rightStart = span[2];
            this.rightEnd = span[3];
        }

        @Override
        public double eval(Variables vars) {
            Outcome o = relation.compare(left.eval(vars), right.eval(vars));
            if (o == Outcome.UNDEFINED) {
                return Double.NaN;
            }
            return truth(o != Outcome.FAILS);
        }
    }

    /** A recursive-descent parser over the grammar in the class comment. */
    private static final class Parser {
        private final String src;
        private final Set<String> names = new LinkedHashSet<>();
        private int pos;

        Parser(String src) {
            this.src = src == null ? "" : src;
        }

        Expression parseAll() {
            skipSpace();
            if (pos == src.length()) {
                throw new SyntaxException("The formula is empty.", 0);
            }
            Node root = or();
            expectEnd();
            if (root instanceof Comparison c) {
                String l = src.substring(c.leftStart, c.leftEnd).trim();
                String r = src.substring(c.rightStart, c.rightEnd).trim();
                return new Expression(src.trim(), root, c.relation,
                        new Expression(l, c.left, null, null, null, namesIn(l)),
                        new Expression(r, c.right, null, null, null, namesIn(r)), names);
            }
            return new Expression(src.trim(), root, null, null, null, names);
        }

        private Set<String> namesIn(String part) {
            Parser p = new Parser(part);
            try {
                p.sum();
            } catch (SyntaxException e) {
                // Only names are wanted here; the part has already parsed once.
            }
            return p.names;
        }

        private void expectEnd() {
            skipSpace();
            if (pos < src.length()) {
                throw new SyntaxException("Unexpected \"" + src.charAt(pos) + "\".", pos);
            }
        }

        private Node or() {
            Node l = and();
            while (keyword("or") || symbol("||")) {
                Node a = l;
                Node b = and();
                l = v -> truth(isTrue(a.eval(v)) || isTrue(b.eval(v)));
            }
            return l;
        }

        private Node and() {
            Node l = not();
            while (keyword("and") || symbol("&&")) {
                Node a = l;
                Node b = not();
                l = v -> truth(isTrue(a.eval(v)) && isTrue(b.eval(v)));
            }
            return l;
        }

        private Node not() {
            if (keyword("not")) {
                Node a = not();
                return v -> truth(!isTrue(a.eval(v)));
            }
            skipSpace();
            int leftStart = pos;
            Node l = sum();
            int leftEnd = pos;
            Relation rel = relationAhead();
            if (rel == null) {
                return l;
            }
            skipSpace();
            int rightStart = pos;
            Node r = sum();
            return new Comparison(rel, l, r, new int[] {leftStart, leftEnd, rightStart, pos});
        }

        private Relation relationAhead() {
            skipSpace();
            String[][] table = {
                {"<=", "AT_MOST"}, {"≤", "AT_MOST"}, {">=", "AT_LEAST"}, {"≥", "AT_LEAST"},
                {"!=", "NOT_EQUAL"}, {"≠", "NOT_EQUAL"}, {"==", "EQUAL"}, {"=", "EQUAL"},
                {"<", "LESS"}, {">", "GREATER"},
            };
            for (String[] t : table) {
                if (src.startsWith(t[0], pos)) {
                    pos += t[0].length();
                    return Relation.valueOf(t[1]);
                }
            }
            return null;
        }

        private Node sum() {
            Node l = product();
            while (true) {
                if (symbol("+")) {
                    Node a = l;
                    Node b = product();
                    l = v -> a.eval(v) + b.eval(v);
                } else if (symbol("-") || symbol("−")) {
                    Node a = l;
                    Node b = product();
                    l = v -> a.eval(v) - b.eval(v);
                } else {
                    return l;
                }
            }
        }

        private Node product() {
            Node l = unaryMinus();
            while (true) {
                if (symbol("*") || symbol("×") || symbol("·")) {
                    Node a = l;
                    Node b = unaryMinus();
                    l = v -> a.eval(v) * b.eval(v);
                } else if (symbol("/")) {
                    Node a = l;
                    Node b = unaryMinus();
                    l = v -> divide(a.eval(v), b.eval(v));
                } else if (symbol("%")) {
                    Node a = l;
                    Node b = unaryMinus();
                    l = v -> a.eval(v) % b.eval(v);
                } else {
                    return l;
                }
            }
        }

        private static double divide(double a, double b) {
            // x/0 is undefined, not infinite: a bound that divides by the number of edges says
            // nothing about an edgeless graph.
            return b == 0 ? Double.NaN : a / b;
        }

        private Node unaryMinus() {
            if (symbol("-") || symbol("−")) {
                Node a = unaryMinus();
                return v -> -a.eval(v);
            }
            if (symbol("+")) {
                return unaryMinus();
            }
            return power();
        }

        private Node power() {
            Node base = atom();
            if (symbol("^")) {
                Node exp = unaryMinus();
                return v -> Math.pow(base.eval(v), exp.eval(v));
            }
            return base;
        }

        private Node atom() {
            skipSpace();
            if (pos >= src.length()) {
                throw new SyntaxException("The formula ends too early.", pos);
            }
            char c = src.charAt(pos);
            if (symbol("(")) {
                Node inner = or();
                expect(")");
                return inner;
            }
            if (Character.isDigit(c) || c == '.') {
                return number();
            }
            if (Character.isLetter(c) || c == '_') {
                return name();
            }
            throw new SyntaxException("Unexpected \"" + c + "\".", pos);
        }

        private Node number() {
            int start = pos;
            while (pos < src.length() && (Character.isDigit(src.charAt(pos)) || src.charAt(pos) == '.')) {
                pos++;
            }
            if (exponentAhead()) {
                pos += 2;
                while (pos < src.length() && Character.isDigit(src.charAt(pos))) {
                    pos++;
                }
            }
            try {
                double value = Double.parseDouble(src.substring(start, pos));
                return v -> value;
            } catch (NumberFormatException e) {
                throw new SyntaxException("\"" + src.substring(start, pos) + "\" is not a number.", start);
            }
        }

        /** @return whether an exponent such as e-3 follows a number's digits */
        private boolean exponentAhead() {
            if (pos + 1 >= src.length() || Character.toLowerCase(src.charAt(pos)) != 'e') {
                return false;
            }
            char next = src.charAt(pos + 1);
            return Character.isDigit(next) || next == '-' || next == '+';
        }

        private Node name() {
            int start = pos;
            String id = identifier();
            skipSpace();
            if (pos < src.length() && src.charAt(pos) == '(') {
                return call(id, start);
            }
            if ("pi".equals(id)) {
                return v -> Math.PI;
            }
            if ("e".equals(id)) {
                return v -> Math.E;
            }
            names.add(id);
            return v -> v.value(id);
        }

        private Node call(String id, int start) {
            Function f = FUNCTIONS.get(id);
            if (f == null) {
                throw new SyntaxException("There is no function called " + id + ".", start);
            }
            expect("(");
            List<Node> args = new ArrayList<>();
            args.add(or());
            while (symbol(",")) {
                args.add(or());
            }
            expect(")");
            int arity = ARITY.get(id);
            if (arity > 0 && args.size() != arity) {
                throw new SyntaxException(id + " takes " + arity + (arity == 1 ? " argument" : " arguments")
                        + ", not " + args.size() + ".", start);
            }
            if ("log".equals(id) && args.size() > 2) {
                throw new SyntaxException("log takes one or two arguments.", start);
            }
            Node[] a = args.toArray(new Node[0]);
            return v -> {
                double[] values = new double[a.length];
                for (int i = 0; i < a.length; i++) {
                    values[i] = a[i].eval(v);
                }
                return f.apply(values);
            };
        }

        private String identifier() {
            int start = pos;
            while (pos < src.length() && (Character.isLetterOrDigit(src.charAt(pos)) || src.charAt(pos) == '_')) {
                pos++;
            }
            return src.substring(start, pos);
        }

        private boolean keyword(String word) {
            skipSpace();
            int end = pos + word.length();
            if (!src.regionMatches(true, pos, word, 0, word.length())) {
                return false;
            }
            if (end < src.length() && (Character.isLetterOrDigit(src.charAt(end)) || src.charAt(end) == '_')) {
                return false;
            }
            pos = end;
            return true;
        }

        private boolean symbol(String s) {
            skipSpace();
            if (!src.startsWith(s, pos)) {
                return false;
            }
            pos += s.length();
            return true;
        }

        private void expect(String s) {
            if (!symbol(s)) {
                throw new SyntaxException("Expected \"" + s + "\".", pos);
            }
        }

        private void skipSpace() {
            while (pos < src.length() && Character.isWhitespace(src.charAt(pos))) {
                pos++;
            }
        }
    }
}
