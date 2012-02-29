// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.select;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.plugins.commonplugin.undo.Undoable;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.extension.GraphActionExtension;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

/**
 * @author Azin Azadi
 */
public class MakeSelectionComplementGraph implements GraphActionExtension, Undoable {
    public String getName() {
        return "Complement Selection";
    }

    public String getDescription() {
        return "Make the selected subgraph complement";
    }

    public void action(GraphData gd) {
        if (gd.select.isSelectionEmpty())
            return;
        HashSet<VertexModel> V = gd.select.getSelectedVertices();
        //add undo data
        UndoableActionOccuredData uaod = new UndoableActionOccuredData(this);
        fillUndoEdges(uaod.properties, gd, "old edges");

        GraphModel G = gd.getGraph();
        doEdgeOperation(G, V);

        fillUndoEdges(uaod.properties, gd, "new edges");
        gd.core.addUndoData(uaod);
    }

    protected void doEdgeOperation(GraphModel g, HashSet<VertexModel> v) {
        boolean directed = g.isDirected();

        for (VertexModel v1 : v) {
            for (VertexModel v2 : v) {
                if (!directed)
                    if (v1.getId() < v2.getId())
                        continue;
                if (g.isEdge(v1, v2)) {
                    g.removeAllEdges(v1, v2);
                } else {
                    g.insertEdge(new EdgeModel(v1, v2));
                }
            }
        }
    }

    //--------------------- U N D O
    public void undo(UndoableActionOccuredData uaod) {
        doUndoremove(uaod.properties, "new edges");
        doUndoadd(uaod.properties, "old edges");
    }

    public void redo(UndoableActionOccuredData uaod) {
        doUndoremove(uaod.properties, "old edges");
        doUndoadd(uaod.properties, "new edges");
    }

    public static Vector<EdgeModel> fillUndoEdges(HashMap<String, Object> properties, GraphData gd, String lbl) {
        Vector<EdgeModel> edges = new Vector<EdgeModel>();
        HashSet<VertexModel> V = gd.select.getSelectedVertices();
        GraphModel g = gd.getGraph();
        for (VertexModel v : V) {
            for (VertexModel vv : V) {
                edges.add(g.getEdge(vv, v));
            }
        }
        properties.put("graph", gd.getGraph());
        properties.put(lbl, edges);
        return edges;
    }

    public static void doUndoadd(HashMap<String, Object> uaod, String lbl) {
        Vector<EdgeModel> edges = (Vector<EdgeModel>) uaod.get(lbl);
        GraphModel g = (GraphModel) uaod.get("graph");
        g.insertEdges(edges);
    }

    public static void doUndoremove(HashMap<String, Object> uaod, String lbl) {
        Vector<EdgeModel> edges = (Vector<EdgeModel>) uaod.get(lbl);
        GraphModel g = (GraphModel) uaod.get("graph");
        for (EdgeModel e : edges)
            g.removeEdge(e);
    }
}