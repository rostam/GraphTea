// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.atributeset;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.attribute.TimeLimitedNotifiableAttrSet;

/**
 * @see graphtea.graph.atributeset.EdgeNotifiableAttrSet
 */
public class GraphNotifiableAttrSet extends TimeLimitedNotifiableAttrSet<GraphAttrSet> {
    public GraphNotifiableAttrSet(GraphModel g) {
        super(new GraphAttrSet(g));
        start();
    }
}