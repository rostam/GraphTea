// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

import graphtea.library.BaseEdge;
import graphtea.library.BaseEdgeProperties;
import graphtea.library.BaseVertex;
import graphtea.library.MatrixGraph;
import graphtea.library.algorithms.traversal.BreadthFirstSearch;
import graphtea.library.algorithms.traversal.DepthFirstSearch;
import graphtea.library.event.handlers.PreWorkPostWorkHandler;
import graphtea.library.genericcloners.BaseEdgeVertexCopier;

import java.util.Comparator;
import java.util.Iterator;

/**
 * TestMatrixGraph.java
 *
 * Created on November 15, 2004, 4:30 AM
 * @author Omid Aladini
 */
public class TestMatrixGraph {

    static class SampleTraversalHandler implements PreWorkPostWorkHandler<BaseVertex> {
        public boolean doPreWork(BaseVertex fromVertex, BaseVertex toVertex) {
            System.out.print("->" + toVertex.getId());
            return false;
        }

        public boolean doPostWork(BaseVertex returnFrom, BaseVertex returnTo) {
            System.out.println("<-" + returnTo.getId());
            return false;
        }

    }

    /*
      * This class is for comparing two edges according to their weights.
      * @author Omid Aladini
      */
    public static class BaseEdgeWeightComparator
            implements Comparator<BaseEdge<BaseVertex>> {
        public int compare(BaseEdge<BaseVertex> o1, BaseEdge<BaseVertex> o2) {
            return Integer.compare(o1.getWeight(), o2.getWeight());
        }
    }

    public static void main(String args[]) {
        try {
            MatrixGraph<BaseVertex, BaseEdge<BaseVertex>> myList = new MatrixGraph<>();
            BaseVertex v0 = new BaseVertex();
            BaseVertex v1 = new BaseVertex();
            BaseVertex v2 = new BaseVertex();

            myList.insertVertex(v0);
            myList.insertVertex(v1);
            myList.insertVertex(v2);

            myList.insertEdge(new BaseEdge<>(v0, v1, new BaseEdgeProperties(0, 5, false)));
            myList.insertEdge(new BaseEdge<>(v1, v2, new BaseEdgeProperties(0, 1, false)));
            myList.insertEdge(new BaseEdge<>(v2, v0, new BaseEdgeProperties(0, 2, false)));
            myList.dump();

            BaseVertex v3 = new BaseVertex();

            myList.insertVertex(v3);
            myList.insertEdge(new BaseEdge<>(v2, v3, new BaseEdgeProperties(0, 1, false)));
            myList.insertEdge(new BaseEdge<>(v0, v1, new BaseEdgeProperties(0, 20, false)));
            System.out.println("||---");
            myList.dump();
            System.out.println("||---");
            myList.copy(new BaseEdgeVertexCopier()).dump();
            System.out.println("TestMatrixGraph graph traversal");
            System.out.println("\nDFS");

            BaseVertex v = myList.getAVertex();

            Iterator<BaseEdge<BaseVertex>> eIterator = myList.edgeIterator();
            System.out.print('\n');
            while (eIterator.hasNext()) {
                BaseEdge e = eIterator.next();
                System.out.println("An Edge from:" + e.source.getId() + " to:" + e.target.getId());
            }

            System.out.println("\nPrim Algorithm on this graph:");
            myList.dump();
//prim output has been changed
//			new Prim<BaseVertex,BaseEdge<BaseVertex>>(myList, new BaseEdgeVertexGraphConverter()).
//					findMinimumSpanningTree(myList.getAVertex(),new BaseEdgeWeightComparator()).dump();

            //System.out.println("\nRemoving a vertex " + myList.getVerticesCount());
            //myList.removeVertex(v);

            System.out.println("\nBefore traversal");
            new DepthFirstSearch<>(myList).doSearch(v, new SampleTraversalHandler());
            new BreadthFirstSearch<>(myList).doSearch(v, new SampleTraversalHandler());

        } catch (Exception e) {
            System.out.println("Noooooo:");
            e.printStackTrace();
        }
    }

}
