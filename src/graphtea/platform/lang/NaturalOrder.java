// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.platform.lang;

import java.util.Comparator;

/**
 * Orders strings the way a person reads them: ignoring case, and comparing runs of digits by
 * their value, so "Chain Graph 2" comes before "Chain Graph 10" and "k-regular" sits next to
 * "Kneser".
 */
public final class NaturalOrder implements Comparator<String> {
    /** The shared instance; the comparator holds no state. */
    public static final NaturalOrder INSTANCE = new NaturalOrder();

    private NaturalOrder() {
    }

    @Override
    public int compare(String a, String b) {
        int i = 0;
        int j = 0;
        while (i < a.length() && j < b.length()) {
            char ca = a.charAt(i);
            char cb = b.charAt(j);
            if (Character.isDigit(ca) && Character.isDigit(cb)) {
                int endA = digitsEnd(a, i);
                int endB = digitsEnd(b, j);
                int byValue = compareDigits(a.substring(i, endA), b.substring(j, endB));
                if (byValue != 0) {
                    return byValue;
                }
                i = endA;
                j = endB;
            } else {
                int byChar = Character.compare(Character.toLowerCase(ca), Character.toLowerCase(cb));
                if (byChar != 0) {
                    return byChar;
                }
                i++;
                j++;
            }
        }
        int byLength = Integer.compare(a.length() - i, b.length() - j);
        // Strings equal but for case still need a consistent order.
        return byLength != 0 ? byLength : a.compareTo(b);
    }

    private static int digitsEnd(String s, int from) {
        int end = from;
        while (end < s.length() && Character.isDigit(s.charAt(end))) {
            end++;
        }
        return end;
    }

    private static int compareDigits(String a, String b) {
        String x = stripLeadingZeros(a);
        String y = stripLeadingZeros(b);
        if (x.length() != y.length()) {
            return Integer.compare(x.length(), y.length());
        }
        return x.compareTo(y);
    }

    private static String stripLeadingZeros(String digits) {
        int k = 0;
        while (k < digits.length() - 1 && digits.charAt(k) == '0') {
            k++;
        }
        return digits.substring(k);
    }
}
