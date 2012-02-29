// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.graph.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Azin Azadi
 */
public class GraphColoring {
    public GraphModel graph;
    public HashMap<VertexModel, Integer> vertexColors = new HashMap<VertexModel, Integer>();
    public HashMap<EdgeModel, Integer> edgeColors = new HashMap<EdgeModel, Integer>();
    public String label = "";

    public GraphColoring(GraphModel graph) {
        this.graph = graph;
    }

    /**
     * using this constructor the selected graph will be considered as the default graph in the blackboard
     */
    public GraphColoring() {
        graph = null;
    }

    @Override
    public String toString() {
        String txt = "";
        if (label != null && !label.equals("")) {
            txt = txt + label + ":  \n";
        }
        if (vertexColors != null && vertexColors.size() > 0) {
            txt = txt + "Vertex colors: ";
            for (Map.Entry<VertexModel, Integer> p : vertexColors.entrySet()) {
                txt = txt + p.getKey().getLabel() + ":" + p.getValue() + " , ";
            }
        }
        if (edgeColors != null && edgeColors.size() > 0) {
            txt = txt + "\nEdge colors: ";
            for (Map.Entry<EdgeModel, Integer> p : edgeColors.entrySet()) {
                txt = txt + p.getKey().getLabel() + ":" + p.getValue() + " , ";
            }
        }
        return txt;
    }

    public void applyColoring() {
        if (vertexColors != null && vertexColors.size() > 0) {
            for (Map.Entry<VertexModel, Integer> p : vertexColors.entrySet()) {
                p.getKey().setColor(p.getValue());
            }
        }
        if (edgeColors != null && edgeColors.size() > 0) {
            for (Map.Entry<EdgeModel, Integer> p : edgeColors.entrySet()) {
                p.getKey().setColor(p.getValue());
            }
        }

    }

    /**
     * resets and stores all colorings of g
     */
    public void backupColoring() {
        vertexColors = new HashMap<VertexModel, Integer>();
        for (VertexModel v : graph) {
            vertexColors.put(v, v.getColor());
        }
        edgeColors = new HashMap<EdgeModel, Integer>();
        for (Iterator<EdgeModel> ite = graph.edgeIterator(); ite.hasNext();) {
            EdgeModel e = ite.next();
            edgeColors.put(e, e.getColor());
        }
    }
}
