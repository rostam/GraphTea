// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.graph.event;

/**
 * the base interface which should be used to implement any GraphRenderer
 *
 * @author Azin Azadi
 */
public interface GraphControlListener {
    public void ActionPerformed(GraphEvent event);

    public void ActionPerformed(VertexEvent event);

    public void ActionPerformed(EdgeEvent event);
}
