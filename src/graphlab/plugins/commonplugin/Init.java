// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.event.GraphModelListener;
import graphlab.graph.graph.GraphModel;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.Listener;
import graphlab.platform.plugin.PluginInterface;
import graphlab.platform.preferences.lastsettings.StorableOnExit;
import graphlab.plugins.commonplugin.undo.UndoManager;

import java.util.HashMap;

/**
 * @author Reza Mohammadi
 */
public class Init implements PluginInterface, StorableOnExit {
    public static HashMap<GraphModel, UndoManager> undoers = new HashMap<GraphModel, UndoManager>();
    public void init(BlackBoard blackboard) {
        new graphlab.plugins.commonplugin.reporter.Init().init(blackboard);
        new graphlab.plugins.commonplugin.help.Init().init(blackboard);


        //make all graphs undoable
        blackboard.addListener(GraphAttrSet.name, new Listener() {
            @Override
            public void keyChanged(String key, Object value) {
                GraphModel g = (GraphModel) value;
                if (! undoers.containsKey(g)){
                    undoers.put(g, new UndoManager(g));
                }

            }
        });
    }
}
