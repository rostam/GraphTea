// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.commandline;

import graphlab.platform.core.BlackBoard;

/**
 * @author Azin Azadi
 */
public class ShellPluginMethods {
    /**
     * @see graphlab.plugins.commandline.Shell#getCurrentShell(graphlab.platform.core.BlackBoard)
     */
    public static Shell getCurrentShell(BlackBoard b) {
        return Shell.getCurrentShell(b);
    }
}
