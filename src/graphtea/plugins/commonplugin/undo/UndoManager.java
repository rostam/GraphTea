// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.undo;

/*
* author: azin azadi
*/

import graphtea.extensions.io.GraphSaveObject;
import graphtea.graph.graph.GraphModel;

public class UndoManager {
    public static final String EVENT_KEY = "Undo Log Manager";

    public UndoManager(GraphModel g) {
        current = lastNode;
        watchGraph(g);
    }

    Node current;
    Node lastNode = new Node();

    String lastgml = "";

    void watchGraph(final GraphModel g) {
        System.out.println("watch undo manager");
        new Thread() {
            public void run() {
                while (true) {
                    try {

                        String cur = GraphSaveObject.graph2String(g);
                        if (!cur.equals(lastgml)) {
                            //st changed
                            System.out.println(cur);
                            addChange(cur);
                            lastgml = cur;
                        }
                        sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    public void addChange(String graphml) {
        Node first = new Node();
        first.val = graphml;
        first.setNext(current);
        this.current = first;
    }

    /**
     * returns the data for the next undo action
     */
    public String getNextUndoData() {
        if (current == lastNode)
            return null;    //no action to undo
        String val = current.val;
        current = current.next;
        return val;
    }

    /**
     * returns the data for the next undo action
     */
    public String getNextRedoData() {
        if (current.prev == null)
            return null;    //no action to undo
        current = current.prev;
        return current.val;
    }

}

class Node {
    Node next, prev;
    String val;

    /**
     * sets the next node linked to this node and also updates the previous node of the next to this
     */
    public void setNext(Node next) {
        this.next = next;
        if (next != null)
            next.prev = this;
    }
}
