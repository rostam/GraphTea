// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms.goperators.product;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseVertex;
import graphtea.plugins.graphgenerator.core.PositionGenerators;

import java.awt.*;

/**
 * @author Mohammad Ali Rostami
 * @email a.rostami@gmail.com
 */

public class GCartesianProduct extends GProduct {
    public boolean compare(BaseVertex v1OfFirstG, BaseVertex v2OfFirstG, BaseVertex v1OfSecondG, BaseVertex v2OfSecondG) {
        return (v1OfFirstG == v2OfFirstG
                && g2.isEdge(v1OfSecondG, v2OfSecondG))
                || (v1OfSecondG == v2OfSecondG
                && g1.isEdge(v1OfFirstG, v2OfFirstG));
    }

    @Override
    public void setPositions(GraphModel g) {
        int n = g.getVerticesCount();
        Point ps[] = PositionGenerators.circle(250, 300, 300, n);
        setProductLabel(g);
        int count = 0;
        for (Vertex v : g) {
            v.setLocation(new GPoint(ps[count].x, ps[count].y));
            count++;
        }
    }
}
