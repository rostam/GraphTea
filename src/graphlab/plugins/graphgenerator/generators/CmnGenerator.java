// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.graphgenerator.generators;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.platform.lang.CommandAttitude;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.graphgenerator.GraphGenerator;
import graphlab.plugins.graphgenerator.core.PositionGenerators;
import graphlab.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphlab.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

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

    VertexModel[] v;

    public VertexModel[] getVertices() {
        VertexModel[] ret = new VertexModel[n * m];
        for (int i = 0; i < m * n; i++)
            ret[i] = new VertexModel();
        v = ret;
        return ret;
    }

    public EdgeModel[] getEdges() {
        EdgeModel[] ret = new EdgeModel[2 * m * n];
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j > m - 2) ret[counter] = new EdgeModel(v[i * m + j], v[i * m]);
                else ret[counter] = new EdgeModel(v[i * m + j], v[i * m + j + 1]);
                counter++;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i > n - 2) ret[counter] = new EdgeModel(v[i * m + j], v[j]);
                else ret[counter] = new EdgeModel(v[i * m + j], v[(i + 1) * m + j]);
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
}

