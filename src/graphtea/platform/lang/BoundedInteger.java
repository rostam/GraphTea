// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.lang;

import graphtea.platform.attribute.AtomAttribute;

/**
 * represents a bounded integer: it's value only can be in the (max ,min) bound.
 *
 * @author azin azadi
 */
//todo: bounded number
public class BoundedInteger implements Validator<Integer>, AtomAttribute<Integer> {
    private int max;
    private int min;

    /**
     * sets the max and min to integer.maxvalue , minvalue
     *
     * @param value The value
     */
    public BoundedInteger(int value) {
        max = Integer.MAX_VALUE;
        min = Integer.MIN_VALUE;
        this.value = value;
    }

    public BoundedInteger(int value, int max, int min) {
        setMax(max);
        setMin(min);
        this.value = value;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max < min)
            throw new RuntimeException("max value should be smaller than min value");
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        if (max < min)
            throw new RuntimeException("max value should be smaller than min value");
        this.min = min;
    }

    public String toString() {
        return "BInt" + value;
    }

    /**
     * returns true if value is in the bounds of this bounded integer and set the current value
     */
    public boolean setValue(Integer value) {
        if (isValid(value)) {
            this.value = value;
            return true;
        }
        return false;
    }

    public Integer getValue() {
        return value;
    }

    private int value;


    /**
     * is xx in bounds?
     */
    public boolean isValid(Integer xx) {
        return xx < max && xx > min;
    }
}
