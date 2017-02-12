// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/**
 * TestListGraph.java
 *
 * Created on November 15, 2004, 4:30 AM
 */

package graphtea.library.test;

import graphtea.library.*;
import graphtea.library.algorithms.spanningtree.Kruskal;
import graphtea.library.algorithms.subgraphs.InducedSubgraphs;
import graphtea.library.algorithms.traversal.BreadthFirstSearch;
import graphtea.library.algorithms.traversal.DepthFirstSearch;
import graphtea.library.algorithms.util.AcyclicChecker;
import graphtea.library.algorithms.util.ConnectivityChecker;
import graphtea.library.event.handlers.PreWorkPostWorkHandler;
import graphtea.library.genericcloners.BaseEdgeVertexCopier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Omid Aladini
 */
public class TestListGraph {

    static class SampleTraversalHandler implements PreWorkPostWorkHandler<BaseVertex> {
        ArrayList<Integer> existanceSet = new ArrayList<>();

        public boolean doPreWork(BaseVertex fromVertex, BaseVertex toVertex) {
            System.out.print("->" + toVertex.getId());

            if (existanceSet.contains(toVertex.getId()))
                System.out.println("Oh, double entering!");
            else
                existanceSet.add(toVertex.getId());

            return false;
        }

        public boolean doPostWork(BaseVertex returnFrom, BaseVertex returnTo) {
            System.out.println("");
            System.out.print("<-" + returnTo.getId());
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
            if (o1.getWeight() < o2.getWeight())
                return -1;
            else if (o1.getWeight() == o2.getWeight())
                return 0;
            else
                return 1;
        }
    }

    public static void mainCheckAcyclicChecker(String args[]) {
        ListGraph<BaseVertex, BaseEdge<BaseVertex>> myListGraph = new ListGraph<>(true, 0);
        BaseVertex v1 = new BaseVertex();
        BaseVertex v2 = new BaseVertex();
        BaseVertex v3 = new BaseVertex();
        BaseVertex v4 = new BaseVertex();
        myListGraph.insertVertex(v1);
        myListGraph.insertVertex(v2);
        myListGraph.insertVertex(v3);
        myListGraph.insertVertex(v4);

        myListGraph.insertEdge(new BaseEdge<>(v1, v2));
        myListGraph.insertEdge(new BaseEdge<>(v2, v3));
        myListGraph.insertEdge(new BaseEdge<>(v3, v4));
        BaseEdge<BaseVertex> e = new BaseEdge<>(v4, v1);
        myListGraph.insertEdge(e);
        //myListGraph.removeEdge(e);
        System.out.println("Acyclic:" + (AcyclicChecker.isGraphAcyclic(myListGraph) ? "yes" : "no"));

        /*
          final int iterations = 1000;
          int acyclic = 0;
          for(int i =0;i<iterations;++i)
          {
              ListGraph<BaseVertex,BaseEdge<BaseVertex>> myListGraph =
                  generateRandomListGraph(10,9);

              acyclic += AcyclicChecker.isGraphAcyclic(myListGraph)?1:0;
              //System.out.println("Graph acyclick: " + isAc);
              System.out.println("i:" + i);
          }

          System.out.println("Graph acyclic: " + ((double)(acyclic))/(iterations)*100 + "% of times.");
          */
    }

    public static void mainCheckConnectivityChecker(String args[]) {
        final int iterations = 100000;
        int connected = 0;
        for (int i = 0; i < iterations; ++i) {
            ListGraph<BaseVertex, BaseEdge<BaseVertex>> myListGraph =
                    generateRandomListGraph(3, 2);

            connected += ConnectivityChecker.isGraphConnected(myListGraph) == true ? 1 : 0;
        }

        System.out.println("Graph connected: " + ((double) (connected)) / (iterations) * 100 + "% of times.");
    }

    public static void mainCheckTraversals(String args[]) {
        ListGraph<BaseVertex, BaseEdge<BaseVertex>> myListGraph =
                generateRandomListGraph(30, 100);

        System.out.println("-------------DFS TEST-----------");

        new DepthFirstSearch<>(myListGraph)
                .doSearch(myListGraph.getAVertex(), new SampleTraversalHandler());

        for (BaseVertex v : myListGraph)
            if (!v.getMark())
                System.out.println("Oh, one vertex not traversed!");

        System.out.println("\n-------------BFS TEST-----------");

        new BreadthFirstSearch<>(myListGraph)
                .doSearch(myListGraph.getAVertex(), new SampleTraversalHandler());


        for (BaseVertex v : myListGraph)
            if (!v.getMark())
                System.out.println("Oh, one vertex not traversed!");

    }

