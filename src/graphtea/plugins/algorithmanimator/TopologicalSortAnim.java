// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.algorithmanimator;

import graphtea.library.algorithms.DAG;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;

public class TopologicalSortAnim
        extends DAG
        implements AlgorithmExtension {

    public String getName() {
        return "Topological Sort";
    }

    public String getDescription() {
        return "Calculates the topological sort sequence of vertices.";
    }

}
