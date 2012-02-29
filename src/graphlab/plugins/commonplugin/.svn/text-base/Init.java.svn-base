// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.plugin.PluginInterface;
import graphlab.platform.preferences.lastsettings.StorableOnExit;

/**
 * @author Reza Mohammadi
 */
public class Init implements PluginInterface, StorableOnExit {
    public void init(BlackBoard blackboard) {
        new graphlab.plugins.commonplugin.reporter.Init().init(blackboard);
        new graphlab.plugins.commonplugin.help.Init().init(blackboard);
    }
}
