// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.lang;

import java.util.HashSet;

/**
 * This is a validator that it's valid values are a set of values
 *
 * @author Azin Azadi
 */
public class SetValidator<t> implements Validator<t> {
    HashSet<t> vals = new HashSet<t>();

    public void addValidValues(t[] o) {
        for (t _ : o) {
            vals.add(_);
        }
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
        String ret = "";
        if (vals.isEmpty())
            return " #$%# ";
        Object[] valar = vals.toArray();
        ret = ret + valar[0].getClass().getName() + " #$%# " + valar[0];
        for (int i = 1; i < valar.length; i++) {
            ret = ret + " #$%# " + valar[i].getClass().getName() + " #$%# " + valar[i].toString();
        }
        return ret;
    }
}
