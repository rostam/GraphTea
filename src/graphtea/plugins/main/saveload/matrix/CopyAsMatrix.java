// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.matrix;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.select.Select;
import graphtea.ui.UIUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * @author Hooman Mohajeri Moghaddam added weighted save&load support
 * @author Azin Azadi
 */
public class CopyAsMatrix extends AbstractAction {
    String event = UIUtils.getUIEventKey("copy as matrix");

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public CopyAsMatrix(BlackBoard bb) {
        super(bb);
        listen4Event(event);
    }

    public void performAction(String eventName, Object value) {
        SubGraph sd = Select.getSelection(blackboard);
        copyAsMatrix(blackboard.getData(GraphAttrSet.name), sd);
    }

    /**
     * sd should be a subset of g
     */
    public static void copyAsMatrix(GraphModel gg, SubGraph sd) {
        GraphModel g = new GraphModel(gg.isDirected());
        moveToGraph(sd, g);
        Object[][] m = Matrix.graph2Matrix(g);
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        String data = Matrix.Matrix2String(m);
        StringSelection string = new StringSelection(data);
        cb.setContents(string, string);
//        moveToGraph(sd, gg);
    }

    private static void moveToGraph(SubGraph sd, GraphModel g) {
        g.insertVertices(sd.vertices);
        g.insertEdges(sd.edges);
    }


}
