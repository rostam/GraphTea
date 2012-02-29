// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core.actions.vertex;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.commonplugin.undo.Undoable;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;

import java.util.Iterator;

/**
 * Author: Ruzbeh Ebrahimi
 */
public class DeleteVertex extends AbstractAction implements Undoable {
    public DeleteVertex(BlackBoard bb) {
        super(bb);
        this.listen4Event(VertexSelectData.EVENT_KEY);
    }

    public void performAction(String eventName, Object value) {
        VertexSelectData vsd = blackboard.getData(VertexSelectData.EVENT_KEY);
        GraphModel g = blackboard.getData(GraphAttrSet.name);
        VertexModel v = vsd.v;
        UndoableActionOccuredData uaod = new UndoableActionOccuredData(this);
        uaod.properties.put("DeletedVertex", v);
        uaod.properties.put("RelatedEdges", g.edgeIterator(v));
        uaod.properties.put("Graph", g);
        blackboard.setData(UndoableActionOccuredData.EVENT_KEY, uaod);
        doJob(g, v);
    }

    public static void doJob(GraphModel g, VertexModel v) {

        g.removeVertex(v);
    }

    public void undo(UndoableActionOccuredData uaod) {
        VertexModel v = (VertexModel) uaod.properties.get("DeletedVertex");
        GraphModel g = (GraphModel) uaod.properties.get("Graph");
        g.insertVertex(v);
        Iterator<EdgeModel> iter = (Iterator<EdgeModel>) uaod.properties.get("RelatedEdges");
        for (Iterator iter2 = iter; iter2.hasNext();) {
            g.insertEdge(((EdgeModel) iter2.next()));
        }
//        v.view.repaint();

    }

    public void redo(UndoableActionOccuredData uaod) {
        VertexModel v = (VertexModel) uaod.properties.get("DeletedVertex");
        GraphModel g = (GraphModel) uaod.properties.get("Graph");
        DeleteVertex.doJob(g, v);

    }
}