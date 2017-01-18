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
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */
@CommandAttitude(name = "generate_wheel", abbreviation = "_g_w"
        , description = "generate a n vertices wheel graph")
public class WheelGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    private GraphModel g;
    @Parameter(name = "n")
    public static Integer n = 5;

    private Vertex[] v;

    public String getName() {
        return "Wheel Graph";
    }

    public String getDescription() {
        return "Wheel Graph";
    }

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[n];
        for (int i = 0; i < n; i++)
            ret[i] = new Vertex();
        this.v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Edge[] ret = new Edge[2 * n - 2];
        int counter = 0;
        for (int i = 1; i < n - 1; i++) {
            ret[counter] = new Edge(v[i], v[i + 1]);
            counter++;
        }

        ret[counter] = new Edge(v[n - 1], v[1]);
        counter++;

        for (int i = 1; i < n; i++) {
            ret[counter] = new Edge(v[0], v[i]);
            counter++;
        }
        return ret;
    }

    public Point[] getVertexPositions() {
        Point p[] = new Point[n];
        Point p1[] = PositionGenerators.circle(50000, 200, 200, n - 1);
        Point p2 = new Point(200, 200);
        System.arraycopy(p1, 0, p, 1, n - 1);
        p[0] = p2;
        return p;
    }

    public String checkParameters() {
        //!!! check it!!!
    	if (n<4)return "n must be higher than 4 !!!";
        else
        	return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    /**
     * generates a Wheel Graph with given parameters
     */
    public static GraphModel generateWheel(int n) {
        WheelGenerator.n = n;
        return GraphGenerator.getGraph(false, new WheelGenerator());
    }

    @Override
    public String getCategory() {
        return "Web Class Graphs";
    }
}
