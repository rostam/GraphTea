// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.graph.atributeset;

import graphlab.graph.graph.GraphModel;
import graphlab.platform.attribute.TimeLimitedNotifiableAttrSet;

/**
 * @see graphlab.graph.atributeset.EdgeNotifiableAttrSet
 */
public class GraphNotifiableAttrSet extends TimeLimitedNotifiableAttrSet<GraphAttrSet> {
    public GraphNotifiableAttrSet(GraphModel g) {
        super(new GraphAttrSet(g));
        start();
    }
}