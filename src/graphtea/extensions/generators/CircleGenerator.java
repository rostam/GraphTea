// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.PositionGenerators;

import java.awt.*;

/**
 * @author azin azadi

 */
@CommandAttitude(name = "generate_cn", abbreviation = "_g_cn", description = "generated a Circle with n vertices")
public class CircleGenerator extends PathGenerator {

    public String getName() {
        return "Circle";
    }

    public String getDescription() {
        return "Genrates a Circle(Cn)";
    }

    @Override
    public Edge[] getEdges() {
        Edge[] pre = super.getEdges();
        int l = pre.length;
        Edge[] ret = new Edge[l + 1];
        System.arraycopy(pre, 0, ret, 0, l);
        ret[l] = new Edge(v[l], v[0]);
        return ret;
    }

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[n];
        for (int i = 0; i < n; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    public Point[] getVertexPositions() {
        return PositionGenerators.circle(5, 5, 100, 100, n);
    }

    /**
     * generates a Circle Graph with given parameters
     */
    public static GraphModel generateCircle(int n) {
        CircleGenerator.n = n;
        return GraphGenerator.getGraph(false, new CircleGenerator());
    }

}
