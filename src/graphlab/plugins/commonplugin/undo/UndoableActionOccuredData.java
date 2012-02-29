// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.undo;

import graphlab.platform.core.BlackBoard;

import java.util.HashMap;

/**
 * @author Ruzbeh Ebrahimi
 */
public class UndoableActionOccuredData {
    public static final String EVENT_KEY = "UndoableAction.Occured";
    public Undoable undoableAction;
    public HashMap<String, Object> properties;

    public UndoableActionOccuredData(Undoable undoableAction) {
        properties = new HashMap<String, Object>();
        this.undoableAction = undoableAction;
    }

    /**
     * puts data in the stack of undo/redo actions, so it will be regarded as an undoable action and will be undone by the rules of undo/redo.
     */
    public static void addUndoData(BlackBoard blackboard, UndoableActionOccuredData data) {
        blackboard.setData(EVENT_KEY, data);
    }
}
