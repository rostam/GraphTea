// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.samples.extensions;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.extension.GraphActionExtension;

import java.util.HashSet;

public class CompleteSelectionAction implements GraphActionExtension {

    public void action(GraphData graphData) {
        HashSet<VertexModel> sel = graphData.select.getSelectedVertices();
        GraphModel g = graphData.getGraph();
        for (VertexModel v1 : sel) {
            for (VertexModel v2 : sel) {
                EdgeModel e = new EdgeModel(v1, v2);
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
}