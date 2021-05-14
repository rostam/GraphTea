// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * MatrixGraph.java
 *
 * Created on November 13, 2004, 8:32 PM
 * Last Modify on November 15, 2004, 05:03 AM
 */

package graphtea.library;


import Jama.Matrix;
import graphtea.library.event.handlers.PreWorkHandler;
import graphtea.library.event.handlers.PreWorkPostWorkHandler;
import graphtea.library.exceptions.InvalidEdgeException;
import graphtea.library.exceptions.InvalidGraphException;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.library.genericcloners.EdgeVertexCopier;
import graphtea.library.genericcloners.GraphConverter;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Adjacency Matrix Graph.
 * For information about Adjacency Matrix refer to http://en.wikipedia.org/wiki/Adjacency_matrix 
 *
 * @author Hooman Mohajeri Moghaddam, added weighted version of weightOfEdge method
 * @author Omid Aladini
 * @param <VertexType> Type of the vertices the graph can work with.
 * @param <EdgeType> Type of the edges the graph can work with.
 */
public class MatrixGraph<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        extends BaseGraph<VertexType, EdgeType> {
    /**
     * The data structure to store graph data, it looks like a three dimensional matrix with EdgeType elements.
     * The third dimension is designed to store multiple edges between two vertices.
     * ArrayLists can work with generic type's parameters and they are as fast as arrays for indexing, but
     * they are not thread safe. So MatrixGraph operations are not thread-safe and should be synchronized
     * externally.
     */
    private ArrayList<ArrayList<ArrayList<EdgeType>>> adjacencyMatrix;

    //list of vertices
    private ArrayList<VertexType> vertices;

    //In-degree of vertices by order stored in <code>vertices</code> object.
    //More clearly, the number of edges which their sources are connected to the vertex.
    private ArrayList<Integer> inDegree;
    //Out-degree of vertices by order stored in <code>vertices</code> object.
    //More clearly, the number of edges which their targets are connected to the vertex.
    private ArrayList<Integer> outDegree;

    //Specified whether the graph is directed.
    private boolean directed;
    private int edgeCount = 0;

    /**
     * Constructs a graph object that stores graph data using adjacency matrix data structure.
     *
     * @param directed                 Indicated whether the graph is directed.
     * @param expectedNumberOfVertices Approximate number of vertices that will be
     *                                 added to the graph. This paramether is optional and is available for performance
     *                                 reasons.
     */
    public MatrixGraph(boolean directed, int expectedNumberOfVertices) {
        this.directed = directed;

        adjacencyMatrix = new ArrayList<>(expectedNumberOfVertices);
        for (int rowCount = 0; rowCount < expectedNumberOfVertices; rowCount++) {
            ArrayList<ArrayList<EdgeType>> columns = new ArrayList<>(expectedNumberOfVertices);
            adjacencyMatrix.add(columns);
        }
        vertices = new ArrayList<>(expectedNumberOfVertices);

        inDegree = new ArrayList<>(expectedNumberOfVertices);

        if (!directed)
            outDegree = inDegree;
        else
            outDegree = new ArrayList<>(expectedNumberOfVertices);
    }

    /**
     * Constructs a MatrixGraph object
     *
     * @param directed Whether the graph is directed or undirected.
     */
    public MatrixGraph(boolean directed) {
        this(directed, 5);
    }

    /**
     * Constructs an undirected graph object that stores graph data using
     * adjacency list data structure.
     */
    public MatrixGraph() {
        this(false, 5);
    }

