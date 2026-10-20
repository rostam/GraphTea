// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.algorithmanimator.extension;

import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
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

        Thread runner = new Thread(() -> {
            AlgorithmAnimator algorithmAnimator = new AlgorithmAnimator(blackboard);
            target.acceptEventDispatcher(algorithmAnimator);
            algorithmAnimator.createControlDialog(target.getName());
            boolean completed = false;
            try {
                target.doAlgorithm();
                completed = true;
            } catch (AlgorithmAnimator.AlgorithmCancelled stopped) {
                // The user closed the runner window; nothing to report.
            } catch (RuntimeException e) {
                ExceptionHandler.catchException(target.getName(), e);
            } finally {
                algorithmAnimator.algorithmEnded(completed);
            }
            if (completed) {
                GTabbedGraphPane.showTimeNotificationMessage(target.getName() + " finished", blackboard, 3000, true);
            }
        }, "graphtea-algorithm-" + target.getName());
        // A paused algorithm must not keep the application alive after its window is gone.
        runner.setDaemon(true);
        runner.start();
    }
}
