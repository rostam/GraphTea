// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.graphgenerator.core;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.awt.*;

/**
 * a helper interface to generate graphs easily, historically the base version of graph generator extension
 *
 * @author azin azadi

 */
public interface SimpleGeneratorInterface {
    void setWorkingGraph(GraphModel g);

    Vertex[] getVertices();

    Edge[] getEdges();

    //todo: make it return GPoint
    Point[] getVertexPositions();
}