    /**
     * Constructs a graph object that stores graph data using adjacency matrix data structure by importing
     * graph data from a pre-existing graph. A GraphConvertor object is passed as a parameter which is
     * reponsible for duplication/type-convertion of graph elements.
     *
     * @param <ImportVertexType> The type of vertex object which the input graph contain.
     * @param <ImportEdgeType>   The type of edge object which the input graph contain.
     * @param graph              The given graph
     * @param gc          A GraphConverter object which is responsible for duplicating/converting graph
     *                           elements.
     * @throws InvalidGraphException Throws when the input graph is an invalid graph object.
     */
    public <ImportVertexType extends BaseVertex,
            ImportEdgeType extends BaseEdge<ImportVertexType>,
            ImportGraphType extends BaseGraph<ImportVertexType, ImportEdgeType>
            >
    MatrixGraph(BaseGraph<ImportVertexType, ImportEdgeType> graph,
                GraphConverter<ImportVertexType,
                        VertexType,
                        ImportEdgeType,
                        EdgeType,
                        ImportGraphType,
                        MatrixGraph<VertexType, EdgeType>> gc)
            throws InvalidGraphException {
        ArrayList<VertexType> tempAL = new ArrayList<>(getVerticesCount());

        for (ImportVertexType v : graph) {
            insertVertex(gc.convert(v));
            tempAL.add(gc.convert(v));
        }

        Iterator<ImportEdgeType> iet = graph.edgeIterator();

        ImportEdgeType edge;
        try {
            while (iet.hasNext()) {
                edge = iet.next();
                insertEdge(gc.convert(edge, tempAL.get(edge.source.getId()), tempAL.get(edge.target.getId())));
            }
        } catch (InvalidVertexException e) {
            throw new InvalidGraphException();
        }
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#getVerticesCount()
      */
    @Override
    public int getVerticesCount() {
        return vertices.size();
    }

    /**
     * Checks if a vertex with internal id <i>id</i> exist.
     *
     * @param id Id of the vertex.
     * @return true of exist false if not.
     */
    private boolean vertexIdOutOfRange(int id) {
        return id < 0 || id >= vertices.size();

    }

    /**
     * Returns the vertex with internal id <I>id</I>
     *
     * @param id Internal index of the vertex.
     * @return Reference of the vertex object.
     */
    private VertexType getVertex(int id)
            throws InvalidVertexException {
        if (vertexIdOutOfRange(id))
            throw new InvalidVertexException();

        return vertices.get(id);
    }

    /**
     * Lables the vertices using their internal Id property by the index they live inside the graph.
     */
    private void setVertexIds() {
        try {
            for (int i = 0; i < getVerticesCount(); i++)
                getVertex(i).setId(i);
        } catch (InvalidVertexException e) {
            System.out.println("NEVER-HAPPENS EXCEPTION");
            e.printStackTrace();
        }
    }


    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#insertEdge(null)
      */
    @Override
    public void insertEdge(EdgeType newEdge)
            throws InvalidVertexException {
        VertexType sourceObj, targetObj;
        int source, target;

        sourceObj = newEdge.source;
        targetObj = newEdge.target;
        source = newEdge.source.getId();
        target = newEdge.target.getId();

        checkVertex(sourceObj);
        checkVertex(targetObj);

        ArrayList<EdgeType> edges;

        if (adjacencyMatrix.get(source).get(target) == null) {
            edges = new ArrayList<>(5);
            adjacencyMatrix.get(source).set(target, edges);
        } else {
            edges = adjacencyMatrix.get(source).get(target);
        }

        edges.add(newEdge);

        if (!directed)
            adjacencyMatrix.get(target).set(source, edges);

        outDegree.set(source, inDegree.get(source) + 1);
        inDegree.set(target, inDegree.get(target) + 1);

        if (!directed) {
            inDegree.set(source, inDegree.get(source) + 1);
            outDegree.set(target, inDegree.get(target) + 1);
        }

        ++edgeCount;

    }

    /**
     * Returns all edges between two vertices.
     *
     * @param source Index of the edges' start point.
     * @param target Index of the edges' end point.
     * @return An ArrayList of <I>EdgeType</I> containing all edges between <I>from</I> and <I>to</I>.
     * @throws graphtea.library.exceptions.InvalidVertexException
     *          Thrown when two supplied indexes of vertices are invalid.
     */
    private ArrayList<EdgeType> getEdges(int source, int target)
            throws InvalidVertexException {
        if (vertexIdOutOfRange(source) || vertexIdOutOfRange(target))
            throw new InvalidVertexException();

        return adjacencyMatrix.get(source).get(target);
    }

    /**
     * Returns all edges between two vertices.
     *
     * @param source Index of the edges' start point.
     * @param target   Index of the edges' end point.
     * @return An ArrayList of <I>EdgeType</I> containing all edges between <I>from</I> and <I>to</I>.
     * @throws graphtea.library.exceptions.InvalidVertexException
     *          Thrown when two supplied indexes of vertices are invalid.
     */
    @Override
    public ArrayList<EdgeType> getEdges(VertexType source, VertexType target)
            throws InvalidVertexException {
        int sourceId = source.getId();
        int targetId = target.getId();

        checkVertex(target);
        checkVertex(source);

        return adjacencyMatrix.get(sourceId).get(targetId);
    }

    /* (non-Javadoc)
    * @see graphtea.library.BaseGraph#insertVertex(null)
    */
    @Override
    public void insertVertex(VertexType newVertex) {
        vertices.add(newVertex);
        int size = getVerticesCount();
        newVertex.setId(size);

        if (adjacencyMatrix.size() < size) {
            adjacencyMatrix.ensureCapacity(size * 2);
            adjacencyMatrix.add(new ArrayList<>());
        }

        int newSize = adjacencyMatrix.size();
        for (int row = 0; row < newSize; row++) {
            if (row == newSize - 1) {
                for (int newSizeIndex = 0; newSizeIndex < newSize; newSizeIndex++) {
                    adjacencyMatrix.get(row).add(null);
                }
            } else {
                adjacencyMatrix.get(row).add(null);
            }
        }

        inDegree.add(0);
        outDegree.add(0);

    }

    /**
     * Runs Depth First Search (DFS) algorithm on the graph starting from vertex <I>vertexId</I>.
     * A reference to a PreWorkPostWorkHandler is supplied that contains implementation
     * of pre-work and post-work operations that depends on the application of DFS.
     *
     * @param vertex  Starting vertex of the traversal.
     * @param handler A reference to a PreWorkPostWorkHandler that contains implementation
     *                of pre-work and post-work operations that depends on the application of DFS.
     * @return Whether the traversal has stopped at the middle by the handler.
     * @throws InvalidVertexException the vertex is invalid
     */

    public boolean depthFirstSearch(VertexType vertex, PreWorkPostWorkHandler<VertexType> handler) throws InvalidVertexException {
        return new graphtea.library.algorithms.traversal.DepthFirstSearch<>(this)
                .doSearch(vertex, handler);
    }


    /**
     * Runs Breadth First Search (BFS) algorithm on the graph starting from vertex <I>vertexId</I>.
     * A reference to a PreWorkHandler is supplied that contains implementation
     * of pre-work operation that depends on the application of BFS.
     *
     * @param vertex  Starting vertex of the traversal.
     * @param handler A reference to a PreWorkHandler that contains implementation
     *                of pre-work operation that depends on the application of DFS.
     * @return Whether the traversal has stopped at the middle by the handler.
     * @throws InvalidVertexException the vertex is invalid
     */
    public boolean breadthFirstSearch(VertexType vertex, PreWorkHandler<VertexType> handler) throws InvalidVertexException {
        return new graphtea.library.algorithms.traversal.BreadthFirstSearch<>(this)
                .doSearch(vertex, handler);

    }

    /**
     * Checks whether the current graph is a connected graph.
     *
     * @return True if graph is connected and false otherwise.
     */
    public boolean isGraphConnected() {
        try {
            return graphtea.library.algorithms.util.ConnectivityChecker.isGraphConnected(this);

        } catch (InvalidGraphException e) {
            //Generally should not happen. So I don't bother the user by
            //adding throws declaration.
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks whether the current graph is acyclic.
     *
     * @return True if graph is acyclic and false otherwise.
     */
    public boolean isGraphAcyclic() {
        try {
            //Soooo easy to use!
            return graphtea.library.algorithms.util.AcyclicChecker.isGraphAcyclic(this);
        } catch (InvalidGraphException e) {
            //Generally should not happen. So I don't bother the user by
            //adding throws declaration.
            e.printStackTrace();
            return false;
        }
    }

    /* (non-Javadoc)
    * @see java.lang.Iterable#iterator()
    */
    @Override
    public Iterator<VertexType> iterator() {
        return vertices.iterator();
    }

    /* (non-Javadoc)
    * @see graphtea.library.BaseGraph#isDirected()
    */
    @Override
    public boolean isDirected() {
        return directed;
    }

    /**
     * Returns in-degree of vertex <I>vertexId</I>, the number of edges which
     * their target goes to the specified vertex.
     *
     * @return in-degree of vertex <I>vertexId</I>.
     * @throws InvalidVertexException Invalid vertex exception
     */
    private int getInDegree(int vertexId) throws InvalidVertexException {
        if (vertexIdOutOfRange(vertexId))
            throw new InvalidVertexException();
        return inDegree.get(vertexId);
    }

    /**
     * Returns out-degree of vertex <I>vertexId</I>, the number of edges which
     * their tale is attached to the specified vertex.
     *
     * @return out-degree of vertex <I>vertexId</I>.
     * @throws InvalidVertexException The vertex is invalid
     */
    private int getOutDegree(int vertexId) throws InvalidVertexException {
        if (vertexIdOutOfRange(vertexId))
            throw new InvalidVertexException();
        return outDegree.get(vertexId);
    }

    /**
     * This class iterates all, edges coming from or going to a specified vertex.
     * The order of edges the iterator iterate is undefined because of future code changes.
     *
     * @author Omid Aladini
     */
    private class EdgeIterator implements Iterator<EdgeType> {
        private final Iterator<EdgeType> edgesIterator;
        private EdgeType lastEdge = null;

        /**
         * Constructs an Edge Iterator object which iterates through all the edges in the graph.
         * Note that if the graph object is changed during iteration, the iteration may not
         * actually represent current state of the graph. For example, if you deleted an edge
         * after construction of this object, the edge would be included in the iteration.
         */
        public EdgeIterator() {
            ArrayList<EdgeType> edges = new ArrayList<>();
            if (directed) {
                for (ArrayList<ArrayList<EdgeType>> aae : adjacencyMatrix)
                    for (ArrayList<EdgeType> ae : aae)
                        edges.addAll(ae);
            } else {
                int iCount = 0;
                int jCount;
                for (ArrayList<ArrayList<EdgeType>> aae : adjacencyMatrix) {
                    Iterator<ArrayList<EdgeType>> it = aae.iterator();
                    jCount = 0;
                    while (iCount >= jCount) {
                        ++jCount;

                        ArrayList<EdgeType> ae = it.next();

                        if (ae == null)
                            continue;

                        edges.addAll(ae);
                    }
                    ++iCount;
                }
            }
            edgesIterator = edges.iterator();
        }


        /**
         * Number of times edge Iteration is called. This will set as a temporary flag into
         * edges in order to reduce running time of edge iteration back to O(n^2).
         */
        int edgeIterationIndex = 0;

        /**
         * Constructs an Edge Iterator object which iterates through all the edges going to
         * or coming from the specified vertex <code>v</code>.
         * Note that if the graph object is changed during iteration, the iteration may not
         * actually represent current state of the graph. For example, if you deleted an edge
         * after construction of this object, the edge would be included in the iteration.
         *
         * @param v Source or target of desired edges.
         */
        private EdgeIterator(VertexType v)
                throws InvalidVertexException {
            checkVertex(v);

            if (!directed)
                ++edgeIterationIndex;

            ArrayList<EdgeType> edges = new ArrayList<>();

            ArrayList<ArrayList<EdgeType>> row = adjacencyMatrix.get(v.getId());

            for (ArrayList<EdgeType> ae : row)
                for (EdgeType e : ae) {
                    edges.add(e);

                    if (!directed)
                        e.edgeIterationIndex = edgeIterationIndex;
                }

            for (ArrayList<ArrayList<EdgeType>> aae : adjacencyMatrix) {
                if (row == aae)
                    continue;
                for (ArrayList<EdgeType> ae : aae)
                    for (EdgeType e : ae) {
                        if (e.target == v) {
                            if (directed) {
                                edges.add(e);
                            } else if (e.edgeIterationIndex != edgeIterationIndex) {
                                e.edgeIterationIndex = edgeIterationIndex;
                                edges.add(e);
                            }
                        }
                    }
            }

            edgesIterator = edges.iterator();
        }

        /**
         * Constructs an Edge Iterator object which iterates through all the edges going to
         * or coming from (depending on the second parameter) the specified vertex <code>v</code>.
         * If the second parameter it true, then the first parameter is considered to be source of
         * all desired edges, and if it's false the first parameter is considered to be target of desired edges.
         * Note that if the graph object is changed during iteration, the iteration may not
         * actually represent current state of the graph. For example, if you deleted an edge
         * after construction of this object, the edge would be included in the iteration.
         *
         * @param v      If the second parameter is true indicated the vertex which is source of desired edges, otherwise
         *               it is considered to be target of desired edges.
         * @param source True means the first parameter should be considered source of desired edges.
         */
        public EdgeIterator(VertexType v, boolean source)
                throws InvalidVertexException {
            checkVertex(v);

            if (!directed)
                ++edgeIterationIndex;

            ArrayList<EdgeType> edges = new ArrayList<>();

            ArrayList<ArrayList<EdgeType>> row = adjacencyMatrix.get(v.getId());

            if (source) {
                for (ArrayList<EdgeType> ae : row)
                    for (EdgeType e : ae) {
                        edges.add(e);
                        if (!directed)
                            e.edgeIterationIndex = edgeIterationIndex;
                    }
            } else {
                for (ArrayList<ArrayList<EdgeType>> aae : adjacencyMatrix) {
                    if (row == aae)
                        continue;
                    for (ArrayList<EdgeType> ae : aae)
                        for (EdgeType e : ae) {
                            if (e.target == v) {
                                if (directed) {
                                    edges.add(e);
                                } else if (e.edgeIterationIndex != edgeIterationIndex) {
                                    e.edgeIterationIndex = edgeIterationIndex;
                                    edges.add(e);
                                }
                            }
                        }
                }
            }
            edgesIterator = edges.iterator();

        }

        public boolean hasNext() {
            return edgesIterator.hasNext();
        }

        public EdgeType next() {
            lastEdge = edgesIterator.next();
            return lastEdge;
        }

        public void remove() {
            try {
                removeEdge(lastEdge);
            } catch (InvalidEdgeException e) {
                System.out.println("Invalid remove operation.");
                e.printStackTrace();
            }
        }

    }

    /**
     * Returns iterator object for the edges.
     *
     * @return iterator object for the edges.
     */
    public Iterator<EdgeType> edgeIterator() {
        return new EdgeIterator();

    }

    public Iterable<EdgeType> edges() {
        return this::edgeIterator;
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#edgeIterator(null)
      */
    @Override
    public Iterator<EdgeType> edgeIterator(VertexType v)
            throws InvalidVertexException {
        return new EdgeIterator(v);
    }
    
    /* (non-Javadoc)
     * @see graphtea.library.BaseGraph#edgeIterator(null, boolean)
     */
   @Override
   public Iterator<EdgeType> edgeIterator(VertexType v, boolean source)
           throws InvalidVertexException {
       return new EdgeIterator(v, source);
   }


    /* (non-Javadoc)
    * @see graphtea.library.BaseGraph#getAdjacencyMatrix()
    */
    @Override
    public Matrix getAdjacencyMatrix() {
        Matrix matrix = new Matrix(getVerticesCount(), getVerticesCount());
        try {
            if (directed) {
                for (int i = 0; i < getVerticesCount(); i++)
                    for (int j = 0; j < getVerticesCount(); j++)
                        if (getEdges(i, j) != null)
                            matrix.set(i, j, getEdges(i, j).size());
            } else {
                for (int i = 0; i < getVerticesCount(); i++)
                    for (int j = 0; j <= i; j++)
                        if (getEdges(i, j) != null) {
                            matrix.set(i, j, getEdges(i, j).size());
                            if (i != j)
                                matrix.set(j, i, getEdges(i, j).size());
                        }
            }
        } catch (Exception e) {
            //never happens
            System.out.println("NEVER-HAPPENS-BUG:getAdjMatrix:");
            e.printStackTrace();
        }
        return matrix;
    }

    
    public Matrix getWeightedAdjacencyMatrix() {
        Matrix matrix = new Matrix(getVerticesCount(), getVerticesCount());
        try {
            if (directed) {
                for (int i = 0; i < getVerticesCount(); i++)
                    for (int j = 0; j < getVerticesCount(); j++)
                        if (getEdges(i, j) != null)
                            matrix.set(i, j, getEdges(i, j).get(0).getWeight());
            } else {
                for (int i = 0; i < getVerticesCount(); i++)
                    for (int j = 0; j <= i; j++)
                        if (getEdges(i, j) != null) {
                            matrix.set(i, j, getEdges(i, j).get(0).getWeight());
                            if (i != j)
                                matrix.set(j, i, getEdges(i, j).get(0).getWeight());
                        }
            }
        } catch (Exception e) {
            //never happens
            System.out.println("NEVER-HAPPENS-BUG:getAdjMatrix:");
            e.printStackTrace();
        }
        return matrix;
    }
    /* (non-Javadoc)
    * @see graphtea.library.BaseGraph#dump()
    */
    @Override
    public void dump() {
        System.out.print('\n');
        for (int i = 0; i < getVerticesCount(); i++) {
            for (int j = 0; j < getVerticesCount(); j++) {
                System.out.print(" ");
                System.out.print(adjacencyMatrix.get(i).get(j) == null ? 0 : 1);
            }
            System.out.println();
        }
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#removeAllEdges(null, null)
      */
    @Override
    public void removeAllEdges(VertexType source, VertexType target) throws InvalidVertexException {
        if (vertexIdOutOfRange(source.getId()) || vertexIdOutOfRange(target.getId()) ||
                source != vertices.get(source.getId()) || target != vertices.get(target.getId()))
            throw new InvalidVertexException();

        adjacencyMatrix.get(source.getId()).get(target.getId()).clear();

        if (!directed)
            removeAllEdges(target, source);
    }

    /**
     * Removes a vertex and all it's connected edges.
     *
     * @param vertexId index of the vertex to be removed
     */
    private void removeVertex(int vertexId)
            throws InvalidVertexException {
        for (ArrayList<ArrayList<EdgeType>> c : adjacencyMatrix) {
            c.remove(vertexId);
        }
        adjacencyMatrix.remove(vertexId);

        vertices.remove(vertexId);
        inDegree.remove(vertexId);
        outDegree.remove(vertexId);

        setVertexIds();
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#removeVertex(null)
      */
    @Override
    public void removeVertex(VertexType v) throws InvalidVertexException {
        checkVertex(v);
        removeVertex(v.getId());
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#getInDegree(null)
      */
    @Override
    public int getInDegree(VertexType v) throws InvalidVertexException {
        checkVertex(v);
        return getInDegree(v.getId());
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#getOutDegree(null)
      */
    @Override
    public int getOutDegree(VertexType v) throws InvalidVertexException {
        checkVertex(v);
        return getOutDegree(v.getId());
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#removeEdge(null)
      */
    @Override
    public void removeEdge(EdgeType edge)
            throws InvalidEdgeException {
        int source = edge.source.getId();
        int target = edge.target.getId();

        try {
            checkVertex(edge.target);
            checkVertex(edge.source);
        } catch (InvalidVertexException e) {
            throw new InvalidEdgeException();
        }

        adjacencyMatrix.get(target).get(source).remove(edge);

        if (!directed)
            adjacencyMatrix.get(source).get(target).remove(edge);

        --edgeCount;
    }

    public VertexType getAVertex() {
        Iterator<VertexType> it = iterator();
        if (it.hasNext())
            return it.next();
        else
            return null;
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#copy(graphtea.library.GraphConverter)
      */
    @Override
    public BaseGraph<VertexType, EdgeType>
           copy(EdgeVertexCopier<VertexType, EdgeType> gc)
            throws InvalidGraphException {
        MatrixGraph<VertexType, EdgeType> oGraph = new MatrixGraph<>(directed, getVerticesCount());

        ArrayList<VertexType> tempAL = new ArrayList<>(getVerticesCount());

        VertexType tempVertex;
        for (VertexType v : this) {
            tempVertex = gc.convert(v);
            oGraph.insertVertex(tempVertex);
            tempAL.add(tempVertex);
        }

        Iterator<EdgeType> iet = edgeIterator();

        EdgeType edge;
        try {
            while (iet.hasNext()) {
                edge = iet.next();
                oGraph.insertEdge(gc.convert(edge, tempAL.get(edge.source.getId()), tempAL.get(edge.target.getId())));
            }
        } catch (InvalidVertexException e) {
            throw new InvalidGraphException();

        }

        return oGraph;
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#containsVertex(null)
      */
    @Override
    public boolean containsVertex(VertexType v) {
        return vertices.contains(v);
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#checkVertex(null)
      */
    @Override
    public void checkVertex(VertexType v)
            throws InvalidVertexException {
        if (vertexIdOutOfRange(v.getId()) || v != vertices.get(v.getId()))
            throw new InvalidVertexException();
    }

    public <GraphType extends BaseGraph<VertexType, EdgeType>> GraphType createEmptyGraph() {
        return null;
    }

    @Override
    public boolean isEdge(VertexType source, VertexType target)
            throws InvalidVertexException {
        return (getEdges(source, target) != null) && (getEdges(source, target).size() != 0);
    }

//    @Override
    public MatrixGraph<VertexType, EdgeType> dcreateEmptyGraph() {
        return new MatrixGraph<>(directed, 0);
    }

    @Override
    public void setDirected(boolean isDirected) {
        //TODO: Stub
        throw new RuntimeException("Not yet implemented.");
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#getVertexArray()
      */
    @Override
    public BaseVertex[] getVertexArray() {
        BaseVertex[] arr = new BaseVertex[getVerticesCount()];

        for (VertexType v : this)
            arr[v.getId()] = v;

        return arr;
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#getEdgeArray()
      */
    @Override
    public int[][] getEdgeArray() {
        int[][] arr = new int[getVerticesCount()][];

        int i = 0;
        int j;
        int k;

        for (ArrayList<ArrayList<EdgeType>> ll : adjacencyMatrix) {
            j = 0;

            ArrayList<Integer> temp = new ArrayList<>();

            for (ArrayList<EdgeType> alet : ll) {
                if (alet != null && alet.size() != 0)
                    temp.add(j++);
            }

            arr[i] = new int[temp.size()];

            k = 0;
            for (Integer vertexId : temp)
                arr[i][k++] = vertexId;

            ++i;
        }
        return arr;
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#lightEdgeIterator()
      */
    @Override
    public Iterator<EdgeType> lightEdgeIterator() {
        //TODO: Stub, not yet light.
        return edgeIterator();
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#lightEdgeIterator(null)
      */
    @Override
    public Iterator<EdgeType> lightEdgeIterator(VertexType v) throws InvalidVertexException {
        //TODO: Stub, not yet light.
        return edgeIterator(v);
    }

    public Iterator<EdgeType> lightBackEdgeIterator(VertexType v) throws InvalidVertexException {
        //TODO: Stub
        throw new RuntimeException("Not yet implemented.");
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#clear()
      */
    @Override
    public void clear() {
        int expectedNumberOfVertices = 0;
        adjacencyMatrix = new ArrayList<>(expectedNumberOfVertices);
        for (int rowCount = 0; rowCount < expectedNumberOfVertices; rowCount++) {
            ArrayList<ArrayList<EdgeType>> columns = new ArrayList<>(expectedNumberOfVertices);
            adjacencyMatrix.add(columns);
        }
        vertices = new ArrayList<>(expectedNumberOfVertices);

        inDegree = new ArrayList<>(expectedNumberOfVertices);

        if (!directed)
            outDegree = inDegree;
        else
            outDegree = new ArrayList<>(expectedNumberOfVertices);

        edgeCount = 0;
    }

    @Override
    public int getEdgesCount() {
        return edgeCount;
    }

    /* (non-Javadoc)
     * @see graphtea.library.BaseGraph#weightOfEdge(null, null)
     */
   @Override
    public int[] weightOfEdge(VertexType source, VertexType target)
    throws InvalidVertexException
    {
	   int[] res=null;

	   if(isEdge(source, target))
	   {
		   AbstractList<EdgeType> edges = getEdges(source, target);
		   int i=0;
		   for(EdgeType et : edges )
		   {
			   res[i] = et.getWeight();
			   i++;
		   }
	   }
	   return res;
    }

}

