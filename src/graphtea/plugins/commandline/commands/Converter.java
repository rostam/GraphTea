// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline.commands;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.Vertex;
import graphtea.library.genericcloners.EdgeVertexConverter;

/**
 * @author Mohammad Ali Rostami
 */
public class Converter
        implements EdgeVertexConverter<Vertex, Vertex, Edge, Edge> {
    public Edge convert(Edge e, Vertex newSource, Vertex newTarget) {
        if (e != null)
            return new Edge(e, newSource, newTarget);
        else
            return new Edge(newSource, newTarget);
    }

    public Vertex convert(Vertex e) {
        return new Vertex(e);
    }
}
