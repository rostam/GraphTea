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
 * User: root
 */
@CommandAttitude(name = "generate_kmn", abbreviation = "_g_kmn", description = "generates a 2partite complete graph")
public class CocktailPartyGraph implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {

    @Parameter(name = "N")
    public static Integer n = 3;
    GraphModel g;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

//    public NotifiableAttributeSet getParameters() {
//        PortableNotifiableAttributeSetImpl a=new PortableNotifiableAttributeSetImpl();
//        a.put("M",10);
//        a.put("N",4);
//        return a;
//    }
//
//    public void setParameters(NotifiableAttributeSet parameters) {
//        m = Integer.parseInt(""+parameters.getAttributes().get("M"));
//        n = Integer.parseInt(""+parameters.getAttributes().get("N"));
//    }

    public String getName() {
        return "CockTail Party Graph";
    }

    public String getDescription() {
        return "CockTail Party Graph";
    }

    Vertex[] v;

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[n + n];
        for (int i = 0; i < n + n; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Edge[] ret = new Edge[3* (n-1) * n];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                ret[cnt] = new Edge(v[i], v[n + j]);
                cnt++;

                ret[cnt] = new Edge(v[i],v[j]);
                cnt++;

                ret[cnt] = new Edge(v[n+i],v[n+j]);
                cnt++;
            }
        }

        return ret;
    }

    public Point[] getVertexPositions() {
        return PositionGenerators.circle(5, 5, 100000, 100000, 2*n);
    }

    public String checkParameters() {
    	return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    @Override
    public String getCategory() {
        return "General Graphs";
    }
}