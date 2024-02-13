// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph;

import graphtea.graph.graph.AbstractGraphRenderer;
import graphtea.graph.graph.FastRenderer;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.BlackBoard;

import javax.swing.*;
import java.awt.*;

/**
 * A Swing Graph component which can be used easily to show graphs,
 * note that the resulting component will not have any editing capabilities,
 * The Events (GraphEvent, VertexEvent, EdgeEvent) will be sent to blackboard and
 * can be used from there.
 * <p/>
 * you should attach the relating actions (e.g. AddVertex, AddEdge, ...) to blackboard
 * to have the editing.
 *
 * @author azin azadi
 * @see graphtea.graph.event.GraphEvent
 */
public class JGraph extends JScrollPane {
    private static final long serialVersionUID = -500431952321243220L;
    private final AbstractGraphRenderer view;

    public JGraph(GraphModel g, AbstractGraphRenderer view) {
        super(view);
        this.g = g;
        this.view = view;
        view.setGraph(g);
    }

    public void scrollRectToVisible(Rectangle aRect) {
        super.scrollRectToVisible(aRect);
    }

    public GraphModel getGraph() {
        return g;
    }

    public AbstractGraphRenderer getGraphView() {
        return view;
    }

    GraphModel g;

    static int i = 0;

    public static JGraph getNewComponent(BlackBoard b) {
        GraphModel model = new GraphModel();
        AbstractGraphRenderer view = new FastRenderer(model, b);
        model.setLabel("G" + (i++));
        return new JGraph(model, view);
    }

    public AbstractGraphRenderer getView() {
        return view;
    }

    /*
     * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6333318 jscrollpane bug
     */
}
