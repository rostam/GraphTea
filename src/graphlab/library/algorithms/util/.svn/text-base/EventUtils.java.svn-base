// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.util;

import graphlab.library.algorithms.Algorithm;
import graphlab.library.event.AlgorithmStep;

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

}
