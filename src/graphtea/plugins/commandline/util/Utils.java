// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline.util;

/**
 * @author Azin Azadi
 */
public class Utils {
    public static String getMaximumSimilarities(String[] arg) {
        for (int k = 1; k <= arg[0].length(); k++) {
            for (String anArg : arg)
                if (!anArg.startsWith(arg[0].substring(0, k)))
                    return arg[0].substring(0, k - 1);
        }
        return arg[0];
    }
}