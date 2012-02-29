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

public class UndoAction extends AbstractAction {

    public static final String EVENT_KEY = UIUtils.getUIEventKey("Undo Action");


    public UndoAction(BlackBoard bb) {
        super(bb);
        listen4Event(EVENT_KEY);
        blackboard.setData(UndoLogManager.EVENT_KEY, new UndoLogManager(bb));
    }

    /**
     * performs the undo operation for basic actions
     */
    public static void undo(UndoableActionOccuredData uaod) {
        uaod.undoableAction.undo(uaod);

    }

    public void performAction(String eventName, Object value) {
        undo(blackboard);
    }

    /**
     * undo the last undoabe operation done in the context of current blackboard
     *
     * @param blackboard
     */
    public static void undo(BlackBoard blackboard) {
        UndoLogManager logManager = blackboard.getData(UndoLogManager.EVENT_KEY);
        UndoableActionOccuredData uaod = logManager.getNextUndoData();
        if (uaod != null)
            undo(uaod);
    }

}