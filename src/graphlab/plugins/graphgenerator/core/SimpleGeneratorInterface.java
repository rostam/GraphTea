// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.graphgenerator.core;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;

import java.awt.*;

/**
 * a helper interface to generate graphs easily, historically the base version of graph generator extension
 *
 * @author azin azadi

 */
public interface SimpleGeneratorInterface {
    public void setWorkingGraph(GraphModel g);

    public Vertex[] getVertices();

    public Edge[] getEdges();

    //todo: make it return GraphPoint
    public Point[] getVertexPositions();
}
