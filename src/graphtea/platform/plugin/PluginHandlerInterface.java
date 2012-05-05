// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.plugin;

import graphtea.platform.core.BlackBoard;

/**
 * @author Reza Mohammadi
 */
public interface PluginHandlerInterface {

    /**
     * Default "child plugin" initializer.
     * "child plugin" = a plugin that depends on "THE Pluggin"
     * "THE Pluggin" = Plugin that is implementing this interface
     * and has graphtea.gui.plugin.<i>pluginname</i>.HandlerInit
     *
     * @param path       path of config file. if your config file
     *                   is in the jar file and in directory "/plugin/config/"
     *                   and it's name is "config.xml", you should set "plugin-configxml"
     *                   to "/plugin/config/config.xml"
     * @param blackboard blackboard of GraphTea instance
     * @see PluginInterface#init(graphtea.platform.core.BlackBoard)
     */
    public void init(String path, BlackBoard blackboard);
}
