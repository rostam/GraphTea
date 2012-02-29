// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.library.algorithms.goperators.product;

import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.util.Pair;

import java.io.FileWriter;
import java.io.IOException;


/**
 * @author Mohammad Ali Rostami
 * @email ma.rostami@yahoo.com
 */
public abstract class GProduct
        <VertexType extends BaseVertex,
                EdgeType extends BaseEdge<VertexType>,
                GraphType extends BaseGraph<VertexType, EdgeType>> {
    protected GraphType g1;
    protected GraphType g2;

    public final GraphType multiply(GraphType g1
            , GraphType g2) {
        GraphType g = (GraphType) g1.createEmptyGraph();
        for (VertexType v1 : g1) {
            for (VertexType v2 : g2) {
                VertexType vnew = (VertexType) v1.getCopy();
                vnew.getProp().obj = new Pair<VertexType, VertexType>(v1, v2);
                g.insertVertex(vnew);
            }
        }

        this.g1 = g1;
        this.g2 = g2;

        for (VertexType v1 : g) {
            for (VertexType v2 : g) {
                if (compare(((Pair<VertexType, VertexType>) v1.getProp().obj).first
                        , ((Pair<VertexType, VertexType>) v2.getProp().obj).first
                        , ((Pair<VertexType, VertexType>) v1.getProp().obj).second
                        , ((Pair<VertexType, VertexType>) v2.getProp().obj).second)) {
                    g.insertEdge((EdgeType) g1.edgeIterator().next().getCopy(v1, v2));
                }

            }
        }

        return g;
    }

    public abstract boolean compare(VertexType v1OfFirstG
            , VertexType v2OfFirstG
            , VertexType v1OfSecondG
            , VertexType v2OfSecondG);
}
