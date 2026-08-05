// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.ui.texteditor.myplugin.actions;

import graphtea.platform.core.BlackBoard;
import graphtea.ui.extension.UIActionExtension;

public class ExitAction implements UIActionExtension {
    public void actionPerformed(BlackBoard blackBoard) {
        System.exit(0);
    }
}
