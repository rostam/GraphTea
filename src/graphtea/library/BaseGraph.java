// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * BaseGraph.java
 *
 * Created on November 13, 2004, 8:20 PM
 */

package graphtea.library;

import Jama.Matrix;
import graphtea.library.exceptions.InvalidEdgeException;
import graphtea.library.exceptions.InvalidGraphException;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.library.genericcloners.EdgeVertexCopier;

import java.util.AbstractList;
import java.util.Iterator;

/**
 * Generic base class for representation of all types of graphs.
 * @author Hooman Mohajeri Moghaddam, added weighted version of weightOfEdge method 
 * @author Omid Aladini
 */
abstract public class BaseGraph<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        implements Iterable<VertexType> {
    /**
     * Returns the number of vertices.
     *
     * @return Number of vertices in the graph.
     */
    public abstract int getVerticesCount();

    /**
     *
     * @return the same as getVerticesCount()
     * @see graphtea.library.BaseGraph#getVerticesCount()
     */
    public int numOfVertices(){
        return getVerticesCount();
    }
    /**
     * Creates a clone of the current graph using the GraphConverter object which is responsible
     * for duplication of the graph elements (edges and vertices).
     *
     * @param gc Reference to EdgeVertexCopier object.
     * @return Clone of the current graph which is independent of it's source graph.
     * @throws InvalidGraphException If the graph is not a valid graph object.
     */
    public abstract BaseGraph<VertexType, EdgeType>
    copy(EdgeVertexCopier<VertexType, EdgeType> gc)
            throws InvalidGraphException;

    /**
     * Inserts an edge in the graph.
     *
     * @param newEdge Reference to the new edge object.
     * @throws graphtea.library.exceptions.InvalidVertexException
     *          Thrown when the edge object tries
     *          to connect two vertices whom their indexes are invalid.
     */
    public abstract void insertEdge(EdgeType newEdge)
            throws InvalidVertexException;

    /**
     *
     * @param newEdge the new edge to be added
     * @throws InvalidVertexException the vertex is invalid
     * @see BaseGraph#insertEdge(EdgeType)
     */
    public void addEdge(EdgeType newEdge)
            throws InvalidVertexException{
        insertEdge(newEdge);
    }
    /**
     * Removes all edges between two vertices.
     *
     * @param source Index of the edges' start point.
     * @param target Index of the edges' end point.
     * @throws graphtea.library.exceptions.InvalidVertexException
     *          Thrown when two supplied indexes of vertices are invalid.
     */
    public abstract void removeAllEdges(VertexType source, VertexType target)
            throws InvalidVertexException;

    /**
     * Removes an edge from the graph.
     *
     * @param edge Edge to be removed.
     * @throws InvalidEdgeException If <code>edge</code> is an invalid edge object.
     */
    public abstract void removeEdge(EdgeType edge)
            throws InvalidEdgeException;

    /**
     * Returns a collection of all edges which connects two vertices supplied as first and second arguments of
     * this method.
     *
     * @param source of the desired edges.
     * @param target of the desired edges.
     * @return Returns a collection of all edges which connects two vertices supplied as first and second arguments of
     *         this method.
     * @throws InvalidVertexException if supplied source or target are invalid.
     */
    public abstract AbstractList<EdgeType> getEdges(VertexType source, VertexType target)
            throws InvalidVertexException;

    /**
     * Returns true if there is an edge between specified vertices (direction considered for directed graphs).
     * * @param Source of the edge for existance check.
     *
     * @param source of the desired edges.
     * @param target of the edge for existance check.
     * @return true if there is an edge between specified vertices (direction considered for directed graphs).
     * @throws InvalidVertexException if supplied source or target are invalid.
     */
    public abstract boolean isEdge(VertexType source, VertexType target)
            throws InvalidVertexException;
    
    
    /**
     * Gets the weight of the edge between two vertices(if an edge exists).\
     * Note that it currently supports only one edge between two vertices!!!!
     * However the return type is an array to support hyper-graphs.
     * @param source of the edge to check.
     * @param target of the edge to check.
     * @return the weight of the edge if it exists, null otherwise (direction considered for directed graphs).
     * @throws InvalidVertexException if supplied source or target are invalid.
     */
    public abstract int[] weightOfEdge(VertexType source, VertexType target)
            throws InvalidVertexException;

