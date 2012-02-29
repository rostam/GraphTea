// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.rightclick;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.plugin.PluginMethods;

/**
 * @author azin azadi

 */
public class RightClickPluginMethods implements PluginMethods {
    /**
     * registers a popup menu that will be shown on each graph that assigned to Graph.name in blackboard (after the assignment)
     *
     * @param id    the string shown on mnu
     * @param index place of it
     * @param n     this action will be enabled(in it's group) and then the performJob will be called
     */
    public void registerGraphPopupMenu(String id, int index, AbstractAction n, boolean forceEnable) {
        PopupMenuHandler.registerGraphPopupMenu(id, index, n, forceEnable);
    }

    public void registerVertexPopupMenu(String id, int index, AbstractAction n, boolean forceEnable) {
        PopupMenuHandler.registerVertexPopupMenu(id, index, n, forceEnable);
    }

    public void registerEdgePopupMenu(String id, int index, AbstractAction n, boolean forceEnable) {
        PopupMenuHandler.registerEdgePopupMenu(id, index, n, forceEnable);
    }

}
