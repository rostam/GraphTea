// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.algorithmanimator.core.atoms.extension;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.extension.ExtensionHandler;
import graphlab.platform.StaticUtils;
import graphlab.plugins.algorithmanimator.core.AlgorithmAnimator;

public class AtomAnimatorExtensionHandler implements ExtensionHandler {

    AbstractAction a = null;

    public AbstractAction handle(BlackBoard b, Object ext) {
        a = null;
        if (ext instanceof AtomAnimatorExtension) {
            try {
                AtomAnimatorExtension vm = (AtomAnimatorExtension) ext;
                a = new AtomAnimatorExtensionAction(b, vm);
                AlgorithmAnimator.registerAtomAnimation(vm);
            } catch (Exception e) {
                e.printStackTrace();
                StaticUtils.addExceptiontoLog(e, b);
            }
        }
        return a;
    }
}