    /**
     * Inserts a new vertex to the graph.
     *
     * @param newVertex The new vertex to be inserted.
     */
    public abstract void insertVertex(VertexType newVertex);

    /**
     *
     * @param newVertex the new vertex
     * @see BaseGraph#insertVertex(VertexType)
     */
    public void addVertex(VertexType newVertex){
        insertVertex(newVertex);
    }
    /**
     * Removes a vertex and all it's connected edges.
     *
     * @param v the vertex to be removed.
     */
    public abstract void removeVertex(VertexType v)
            throws InvalidVertexException;

    /**
     * Returns iterator object for the vertices.
     *
     * @return iterator object for the vertices.
     */
    public abstract Iterator<VertexType> iterator();

    /**
     * Returns in-degree of vertex <I>vertexId</I>, the number of edges which
     * their target goes to the specified vertex.
     *
     * @return in-degree of vertex <I>vertexId</I>.
     * @throws InvalidVertexException the vertex is invalid
     * @see graphtea.library.BaseGraph#getDegree(BaseVertex)
     */
    public abstract int getInDegree(VertexType v)
            throws InvalidVertexException;

    /**
     * Returns out-degree of the supplied vertex, the number of edges which
     * their source is attached to the specified vertex.
     *
     * @return out-degree of vertex <I>vertexId</I>.
     * @throws InvalidVertexException the vertex is invalid
     * @see graphtea.library.BaseGraph#getDegree(BaseVertex)
     */
    public abstract int getOutDegree(VertexType v)
            throws InvalidVertexException;

    /**
     * Returns a Jama Matrix object that represents adjacency matrix of
     * the graph. the Matrix object have the ability apply simple linear
     * algebra operations on the adjacency matrix.
     *
     * @return Adjacency Matrix of the graph as a Jama Matrix object.
     */
    public abstract Matrix getAdjacencyMatrix();
    
    /**
     * Returns a Jama Matrix object that represents weighted adjacency matrix of
     * the graph. the Matrix object have the ability apply simple linear
     * algebra operations on the adjacency matrix.
     *
     * @return Adjacency Matrix of the graph as a Jama Matrix object.
     */
    public abstract Matrix getWeightedAdjacencyMatrix();

    /**
     * Returns whether the graph is directed.
     *
     * @return True is graph is constructed as a directed graph and false otherwise.
     */
    public abstract boolean isDirected();

    /**
     * Prints the Adjacency Matrix to the standard output.
     */
    public abstract void dump();

    /**
     * Constructs and returns an Edge Iterator object which iterates through all the edges in the graph.
     * Note that if the graph object is changed during iteration, the iteration may not
     * actually represent current state of the graph. For example, if you deleted an edge
     * after construction of this object, the edge would be included in the iteration.
     *
     * @return Iterator object on edges.
     * @see BaseGraph#lightEdgeIterator()
     */
    public abstract Iterator<EdgeType> edgeIterator();

    /**
     * Constructs an Edge Iterator object which iterates through all the edges going to
     * or coming from the specified vertex <code>v</code>.
     * Note that if the graph object is changed during iteration, the iteration may not
     * actually represent current state of the graph. For example, if you deleted an edge
     * after construction of this object, the edge would be included in the iteration.
     *
     * @param v Source or target of desired edges.
     * @return Iterator object on edges which their sources or targets are the supplied vertex.
     * @see graphtea.library.BaseGraph#lightEdgeIterator(BaseVertex)
     * @see graphtea.library.BaseGraph#getNeighbors(BaseVertex)
     */
    public abstract Iterator<EdgeType> edgeIterator(VertexType v)
            throws InvalidVertexException;
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
     * @return Iterator on edges which either their sources or their targets, but not necessarily both,
     *         are the supplied vertex.
     */
    public abstract Iterator<EdgeType> edgeIterator(VertexType v, boolean source)
            throws InvalidVertexException;
    /**
     * This method returns true if the graph contains the specified vertex, false otherwise.
     *
     * @param v Vertex to check existance.
     * @return True if the graph contains the specified vertex, false otherwise.
     */
    public abstract boolean containsVertex(VertexType v);

