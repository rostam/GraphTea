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

/**
 * @author rostam
 */
@CommandAttitude(name = "generate_cn", abbreviation = "_g_cn",
        description = "generated a Tadpole")
public class TadpoleGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "n")
    public static Integer n = 10;

    @Parameter(name = "k")
    public static Integer k = 10;

    Vertex[] v;
    GraphModel g;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public String getName() {
        return "Tadpole Graph";
    }

    public String getDescription() {
        return "Genrates a Tadpole";
    }

    @Override
    public Edge[] getEdges() {
        Edge[] ret = new Edge[n+k];
        for (int i = 0; i < n - 1; i++) {
            ret[i] = new Edge(v[i], v[i + 1]);
        }
        ret[n-1]=new Edge(v[n-1],v[0]);
        ret[n]  = new Edge(v[1],v[n]);
        for(int i=n+1;i<n+k;i++) {
            ret[i] = new Edge(v[i-1],v[i]);
        }
        return ret;
    }

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[n+k];
        for (int i = 0; i < n+k; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    public Point[] getVertexPositions() {
        Point[] ret = new Point[n+k];
        Point[] circ = PositionGenerators.circle(5, 5, 100, 100, n);
        System.arraycopy(circ, 0, ret, 0, n);
        for(int i=n;i<n+k;i++) {
            ret[i] = new Point(ret[n-1].x+(i-n+1)*60+100,ret[n-1].y);
        }

        return ret;
    }

    public String checkParameters() {
        if (n < 0) return "n must be positive";
        else
            return null;
    }


    /**
     * generates a Tadpole Graph with given parameters
     */
    public static GraphModel generateTadpole(int n,int k) {
        TadpoleGenerator.n = n;
        TadpoleGenerator.k = k;
        return GraphGenerator.getGraph(false, new TadpoleGenerator());
    }

    @Override
    public String getCategory() {
        return "General Graphs";
    };
}
