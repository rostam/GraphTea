// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.select;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

/**
 * @author Azin Azadi
 */
public class MakeSelectionComplementGraph implements GraphActionExtension {
    public String getName() {
        return "Complement Selection";
    }

    public String getDescription() {
        return "Make the selected subgraph complement";
    }

    public void action(GraphData gd) {
        if (gd.select.isSelectionEmpty())
            return;
        HashSet<Vertex> V = gd.select.getSelectedVertices();
        //add undo data

        GraphModel G = gd.getGraph();
        doEdgeOperation(G, V);

    }

    protected void doEdgeOperation(GraphModel g, HashSet<Vertex> v) {
        boolean directed = g.isDirected();
        
        for (Vertex v1 : v) {
            for (Vertex v2 : v) {
                if (!directed)
                    if (v1.getId() < v2.getId())
                        continue;
                if (g.isEdge(v1, v2)) {
                    g.removeAllEdges(v1, v2);
                } else {
                    g.insertEdge(new Edge(v1, v2));
                }
            }
        }
    }

    public static Vector<Edge> fillUndoEdges(HashMap<String, Object> properties, GraphData gd, String lbl) {
        Vector<Edge> edges = new Vector<Edge>();
        HashSet<Vertex> V = gd.select.getSelectedVertices();
        GraphModel g = gd.getGraph();
        for (Vertex v : V) {
            for (Vertex vv : V) {
                edges.add(g.getEdge(vv, v));
            }
        }
        properties.put("graph", gd.getGraph());
        properties.put(lbl, edges);
        return edges;
    }

    @Override
    public String getCategory() {
        return "Basic Operations";
    }
}