// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.graph.event;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.Vertex;

/**
 * the base interface which should be used to implement any GraphRenderer
 *
 * @author Azin Azadi
 */
public interface GraphModelListener {
    public void vertexAdded(Vertex v);

    public void vertexRemoved(Vertex v);

    public void edgeAdded(Edge e);

    public void edgeRemoved(Edge e);

    public void graphCleared();

    public void repaintGraph();
}
