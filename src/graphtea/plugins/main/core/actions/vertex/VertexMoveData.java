// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions.vertex;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;

/**
 * @author  Ruzbeh
 */
public class VertexMoveData {
    public final static String EVENT_KEY = "Vertex.DND";
    public Vertex v;
    //    public double newX;
    //    public double newY;
    public GPoint newPosition;

}