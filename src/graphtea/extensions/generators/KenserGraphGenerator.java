// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.awt.*;
import java.util.Vector;

/**
 *
 */

@CommandAttitude(name = "generate_knd", abbreviation = "_g_knd")
public class KenserGraphGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "D")
    public static Integer d = 3;
    @Parameter(name = "N")
    public static Integer n = 4;
    GraphModel g;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    Vertex[] v;

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[n];
        for (int i = 0; i < n; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Vector<Edge> ret = new Vector<>();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < (Math.min(n, i + d)); j++) {
                ret.add(new Edge(v[i], v[j]));
            }
        }
        Edge[] ret1 = new Edge[ret.size()];

        for (int i = 0; i < ret.size(); i++) {
            ret1[i] = ret.get(i);
        }

        return ret1;
    }

    public Point[] getVertexPositions() {
        return PositionGenerators.circle(5, 5, 100, 100, n);
    }

    public String getName() {
        return "K(n/d)";
    }

    public String getDescription() {
        return "Generate K(n/d)";
    }

    public String checkParameters() {
    	if( d<0 || n<0)return "Both N & D must be positive!";
    	if(d>0 & n>0 & d>n)return " D must be smaller than N!";
    	else
    		return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    /**
     * generates a kenser Graph with given parameters
     */
    public static GraphModel generateKenserGraph(int n, int d) {
        KenserGraphGenerator.n = n;
        KenserGraphGenerator.d = d;
        return GraphGenerator.getGraph(false, new KenserGraphGenerator());
    }


    @Override
    public String getCategory() {
        return "General Graphs";
    }
}