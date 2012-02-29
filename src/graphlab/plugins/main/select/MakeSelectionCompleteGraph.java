// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.select;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;
import graphlab.plugins.main.GraphData;

import java.util.HashSet;
import java.util.Vector;

/**
 * @author Azin Azadi
 */
public class MakeSelectionCompleteGraph extends MakeSelectionComplementGraph {
    public String getName() {
        return "Make Selection Complete";
    }

    public String getDescription() {
        return "Make the selected subgraph a complete graph";
    }

    public void action(GraphData gd) {
        if (gd.select.isSelectionEmpty())
            return;
        HashSet<VertexModel> V = gd.select.getSelectedVertices();
        //add undo data
        UndoableActionOccuredData uaod = new UndoableActionOccuredData(this);
        fillUndoEdges(uaod.properties, gd, "old edges");

        GraphModel G = gd.getGraph();

        for (VertexModel v1 : V) {
            for (VertexModel v2 : V) {
//                if (v1.getId() < v2.getId()) {
//                    System.out.println(v1+","+v2);
                G.insertEdge(new EdgeModel(v1, v2));
//                }
            }
        }

        Vector<EdgeModel> ed = fillUndoEdges(uaod.properties, gd, "new edges");
        gd.select.setSelectedEdges(ed);
        gd.core.addUndoData(uaod);
    }

    protected void doEdgeOperation(GraphModel g, HashSet<VertexModel> v) {

    }
}
