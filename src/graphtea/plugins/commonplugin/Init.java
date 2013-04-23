// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.platform.plugin.PluginInterface;
import graphtea.platform.preferences.lastsettings.StorableOnExit;

import java.util.HashMap;

/**
 * @author Reza Mohammadi
 */
public class Init implements PluginInterface, StorableOnExit {
    public void init(BlackBoard blackboard) {
        new graphtea.plugins.commonplugin.reporter.Init().init(blackboard);


        //make all graphs undoable
        blackboard.addListener(GraphAttrSet.name, new Listener() {
            @Override
            public void keyChanged(String key, Object value) {
                GraphModel g = (GraphModel) value;

            }
        });
    }
}