    /**
     * If the supplied vertex is invalid (Not one of graph's vertices), throws InvalidVertexException. This
     * method should be called before any operation by algorithms where some vertices
     * are supplied as their arguments.
     *
     * @param v The vertex to be checked.
     * @throws InvalidVertexException If the supplied vertex is invalid.
     */
    public abstract void checkVertex(VertexType v)
            throws InvalidVertexException;

    /**
     * Returns a new instance of an empty graph of the current graph type.
     *
     * @return A new instance of an empty graph of the current graph type.
     */
    public abstract <GraphType extends BaseGraph<VertexType, EdgeType>>
    GraphType createEmptyGraph();

    /**
     * Returns array of vertices upcasted to BaseVertex.
     *
     * @return Array of vertices upcasted to BaseVertex.
     */
    public abstract BaseVertex[] getVertexArray();

    /**
     * Returns array of array of 'int's where represents a simple adjacency list.
     *
     * @return Array of array of 'int's where represents a simple adjacency list.
     */
    public abstract int[][] getEdgeArray();

    /**
     * Returns a light(weight) Edge Iterator object which iterates through all the edges in the graph.
     * The light(weight) edge iterator presents an iterator with O(1) constructor. Note that you should
     * not change the content of the graph during your iteration. You can still change properties of
     * each edge or vertex.
     *
     * @return Returns a light(weight) Edge Iterator object.
     */
    public abstract Iterator<EdgeType> lightEdgeIterator();

    /**
     * Constructs a light(weight) Edge Iterator object which iterates through all the edges going to
     * or coming from the specified vertex <code>v</code>.
     * The light(weight) edge iterator presents an iterator with O(1) constructor. Note that you should
     * not change the content of the graph during your iteration. You can still change properties of
     * each edge or vertex.
     *
     * @param v Source or target of desired edges.
     * @return A light(weight) Edge Iterator object which iterates through all the edges going to
     *         or coming from the specified vertex.
     */
    public abstract Iterator<EdgeType> lightEdgeIterator(VertexType v)
            throws InvalidVertexException;

    /**
     * in a directed graph this method returns edges whose targets are v
     *
     * @param v The given vertex
     * @return An iterator over the edges with the target v
     */
    public abstract Iterator<EdgeType> lightBackEdgeIterator(VertexType v)
            throws InvalidVertexException;

    /**
     * Clears the graph.
     */
    public abstract void clear();

    public abstract void setDirected(boolean isDirected);

    /**
     * If zero, indicates that the graph is not a subgraph. If greater than zero,
     * it indicates that it's a subgraph with Id <code>subgraphId</code>.
     */
    int subgraphIndex = 0;

    /**
     * Whether the graph is a subgraph. Then it shares it's contents.
     */
    private boolean isSubgraph = false;
    private BaseGraph<VertexType, EdgeType> superGraph = null;

    /**
     * Sets the graph as a subgraph.
     *
     * @param superGraph The supergraph
     */
    public void registerSubgraph(BaseGraph<VertexType, EdgeType> superGraph) {
        isSubgraph = true;
        this.superGraph = superGraph;
        superGraph.informVertices();
    }

    private void informVertices() {
        if (isSubgraph)
            superGraph.informVertices();

        for (VertexType v : this) {
            v.informNewSubgraph();
        }

    }

    /**
     * Get new id for a new subgraph;
     *
     */
    public int getNewSubgraphIndex() {
        if (isSubgraph)
            return superGraph.getNewSubgraphIndex();
        int lastSubgraphIndex = 0;
        return lastSubgraphIndex + 1;
    }

    /**
     * Set's the subgraph index.
     *
     * @param i the subgraph index.
     */
    public void setSubGraphIndex(int i) {
        subgraphIndex = i;
    }


