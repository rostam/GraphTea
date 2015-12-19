// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.ccp;

import graphtea.extensions.io.GraphSaveObject;
import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.select.ClearSelection;
import graphtea.plugins.main.select.Select;
import graphtea.ui.UIUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Ruzbeh Ebrahimi
 */
public class Cut extends AbstractAction {
    public static final String event = UIUtils.getUIEventKey("Cut");

    public Cut(BlackBoard bb) {
        super(bb);
        this.listen4Event(event);
    }

    public void performAction(String eventName, Object value) {
        SubGraph sd = Select.getSelection(blackboard);
        GraphModel gg = blackboard.getData(GraphAttrSet.name);
        cut(sd, gg, blackboard);
        Paste.status = "Cut";


    }

    public static void cut(SubGraph sd, GraphModel gg, BlackBoard bb) {
        GraphModel g = new GraphModel(gg.isDirected());
        moveToGraph(g, sd.edges, sd.vertices, gg);


        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        String data = GraphSaveObject.graph2String(g);


        StringSelection string = new StringSelection(data);
        cb.setContents(string, string);
        ClearSelection.clearSelected(bb);
    }

    public static void moveToGraph(GraphModel g, Collection<Edge> edges, Collection<Vertex> vertices, GraphModel previousGraph) {
        HashSet<Edge> rightEdges = new HashSet<Edge>();
        HashSet<Edge> wrongEdges = new HashSet<Edge>();
        Iterator<Edge> iter = previousGraph.edgeIterator();
        removeInCompleteEdgesFromGraph(g, iter, vertices, wrongEdges);
        removeInCompleteEdgesFromSelection(previousGraph, edges, vertices, rightEdges);
        for (Vertex v : vertices)
            previousGraph.removeVertex(v);
        g.insertVertices(vertices);

        for (Edge e : rightEdges)
            g.insertEdge(e);

//        previousGraph.view.repaint();

    }

    private static void removeInCompleteEdgesFromSelection(GraphModel g, Collection<Edge> edges, Collection<Vertex> vertices, HashSet<Edge> rightEdges) {
        for (Edge e : edges) {

            if (vertices.contains(e.source) && vertices.contains(e.target)) {
                rightEdges.add(e);
            } else {
                g.removeEdge(e);
            }
        }
    }

    private static void removeInCompleteEdgesFromGraph(GraphModel g, Iterator<Edge> iter, Collection<Vertex> vertices, HashSet<Edge> wrongEdges) {
        for (; iter.hasNext();) {
            Edge em = iter.next();

            if ((vertices.contains(em.source) && !vertices.contains(em.target)) || (vertices.contains(em.target) && !vertices.contains(em.source))) {
//                wrongEdges.add(em);
                //the power full edge iterator
                g.removeEdge(em);

            }

        }

    }


}
