// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.select;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.plugin.PluginMethods;

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

    public void setSelected(Collection<Vertex> selectedVertices, Collection<Edge> selectedEdges) {
        SubGraph sd = new SubGraph();
        sd.vertices = new HashSet<>(selectedVertices);
        sd.edges = new HashSet<>(selectedEdges);
        b.setData(Select.EVENT_KEY, sd);
    }

    public void setSelected(Vertex[] selectedVertices, Edge[] selectedEdges) {
        SubGraph sd = new SubGraph();
        sd.vertices = new HashSet<>(Arrays.asList(selectedVertices));
        sd.edges = new HashSet<>(Arrays.asList(selectedEdges));
        b.setData(Select.EVENT_KEY, sd);
    }

    public HashSet<Vertex> getSelectedVertices() {
        SubGraph selected = getSelected();
        if (selected == null)
            return new HashSet<>();
        return selected.vertices;
    }

    public void setSelectedVertices(Collection<Vertex> selectedVertices) {
        SubGraph sd = new SubGraph();
        sd.vertices = new HashSet<>(selectedVertices);
        b.setData(Select.EVENT_KEY, sd);
    }

    public HashSet<Edge> getSelectedEdges() {
        return getSelected().edges;
    }

    public void setSelectedEdges(Collection<Edge> selectedEdges) {
        SubGraph sd = new SubGraph();
        sd.edges = new HashSet<>(selectedEdges);
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
//        Vector<Vertex> toSelect = new Vector<Vertex>();
//
//        for (Vertex v : selection.vertices) {
//            if (selection.getNeighbors(v).size() > 1)
//                toSelect.add(v);
//        }
//        setSelectedVertices(toSelect);
//
//    }

    /**
     * adds any vertex in graph which is adjacent to at list one vertex in selected vertices
     */
    public void expandSelection() {
        GraphModel g = b.getData(GraphAttrSet.name);
        HashSet<Vertex> sV = getSelectedVertices();
        Vector<Vertex> toSelect = new Vector<>();

        for (Vertex v : sV) {
            for (Vertex nv : g.getNeighbors(v))
                toSelect.add(nv);
        }
        toSelect.addAll(sV);
        setSelectedVertices(toSelect);
    }

}
