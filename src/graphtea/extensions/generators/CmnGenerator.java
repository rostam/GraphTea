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
@CommandAttitude(name = "generate_cmn", abbreviation = "_g_cmn", description = "generates Cm * Cn")
public class CmnGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "M")
    public static Integer m = 3;

    @Parameter(name = "N")
    public static Integer n = 5;
    GraphModel g;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
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
        Edge[] ret = new Edge[2 * m * n];
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j > m - 2) ret[counter] = new Edge(v[i * m + j], v[i * m]);
                else ret[counter] = new Edge(v[i * m + j], v[i * m + j + 1]);
                counter++;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i > n - 2) ret[counter] = new Edge(v[i * m + j], v[j]);
                else ret[counter] = new Edge(v[i * m + j], v[(i + 1) * m + j]);
                counter++;
            }
        }


        return ret;
    }

    public Point[] getVertexPositions() {
        Point ret[] = new Point[m * n];
        Point centerPoints[] = PositionGenerators.circle(0, 0, m * n, m * n, n);
        for (int i = 0; i < n; i++) {
            Point p[] = PositionGenerators.circle(m, centerPoints[i].x, centerPoints[i].y, m);
            System.arraycopy(p, 0, ret, i * m, m);
        }


        return ret;
    }

    public String getName() {
        return "Cm * Cn";
    }

    public String getDescription() {
        return "Generate Cm*Cn";
    }

    public String checkParameters() {
    	if ( n<0 || m<0)return"Both m & n must be positive!";
    	else
    		return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    /**
     * generates a Cm*Cn Graph with given parameters
     */
    public static GraphModel generateCmn(int m, int n) {
        CmnGenerator.m = m;
        CmnGenerator.n = n;
        return GraphGenerator.getGraph(false, new CmnGenerator());
    }

    @Override
    public String getCategory() {
        return "General Graphs";
    }
}

