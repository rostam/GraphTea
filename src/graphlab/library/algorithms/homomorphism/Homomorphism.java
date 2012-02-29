// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.homomorphism;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Soroush Sabet
 */
public class Homomorphism<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>> {

    private BaseGraph<VertexType, EdgeType> domain;
    private BaseGraph<VertexType, EdgeType> range;
    HashMap<VertexType, VertexType> homomorphism;

    Homomorphism(BaseGraph<VertexType, EdgeType> domain, BaseGraph<VertexType, EdgeType> range,
                 HashMap<VertexType, VertexType> map) {
        this.domain = domain;
        this.range = range;
        homomorphism.putAll(map);
    }

    public BaseGraph<VertexType, EdgeType> getDomain() {
        return domain;
    }

    public BaseGraph<VertexType, EdgeType> getRange() {
        return range;
    }

    public boolean isValid() {
        boolean res = true;
        for (VertexType v : domain) {
            if (!(homomorphism.containsKey(v)) || (homomorphism.get(v) == null)) {
                res = false;
            }

        }
        if (res) {
            Iterator<EdgeType> i = domain.edgeIterator();
            EdgeType e;
            while (i.hasNext()) {
                e = i.next();
                res = range.isEdge(homomorphism.get(e.source), homomorphism.get(e.target));
            }
        }

        return res;
    }


}
