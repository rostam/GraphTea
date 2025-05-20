// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.lang;

import java.util.Collections;
import java.util.HashSet;

/**
 * This is a validator that it's valid values are a set of values
 *
 * @author Azin Azadi
 */
public class SetValidator<t> implements Validator<t> {
    HashSet<t> vals = new HashSet<>();

    public void addValidValues(t[] o) {
        Collections.addAll(vals, o);
    }

    public void addValidValue(t x) {
        vals.add(x);
    }

    public void removeValidValue(t x) {
        vals.remove(x);
    }

    public boolean isValid(t o) {
        return vals.contains(o);
    }

    public Object[] getValidValues() {
        return vals.toArray();
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        if (vals.isEmpty())
            return " #$%# ";
        Object[] valar = vals.toArray();
        ret.append(valar[0].getClass().getName()).append(" #$%# ").append(valar[0]);
        for (int i = 1; i < valar.length; i++) {
            ret.append(" #$%# ").append(valar[i].getClass().getName()).append(" #$%# ").append(valar[i].toString());
        }
        return ret.toString();
    }
}
