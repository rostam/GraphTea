// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.util.Vector;

/**
 * @author  M. Ali Rostami
 */
@CommandAttitude(name = "generate_sudoko", abbreviation = "_g_kn", description = "Generates a Sudoko with n vertices")
public class Sudoko implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    GraphModel g;
    @Parameter(name = "n")
    public static Integer n = 9;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    Vertex[] v;

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[n*n];
        for (int i = 0; i < n*n; i++) {
            ret[i] = new Vertex();
            ret[i].setLabel("F" + (i + 1));
        }
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Vector<Edge> vs = new Vector<>();

        for(int k=0;k < n;k++) {
            for (int i = k * n; i < (k + 1) * n; i++)
                for (int j = k * n; j < i; j++)
                    vs.add(new Edge(v[i], v[j]));
        }


        for (int k = 0; k < Math.sqrt(n)-1; k++) {
            for (int i = 0; i < Math.sqrt(n); i++) {
                for (int j = 0; j < n; j++) {
                    vs.add(new Edge(v[(int) (Math.sqrt(n) * n * i + j + n*k)], v[(int) (Math.sqrt(n) * n * i + j + n + n*k)]));
                }
            }
        }

        for (int i = 0; i < Math.sqrt(n) * n * (Math.sqrt(n) - 1); i++) vs.add(new Edge(v[i], v[(int) (i + Math.sqrt(n) * n)]));
        for (int i = 0; i < Math.sqrt(n); i++) {
        }

        Edge[] ret = new Edge[vs.size()];
        for (int i = 0; i < vs.size(); i++) ret[i] = vs.get(i);
        return ret;
    }

    public GPoint[] getVertexPositions() {
        Vector<GPoint> vs = new Vector<>();
        int baseX = 210;int distX = 60;
        int baseY = 210;int distY = 60;

        for(int k1=0;k1 < Math.sqrt(n);k1++) {
            for (int k2 = 0; k2 < Math.sqrt(n); k2++) {
                for (int i = 0; i < Math.sqrt(n); i++) {
                    for (int j = 0; j < Math.sqrt(n); j++) {
                        vs.add(new GPoint(k2*baseX + j * distX, k1*baseY + i * distY));
                    }
                }
            }
        }

        GPoint[] ret = new GPoint[vs.size()];
        for(int i=0;i<vs.size();i++) ret[i] = vs.get(i);
        return ret;
    }

    public String getName() {
        return "Sudoku Graph";
    }

    public String getDescription() {
        return "Generates a Sudoku Graph";
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
     * generates a Complete Graph with given parameters
     */
    public static GraphModel generateSudokoGraph(int n) {
        Sudoko.n = n;
        return GraphGenerator.getGraph(false, new Sudoko());
    }

    @Override
    public String getCategory() {
        return "General Graphs";
    }
}