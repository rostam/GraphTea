// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.visualization.localsfvis;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.AbstractGraphRenderer;
import graphlab.graph.graph.FastRenderer;
import graphlab.graph.graph.GraphModel;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.Listener;
import graphlab.ui.UIUtils;

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

    JButton stopbtn;
    animatorLSF a;

    /**
     * like Action
     *
     * @param eventName
     * @param value
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

    AbstractGraphRenderer gv;

    void start() {
        g = blackboard.getData(GraphAttrSet.name);
        gv = blackboard.getData(AbstractGraphRenderer.EVENT_KEY);
        if (gv instanceof FastRenderer) {
            FastRenderer fgv = (FastRenderer) gv;
            fgv.forceQuickPaint = true;
        }
        if (!g2a.containsKey(g)) {
            a = new animatorLSF(blackboard, g, gv);
            g2a.put(g, a);
            a.start();
        }
    }

    animatorLSF getCurrentAnimator() {
        g = blackboard.getData(GraphAttrSet.name);
        return g2a.get(g);
    }

    HashMap<GraphModel, animatorLSF> g2a = new HashMap<GraphModel, animatorLSF>();
    GraphModel g;
}


