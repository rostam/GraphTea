// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.algorithmanimator.extension;

import graphlab.graph.ui.GTabbedGraphPane;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.AlgorithmAnimator;
import graphlab.ui.AbstractExtensionAction;


public class AlgorithmExtensionAction
        extends AbstractExtensionAction<AlgorithmExtension> {

    public AlgorithmExtensionAction(BlackBoard bb, AlgorithmExtension sp) {
        super(bb, sp);
    }

    @Override
    public String getParentMenuName() {
        return "Algorithms";
    }

    @Override
    public void performExtension() {

        new Thread() {
            public void run() {
                AlgorithmAnimator algorithmAnimator = new AlgorithmAnimator(blackboard);
                target.acceptEventDispatcher(algorithmAnimator);
                GTabbedGraphPane.showTimeNotificationMessage(target.getName() + " Algorithm started", blackboard, 2000, true);
                algorithmAnimator.createControlDialog(target.getName());
                target.doAlgorithm();
                GTabbedGraphPane.showTimeNotificationMessage(target.getName() + " Algorithm finished", blackboard, 3000, true);
            }
        }.start();
    }
}
