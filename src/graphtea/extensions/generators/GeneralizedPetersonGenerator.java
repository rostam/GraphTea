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
 * @email mamliam@gmail.com
 */

@CommandAttitude(name = "generate_generalized_peterson", abbreviation = "_g_p", description = "generalized peterson")
public class GeneralizedPetersonGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    private GraphModel g;

    @Parameter(name = "n")
    public static Integer n = 5;

    @Parameter(name = "k")
    public static Integer k = 2;
    private Vertex[] v;

    public String getName() {
        return "Generalized Peterson";
    }

    public String getDescription() {
        return "Generalized Peterson";
    }

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[2 * n];
        for (int i = 0; i < 2 * n; i++)
            ret[i] = new Vertex();
        this.v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Edge[] ret = new Edge[3 * n];
        int counter = 0;
        for (int i = 0; i < n; i++) {
            ret[counter] = new Edge(v[(i % n)], v[((i + 1) % n)]);
            counter++;
            ret[counter] = new Edge(v[(i % n)], v[((i + n) % (2 * n))]);
            counter++;
            if (i + n + k > 2 * n - 1)
                ret[counter] = new Edge(v[((i + n) % (2 * n))], v[((i + n + k) % (2 * n)) + n]);
            else
                ret[counter] = new Edge(v[((i + n) % (2 * n))], v[((i + n + k) % (2 * n))]);
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

    @Override
    public String getCategory() {
        return "General Graphs";
    }
}
