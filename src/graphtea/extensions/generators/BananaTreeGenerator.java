// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/
package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.StaticUtils;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.awt.*;

@CommandAttitude(name = "generate_banana_tree", abbreviation = "_g_banana", description = "generates a Banana graph")
public class BananaTreeGenerator implements GraphGeneratorExtension, Parametrizable {

    //the depth should be positive, and also if it is very large the
    //generated graph is too large to generate.
    @Parameter(description = "N")
    public int n = 4;
    @Parameter(description = "K")
    public int k = 4;      //num of each star vertices


    public String checkParameters() {
        if( n<0 || k<0 )return " Both k & n must be positive!";
        else
            return null;    //the parameters are well defined.
    }

    public String getName() {
        return "Banana  tree";
    }

    public String getDescription() {
        return "generates a banana tree with n k-stars";
    }

    public GraphModel generateGraph() {
        return generateBananaTree(n, k);
    }

    private static GraphModel generateBananaTree(int n, int k) {
        //num of tree vertices
        GraphModel g = new GraphModel(false);
        Vertex root = new Vertex();
        g.insertVertex(root);
        root.setLocation(new GPoint(0, 0));
        Vertex curv;
        //generating edges and setting positions
        Point[] fR = PositionGenerators.circle(3000, 0, 0, n);
        Vertex[] firstDepth = new Vertex[n];

        //generating first level vertices
        for (int i = 0; i < n; i++) {
            Point center = fR[i];
            curv = new Vertex();
            setloc(curv, center);
            firstDepth[i] = curv;
            g.insertVertex(curv);
            g.insertEdge(new Edge(root, curv));
        }

        //generating second level vertices
        for (int i = 0; i < n; i++) {
            Point center = fR[i];
            Vertex centerv = firstDepth[i];

            Point[] sR;
            sR = PositionGenerators.circle(1000, center.x, center.y, k);
            for (int j = 0; j < k; j++) {
                if(j == (i + k/2)%n) continue;
                curv = new Vertex();
                g.insertVertex(curv);
                setloc(curv, sR[j]);
                g.insertEdge(new Edge(centerv, curv));
            }
        }
        return g;
    }

    private static void setloc(Vertex vv, Point gp) {
        vv.setLocation(new GPoint(gp.x, gp.y));
    }


    public static void main(String[] args) {
        graphtea.platform.Application.main(args);
        StaticUtils.loadSingleExtension(BananaTreeGenerator.class);

    }

    @Override
    public String getCategory() {
        return "Trees";
    }
}