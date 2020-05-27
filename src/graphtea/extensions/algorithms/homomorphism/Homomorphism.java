// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.algorithms.homomorphism;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Soroush Sabet
 */
public class Homomorphism {

    private GraphModel domain;
    private GraphModel range;
    HashMap<Vertex, Vertex> homomorphism;

    Homomorphism(GraphModel domain, GraphModel range,
                 HashMap<Vertex, Vertex> map) {
        this.domain = domain;
        this.range = range;
        homomorphism.putAll(map);
    }

    public GraphModel getDomain() {
        return domain;
    }

    public GraphModel getRange() {
        return range;
    }

    public boolean isValid() {
        boolean res = true;
        for (Vertex v : domain) {
            if (!(homomorphism.containsKey(v)) || (homomorphism.get(v) == null)) {
                res = false;
            }

        }
        if (res) {
            Iterator<Edge> i = domain.edgeIterator();
            Edge e;
            while (i.hasNext()) {
                e = i.next();
                res = range.isEdge(homomorphism.get(e.source), homomorphism.get(e.target));
            }
        }

        return res;
    }


}
