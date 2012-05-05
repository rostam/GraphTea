// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core;

import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

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
