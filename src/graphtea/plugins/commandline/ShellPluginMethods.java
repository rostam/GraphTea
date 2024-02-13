// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline;

import graphtea.platform.core.BlackBoard;

/**
 * @author Azin Azadi
 */
public class ShellPluginMethods {
    /**
     * @see graphtea.plugins.commandline.Shell#getCurrentShell(graphtea.platform.core.BlackBoard)
     */
    public static Shell getCurrentShell(BlackBoard b) {
        return Shell.getCurrentShell(b);
    }
}
