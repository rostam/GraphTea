package graphlab.samples.extensions;// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.lang.BoundedInteger;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.graphgenerator.core.PositionGenerators;
import graphlab.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.awt.*;

public class BinaryTreeGenerator implements GraphGeneratorExtension, Parametrizable {

    //the depth should be positive, and also if it is very large the
    //generated graph is too large to generate.
    @Parameter(description = "depth of the tree")
    public BoundedInteger depth = new BoundedInteger(3, 15, 1);
    private int d;
    private int n;      //num of vertices


    public String checkParameters() {
        d = depth.getValue();
        n = (int) (Math.pow(2, d + 1) - 1);
        return null;    //the parameters are well defined.
    }

    public String getName() {
        return "binary tree";
    }

    public String getDescription() {
        return "generates a binary tree";
    }

    public GraphPoint[] getVertexPositions() {
        Point[] r = new Point[n];
        r[0] = new Point(0, 0);
        int last = 1;
        for (int h = 1; h <= d; h++) {
            int nh = (int) Math.pow(2, h);      //num of vertices at height h.
            Point p[] = PositionGenerators.circle(30 * h * h, 0, 0, nh);
            System.arraycopy(p, 0, r, last, nh);
            last += nh;
        }
        GraphPoint ret[] = new GraphPoint[n];
        for (int i = 0; i < n; i++) {
            ret[i] = new GraphPoint(r[i].x, r[i].y);
        }
        return ret;
    }

    public GraphModel generateGraph() {
        GraphModel g = new GraphModel(false);
        VertexModel[] v = new VertexModel[n];
        EdgeModel[] e = new EdgeModel[n - 1];
        //generating vertices
        for (int i = 0; i < n; i++)
            v[i] = new VertexModel();
        //generating edges
        for (int i = 0; i < n - 1; i++)
            e[i] = new EdgeModel(v[i + 1], v[i / 2]);

        g.insertVertices(v);
        g.insertEdges(e);

        //generating and setting vertex positions
        GraphPoint[] pos = getVertexPositions();
        for (int i = 0; i < n; i++)
            v[i].setLocation(pos[i]);
        return g;
    }
}
