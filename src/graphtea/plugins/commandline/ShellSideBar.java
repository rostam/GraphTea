// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline;

import graphtea.platform.core.BlackBoard;
import graphtea.ui.components.GComponentInterface;

import java.awt.*;

/**
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */
public class ShellSideBar implements GComponentInterface {
    public Component getComponent(BlackBoard b) {
        ShellConsole j = new ShellConsole();
        j.setMinimumSize(new Dimension(300, 100));
        j.setPreferredSize(new Dimension(300, 200));
        return j;
    }
}
