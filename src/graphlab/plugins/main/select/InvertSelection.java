// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.select;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.SubGraph;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

import java.util.Iterator;

/**
 * @author Rouzbeh Ebrahimi
 */
public class InvertSelection extends AbstractAction {
    public static final String event = UIUtils.getUIEventKey("InvertSelection");

    public InvertSelection(BlackBoard bb) {
        super(bb);
        listen4Event(event);
        blackboard.setData(Select.EVENT_KEY, new SubGraph());

    }

    public void performAction(String eventName, Object value) {
        GraphModel g = ((GraphModel) (blackboard.getData(GraphAttrSet.name)));
        SubGraph sd = getSelection();
        Iterator<VertexModel> vertices = g.iterator();
        Iterator<EdgeModel> edges = g.lightEdgeIterator();
        for (; vertices.hasNext();) {

            VertexModel vertex = vertices.next();
            if (sd.vertices.contains(vertex)) {
                sd.vertices.remove(vertex);
            } else
                sd.vertices.add(vertex);

        }
        for (; edges.hasNext();) {

            EdgeModel edge = edges.next();
            if (sd.edges.contains(edge)) {
                sd.edges.remove(edge);
            } else {
                sd.edges.add(edge);
            }

        }
        Select.setSelection(blackboard, sd);
    }

    public SubGraph getSelection() {
        return Select.getSelection(blackboard);
    }
}
