package graphlab.extensions.generators;
// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.lang.CommandAttitude;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.graphgenerator.core.PositionGenerators;
import graphlab.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.awt.*;

@CommandAttitude(name = "generate_helmn", abbreviation = "_g_prism", description = "generates a Prism graph of order n")
public class PrismGraph implements GraphGeneratorExtension, Parametrizable {

    //the depth should be positive, and also if it is very large the
    //generated graph is too large to generate.
    //@Parameter(description = "depth of the tree")
    //public BoundedInteger depth = new BoundedInteger(3, 15, 1);
    @Parameter(description = "Num of vertices")
    public static int n = 4;      //num of vertices


    public String checkParameters() {
        //d = depth.getValue();
        //n = (int) (Math.pow(2, d + 1) - 1);
        //return null;    //the parameters are well defined.
    	//////// !!! check it later!!!
    	//if (n<4)return "n must be higher than 4!!! For more information check: 'http://mathworld.wolfram.com/CubicalGraph.html'";
    	//else 
    	if(n<0)return "n must be positive!";
    	else
    		return null;
    }

    public String getName() {
        return "Prism Graph";
    }

    public String getDescription() {
        return "generates a binary tree";
    }

    public GraphPoint[] getVertexPositions() {
        Point[] r = new Point[2 * n];

        Point p1[] = PositionGenerators.circle(20000, 10000, 10000,  n);
        Point p2[] = PositionGenerators.circle(30000, 10000, 10000,  n);
        System.out.println(p2[0]);
        for (int i = 0; i < n; i++) {
            r[i] = p1[i];
            r[n + i] = p2[i];
        }

        GraphPoint ret[] = new GraphPoint[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            ret[i] = new GraphPoint(r[i].x, r[i].y);
        }
        return ret;
    }

    public GraphModel generateGraph() {
        GraphModel g = new GraphModel(false);
        VertexModel[] v = new VertexModel[2 * n];
        EdgeModel[] e = new EdgeModel[3 * n];

        //generating vertices
        for (int i = 0; i < 2 * n; i++)
            v[i] = new VertexModel();

        //generating edges
        for (int i = 0; i < n; i++) {
            e[i] = new EdgeModel(v[i], v[(i + 1) % n]);
            if ((i + n + 1) == 2 * n) e[i + n] = new EdgeModel(v[i + n], v[n]);
            else e[i + n] = new EdgeModel(v[i + n], v[i + n + 1]);
            e[i + (2 * n)] = new EdgeModel(v[i], v[(i + n)]);
        }

        g.insertVertices(v);
        g.insertEdges(e);

        //generating and setting vertex positions
        GraphPoint[] pos = getVertexPositions();
        for (int i = 0; i < 2*n; i++)
            v[i].setLocation(pos[i]);
        return g;
    }
}