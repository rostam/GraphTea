// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.vertexcover.AppVertexCover;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;

public class AppVertexCoverAnim
        extends AppVertexCover<Vertex, Edge>
        implements AlgorithmExtension {

    public AppVertexCoverAnim() {
        super(null, null);
    }

    public String getName() {
        return "Approximated Vertex Cover";
    }

    public String getDescription() {
        return "Approximated Vertex Cover";
    }
}
