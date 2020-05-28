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
public class KmnGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {

    @Parameter(name = "M")
    public static Integer m = 3;

    @Parameter(name = "N")
    public static Integer n = 3;

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
        return "Km,n";
    }

    public String getDescription() {
        return "Generate Km,n";
    }

    Vertex[] v;

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[n + m];
        for (int i = 0; i < m + n; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Edge[] ret = new Edge[m * n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                ret[i * m + j] = new Edge(v[i], v[n + j]);
            }
        return ret;
    }

    public Point[] getVertexPositions() {
        int w = 100;
        int h = 100;
        Point[] ret = new Point[m + n];
        Point[] np = PositionGenerators.line(5, h / 4, w, 0, n);
        Point[] mp = PositionGenerators.line(5, 3 * h / 4, w, 0, m);
        System.arraycopy(np, 0, ret, 0, n);
        System.arraycopy(mp, 0, ret, n, m);
        return ret;
    }

    public String checkParameters() {
    	if ( n<0 || m<0)return " Values of both M & N must be positive!";
    	else
    		return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    /**
     * generates a Km,n Graph with given parameters
     */
    public static GraphModel generateKmn(int m, int n) {
        KmnGenerator.m = m;
        KmnGenerator.n = n;
        return GraphGenerator.getGraph(false, new KmnGenerator());
    }

    @Override
    public String getCategory() {
        return "Multipartite Graphs";
    }
}