// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.select;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.SubGraph;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;

import java.util.Vector;

/**
 * this class is do the update of the graph after the selection changes, it sets the selection of vertices and edges, and
 * repaints the graph if necessary
 *
 * @author Azin Azadi
 */
public class SelectUpdater extends AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public SelectUpdater(BlackBoard bb) {
        super(bb);
        listen4Event(Select.EVENT_KEY);
    }

    SubGraph last = new SubGraph();

    public void performAction(String eventName, Object value) {
        SubGraph sd = Select.getSelection(blackboard);
//        for (Vertex v:sd.vertices)
//        for (Vertex v : sd.vertices)
//            System.out.print(v.getID());
//        System.out.println("");
        if (sd == null)
            sd = new SubGraph();
        for (VertexModel v : sd.vertices)
            if (!last.vertices.contains(v)) {
                select(v);
                last.vertices.add(v);
            }
        Vector<VertexModel> rm = new Vector<VertexModel>();
        for (VertexModel v : last.vertices)
            if (!sd.vertices.contains(v)) {
                deselect(v);
                rm.add(v);
//                last.vertices.remove(v);
            }
        last.vertices.removeAll(rm);
        for (EdgeModel e : sd.edges)
            if (!last.edges.contains(e)) {
                select(e);
                last.edges.add(e);
            }
        Vector<EdgeModel> rme = new Vector<EdgeModel>();
        for (EdgeModel e : last.edges)
            if (!sd.edges.contains(e)) {
                deselect(e);
                rme.add(e);
            }
        last.edges.removeAll(rme);
        //last=sd;
        //last.vertices=(HashSet<Vertex>) sd.vertices.clone();
        //last.edges=(HashSet<Edge>) sd.edges.clone();
    }

    private void deselect(EdgeModel e) {
        e.setSelected(false);
    }

    private void select(EdgeModel e) {
        e.setSelected(true);
    }

    private void deselect(VertexModel v) {
        v.setSelected(false);
    }

    private void select(VertexModel v) {
        v.setSelected(true);
    }
}