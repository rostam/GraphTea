package graphtea.extensions.generators;
// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

@CommandAttitude(name = "generate_helmn", abbreviation = "_g_prism", description = "generates a Prism graph of order n")
public class AntiprismGraph implements GraphGeneratorExtension, Parametrizable {

    //the depth should be positive, and also if it is very large the
    //generated graph is too large to generate.
    //@Parameter(description = "depth of the tree")
    //public BoundedInteger depth = new BoundedInteger(3, 15, 1);
    @Parameter(name = "n",description = "Num of vertices")
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
        return "Antiprism Graph";
    }

    public String getDescription() {
        return "Antiprism Graph";
    }

    public GPoint[] getVertexPositions() {
       return PrismGraph.computePrismCoords(n);
    }


    public GraphModel generateGraph() {
        GraphModel g = new GraphModel(false);
        Vertex[] v = new Vertex[2 * n];
        Edge[] e = new Edge[4 * n];

        //generating vertices
        for (int i = 0; i < 2 * n; i++) {
            v[i] = new Vertex();
        }
        g.insertVertices(v);


        int cnt =0;
        //generating edges
        for (int i = 0; i < n; i++) {
            e[cnt] = new Edge(v[i], v[(i + 1) % n]);
            cnt++;
            if ((i + n + 1) == 2 * n) e[cnt] = new Edge(v[i + n], v[n]);
            else e[cnt] = new Edge(v[i + n], v[i + n + 1]);
            cnt++;
            e[cnt] = new Edge(v[i], v[(i+n)]);
            cnt++;
            e[cnt] = new Edge(v[(i + 1) % n], v[i+n]);
            cnt++;
        }

        g.insertEdges(e);

        //generating and setting vertex positions
        GPoint[] pos = getVertexPositions();
        for (int i = 0; i < 2*n; i++)
            v[i].setLocation(pos[i]);
        return g;
    }

    @Override
    public String getCategory() {
        return "Prism Graphs";
    }
}