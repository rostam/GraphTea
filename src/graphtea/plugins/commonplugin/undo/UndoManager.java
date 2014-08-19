// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.undo;

/*
* author: azin azadi, Mostafa Shaeri
*/

import graphtea.graph.graph.GraphModel;


public class UndoManager {
    public static final String EVENT_KEY = "Undo Log Manager";

    public UndoManager(GraphModel g) {
        current = lastNode;
        watchGraph(g);
    }

    
    Node current;
    Node lastNode = new Node();

    GraphModel lastgml=null;
    void watchGraph(final GraphModel g) {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                       
                        GraphModel cur=g;
                        if ( !cur.equals(lastgml)   /*!cur.equals(lastgml)*/) {
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


    public void addChange(GraphModel graphml) {
        Node first = new Node();
        first.val = graphml;
        first.setNext(current);
        this.current = first;
    }
    

    /**
     * returns the data for the next undo action
     */
    public GraphModel getNextUndoData() {
        if (current == lastNode)
            return null;    //no action to undo
        GraphModel val = current.val;
        current = current.next;
        return val;
    }

    /**
     * returns the data for the next undo action
     */
    public GraphModel getNextRedoData() {
        if (current.prev == null)
            return null;    //no action to undo
        current = current.prev;
        return current.val;
    }

}

class Node {
    Node next, prev;
    GraphModel val;

    /**
     * sets the next node linked to this node and also updates the previous node of the next to this
     */
    public void setNext(Node next) {
        this.next = next;
        if (next != null)
            next.prev = this;
    }
}
