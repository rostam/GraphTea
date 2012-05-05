// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.util;

import graphtea.library.algorithms.Algorithm;
import graphtea.library.event.AlgorithmStep;

public class EventUtils {

    /**
     * dispatches an event in a which means that there is a Algorithm Step point here
     *
     * @param a
     * @param message
     */
    public static void algorithmStep(Algorithm a, String message) {
        AlgorithmStep step = new AlgorithmStep();
        step.setMessage(message);
        a.dispatchEvent(step);
    }

    public static void algorithmStep(Algorithm algorithm, String msg, String id) {
        AlgorithmStep step = new AlgorithmStep(msg, id);
        algorithm.dispatchEvent(step);
    }
}
