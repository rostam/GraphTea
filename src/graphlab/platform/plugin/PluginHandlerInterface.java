// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.plugin;

import graphlab.platform.core.BlackBoard;

/**
 * @author Reza Mohammadi
 */
public interface PluginHandlerInterface {

    /**
     * Default "child plugin" initializer.
     * "child plugin" = a plugin that depends on "THE Pluggin"
     * "THE Pluggin" = Plugin that is implementing this interface
     * and has graphlab.gui.plugin.<i>pluginname</i>.HandlerInit
     *
     * @param path       path of config file. if your config file
     *                   is in the jar file and in directory "/plugin/config/"
     *                   and it's name is "config.xml", you should set "plugin-configxml"
     *                   to "/plugin/config/config.xml"
     * @param blackboard blackboard of GraphLab instance
     * @see PluginInterface#init(graphlab.platform.core.BlackBoard)
     */
    public void init(String path, BlackBoard blackboard);
}
