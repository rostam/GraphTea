// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.select;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

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
