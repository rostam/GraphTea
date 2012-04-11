// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.graph.event;

import graphlab.graph.graph.Edge;

import java.awt.*;

/**
 * EdgeListener defines the interface for an object that listens
 * to changes in a EdgModel.
 *
 * @author Azin Azadi
 * @see graphlab.graph.graph.Edge
 */
public interface EdgeListener {

    void repaint(Edge src);

    void updateBounds(Rectangle r, Edge src);
}
