// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.plugin;

import graphtea.platform.core.BlackBoard;

/**
 * Base Init interface which plugins are loaded from.
 * There are options to not implement this class and just put config.xml file as XMLBasedUI In
 * the root directory of plugin package.
 *
 * @author Reza Mohammadi
 */
public interface PluginInterface {

    /**
     * Initializer of plugin. Every plugin can be
     * initialized by Implementing this interface.
     * If the implemented class is not located in
     * graphtea.gui.plugin.<i>pluginname</i>.Init,
     * it should define it's location by setting
     * plugin-initializer in manifest of jar file.
     *
     * @param blackboard blackboard of GraphTea instance
     */
    public void init(BlackBoard blackboard);
}