    /**
     * A wrapper for getting vertex Id's which supports multiple vertex owners.
     *
     * @param v Vertex which the caller intends to get its Id.
     * @return The Id.
     */
    protected int getId(VertexType v) {
        if (subgraphIndex != 0) {
            return v.getSubgraphId(subgraphIndex);
        }

        return v.getId();
    }

    public abstract int getEdgesCount();

    // -------------------         Some Helper Methods          ----------------------
    /**
     * @see BaseGraph#lightEdgeIterator()
     */
    public Iterable<EdgeType> edges() {
        return new Iterable<EdgeType>() {
            public Iterator<EdgeType> iterator() {
                return lightEdgeIterator();
            }
        };
    }

    /**
     * to make loop over edges connected to a vertex,
     * for (EdgeType e: edges(v))
     * yeah
     */
    public Iterable<EdgeType> edges(final VertexType v){
        return new Iterable<EdgeType>(){
            @Override
            public Iterator<EdgeType> iterator() {
                return lightEdgeIterator(v);
            }
        };
    }
    /**
     * Returns degree of vertex, the number of edges which their target or source is the specified vertex.
     *
     * @param vertex The given vertex
     */
    public int getDegree(VertexType vertex) {
        return isDirected() ? getInDegree(vertex) + getOutDegree(vertex) : getInDegree(vertex);
    }

    /**
     * @param vertex The given vertex
     * @return an Iterable object which can be iterated trough all neighbours of the given vertex using lightEdgeIterator(vertex)
     * @see graphtea.library.BaseGraph#lightEdgeIterator(BaseVertex)
     */
    public Iterable<VertexType> getNeighbors(final VertexType vertex) {
        return new Iterable<VertexType>() {

            public Iterator<VertexType> iterator() {
                final Iterator<EdgeType> ei = lightEdgeIterator(vertex);
                return new Iterator<VertexType>() {
                    public boolean hasNext() {
                        return ei.hasNext();
                    }

                    public VertexType next() {
                        EdgeType edg = ei.next();
                        if (edg.source == vertex)
                            return edg.target;
                        else
                            return edg.source;
                    }

                    public void remove() {
                        ei.remove();
                    }
                };
            }
        };
    }


    /**
     * Its the same as edges
     */
    public Iterable<EdgeType> getEdges() {
        return edges();
    }

    /**
     * @return an Iterable which can iterated on all vertices of graph
     */
    public Iterable<VertexType> vertices() {
        return this;
    }

    /**
     * this method is usefull in directed graphs which you want to know which vertices are sources of v(as target)
     *
     * @param vertex
     * @return an Iterable object which can be iterated trough all neighbours of the given vertex using lightEdgeIterator(vertex, false)
     * @see ListGraph#lightBackEdgeIterator(graphtea.library.BaseVertex)
     */
    public Iterable<VertexType> getBackNeighbours(final VertexType vertex) {
        return new Iterable<VertexType>() {
            public Iterator<VertexType> iterator() {
                final Iterator<EdgeType> ei = lightBackEdgeIterator(vertex);
                return new Iterator<VertexType>() {
                    public boolean hasNext() {
                        return ei.hasNext();
                    }

                    public VertexType next() {
                        EdgeType edg = ei.next();
                        if (edg.source == vertex)
                            return edg.target;
                        else
                            return edg.source;
                    }

                    public void remove() {
                        ei.remove();
                    }
                };
            }
        };
    }
    
    //same as methods, yes we cool
    /**
     * same as #removeVertex
     */
    public void deleteVertex(VertexType v){
        removeVertex(v);
    }

    /**
     * same as #removeEdge
     */
    public void deleteEdge(EdgeType e){
        removeEdge(e);
    }

    /**
     * same as #getNeighbors
     */
    public Iterable<VertexType> neighbors(final VertexType vertex) {
        return getNeighbors(vertex);
    }

    /**
     * same as #getBackNeighbors
     */
    public Iterable<VertexType> backNeighbours(final VertexType vertex) {
        return getBackNeighbours(vertex);
    }
    
    

}
