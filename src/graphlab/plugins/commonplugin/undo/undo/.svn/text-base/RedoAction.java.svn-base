// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.undo.undo;
/*
author :roozbeh
*/

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;
import graphlab.ui.UIUtils;

public class RedoAction extends AbstractAction {

    public static final String EVENT_KEY = UIUtils.getUIEventKey("Redo Action");


    public RedoAction(BlackBoard bb) {
        super(bb);
        listen4Event(EVENT_KEY);
    }

    /**
     * performs the redo operation for basic actions
     */
    public static void redo(UndoableActionOccuredData uaod) {
        uaod.undoableAction.redo(uaod);

    }

    public void performAction(String eventName, Object value) {
        redo(blackboard);
    }

    /**
     * redo the last undoabe operation done in the context of current blackboard
     */
    public static void redo(BlackBoard blackboard) {
        UndoLogManager logManager = blackboard.getData(UndoLogManager.EVENT_KEY);
        UndoableActionOccuredData uaod = logManager.getNextRedoData();
        if (uaod != null)
            redo(uaod);
    }
}