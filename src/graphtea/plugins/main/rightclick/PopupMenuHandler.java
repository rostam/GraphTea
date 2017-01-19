// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.rightclick;

import graphtea.graph.event.EdgeEvent;
import graphtea.graph.event.GraphEvent;
import graphtea.graph.event.VertexEvent;
import graphtea.graph.graph.AbstractGraphRenderer;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * @author azin azadi
 */
public class PopupMenuHandler extends AbstractAction {

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public PopupMenuHandler(BlackBoard bb) {
        super(bb);
        listen4Event(VertexEvent.EVENT_KEY);
        listen4Event(EdgeEvent.EVENT_KEY);
        listen4Event(GraphEvent.EVENT_KEY);

//        GraphPropertyEditor gpv=new GraphPropertyEditor(blackboard);
//        GraphPropertyEditor gpe=new GraphPropertyEditor(blackboard);
//        gpv.getPropertyEditor().getTable().setTableHeader(new JTableHeader());
//        gpe.getPropertyEditor().getTable().setTableHeader(new JTableHeader());
//        vertexMnu.add(gpv.getPropertyEditor().getTable());
//        edgeMnu.add(gpe.getPropertyEditor().getTable());
    }
    public void track(){}

    public void performAction(String eventName, Object value) {
//        if (eventName ==VertexEvent.name)) {
//            VertexEvent vcd = blackboard.get(VertexEvent.name);
//            if (vcd.eventType == VertexEvent.CLICKED) {
//                if (vcd.mouseBtn == MouseEvent.BUTTON3) {
//                    Point vp = GPoint.createViewPoint(g, vcd.mousePos);
//                    vertexMnu.show((Component) vcd.v.view, vp.x, vp.y);
//                }
//            }
//        }
//        if (eventName ==EdgeEvent.name)) {
//            EdgeEvent ecd = blackboard.get(EdgeEvent.name);
//            if (ecd.eventType == EdgeEvent.CLICKED) {
//                if (ecd.mouseBtn == MouseEvent.BUTTON3) {
//                    Point ep = GPoint.createViewPoint(g, ecd.mousePos);
//                    edgeMnu.show((Component) ecd.e.view, ep.x, ep.y);
//                }
//            }
//        }

        if (eventName.equals(GraphEvent.EVENT_KEY)) {
            GraphEvent ge = (GraphEvent) value;
            if (ge.eventType == GraphEvent.CLICKED) {
                if (ge.mouseBtn == MouseEvent.BUTTON3) {
                    AbstractGraphRenderer gv = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
                    graphMnu.show(gv, (int) ge.mousePos.x, (int) ge.mousePos.y);
                    super.track();
                }
            }
        }
    }

    private static JPopupMenu graphMnu = new JPopupMenu();
    private static JPopupMenu vertexMnu = new JPopupMenu();
    private static JPopupMenu edgeMnu = new JPopupMenu();

    /**
     * registers a popup menu that will be shown on each graph that assigned to Graph.name in blackboard (after the assignment)
     *
     * @param id    the string shown on mnu
     * @param index place of it
     * @param n     this action will be enabled(in it's group) and then the performJob will be called
     */
    public static void registerGraphPopupMenu(String id, int index, graphtea.platform.core.AbstractAction n, boolean forceEnable) {
        registerPMenu(graphMnu, id, index, n, forceEnable);
    }

    public static void registerVertexPopupMenu(String id, int index, graphtea.platform.core.AbstractAction n, boolean forceEnable) {
        registerPMenu(vertexMnu, id, index, n, forceEnable);
    }

    public static void registerEdgePopupMenu(String id, int index, graphtea.platform.core.AbstractAction n, boolean forceEnable) {
        registerPMenu(edgeMnu, id, index, n, forceEnable);
    }

    //todo: i hate the force enable, it destroys all the design...
    private static void registerPMenu(JPopupMenu mnu, final String id, final int index, final graphtea.platform.core.AbstractAction n, final boolean forceEnable) {
        JMenuItem item = new JMenuItem(id);
        mnu.add(item, index);
        mnu.validate();
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (forceEnable) {
//                    Configuration conf = n.getBlackBoard().getData(UIEventHandler.CONF);
//                    conf.enableAction(n);
                    if (n.isEnable()) {
                        n.performAction("popup menu: " + id, null);
                    }
                } else {
                    n.performAction("popup menu: " + id, null);
                }
            }
        });
    }
}
