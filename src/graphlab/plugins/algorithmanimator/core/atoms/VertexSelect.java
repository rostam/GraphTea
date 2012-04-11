// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.core.atoms;

import graphlab.graph.graph.Vertex;
import graphlab.graph.ui.GTabbedGraphPane;
import graphlab.library.event.Event;
import graphlab.library.event.VertexRequest;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.AtomAnimator;
import graphlab.plugins.main.core.actions.vertex.VertexSelectData;
import graphlab.plugins.main.select.SelectPluginMethods;

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