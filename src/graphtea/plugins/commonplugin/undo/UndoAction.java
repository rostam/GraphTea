// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.undo;

import graphtea.extensions.io.GraphSaveObject;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.plugins.main.GraphData;
import graphtea.ui.UIUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class UndoAction extends AbstractAction {
    public static final String UNDO_EVENT = UIUtils.getUIEventKey("Undo Action");
    public static final String REDO_EVENT = UIUtils.getUIEventKey("Redo Action");
    private HashMap<GraphModel, Stack<GraphSaveObject>> undoers = new HashMap<>();
    private HashMap<GraphModel, Stack<GraphSaveObject>> redoers = new HashMap<>();

    public void pushUndo(GraphModel label, GraphSaveObject gso) {
        if(undoers.get(label) == null) {
            undoers.put(label,new Stack<GraphSaveObject>());
        }
        redoers.put(label,new Stack<GraphSaveObject>());  //reset redo for this graph
        boolean isContained = false;
        for(GraphSaveObject tmp : undoers.get(label)) {
            byte[] b1 = GraphSaveObject.getBytesOfGraphSaveObject(tmp);
            byte[] b2 = GraphSaveObject.getBytesOfGraphSaveObject(gso);
            if (Arrays.equals(b1, b2)) {
                isContained = true;
                break;
            }
        }
        if(!isContained) {
            undoers.get(label).push(gso);
        }
    }

    public GraphSaveObject popUndo(GraphModel label) {
        if(undoers.get(label)== null) return null;
        if(undoers.get(label).size()==0) return null;
        GraphSaveObject temp = undoers.get(label).pop();
        if(redoers.get(label)== null) {
            redoers.put(label, new Stack<GraphSaveObject>());
        }
        redoers.get(label).push(temp);
        return temp;
    }

    public GraphSaveObject popRedo(GraphModel label) {
        if(redoers.get(label)== null) return null;
        if(redoers.get(label).size()==0) return null;
        GraphSaveObject temp = redoers.get(label).pop();
        if(undoers.get(label)!= null) undoers.get(label).push(temp);
        return temp;
    }


    public UndoAction(BlackBoard bb) {
        super(bb);
        listen4Event(UNDO_EVENT);
        listen4Event(REDO_EVENT);

        bb.addListener("undo point", new Listener<GraphModel>() {
            public void keyChanged(String key, GraphModel value) {
                pushUndo(value, new GraphSaveObject(value));
            }
        });
    }

    public void performAction(String eventName, Object value) {
        if (eventName.equals(UNDO_EVENT))
            undo();
        else
            redo();
    }

    public void undo() {
        GraphData gd = new GraphData(blackboard);
        GraphModel cur = gd.getGraph();
        GraphSaveObject gso = popUndo(cur);
        if(gso == null) return;
        cur.clear();
        gso.insertIntoGraph(cur);
        gd.getGraphRenderer().repaintGraph();
    }

    public  void redo() {
        GraphData gd = new GraphData(blackboard);
        GraphModel cur = gd.getGraph();
        GraphSaveObject gso = popRedo(cur);
        if (gso == null) return;
        cur.clear();
        gso.insertIntoGraph(cur);
    }

}