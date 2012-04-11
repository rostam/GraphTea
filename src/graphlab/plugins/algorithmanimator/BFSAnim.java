// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.Vertex;
import graphlab.library.algorithms.traversal.BreadthFirstSearch;
import graphlab.platform.lang.CommandAttitude;
import graphlab.plugins.algorithmanimator.extension.AlgorithmExtension;


@CommandAttitude(name = "breadth_first_search", abbreviation = "_bfs")
public class BFSAnim
        extends BreadthFirstSearch<Vertex, Edge>
        implements AlgorithmExtension {
    public String getName() {
        return "BFS";
    }

    public String getDescription() {
        return "Breadth First Search";
    }
}
