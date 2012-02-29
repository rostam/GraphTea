// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.graph.event;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.VertexModel;

/**
 * the base interface which should be used to implement any GraphRenderer
 *
 * @author Azin Azadi
 */
public interface GraphModelListener {
    public void vertexAdded(VertexModel v);

    public void vertexRemoved(VertexModel v);

    public void edgeAdded(EdgeModel e);

    public void edgeRemoved(EdgeModel e);

    public void graphCleared();

    public void repaintGraph();
}
