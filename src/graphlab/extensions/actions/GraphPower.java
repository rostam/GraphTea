// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphlab.extensions.actions;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.core.AlgorithmUtils;
import graphlab.plugins.main.extension.GraphActionExtension;

import java.util.Vector;

/**
 * @author Azin Azadi
 */


public class GraphPower implements GraphActionExtension, Parametrizable {
    @Parameter
    public int k = 2;

    Vector<Edge> toInsert = new Vector<Edge>();
    Vector<Vertex> subtree = new Vector<Vertex>();

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
        toInsert = new Vector<Edge>();
        subtree = new Vector<Vertex>();
        return (k < 2 ? "K must be larger than 1" : null);
    }
}


