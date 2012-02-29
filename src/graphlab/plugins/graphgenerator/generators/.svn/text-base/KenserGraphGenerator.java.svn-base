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
import java.util.Vector;

/**
 *
 */

@CommandAttitude(name = "generate_knd", abbreviation = "_g_knd")
public class KenserGraphGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "D")
    public static Integer d = 3;
    @Parameter(name = "N")
    public static Integer n = 4;
    GraphModel g;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    VertexModel[] v;

    public VertexModel[] getVertices() {
        VertexModel[] ret = new VertexModel[n];
        for (int i = 0; i < n; i++)
            ret[i] = new VertexModel();
        v = ret;
        return ret;
    }

    public EdgeModel[] getEdges() {
        Vector<EdgeModel> ret = new Vector<EdgeModel>();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < (Math.min(n, i + d)); j++) {
                ret.add(new EdgeModel(v[i], v[j]));
            }
        }
        EdgeModel[] ret1 = new EdgeModel[ret.size()];

        for (int i = 0; i < ret.size(); i++) {
            ret1[i] = ret.get(i);
        }

        return ret1;
    }

    public Point[] getVertexPositions() {
        return PositionGenerators.circle(5, 5, 100, 100, n);
    }

    public String getName() {
        return "K(n/d)";
    }

    public String getDescription() {
        return "Generate K(n/d)";
    }

    public String checkParameters() {
    	if( d<0 || n<0)return "Both N & D must be positive!";
    	if(d>0 & n>0 & d>n)return " D must be smaller than N!";
    	else
    		return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    /**
     * generates a kenser Graph with given parameters
     */
    public static GraphModel generateKenserGraph(int n, int d) {
        KenserGraphGenerator.n = n;
        KenserGraphGenerator.d = d;
        return GraphGenerator.getGraph(false, new KenserGraphGenerator());
    }


}