// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.graph.event;

import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;

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

public interface VertexModelListener {

    public void repaint(VertexModel src);

    public void updateSize(VertexModel src, GraphPoint newSize);

    public void updateLocation(VertexModel src, GraphPoint newLocation);

}
