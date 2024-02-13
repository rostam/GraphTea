// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.event;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.Vertex;

/**
 * the base interface which should be used to implement any GraphRenderer
 *
 * @author Azin Azadi
 */
public interface GraphModelListener {
    void vertexAdded(Vertex v);

    void vertexRemoved(Vertex v);

    void edgeAdded(Edge e);

    void edgeRemoved(Edge e);

    void graphCleared();

    void repaintGraph();
}
