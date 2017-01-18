// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.localsfvis;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.AbstractGraphRenderer;
import graphtea.graph.graph.FastRenderer;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * User: root
 */
public class LocalSF extends AbstractAction {
    public static final String EVENT_KEY = UIUtils.getUIEventKey("LocalSF");

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public LocalSF(BlackBoard bb) {
        super(bb);
        listen4Event(EVENT_KEY);
        //the variable which the component is in.
        final String stop = UIUtils.getComponentVariableKeyNameInBlackBoard("stop");
        stopbtn = (JButton) UIUtils.getComponent(bb, "stop");
        if (stopbtn != null)
            stopbtn.setVisible(false);
        blackboard.addListener(stop, new Listener() {
            public void performJob(String name) {
                stopbtn = blackboard.getData(stop);
                stopbtn.setVisible(false);
            }

            public void keyChanged(String name, Object value) {
                stopbtn = blackboard.getData(stop);
                stopbtn.setVisible(false);
            }

            public boolean isEnable() {
                return true;
            }
        });
    }

    private JButton stopbtn;

    /**
     * like Action
     *
     * @param eventName The event name
     * @param value The value
     */
    public void performAction(String eventName, Object value) {
        LSFUI l = new LSFUI();
        l.setTaget(this);
        Dialog d = new JDialog();
//        d.setModal(true);
        d.add(l);
        d.setVisible(true);
        d.setAlwaysOnTop(true);
        d.pack();
        start();
    }

    void stop() {
        animatorLSF currentAnimator = getCurrentAnimator();
        if (currentAnimator != null)
            currentAnimator._stop();
        g2a.remove(g);
        if (gv instanceof FastRenderer) {
            FastRenderer fgv = (FastRenderer) gv;
            fgv.forceQuickPaint = false;
            fgv.repaint();
        }

    }

    private AbstractGraphRenderer gv;

    void start() {
        g = blackboard.getData(GraphAttrSet.name);
        gv = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
//        if (gv instanceof FastRenderer) {
//            FastRenderer fgv = (FastRenderer) gv;
//            fgv.forceQuickPaint = true;
//        }
        if (!g2a.containsKey(g)) {
            animatorLSF a = new animatorLSF(blackboard, g, gv);
            g2a.put(g, a);
            a.start();
        }
    }

    animatorLSF getCurrentAnimator() {
        g = blackboard.getData(GraphAttrSet.name);
        return g2a.get(g);
    }

    private HashMap<GraphModel, animatorLSF> g2a = new HashMap<>();
    private GraphModel g;
}


