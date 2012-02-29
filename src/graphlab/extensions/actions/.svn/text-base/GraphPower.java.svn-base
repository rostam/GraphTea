// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.extensions.actions;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.Application;
import graphlab.platform.StaticUtils;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.commonplugin.undo.Undoable;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.AlgorithmUtils;
import graphlab.plugins.main.extension.GraphActionExtension;

import java.util.Vector;

/**
 * @author Azin Azadi
 */


public class GraphPower implements GraphActionExtension, Parametrizable, Undoable {
    @Parameter
    public int k = 2;

    Vector<EdgeModel> toInsert = new Vector<EdgeModel>();
    Vector<VertexModel> subtree = new Vector<VertexModel>();

    public String getName() {
        return "Create Power Graph";
    }

    public String getDescription() {
        return "Create Power graph";
    }

    public void action(GraphData graphData) {
		toInsert.clear();
        GraphModel g = graphData.getGraph();

        AlgorithmUtils.clearVertexMarks(g);
        for (VertexModel v : g) {
            subtree.clear();
            aStar(v, v, k, g);
            for (VertexModel vv : subtree)
                vv.setMark(false);
        }
        g.insertEdges(toInsert);

        //undo log operation
		//todo: make a copy of toInsert
        final UndoableActionOccuredData uaod = new UndoableActionOccuredData(this);
        uaod.properties.put("edges", toInsert);
        uaod.properties.put("graph", g);
        graphData.core.addUndoData(uaod);
    }

    void aStar(VertexModel root, VertexModel v, int k, GraphModel g) {
        if (k == 0)
            return;
        v.setMark(true);
        subtree.add(v);

        for (VertexModel vv : g.getNeighbors(v)) {
            if (!vv.getMark()) {
                toInsert.add(new EdgeModel(root, vv));
                aStar(root, vv, k - 1, g);
            }
        }
    }

    public String checkParameters() {
        toInsert = new Vector<EdgeModel>();
        subtree = new Vector<VertexModel>();
        return (k < 2 ? "K must be larger than 1" : null);
    }

    public static void main(String[] args) {
        Application.main(args);

        StaticUtils.loadSingleExtension(GraphPower.class);
    }

    public void undo(UndoableActionOccuredData uaod) {
        final Vector<EdgeModel> tI = (Vector<EdgeModel>) uaod.properties.get("edges");
        final GraphModel g = (GraphModel) uaod.properties.get("graph");
        for (EdgeModel em:tI)
            g.removeEdge(em);
    }

    public void redo(UndoableActionOccuredData uaod) {
        final Vector<EdgeModel> tI = (Vector<EdgeModel>) uaod.properties.get("edges");
        final GraphModel g = (GraphModel) uaod.properties.get("graph");
        g.insertEdges(tI);
    }
}


