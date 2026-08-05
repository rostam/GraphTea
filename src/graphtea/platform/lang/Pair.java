// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.platform.lang;

/**
 * A pair of two objects.
 *
 * <p>This was a mutable class with hand-written {@code equals}, {@code hashCode} and
 * {@code toString}. The {@code equals} threw {@link NullPointerException} whenever either
 * component was null &mdash; including for two pairs that were plainly equal, and for a pair
 * compared with itself &mdash; because the null guards fell through to
 * {@code first.equals(...)}. As a record the three methods are generated correctly and are
 * null-safe, and the pair becomes immutable, which is what every use of it already assumed:
 * nothing in the code base ever assigned to the fields.
 *
 * <p>{@link graphtea.library.util.Pair} is a separate, serializable type used inside the graph
 * data structures; it deliberately keeps identity semantics and is not this class.
 *
 * @param <First>  type of the first component
 * @param <Second> type of the second component
 * @param first    the first component
 * @param second   the second component
 * @author Omid
 */
public record Pair<First, Second>(First first, Second second) {
}
