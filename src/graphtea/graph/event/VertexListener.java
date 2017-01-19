// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.event;

import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;

/**
 * *************************************************************
 * GraphTea Project                                             *
 * Copyright (C) 2005 Math Science Department                   *
 * of Sharif University of Technology       *
 * *
 * See license.txt for more details about license of GraphTea.  *
 * *
 * **************************************************************
 */

public interface VertexListener {

    public void repaint(Vertex src);

    public void updateSize(Vertex src, GPoint newSize);

    public void updateLocation(Vertex src, GPoint newLocation);

}
