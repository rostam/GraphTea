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
@CommandAttitude(name = "generate_gear", abbreviation = "_g_g"
        , description = "generate a n vertices gear graph")
public class GearGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    private GraphModel g;
    @Parameter(name = "n")
    public static Integer n = 5;

    private Vertex[] v;

    public String getName() {
        return "Gear Graph";
    }

    public String getDescription() {
        return "Gear Graph";
    }

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[2*n+1];
        for (int i = 0; i < 2*n+1; i++)
            ret[i] = new Vertex();
        this.v = ret;
        return ret;
    }

    public Edge[] getEdges() {

        Edge[] ret = new Edge[3*n];
        int ecnt = 0;

        for(int i=1;i<=2*n-1;i++){
            ret[ecnt] = new Edge(v[i], v[i + 1]);
            ecnt++;
        }

        ret[ecnt] = new Edge(v[2*n],v[1]);
        ecnt++;

        for(int i=1;i<=2*n;i=i+2) {
            ret[ecnt] = new Edge(v[0],v[i]);
            ecnt++;
        }

        return ret;
    }

    public Point[] getVertexPositions() {
        Point p[] = new Point[2*n+1];
        Point p1[] = PositionGenerators.circle(50000, 200, 200, 2*n);
        p[0] = new Point(200, 200);
        System.arraycopy(p1, 0, p, 1, 2 * n);
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
    public static GraphModel generateGear(int n) {
        GearGenerator.n = n;
        return GraphGenerator.getGraph(false, new GearGenerator());
    }

    @Override
    public String getCategory() {
        return "Web Class Graphs";
    }
}
