// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.undo.undo;
/*
author:azin azadi

*/

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;

public class UndoLogManager extends AbstractAction {
    public static final String EVENT_KEY = "Undo Log Manager";

    public UndoLogManager(BlackBoard bb) {
        super(bb);
        listen4Event(UndoableActionOccuredData.EVENT_KEY);
        enable();
        current = lastNode;
    }

    Node current;
    Node lastNode = new Node();

    /**
     * Occurs when the undo log adds by an action
     *
     * @param eventName
     * @param value
     */
    public void performAction(String eventName, Object value) {
        UndoableActionOccuredData uaod = blackboard.getData(UndoableActionOccuredData.EVENT_KEY);
        Node first = new Node(); //we can delet this line
        first.val = uaod;
        first.setNext(current);
        this.current = first;
    }

    /**
     * returns the data for the next undo action
     */
    public UndoableActionOccuredData getNextUndoData() {
        if (current == lastNode)
            return null;    //no action to undo
        UndoableActionOccuredData val = current.val;
        current = current.next;
        return val;
    }

    /**
     * returns the data for the next undo action
     */
    public UndoableActionOccuredData getNextRedoData() {
        if (current.prev == null)
            return null;    //no action to undo
        current = current.prev;
        return current.val;
    }

}

class Node {
    Node next, prev;
    UndoableActionOccuredData val;

    /**
     * sets the next node linked to this node and also updates the previous node of the next to this
     */
    public void setNext(Node next) {
        this.next = next;
        if (next != null)
            next.prev = this;
    }
}
