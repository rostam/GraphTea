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
 * User: Houshmand
 */
@CommandAttitude(name = "generate_pmn", abbreviation = "_g_pmn", description = "generates Pm*Pn")
public class PmnGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "M")
    public static Integer m = 10;
    @Parameter(name = "N")
    public static Integer n = 4;
    GraphModel g;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public String getName() {
        return "Pm * Pn";
    }

    public String getDescription() {
        return "Generate Pm*Pn";
    }

    Vertex[] v;

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[n * m];
        for (int i = 0; i < m * n; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Edge[] ret = new Edge[(n * (m - 1) + m * (n - 1))];
        int counter = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                if (j < m - 1) {
                    ret[counter] = new Edge(v[i * m + j], v[i * m + j + 1]);
                    counter++;
                }
                if (i < n - 1) {
                    ret[counter] = new Edge(v[i * m + j], v[(i + 1) * (m) + j]);
                    counter++;
                }
            }
        return ret;
    }

    public Point[] getVertexPositions() {
        int w = 20000;
        int h = 20000;
        Point ret[] = new Point[m * n];
        for (int i = 0; i < n; i++) {
            Point p[] = PositionGenerators.line(5, h * (i + 1) / (n + 1), w, 0, m);
            System.arraycopy(p, 0, ret, i * m, m);

        }
        return ret;
    }

    public String checkParameters() {
    	if(n<0 || m<0)return "Both m & n must be positive!";
    	else
    		return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }


    /**
     * generates a Pm*Pn Graph with given parameters
     */
    public static GraphModel generatePmn(int m, int n) {
        PmnGenerator.m = m;
        PmnGenerator.n = n;
        return GraphGenerator.getGraph(false, new PmnGenerator());
    }

    @Override
    public String getCategory() {
        return "General Graphs";
    }
}