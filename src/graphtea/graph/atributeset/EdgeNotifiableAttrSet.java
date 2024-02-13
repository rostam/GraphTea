// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.atributeset;

import graphtea.graph.graph.Edge;
import graphtea.platform.attribute.TimeLimitedNotifiableAttrSet;

/**
 * changes on Edge will take effect on next 100 milisecond,
 * using this class should be done with care, this class uses a thread
 * and checks the edge on each 100ms for any changes, so creating a lot of
 * instances of this class (for example for all edges of graph)
 * will take more and more cpu,
 * <p/>
 * try to create as few as possible instances of this class and call stop() when you don't need
 * it any more!
 * <p/>
 *
 * @author azin azadi
 * @see EdgeAttrSet
 * @see graphtea.graph.graph.Edge
 * @see EdgeAttrSet
 */
public class EdgeNotifiableAttrSet extends TimeLimitedNotifiableAttrSet<EdgeAttrSet> {
    public EdgeNotifiableAttrSet(Edge input) {
        super(new EdgeAttrSet(input));
    }
}


