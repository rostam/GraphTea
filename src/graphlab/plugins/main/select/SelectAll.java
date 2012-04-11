// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.select;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.SubGraph;
import graphlab.graph.graph.Vertex;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

import java.util.Iterator;

/**
 * @author Ruzbeh Ebrahimi
 */
public class SelectAll extends AbstractAction {
    public static final String event = UIUtils.getUIEventKey("SelectAll");

    public SelectAll(BlackBoard bb) {
        super(bb);
        listen4Event(event);
        blackboard.setData(Select.EVENT_KEY, new SubGraph());

    }

    public void performAction(String eventName, Object value) {
        GraphModel g = ((GraphModel) (blackboard.getData(GraphAttrSet.name)));
        SubGraph sd = getSelection();
        Iterator<Vertex> vertices = g.iterator();
        Iterator<Edge> edges = g.lightEdgeIterator();
        for (; vertices.hasNext();) {

            Vertex vertex = vertices.next();
//            if (vertex.view.isSelected) {
//                //vertex.view.isSelected = false;
//                sd.vertices.remove(vertex);
//            } else
            sd.vertices.add(vertex);

        }
        for (; edges.hasNext();) {

            Edge edge = edges.next();
//            if (edge.view.isSelected){
//            sd.edges.remove(edge);
//            //edge.view.isSelected=false;
//        }
//        else
            sd.edges.add(edge);

        }
        Select.setSelection(blackboard, sd);
    }

    public SubGraph getSelection() {
        return Select.getSelection(blackboard);
    }
}
