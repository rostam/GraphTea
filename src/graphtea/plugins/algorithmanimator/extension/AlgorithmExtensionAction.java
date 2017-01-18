// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.algorithmanimator.extension;

import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.AlgorithmAnimator;
import graphtea.ui.extension.AbstractExtensionAction;


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

        new Thread(() -> {
            AlgorithmAnimator algorithmAnimator = new AlgorithmAnimator(blackboard);
            target.acceptEventDispatcher(algorithmAnimator);
            GTabbedGraphPane.showTimeNotificationMessage(target.getName() + " Algorithm started", blackboard, 2000, true);
            algorithmAnimator.createControlDialog(target.getName());
            target.doAlgorithm();
            GTabbedGraphPane.showTimeNotificationMessage(target.getName() + " Algorithm finished", blackboard, 3000, true);
        }).start();
    }
}
