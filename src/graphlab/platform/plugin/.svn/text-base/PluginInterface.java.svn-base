// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.plugin;

import graphlab.platform.core.BlackBoard;

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
     * graphlab.gui.plugin.<i>pluginname</i>.Init,
     * it should define it's location by setting
     * plugin-initializer in manifest of jar file.
     *
     * @param blackboard blackboard of GraphLab instance
     */
    public void init(BlackBoard blackboard);
}
