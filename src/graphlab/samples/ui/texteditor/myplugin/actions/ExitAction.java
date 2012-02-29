// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.samples.ui.texteditor.myplugin.actions;

import graphlab.platform.core.BlackBoard;
import graphlab.ui.extension.UIActionExtension;

public class ExitAction implements UIActionExtension {
    public void actionPerformed(BlackBoard blackBoard) {
        System.exit(0);
    }
}
