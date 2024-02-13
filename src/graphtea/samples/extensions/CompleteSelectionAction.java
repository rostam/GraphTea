// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.extensions;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.util.HashSet;

public class CompleteSelectionAction implements GraphActionExtension {

    public void action(GraphData graphData) {
        HashSet<Vertex> sel = graphData.select.getSelectedVertices();
        GraphModel g = graphData.getGraph();
        for (Vertex v1 : sel) {
            for (Vertex v2 : sel) {
                Edge e = new Edge(v1, v2);
                g.insertEdge(e);
            }
        }
    }

    public String getName() {
        return "complete selection";
    }

    public String getDescription() {
        return "Makes the selected subgraph a complete subgraph";
    }

    @Override
    public String getCategory() {
        return "Basic Operations";
    }
}