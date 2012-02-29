// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.saveload.image;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.SubGraph;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

/**
 * @author Azin Azadi
 */
public class CopyAsImage extends AbstractAction {
    String event = UIUtils.getUIEventKey("copy as image");

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public CopyAsImage(BlackBoard bb) {
        super(bb);
        listen4Event(event);
    }

    public void performAction(String eventName, Object value) {
        throw new RuntimeException(" graphlab.plugins.main.saveload.image.CopyAsImage Not Implemented Yet");
//        SelectData sd=blackboard.get(SelectData.name);
//        GraphModel g = createGraph(sd,blackboard);
//        BufferedImage bufferedImage = Image.Graph2Image(g);
//        JOptionPane.showMessageDialog(null,"chejoori mishe image o rikht to clip board?");
    }

    /**
     * creates a new graph with the selected edges and vertices, the new graph will be built on the given blackboard
     */
    public GraphModel createGraph(SubGraph sd, BlackBoard b) {
//        Graph g = new Graph(new blackboard());
        GraphModel g = new GraphModel();
        g.insertVertices(sd.vertices);
        g.insertEdges(sd.edges);
        //kar
        GraphModel gg = b.getData(GraphAttrSet.name);
        gg.insertVertices(sd.vertices);
        gg.insertEdges(sd.edges);
        return g;
    }

}
