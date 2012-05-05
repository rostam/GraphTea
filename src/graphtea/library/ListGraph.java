// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library;


import Jama.Matrix;
import graphtea.library.exceptions.InvalidEdgeException;
import graphtea.library.exceptions.InvalidGraphException;
import graphtea.library.exceptions.InvalidVertexException;
import graphtea.library.genericcloners.EdgeVertexCopier;
import graphtea.library.genericcloners.GraphConverter;
import graphtea.library.util.Pair;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Adjacency List Graph.
 *
 * @author Hooman Mohajeri Moghaddam, added weighted version of weightOfEdge method
 * @author Omid Aladini
 * @param <VertexType> Type of the vertices the graph can work with.
 * @param <EdgeType> Type of the edges the graph can work with.
 * <p/>
 * toCheck : edgeIterator class, removeEdge, removeAllEdges, copy, setDirected
 */
public class ListGraph<VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
        extends BaseGraph<VertexType, EdgeType> {
    //The adjacency list data structure. The internal linked list of Pair<EdgeType,VertexType>
    //represent all edge/vertex pairs which the vertex is target of it's paired edge.
    private ArrayList<LinkedList<Pair<EdgeType, VertexType>>> list;

    //The inverse adjacency list data structure. The internal linked list of Pair<EdgeType,VertexType>
    //represent all edge/vertex pairs which the vertex is source of it's paired edge.
    private ArrayList<LinkedList<Pair<EdgeType, VertexType>>> inverseList;

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
    //Specified whether the graph is changed since the last light iteration.
    private boolean guard = false;

    //Number of edges AKA graph order	
    private int edgeCount = 0;

    /**
     * Constructs a graph object that stores graph data using adjacency list data structure.
     *
     * @param directed                 Indicated whether the graph is directed.
     * @param expectedNumberOfVertices Approximate number of vertices that will be
     *                                 added to the graph. This paramether is optional and is available for performance
     *                                 reasons.
     */
    public ListGraph(boolean directed, int expectedNumberOfVertices) {
        this.directed = directed;
        list = new ArrayList<LinkedList<Pair<EdgeType, VertexType>>>(expectedNumberOfVertices);
        inverseList = new ArrayList<LinkedList<Pair<EdgeType, VertexType>>>(expectedNumberOfVertices);
        vertices = new ArrayList<VertexType>(expectedNumberOfVertices);
        outDegree = new ArrayList<Integer>(expectedNumberOfVertices);
        inDegree = new ArrayList<Integer>(expectedNumberOfVertices);
    }

    /**
     * Constructs an undirected graph object that stores graph data using
     * adjacency list data structure.
     */
    public ListGraph() {
        this.directed = false;
        list = new ArrayList<LinkedList<Pair<EdgeType, VertexType>>>();
        inverseList = new ArrayList<LinkedList<Pair<EdgeType, VertexType>>>();
        vertices = new ArrayList<VertexType>();
        outDegree = new ArrayList<Integer>();
        inDegree = new ArrayList<Integer>();
    }


    /**
     * Constructs a graph object that stores graph data using adjacency list data structure by importing
     * graph data from a pre-existing graph. A GraphConvertor object is passed as a parameter which is
     * reponsible for duplication/type-convertion of graph elements.
     *
     * @param <ImportVertexType> The type of vertex object which the input graph contain.
     * @param <ImportEdgeType>   The type of edge object which the input graph contain.
     * @param graph
     * @param converter          A GraphConverter object which is responsible for duplicating/converting graph
     *                           elements.
     * @throws InvalidGraphException Throws when the input graph is an invalid graph object.
     */
    public <ImportVertexType extends BaseVertex,
            ImportEdgeType extends BaseEdge<ImportVertexType>,
            ImportGraphType extends BaseGraph<ImportVertexType, ImportEdgeType>
            >
    ListGraph(BaseGraph<ImportVertexType, ImportEdgeType> graph,
              GraphConverter<ImportVertexType,
                      VertexType,
                      ImportEdgeType,
                      EdgeType,
                      ImportGraphType,
                      ListGraph<VertexType, EdgeType>> converter)
            throws InvalidGraphException {
        this.directed = graph.isDirected();

        for (ImportVertexType v : graph)
            insertVertex(converter.convert(v));

        Iterator<ImportEdgeType> e = graph.edgeIterator();
        ImportEdgeType edge;
        while (e.hasNext()) {
            try {
                edge = e.next();
                insertEdge(converter.convert(edge, vertices.get(graph.getId(edge.source)),
                        vertices.get(graph.getId(edge.target))));
            } catch (InvalidVertexException ex) {
                throw new InvalidGraphException(ex);
            }
        }
    }

    /**
     * A wrapper for setting vertex Id's which supports multiple vertex owners.
     *
     * @param v Vertex which the caller intends to set its Id.
     * @return The Id.
     */
    protected void setId(VertexType v, int id) {
        if (subgraphIndex != 0) {
            v.setSubgraphId(subgraphIndex, id);
            return;
        }

        v.setId(id);
    }

    /* (non-Javadoc)
    * @see graphtea.library.BaseGraph#getVerticesCount()
    */
    @Override
    public int getVerticesCount() {
        return vertices.size();
    }

    /**
     * Returns true if the <code>id</code> parameter does not indicate a vertex in this graph.
     * Note that vertex id's are internal marks and may change by modifying graph object.
     *
     * @param id Internal id of the vertex.
     * @return True if the vertex is out of range.
     */
    private boolean vertexIdOutOfRange(int id) {
        if (id < 0 || id >= vertices.size())
            return true;

        return false;
    }


    /**
     * Number of times edge Iteration is called. This will set as a temporary flag into
     * edges in order to reduce running time of edge iteration back to O(n^2).
     */
    private int edgeIterationIndex = 0;

    /**
     * This class iterates all, edges coming from or going to a specified vertex.
     * The order of edges the iterator iterate is undefined because of future code changes.
     *
     * @author Omid Aladini
     */
    private class EdgeIterator implements Iterator<EdgeType> {
        private Iterator<EdgeType> edgesIterator;
        private EdgeType lastEdge = null;

        /**
         * Constructs an Edge Iterator object which iterates through all the edges in the graph.
         * Note that if the graph object is changed during iteration, the iteration may not
         * actually represent current state of the graph. For example, if you deleted an edge
         * after construction of this object, the edge would be included in the iteration.
         */
        public EdgeIterator() {
            ++edgeIterationIndex;
            ArrayList<EdgeType> edges = new ArrayList<EdgeType>();

            Iterator<VertexType> it = iterator();

            while (it.hasNext()) {
                Iterator<EdgeType> it2 = new EdgeIterator(it.next(), true);
                while (it2.hasNext()) {
                    EdgeType edge = it2.next();
                    if (edge.edgeIterationIndex == edgeIterationIndex)
                        continue;
                    edge.edgeIterationIndex = edgeIterationIndex;
                    edges.add(edge);
                }
            }

            edgesIterator = edges.iterator();

        }

        /**
         * Constructs an Edge Iterator object which iterates through all the edges going to
         * or coming from the specified vertex <code>v</code>.
         * Note that if the graph object is changed during iteration, the iteration may not
         * actually represent current state of the graph. For example, if you deleted an edge
         * after construction of this object, the edge would be included in the iteration.
         *
         * @param v Source or target of desired edges.
         */
        public EdgeIterator(VertexType v)
                throws InvalidVertexException {
            ++edgeIterationIndex;
            checkVertex(v);

            ArrayList<EdgeType> edges = new ArrayList<EdgeType>();

            Iterator<EdgeType> it = new EdgeIterator(v, true);
            while (it.hasNext())
                edges.add(it.next());

            if (directed) {
                it = new EdgeIterator(v, false);
                while (it.hasNext()) {
                    EdgeType edge = it.next();
                    if (edge.edgeIterationIndex == edgeIterationIndex)
                        continue;
                    edge.edgeIterationIndex = edgeIterationIndex;
                    edges.add(edge);

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

            ArrayList<EdgeType> edges = new ArrayList<EdgeType>();

            if (source) {
                for (Pair<EdgeType, VertexType> pev : list.get(getId(v)))
                    edges.add(pev.first);
            } else {
                for (Pair<EdgeType, VertexType> pev : inverseList.get(getId(v)))
                    edges.add(pev.first);
            }
            edgesIterator = edges.iterator();
        }

        /* (non-Javadoc)
           * @see java.util.Iterator#hasNext()
           */
        public boolean hasNext() {
            return edgesIterator.hasNext();
        }

        /* (non-Javadoc)
           * @see java.util.Iterator#next()
           */
        public EdgeType next() {
            lastEdge = edgesIterator.next();
            return lastEdge;
        }

        /* (non-Javadoc)
           * @see java.util.Iterator#remove()
           */
        public void remove() {
            removeEdge(lastEdge);
        }
    }


    /**
     * This is a light(weight) edge iterator which means it offers an O(1) constructor for the iterator.
     * This class iterates all, edges coming from or going to a specified vertex.
     * The order of edges the iterator iterate is undefined because of future code changes.
     *
     * @author Omid Aladini
     */
    private class LightEdgeIterator implements Iterator<EdgeType> {
        //private EdgeType lastEdge = null;
        private EdgeType newEdge = null;

        int iterationType;

        //For iterationType 0 and 1
        private Iterator<LinkedList<Pair<EdgeType, VertexType>>> lpevIterator;
        private Iterator<Pair<EdgeType, VertexType>> pevIterator;

        //For iterationType 1
        private Iterator<Pair<EdgeType, VertexType>> rowIterator1, rowIterator2;
        //private LinkedList<Pair<EdgeType,VertexType>> row;

        //For iterationType 2
        boolean source;

        /**
         * Constructs a light(weight) Edge Iterator object which iterates through all the edges in the graph.
         * The light(weight) edge iterator presents an iterator with O(1) constructor. Note that you should
         * not change the content of the graph during your iteration. You can still change properties of
         * each edge or vertex.
         */
        public LightEdgeIterator() {
            iterationType = 0;
            lpevIterator = list.iterator();
            pevIterator = null;

            if (!directed)
                ++edgeIterationIndex;

            guard = false;
        }

        private boolean hasNextSimpleIteration() {
            do {
                if (pevIterator == null || !pevIterator.hasNext()) {
                    if (!lpevIterator.hasNext())
                        return false;

                    LinkedList<Pair<EdgeType, VertexType>> lpev = lpevIterator.next();
                    pevIterator = lpev.iterator();
                }

                while (pevIterator.hasNext()) {
                    Pair<EdgeType, VertexType> pev = pevIterator.next();
                    if (directed) {
                        newEdge = pev.first;
                        return true;
                    } else if (pev.first.edgeIterationIndex != edgeIterationIndex) {
                        newEdge = pev.first;
                        pev.first.edgeIterationIndex = edgeIterationIndex;
                        return true;
                    }
                }
            } while (lpevIterator.hasNext());

            return false;
        }

        /**
         * Constructs a light(weight) Edge Iterator object which iterates through all the edges going to
         * or coming from the specified vertex <code>v</code>.
         * The light(weight) edge iterator presents an iterator with O(1) constructor. Note that you should
         * not change the content of the graph during your iteration. You can still change properties of
         * each edge or vertex.
         *
         * @param v Source or target of desired edges.
         */
        public LightEdgeIterator(VertexType v)
                throws InvalidVertexException {
            checkVertex(v);

            iterationType = 1;

            rowIterator1 = list.get(getId(v)).iterator();
            rowIterator2 = inverseList.get(getId(v)).iterator();

            guard = false;
        }

        private boolean hasNextVertexIteration() {
            Pair<EdgeType, VertexType> pev;
            if (rowIterator1.hasNext()) {
                pev = rowIterator1.next();
                newEdge = pev.first;
                return true;
            } else if (rowIterator2.hasNext() && !directed) {
                pev = rowIterator2.next();
                newEdge = pev.first;
                return true;
            } else
                return false;
        }


        /**
         * Constructs a light(weight) Edge Iterator object which iterates through all the edges going to
         * or coming from (depending on the second parameter) the specified vertex <code>v</code>.
         * If the second parameter it true, then the first parameter is considered to be source of
         * all desired edges, and if it's false the first parameter is considered to be target of desired edges.
         * The light(weight) edge iterator presents an iterator with O(1) constructor. Note that you should
         * not change the content of the graph during your iteration. You can still change properties of
         * each edge or vertex.
         *
         * @param v      If the second parameter is true indicated the vertex which is source of desired edges, otherwise
         *               it is considered to be target of desired edges.
         * @param source True means the first parameter should be considered source of desired edges.
         */
        public LightEdgeIterator(VertexType v, boolean source)
                throws InvalidVertexException {
            checkVertex(v);

            iterationType = 2;
            pevIterator = null;
            this.source = source;

            if (!directed)
                ++edgeIterationIndex;

            if (source) {
                rowIterator1 = list.get(getId(v)).iterator();
            } else {
                rowIterator1 = inverseList.get(getId(v)).iterator();
            }

            guard = false;
        }

        private boolean hasNextVertexDirectionIteration() {
            Pair<EdgeType, VertexType> pev;
            if (rowIterator1.hasNext()) {
                pev = rowIterator1.next();
                newEdge = pev.first;
                return true;
            } else
                return false;
        }

        /* (non-Javadoc)
           * @see java.util.Iterator#hasNext()
           */
        public boolean hasNext() {
            if (guard)
                throw new RuntimeException("Graph has changed since the initialization of this light edge iterator");
            if (isHasNextCalled)
                return hasNextResult;
            isHasNextCalled = true;
            if (iterationType == 0)
                hasNextResult = hasNextSimpleIteration();
            else if (iterationType == 1)
                hasNextResult = hasNextVertexIteration();
            else //if(iterationType == 2)
                hasNextResult = hasNextVertexDirectionIteration();
            return hasNextResult;
        }

        boolean isHasNextCalled = false;
        boolean hasNextResult = false;

        /* (non-Javadoc)
        * @see java.util.Iterator#next()
        */
        public EdgeType next() {
            if (!isHasNextCalled)
                hasNext();
            isHasNextCalled = false;
            return newEdge;
        }

        /* (non-Javadoc)
           * @see java.util.Iterator#remove()
           */
        public void remove() {
            throw new RuntimeException("Remove is not supported for the light edge iterator.");
        }
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#edgeIterator()
      */
    @Override
    public Iterator<EdgeType> edgeIterator() {
        return new EdgeIterator();
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
      * @see graphtea.library.BaseGraph#lightEdgeIterator()
      */
    @Override
    public Iterator<EdgeType> lightEdgeIterator() {
        return new LightEdgeIterator();
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#lightEdgeIterator(null)
      */
    @Override
    public Iterator<EdgeType> lightEdgeIterator(VertexType v)
            throws InvalidVertexException {
        return new LightEdgeIterator(v);
    }

    /**
     * in a directed graph this method returns edges whose targets are v
     *
     * @param v
     * @return
     */
    public Iterator<EdgeType> lightBackEdgeIterator(VertexType v) {
        return new LightEdgeIterator(v, false);
    }

    /**
     * Returns Vertex internally associated with id <code>id</code>.
     *
     * @param id Internal id of the desired vertex.
     * @return Vertex internally associated with id <code>id</code>.
     * @throws InvalidVertexException if the <code>id</code> is invalid.
     */
    public VertexType getVertex(int id)
            throws InvalidVertexException {
        if (vertexIdOutOfRange(id))
            throw new InvalidVertexException();

        return vertices.get(id);
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#insertEdge(null)
      */
    @Override
    public void insertEdge(EdgeType newEdge)
            throws InvalidVertexException {
        int target = getId(newEdge.target);
        int source = getId(newEdge.source);

        checkVertex(newEdge.target);
        checkVertex(newEdge.source);

        guard = true;

        list.get(source).add(new Pair<EdgeType, VertexType>(newEdge, newEdge.target));
        if (!directed)
            list.get(target).add(new Pair<EdgeType, VertexType>(newEdge, newEdge.source));

        inverseList.get(target).add(new Pair<EdgeType, VertexType>(newEdge, newEdge.source));
        if (!directed)
            inverseList.get(source).add(new Pair<EdgeType, VertexType>(newEdge, newEdge.target));


        outDegree.set(source, outDegree.get(source) + 1);
        inDegree.set(target, inDegree.get(target) + 1);

        if (!directed) {
            inDegree.set(source, inDegree.get(source) + 1);
            outDegree.set(target, outDegree.get(target) + 1);
        }
                
        ++edgeCount;
    }

    /* (non-Javadoc)
     * @see graphtea.library.BaseGraph#insertVertex(null)
     */
    @Override
    public void insertVertex(VertexType newVertex) {
        if (!vertices.contains(newVertex)) {
            guard = true;
            vertices.add(newVertex);
            list.add(new LinkedList<Pair<EdgeType, VertexType>>());
            inverseList.add(new LinkedList<Pair<EdgeType, VertexType>>());
            setId(newVertex, vertices.size() - 1);
            inDegree.add(0);
            outDegree.add(0);
        } else {
            throw new IllegalArgumentException("Adding duplicate vertex.");
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
    * @see graphtea.library.BaseGraph#getAdjacencyMatrix()
    */
    @Override
    public Matrix getAdjacencyMatrix() {
        Matrix matrix = new Matrix(getVerticesCount(), getVerticesCount());

        Iterator<EdgeType> it = edgeIterator();
        int targetId;
        int sourceId;
        double newValue;

        while (it.hasNext()) {
            EdgeType edge = it.next();

            targetId = getId(edge.target);
            sourceId = getId(edge.source);
            newValue = matrix.get(sourceId, targetId) + 1;

            matrix.set(sourceId, targetId, newValue);
            if (!directed)
                matrix.set(targetId, sourceId, newValue);
        }

        return matrix;
    }
    public Matrix getWeightedAdjacencyMatrix() {
        Matrix matrix = new Matrix(getVerticesCount(), getVerticesCount());

        Iterator<EdgeType> it = edgeIterator();
        int targetId;
        int sourceId;
        double newValue;

        while (it.hasNext()) {
            EdgeType edge = it.next();

            targetId = getId(edge.target);
            sourceId = getId(edge.source);
            newValue = matrix.get(sourceId, targetId) + edge.getWeight();

            matrix.set(sourceId, targetId, newValue);
            if (!directed)
                matrix.set(targetId, sourceId, newValue);
        }

        return matrix;
    }

    /* (non-Javadoc)
     * @see graphtea.library.BaseGraph#isDirected()
     */
    @Override
    public boolean isDirected() {
        return directed;
    }

    /* (non-Javadoc)
    * @see graphtea.library.BaseGraph#dump()
    */
    @Override
    public void dump() {

        System.out.print('\n');
        for (int i = 0; i < getVerticesCount(); i++) {
            int[] row = new int[getVerticesCount()];

            for (Pair<EdgeType, VertexType> pev : list.get(i))
                row[getId(pev.second)]++;

            for (int j = 0; j < getVerticesCount(); ++j)
                System.out.print(" " + row[j]);
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#removeAllEdges(null, null)
      */
    @Override
    public void removeAllEdges(VertexType source, VertexType target)
            throws InvalidVertexException {
        Iterator<EdgeType> it = edgeIterator(source);
        while (it.hasNext()) {
            if (it.next().target == target)
                it.remove();
        }
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#removeEdge(null)
      */
    @Override
    public void removeEdge(EdgeType edge)
            throws InvalidEdgeException {
        checkVertex(edge.source);
        checkVertex(edge.target);

        guard = true;

        int sourceId = getId(edge.source);
        int targetId = getId(edge.target);
        ArrayList<LinkedList<Pair<EdgeType, VertexType>>> list = this.list;

        for (int i = 0; i < 2; ++i) {
            if (i == 1) {
                list = inverseList;
                int temp = sourceId;
                sourceId = targetId;
                targetId = temp;
            }

            Iterator<Pair<EdgeType, VertexType>> itpev = list.get(sourceId).iterator();

            boolean done = false;
            while (itpev.hasNext()) {
                Pair<EdgeType, VertexType> pev = itpev.next();
                if (pev.first == edge) {
                    itpev.remove();
                    done = true;
                    break;
                }
            }

//			if(!done)
//				throw new InvalidEdgeException();

            if (directed)
                continue;

            itpev = list.get(targetId).iterator();

            done = false;
            while (itpev.hasNext()) {
                Pair<EdgeType, VertexType> pev = itpev.next();
                if (pev.first == edge) {
                    itpev.remove();
                    done = true;
                    break;
                }
            }

            if (!done)
                throw new InvalidEdgeException();

        } //end 2-time for
        
 
        

        --edgeCount;
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#getEdges(null, null)
      */
    @Override
    public AbstractList<EdgeType> getEdges(VertexType source, VertexType target)
            throws InvalidVertexException {
        checkVertex(source);
        checkVertex(target);

        ArrayList<EdgeType> arr = new ArrayList<EdgeType>();
        for (Pair<EdgeType, VertexType> pev : list.get(getId(source))) {
            if (pev.second == target)
                arr.add(pev.first);
        }

        return arr;
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#removeVertex(null)
      */
    @Override
    public void removeVertex(VertexType v)
            throws InvalidVertexException {
        guard = true;
        Iterator<EdgeType> it = edgeIterator(v);
        while (it.hasNext()) {
            it.next();
            it.remove();
        }

        int vId = getId(v);
        list.remove(vId);
        inverseList.remove(vId);
        vertices.remove(vId);
        inDegree.remove(vId);
        outDegree.remove(vId);
        setVertexIds();
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#getInDegree(null)
      */
    @Override
    public int getInDegree(VertexType v) throws InvalidVertexException {
        checkVertex(v);
//        return inDegree.get(getId(v));
        return inverseList.get(getId(v)).size();

    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#getOutDegree(null)
      */
    @Override
    public int getOutDegree(VertexType v) throws InvalidVertexException {
        checkVertex(v);
//        ListIterator<Pair<EdgeType, VertexType>> iter = list.listIterator();
        return list.get(getId(v)).size();
//        return outDegree.get(getId(v)); //todo: outDegree seems not working properly
    }

    /**
     * Resets all vertices' internal Ids.
     */
    private void setVertexIds() {
        try {
            for (int i = 0; i < getVerticesCount(); i++)
                setId(getVertex(i), i);
        } catch (InvalidVertexException e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#copy(graphtea.library.GraphConverter)
      */
    @Override
    public BaseGraph<VertexType, EdgeType>
           copy(EdgeVertexCopier<VertexType, EdgeType> gc)
            throws InvalidGraphException {
        ListGraph<VertexType, EdgeType> oGraph = new ListGraph<VertexType, EdgeType>(directed, getVerticesCount());

        ArrayList<VertexType> alvt = new ArrayList<VertexType>(getVerticesCount());
        VertexType tempVertex;
        for (VertexType v : this) {
            tempVertex = gc.convert(v);
            oGraph.insertVertex(tempVertex);
            alvt.add(tempVertex);
        }

        Iterator<EdgeType> e = edgeIterator();
        EdgeType edge;
        //System.out.println("Num vertix " + getVerticesCount());
        while (e.hasNext()) {
            try {
                edge = e.next();
                //System.out.println("An edge from " + getId(edge.source) + " to " + getId(edge.target));
                oGraph.insertEdge(gc.convert(edge, alvt.get(getId(edge.source)),
                        alvt.get(getId(edge.target))));
            } catch (InvalidVertexException ex) {
                ex.printStackTrace();
                throw new InvalidGraphException();
            }
        }
        return oGraph;

    }

    public VertexType getAVertex() {
        if (getVerticesCount() == 0)
            return null;
        else
            return vertices.get(0);
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
    public void checkVertex(VertexType v)
            throws InvalidVertexException {
        if (vertexIdOutOfRange(getId(v))) {
            String message = "Out of range";

            if (vertices.contains(v))
                message += ":It seems that this vertex exists in two different graph objects. This is illegal.";

            throw new InvalidVertexException(message);
        }

        if (v != vertices.get(getId(v)))
            throw new InvalidVertexException("Invalid");
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#isEdges(null, null)
      */
    @Override
    public boolean isEdge(VertexType source, VertexType target)
            throws InvalidVertexException {
        AbstractList<EdgeType> edges = getEdges(source, target);
        return (edges != null) && (edges.size() != 0);
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
		   res=new int[edges.size()];
		   int i=0;
		   for(EdgeType et : edges )
		   {
			   res[i] = et.getWeight();
			   i++;
		   }
	   }
	   return res;
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#createEmptyGraph()
      */
    @Override
    public ListGraph<VertexType, EdgeType> createEmptyGraph() {
        return new ListGraph<VertexType, EdgeType>(directed, 0);
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#setDirected(boolean)
      */
    @Override
    public void setDirected(boolean directed) {
        if (this.directed == directed)
            return;

        guard = true;

        if (!directed) {
            Iterator<EdgeType> iet = edgeIterator();
            while (iet.hasNext()) {
                EdgeType edge = iet.next();
                list.get(getId(edge.target)).add(new Pair<EdgeType, VertexType>(edge, edge.source));
                inverseList.get(getId(edge.source)).add(new Pair<EdgeType, VertexType>(edge, edge.target));
            }

            for (int i = 0; i < getVerticesCount(); ++i) {
                int sum = inDegree.get(i) + outDegree.get(i);
                inDegree.set(i, sum);
                outDegree.set(i, sum);
            }

            this.directed = false;
        } else {
            Iterator<EdgeType> iet = edgeIterator();
            while (iet.hasNext()) {
                EdgeType edge = iet.next();
                Iterator<Pair<EdgeType, VertexType>> lit = list.get(getId(edge.target)).iterator();
                while (lit.hasNext()) {
                    if (lit.next().first == edge)
                        lit.remove();
                }

                lit = inverseList.get(getId(edge.source)).iterator();
                while (lit.hasNext()) {
                    if (lit.next().first == edge) {
                        lit.remove();
                        inDegree.set(getId(edge.source), inDegree.get(getId(edge.source)) - 1);
                        outDegree.set(getId(edge.target), outDegree.get(getId(edge.target)) - 1);
                    }
                }
            }

            this.directed = true;
        }
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#getVertexArray()
      */
    @Override
    public BaseVertex[] getVertexArray() {
        BaseVertex[] arr = new BaseVertex[getVerticesCount()];

        for (VertexType v : this)
            arr[getId(v)] = v;

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
        for (LinkedList<Pair<EdgeType, VertexType>> ll : list) {
            arr[i] = new int[ll.size()];
            j = 0;
            for (Pair<EdgeType, VertexType> p : ll) {
                arr[i][j++] = getId(p.second);
            }
            ++i;
        }
        return arr;
    }

    /* (non-Javadoc)
      * @see graphtea.library.BaseGraph#clear()
      */
    @Override
    public void clear() {
        list = new ArrayList<LinkedList<Pair<EdgeType, VertexType>>>();
        inverseList = new ArrayList<LinkedList<Pair<EdgeType, VertexType>>>();
        vertices = new ArrayList<VertexType>();
        outDegree = new ArrayList<Integer>();
        inDegree = new ArrayList<Integer>();
        edgeIterationIndex = 0;
        guard = false;
    }

    @Override
    public int getEdgesCount() {
        return edgeCount;
    }

}
