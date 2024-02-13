// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.atributeset;

import graphtea.graph.graph.Vertex;
import graphtea.platform.attribute.TimeLimitedNotifiableAttrSet;


public class VertexNotifiableAttrSet extends TimeLimitedNotifiableAttrSet<VertexAttrSet> {
    public VertexNotifiableAttrSet(Vertex input) {
        super(new VertexAttrSet(input));
    }
}
