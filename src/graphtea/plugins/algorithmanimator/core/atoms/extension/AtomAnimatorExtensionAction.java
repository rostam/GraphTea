// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.algorithmanimator.core.atoms.extension;

import graphtea.platform.core.BlackBoard;
import graphtea.ui.AbstractExtensionAction;


public class AtomAnimatorExtensionAction
        extends AbstractExtensionAction {
    private AtomAnimatorExtension em;

    public AtomAnimatorExtensionAction(BlackBoard bb, AtomAnimatorExtension sp) {
        super(bb, sp);
        this.em = sp;
    }

    @Override
    public String getParentMenuName() {
        return null;
    }

    @Override
    public final void performExtension() {


    }

}
