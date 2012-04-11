// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.algorithmanimator;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.Vertex;
import graphlab.library.algorithms.traversal.DepthFirstSearch;
import graphlab.plugins.algorithmanimator.extension.AlgorithmExtension;

public class DFSAnim
        extends DepthFirstSearch<Vertex, Edge>
        implements AlgorithmExtension {

    public String getName() {
        return "DFS";
    }

    public String getDescription() {
        return "Depth First Search";
    }
}