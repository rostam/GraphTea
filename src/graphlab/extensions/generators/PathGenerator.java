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
 * @author azin azadi

 */
@CommandAttitude(name = "generate_pn", abbreviation = "_g_pn")
public class PathGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "N")
    public static Integer n = 10;
    VertexModel[] v;
    GraphModel g;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public String getName() {
        return "Path";
    }

    public String getDescription() {
        return "Generates a path with n vertices";
    }

    public VertexModel[] getVertices() {

        VertexModel[] ret = new VertexModel[n];
        for (int i = 0; i < n; i++)
            ret[i] = new VertexModel();
        v = ret;
        return ret;
    }

    public EdgeModel[] getEdges() {
        EdgeModel[] ret = new EdgeModel[n - 1];
        for (int i = 0; i < n - 1; i++) {
            ret[i] = new EdgeModel(v[i], v[i + 1]);
        }
        return ret;
    }

    public Point[] getVertexPositions() {
        return PositionGenerators.line(5, 5, 10000, 10000, n);
    }

    public String checkParameters() {
    	if (n < 0) return "n must be positive";
        else
            return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }


    /**
     * generates a Path Graph with given parameters
     */
    public static GraphModel generatePath(int n) {
        PathGenerator.n = n;
        return GraphGenerator.getGraph(false, new PathGenerator());
    }
}
