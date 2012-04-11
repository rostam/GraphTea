// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.ccp;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.SubGraph;
import graphlab.graph.graph.Vertex;
import graphlab.graph.io.GraphML;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.main.select.Select;
import graphlab.ui.UIUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.HashMap;

/**
 * @author roozbeh
 */
public class Copy extends AbstractAction {
    public static final String event = UIUtils.getUIEventKey("Copy");

    public Copy(BlackBoard bb) {
        super(bb);
        this.listen4Event(event);
    }

    public void performAction(String eventName, Object value) {
        SubGraph sd = Select.getSelection(blackboard);
        GraphModel gg = blackboard.getData(GraphAttrSet.name);
        copy(sd);
//        sd.moveToGraph(gg);

        Paste.status = "Copy";
    }

    /**
     * copies the given subgraph as a (GraphML) String to clipboard
     */
    public static void copy(SubGraph subGraph) {
        GraphModel g = new GraphModel();
        copyGraph(subGraph, g);
//        gg.view.repaint();
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        String data = GraphML.graph2GraphML_with_headers(g);


        StringSelection string = new StringSelection(data);
        cb.setContents(string, string);
    }

    /**
     * copies the sd to g
     */
    public static void copyGraph(SubGraph sd, GraphModel g) {
        HashMap<Vertex, Vertex> map = new HashMap<Vertex, Vertex>();
        for (Vertex v1 : sd.vertices) {
            Vertex v = new Vertex(v1);
            map.put(v1, v);
            g.insertVertex(v);

        }

        for (Edge e1 : sd.edges) {
            Edge e = new Edge(e1, map.get(e1.source), map.get(e1.target));

            g.insertEdge(e);
        }

    }

}