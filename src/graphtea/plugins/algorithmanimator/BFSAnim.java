// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.traversal.BreadthFirstSearch;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;


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
