// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.core;

import graphlab.library.algorithms.AutomatedAlgorithm;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

/**
 * @author azin azadi
 */
public abstract class SimpleAlgorithmAnimator extends AbstractAction {
    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public SimpleAlgorithmAnimator(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey(getID()));
    }

    public void performAction(String eventName, Object value) {
        startAnim();
    }

    private void startAnim() {
        new Thread() {
            public void run() {
                AlgorithmAnimator a = new AlgorithmAnimator(blackboard);
                a.animateAlgorithm(getAlgorithm());
            }
        }.start();

    }

    public abstract String getID();

    public abstract AutomatedAlgorithm getAlgorithm();
}
