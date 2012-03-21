// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.select;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.SubGraph;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.plugin.PluginMethods;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

/**
 * @author azin azadi
 */
public class SelectPluginMethods implements PluginMethods {
    private BlackBoard b;

    public SelectPluginMethods(BlackBoard b) {
        this.b = b;
    }

    /**
     * @return returns the selected Vertices ans Edges of the graph
     */
    public SubGraph getSelected() {
        return b.getData(Select.EVENT_KEY);
    }

    public void setSelected(SubGraph sd) {
        b.setData(Select.EVENT_KEY, sd);
    }

    public void setSelected(Collection<VertexModel> selectedVertices, Collection<EdgeModel> selectedEdges) {
        SubGraph sd = new SubGraph();
        sd.vertices = new HashSet<VertexModel>(selectedVertices);
        sd.edges = new HashSet<EdgeModel>(selectedEdges);
        b.setData(Select.EVENT_KEY, sd);
    }

    public void setSelected(VertexModel[] selectedVertices, EdgeModel[] selectedEdges) {
        SubGraph sd = new SubGraph();
        sd.vertices = new HashSet<VertexModel>(Arrays.asList(selectedVertices));
        sd.edges = new HashSet<EdgeModel>(Arrays.asList(selectedEdges));
        b.setData(Select.EVENT_KEY, sd);
    }

    public HashSet<VertexModel> getSelectedVertices() {
        SubGraph selected = getSelected();
        if (selected == null)
            return new HashSet<VertexModel>();
        return selected.vertices;
    }

    public void setSelectedVertices(Collection<VertexModel> selectedVertices) {
        SubGraph sd = new SubGraph();
        sd.vertices = new HashSet<VertexModel>(selectedVertices);
        b.setData(Select.EVENT_KEY, sd);
    }

    public HashSet<EdgeModel> getSelectedEdges() {
        return getSelected().edges;
    }

    public void setSelectedEdges(Collection<EdgeModel> selectedEdges) {
        SubGraph sd = new SubGraph();
        sd.edges = new HashSet<EdgeModel>(selectedEdges);
        b.setData(Select.EVENT_KEY, sd);
    }

    public boolean isSelectionEmpty() {
        return getSelectedEdges().size() == 0 && getSelectedVertices().size() == 0;
    }

    public void clearSelection() {
        setSelected(new SubGraph());
    }

    //selection modification methods
//    private void shrinkSelection() {
//        SubGraph selection = getSelected();
//        Vector<VertexModel> toSelect = new Vector<VertexModel>();
//
//        for (VertexModel v : selection.vertices) {
//            if (selection.getNeighbors(v).size() > 1)
//                toSelect.add(v);
//        }
//        setSelectedVertices(toSelect);
//
//    }

    /**
     * adds any vertex in graph which is adjacent to at list one vertex in selected vertices
     *
     * @param g
     */
    public void expandSelection() {
        GraphModel g = b.getData(GraphAttrSet.name);
        HashSet<VertexModel> sV = getSelectedVertices();
        Vector<VertexModel> toSelect = new Vector<VertexModel>();

        for (VertexModel v : sV) {
            for (VertexModel nv : g.getNeighbors(v))
                toSelect.add(nv);
        }
        toSelect.addAll(sV);
        setSelectedVertices(toSelect);
    }

}
