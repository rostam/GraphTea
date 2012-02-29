// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.platform.lang;

/**
 * A pair of two objects
 * @author Omid
 */
public class Pair<First, Second> {
    public First first;
    public Second second;

    public Pair(First f, Second s) {
        first = f;
        second = s;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Pair))
            return false;
        Pair t = (Pair) obj;
        if (first == null && t.first!=null )
            return false;
        if (second == null && t.second!=null )
            return false;
        return !(!first.equals(t.first) || !second.equals(t.second));
    }

    public int hashCode() {
        int fh = first == null ? 0 : first.hashCode();
		int lh = second == null ? 0 : second.hashCode();
		return fh + 100000 * lh;
    }

    public String toString() {
        String fs = first == null ? "null" : first.toString();
		String ss = second == null ? "null" :second.toString();
		return fs + ", " + ss;
    }

}
