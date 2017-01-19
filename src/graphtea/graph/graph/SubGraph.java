// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.graph;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author azin azadi
 * @email
 */
public class SubGraph {
    public GraphModel graph;

    public SubGraph(GraphModel graph) {
        this.graph = graph;
    }

    public HashSet<Vertex> vertices = new HashSet<>();
    public HashSet<Edge> edges = new HashSet<>();
    public String label = "";


    /**
     * using this constructor the selected graph will be considered as the default graph in the blackboard
     */
    public SubGraph() {
        graph = null;
    }

    public ArrayList<Vertex> getNeighbors(Vertex v) {
        ArrayList<Vertex> ret = new ArrayList<>();
        for (Vertex nv : graph.getNeighbors(v)) {
            if (vertices.contains(nv))
                ret.add(nv);
        }
        return ret;
    }


    /**
     * removes sg vertices and edges from this subgraph
     */
    public void remove(SubGraph sg) {
        for (Vertex v : sg.vertices) {
            vertices.remove(v);
        }
        for (Edge e : sg.edges) {
            edges.remove(e);
        }
    }

    /**
     * adds sg vertices and edges to this subgraph
     */
    public void add(SubGraph sg) {
        if ((sg.graph == null && graph != null) || (sg.graph != null && !sg.graph.equals(graph)))
            throw new RuntimeException("The given SubGraph has different parent graph");
        vertices.addAll(sg.vertices);
        edges.addAll(sg.edges);
    }

    @Override
    public String toString() {
        String txt = "";
        if (label != null && !label.equals("")) {
            txt += label + ": \n";
        }
        if (vertices != null && vertices.size() > 0) {
            txt = txt + "V: {";
            for (Vertex v : vertices) {
                txt = txt + v.getLabel() + ", ";
            }
            txt = txt.substring(0, txt.length() - 2) + "}";
        }
        if (edges != null && edges.size() > 0) {
            txt += "\nE: {";
            for (Edge e : edges) {
                txt = txt + e + ", ";
            }
            txt = txt.substring(0, txt.length() - 2) + "}";
        }
        return txt;
    }

    /**
     * @return a copy of this SubGraph
     */
    public SubGraph getACopy() {
        SubGraph ret = new SubGraph(graph);
        ret.vertices.addAll(vertices);
        ret.edges.addAll(edges);
        return ret;
    }

    /**
     * @return the edge between the given vertices in the subgraph,
     *         or null if it does not exist
     */
    public static Edge getEdge(SubGraph sg, Vertex v, Vertex u) {
        Edge e = sg.graph.getEdge(u, v);
        if (e != null) {
            if (sg.edges.contains(e))
                return e;
        }
        return null;
    }
    
}
