// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator;

import graphtea.library.algorithms.util.AcyclicChecker;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;

public class AcyclicCheckerAnim
        extends AcyclicChecker
        implements AlgorithmExtension {

    public String getName() {
        return "Acyclic Checker";
    }

    public String getDescription() {
        return "Checks if the graph is acyclic.";
    }
}
