// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.graph.event;

import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.Vertex;

/**
 * *************************************************************
 * GraphLab Project                                             *
 * Copyright (C) 2005 Math Science Department                   *
 * of Sharif University of Technology       *
 * *
 * See license.txt for more details about license of GraphLab.  *
 * *
 * **************************************************************
 */

public interface VertexListener {

    public void repaint(Vertex src);

    public void updateSize(Vertex src, GraphPoint newSize);

    public void updateLocation(Vertex src, GraphPoint newLocation);

}
