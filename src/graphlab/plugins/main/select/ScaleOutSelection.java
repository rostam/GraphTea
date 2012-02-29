// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.select;

import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;
import graphlab.plugins.commonplugin.undo.Undoable;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.AlgorithmUtils;
import graphlab.plugins.main.extension.GraphActionExtension;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Azin Azadi
 */
public class ScaleOutSelection implements GraphActionExtension, Undoable {
    public String getName() {
        return "Scale Out Selection";
    }

    public String getDescription() {
        return "Shrinks the selection";
    }

    public void action(GraphData gd) {
        if (gd.select.isSelectionEmpty())
            return;
        HashSet<VertexModel> V = gd.select.getSelectedVertices();
        //add undo data
        UndoableActionOccuredData uaod = new UndoableActionOccuredData(this);
        fillUndoPos(uaod.properties, gd, "old pos");

        GraphPoint center = AlgorithmUtils.getCenter(V);
        for (VertexModel v : V) {
            GraphPoint loc = v.getLocation();
            double x = loc.x - center.x;
            double y = loc.y - center.y;
            setNewLocation(v, loc, x, y);
        }
        fillUndoPos(uaod.properties, gd, "new pos");

        gd.core.addUndoData(uaod);
    }

    protected void setNewLocation(VertexModel v, GraphPoint loc, double x, double y) {
        v.setLocation(new GraphPoint(loc.x - x / 1.25, loc.y - y / 1.25));
    }


    //---------------           U N D O           ----------------------
    public void undo(UndoableActionOccuredData uaod) {
        doUndoPos(uaod.properties, "new pos");
    }

    public void redo(UndoableActionOccuredData uaod) {
        doUndoPos(uaod.properties, "old pos");
    }

    public static void fillUndoPos(HashMap<String, Object> properties, GraphData gd, String lbl) {
        HashMap<VertexModel, GraphPoint> pos = new HashMap<VertexModel, GraphPoint>();
        for (VertexModel v : gd.select.getSelectedVertices()) {
            pos.put(v, v.getLocation());
        }
        properties.put("graph", gd.getGraph());
        properties.put(lbl, pos);
    }

    public static void doUndoPos(HashMap<String, Object> uaod, String lbl) {
        HashMap<VertexModel, GraphPoint> pos = (HashMap<VertexModel, GraphPoint>) uaod.get(lbl);
        for (Map.Entry<VertexModel, GraphPoint> x : pos.entrySet()) {
            x.getKey().setLocation(x.getValue());
        }
    }
}