    public static ListGraph<BaseVertex, BaseEdge<BaseVertex>>
    generateRandomListGraph(final int vertexCount, final int edgeCount) {
        ListGraph<BaseVertex, BaseEdge<BaseVertex>> myListGraph =
                new ListGraph<>();

        Vector<BaseVertex> vbv = new Vector<>();

        for (int i = 0; i < vertexCount; ++i)
            vbv.add(new BaseVertex());

        for (BaseVertex v : vbv)
            myListGraph.insertVertex(v);

        java.util.Random r = new java.util.Random(/*109876L*/);

        for (int i = 0; i < edgeCount; ++i) {
            int randV1 = Math.abs(r.nextInt()) % vertexCount;
            int randV2 = Math.abs(r.nextInt()) % vertexCount;
            myListGraph.insertEdge(new BaseEdge<>(vbv.get(randV1), vbv.get(randV2), new BaseEdgeProperties(0, 0, false)));
        }
        return myListGraph;
    }

    public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
    void
    setRandomWeights(BaseGraph<VertexType, EdgeType> graph, int limit) {
        java.util.Random r = new java.util.Random();
        Iterator<EdgeType> iet = graph.edgeIterator();
        while (iet.hasNext())
            iet.next().setWeight(Math.abs(r.nextInt()) % limit);
    }


    public static void mainTestVertexInduced(String args[]) {
        ListGraph<BaseVertex, BaseEdge<BaseVertex>> myListGraph = generateRandomListGraph(30, 100);
        myListGraph.dump();

        ArrayList<BaseVertex> arr = new ArrayList<>();

        for (int i = 0; i < myListGraph.getVerticesCount(); i += 2)
            arr.add(myListGraph.getVertexArray()[i]);

        BaseGraph<BaseVertex, BaseEdge<BaseVertex>> vInducedGraph =
                InducedSubgraphs.getVertexInducedSubgraph(myListGraph, arr);

        vInducedGraph.dump();
    }

    public static void mainTestEdgeInduced(String args[]) {
        ListGraph<BaseVertex, BaseEdge<BaseVertex>> myListGraph = generateRandomListGraph(30, 100);
        myListGraph.dump();

        ArrayList<BaseEdge<BaseVertex>> arr = new ArrayList<>();

        Iterator<BaseEdge<BaseVertex>> it = myListGraph.edgeIterator();
        for (int i = 0; i < myListGraph.getEdgesCount(); ++i) {
            BaseEdge<BaseVertex> e = it.next();
            if (i % 12 == 0)
                arr.add(e);
        }

        BaseGraph<BaseVertex, BaseEdge<BaseVertex>> eInducedGraph =
                InducedSubgraphs.getEdgeInducedSubgraph(myListGraph, arr);

        eInducedGraph.dump();
    }

    public static void mainOld(String args[]) {
        try {
            ListGraph<BaseVertex, BaseEdge<BaseVertex>> myList = new ListGraph<>();
            BaseVertex v0 = new BaseVertex();
            BaseVertex v1 = new BaseVertex();
            BaseVertex v2 = new BaseVertex();

            myList.insertVertex(v0);
            myList.insertVertex(v1);
            myList.insertVertex(v2);

            myList.insertEdge(new BaseEdge<>(v0, v1, new BaseEdgeProperties(0, 5, false)));
            myList.insertEdge(new BaseEdge<>(v1, v2, new BaseEdgeProperties(0, 10, false)));
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

    public static void mainTestKruskal(String args[]) {
        ListGraph<BaseVertex, BaseEdge<BaseVertex>> l = new ListGraph<>();
        ListGraph<BaseVertex, BaseEdge<BaseVertex>> f = l.createEmptyGraph();
        final int iterations = 100;
        for (int i = 0; i < iterations; ++i) {
            ListGraph<BaseVertex, BaseEdge<BaseVertex>> myListGraph =
                    generateRandomListGraph(50, 90);
            setRandomWeights(myListGraph, 20);
            int count = Kruskal.findMinimumSpanningTree(myListGraph).size();
            System.out.println("Size of spanning tree:" + count);
        }

    }

    public static void main/*TestLightEdgeIterator*/(String args[]) {
        ListGraph<BaseVertex, BaseEdge<BaseVertex>> g
                = new ListGraph<>(false, 3);
        BaseVertex v[] = new BaseVertex[3];
        for (int i = 0; i < 3; i++) {
            v[i] = new BaseVertex();
            g.insertVertex(v[i]);
        }
        g.insertEdge(new BaseEdge<>(v[1], v[0]));
        g.insertEdge(new BaseEdge<>(v[2], v[1]));
        Iterator<BaseEdge<BaseVertex>> ie = g.lightEdgeIterator(v[1]);
        while (ie.hasNext()) {
            System.out.println(ie.next());
        }
        System.out.println("**");
        for (BaseVertex vv : g.getNeighbors(v[1]))
            System.out.println(vv);
    }

}
