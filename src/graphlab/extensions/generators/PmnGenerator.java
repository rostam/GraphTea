// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.extensions.generators;

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

    VertexModel[] v;

    public VertexModel[] getVertices() {
        VertexModel[] ret = new VertexModel[n * m];
        for (int i = 0; i < m * n; i++)
            ret[i] = new VertexModel();
        v = ret;
        return ret;
    }

    public EdgeModel[] getEdges() {
        EdgeModel[] ret = new EdgeModel[(n * (m - 1) + m * (n - 1))];
        int counter = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                if (j < m - 1) {
                    ret[counter] = new EdgeModel(v[i * m + j], v[i * m + j + 1]);
                    counter++;
                }
                if (i < n - 1) {
                    ret[counter] = new EdgeModel(v[i * m + j], v[(i + 1) * (m) + j]);
                    counter++;
                }
            }
        return ret;
    }

    public Point[] getVertexPositions() {
        int w = 20000;
        int h = 20000;
        Point ret[] = new Point[m * n];
        int yoffset = h - 30 * n;
        for (int i = 0; i < n; i++) {
            yoffset += 50;
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
}