// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.vertex;

import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.Vertex;

/**
 * @author  Ruzbeh
 */
public class VertexMoveData {
    public final static String EVENT_KEY = "Vertex.DND";
    public Vertex v;
    //    public double newX;
    //    public double newY;
    public GraphPoint newPosition;

}