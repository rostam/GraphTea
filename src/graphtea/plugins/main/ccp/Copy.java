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
import graphtea.plugins.main.select.Select;
import graphtea.ui.UIUtils;

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
        String data = GraphSaveObject.graph2String(g);


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