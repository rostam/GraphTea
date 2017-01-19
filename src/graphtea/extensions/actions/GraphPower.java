// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.actions;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.util.Vector;

/**
 * @author Azin Azadi
 */


public class GraphPower implements GraphActionExtension, Parametrizable {
    @Parameter
    public int k = 2;

    Vector<Edge> toInsert = new Vector<>();
    Vector<Vertex> subtree = new Vector<>();

    public String getName() {
        return "Create Power Graph";
    }

    public String getDescription() {
        return "Create Power graph";
    }

    public void action(GraphData graphData) {
        toInsert.clear();
        GraphModel g = graphData.getGraph();

        AlgorithmUtils.clearVertexMarks(g);
        for (Vertex v : g) {
            subtree.clear();
            aStar(v, v, k, g);
            for (Vertex vv : subtree)
                vv.setMark(false);
        }
        g.insertEdges(toInsert);

    }

    void aStar(Vertex root, Vertex v, int k, GraphModel g) {
        if (k == 0)
            return;
        v.setMark(true);
        subtree.add(v);

        for (Vertex vv : g.getNeighbors(v)) {
            if (!vv.getMark()) {
                toInsert.add(new Edge(root, vv));
                aStar(root, vv, k - 1, g);
            }
        }
    }

    public String checkParameters() {
        toInsert = new Vector<>();
        subtree = new Vector<>();
        return (k < 2 ? "K must be larger than 1" : null);
    }

    @Override
    public String getCategory() {
        return "Transformations";
    }
}


