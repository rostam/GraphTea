// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.ccp;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.SubGraph;
import graphlab.graph.graph.VertexModel;
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
        String data = (""
                + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<!DOCTYPE graphml SYSTEM \"graphml.dtd\">\n"
                + "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n"
                + "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "    xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\n"
                + "     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n"
                + GraphML.graph2GraphML(g)
                + "</graphml>");


        StringSelection string = new StringSelection(data);
        cb.setContents(string, string);
    }

    /**
     * copies the sd to g
     */
    public static void copyGraph(SubGraph sd, GraphModel g) {
        HashMap<VertexModel, VertexModel> map = new HashMap<VertexModel, VertexModel>();
        for (VertexModel v1 : sd.vertices) {
            VertexModel v = new VertexModel(v1);
            map.put(v1, v);
            g.insertVertex(v);

        }

        for (EdgeModel e1 : sd.edges) {
            EdgeModel e = new EdgeModel(e1, map.get(e1.source), map.get(e1.target));

            g.insertEdge(e);
        }

    }

}