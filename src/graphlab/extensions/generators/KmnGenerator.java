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
 * User: root
 */
@CommandAttitude(name = "generate_kmn", abbreviation = "_g_kmn", description = "generates a 2partite complete graph")
public class KmnGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {

    @Parameter(name = "M")
    public static Integer m = 3;

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
        return "Km,n";
    }

    public String getDescription() {
        return "Generate Km,n";
    }

    VertexModel[] v;

    public VertexModel[] getVertices() {
        VertexModel[] ret = new VertexModel[n + m];
        for (int i = 0; i < m + n; i++)
            ret[i] = new VertexModel();
        v = ret;
        return ret;
    }

    public EdgeModel[] getEdges() {
        EdgeModel[] ret = new EdgeModel[m * n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                ret[i * m + j] = new EdgeModel(v[i], v[n + j]);
            }
        return ret;
    }

    public Point[] getVertexPositions() {
        int w = 100;
        int h = 100;
        Point ret[] = new Point[m + n];
        Point np[] = PositionGenerators.line(5, h / 4, w, 0, n);
        Point mp[] = PositionGenerators.line(5, 3 * h / 4, w, 0, m);
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

}