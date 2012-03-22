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
 * @author Mohammad Ali Rostami
 * @email mamliam@gmail.com
 */

@CommandAttitude(name = "generate_generalized_peterson", abbreviation = "_g_p", description = "generalized peterson")
public class GeneralizedPetersonGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    private GraphModel g;

    @Parameter(name = "n")
    public static Integer n = 5;

    @Parameter(name = "k")
    public static Integer k = 2;
    private VertexModel[] v;

    public String getName() {
        return "Generalized Peterson";
    }

    public String getDescription() {
        return "Generalized Peterson";
    }

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public VertexModel[] getVertices() {
        VertexModel[] ret = new VertexModel[2 * n];
        for (int i = 0; i < 2 * n; i++)
            ret[i] = new VertexModel();
        this.v = ret;
        return ret;
    }

    public EdgeModel[] getEdges() {
        EdgeModel[] ret = new EdgeModel[3 * n];
        int counter = 0;
        for (int i = 0; i < n; i++) {
            ret[counter] = new EdgeModel(v[(i % n)], v[((i + 1) % n)]);
            counter++;
            ret[counter] = new EdgeModel(v[(i % n)], v[((i + n) % (2 * n))]);
            counter++;
            if (i + n + k > 2 * n - 1)
                ret[counter] = new EdgeModel(v[((i + n) % (2 * n))], v[((i + n + k) % (2 * n)) + n]);
            else
                ret[counter] = new EdgeModel(v[((i + n) % (2 * n))], v[((i + n + k) % (2 * n))]);
            counter++;
        }
        return ret;
    }

    public Point[] getVertexPositions() {
        Point[] p = new Point[2 * n];
        Point[] p1 = PositionGenerators.circle(300, 200, 200, n);
        Point[] p2 = PositionGenerators.circle(600, 200, 200, n);
        for (int i = 0; i < n; i++) {
            p[i] = p2[i];
            p[i + n] = p1[i];
        }
        return p;
    }

    public String checkParameters() {
        if (k>0 & n>0 & k > n / 2)
            return "K should be smaller than n/2 !";
        if ( n<0 || k<0)return " Both k & n must be positive!";
        else
            return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    /**
     * generates a Generalized Peterson Graph with given parameters
     */
    public static GraphModel generateGeneralizedPeterson(int n, int k) {
        GeneralizedPetersonGenerator.n = n;
        GeneralizedPetersonGenerator.k = k;
        return GraphGenerator.getGraph(false, new GeneralizedPetersonGenerator());
    }

}
