// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core.atoms;

import graphtea.graph.graph.Vertex;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.library.event.Event;
import graphtea.library.event.VertexRequest;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.AtomAnimator;
import graphtea.plugins.main.core.actions.vertex.VertexSelectData;
import graphtea.plugins.main.select.SelectPluginMethods;

/**
 * @author Azin Azadi
 */
public class VertexSelect implements AtomAnimator<VertexRequest> {
    public boolean isAnimatable(Event vertexRequest) {
        return vertexRequest instanceof VertexRequest;
    }

    public VertexRequest animate(final VertexRequest vr, final BlackBoard b) {
        GTabbedGraphPane.showNotificationMessage(vr.message, b, true);
        vs vt = new vs(b);
        vt.start();
        vr.setVertex(vt.getResult());
        new SelectPluginMethods(b).clearSelection();
        GTabbedGraphPane.showNotificationMessage("", b, true);
        return vr;
    }

}

class vs extends aa {
    public vs(BlackBoard bb) {
        super(bb);
        listen4Event(VertexSelectData.EVENT_KEY);
    }

    Vertex a;

    public void performAction(String key, Object value) {
        VertexSelectData vsd = blackboard.getData(VertexSelectData.EVENT_KEY);
        a = vsd.v;
        super.performAction(key, value);
    }

    public Vertex getResult() {
        return a;
    }
}

abstract class aa extends AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public aa(BlackBoard bb) {
        super(bb);
//        disable();
    }

    boolean finished = false;

    public void start() {
        enable();
        while (!finished)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        disable();
    }

    public void performAction(String key, Object value) {
        finished = true;
    }
}