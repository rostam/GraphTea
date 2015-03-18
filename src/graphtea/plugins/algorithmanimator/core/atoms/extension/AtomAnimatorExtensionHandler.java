// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.algorithmanimator.core.atoms.extension;

import graphtea.platform.StaticUtils;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.extension.ExtensionHandler;
import graphtea.plugins.algorithmanimator.core.AlgorithmAnimator;

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
