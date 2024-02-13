// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.select;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;

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
    public void track(){}

    public void performAction(String eventName, Object value) {
        SubGraph sd = Select.getSelection(blackboard);
        if (sd == null)
            sd = new SubGraph();
        for (Vertex v : sd.vertices)
            if (!last.vertices.contains(v)) {
                select(v);
                last.vertices.add(v);
            }
        Vector<Vertex> rm = new Vector<>();
        for (Vertex v : last.vertices)
            if (!sd.vertices.contains(v)) {
                deselect(v);
                rm.add(v);
            }
        last.vertices.removeAll(rm);
        for (Edge e : sd.edges)
            if (!last.edges.contains(e)) {
                select(e);
                last.edges.add(e);
            }
        Vector<Edge> rme = new Vector<>();
        for (Edge e : last.edges)
            if (!sd.edges.contains(e)) {
                deselect(e);
                rme.add(e);
            }
        last.edges.removeAll(rme);
        //last=sd;
        //last.vertices=(HashSet<Vertex>) sd.vertices.clone();
        //last.edges=(HashSet<Edge>) sd.edges.clone();
    }

    private void deselect(Edge e) {
        e.setSelected(false);
    }

    private void select(Edge e) {
        e.setSelected(true);
    }

    private void deselect(Vertex v) {
        v.setSelected(false);
    }

    private void select(Vertex v) {
        v.setSelected(true);
    }
}