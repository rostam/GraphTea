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
import java.util.Vector;

/**
 * User: root
 */
@CommandAttitude(name = "generate_reg", abbreviation = "_g_reg", description = "generates a regular graph")
public class RegularGraphGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {

    @Parameter(name = "n")
    public static Integer n = 6;

    @Parameter(name = "degree")
    public static Integer deg = 3;
    GraphModel g;



    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

    public String getName() {
        return "Regular Graph";
    }

    public String getDescription() {
        return "Generate Regular Graph";
    }

    Vertex[] v;

    public Vertex[] getVertices() {
        Vertex[] ret = new Vertex[n];
        for (int i = 0; i < n; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        Edge[] ret = new Edge[(n*deg) / 2];
        int t = 0;
        Vector<String> cach = new Vector<>();
        if(deg%2 == 0) {
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < i+(deg / 2) + 1; j++) {
                    if(i != j) {
                        ret[t++] = new Edge(v[i], v[j % n]);
                    }
                }
            }
        } else {
            for (int i = 0; i < n; i=i+1) {
                for (int j = i + 1; j < i + ((deg - 1) / 2) + 1 ; j++) {
                    if(!cach.contains(i+","+j%n)) {
                        Edge e = new Edge(v[i], v[j % n]);
                        ret[t++] = e;
                        cach.add(i+","+j%n);
                        cach.add(j%n+","+i);
                    }
                }

                for (int j = i - 1; j > i - ((deg - 1) / 2) - 1; j--) {
                    if(!cach.contains(i+","+(j + n) % n)) {
                        Edge e = new Edge(v[i], v[(j + n) % n]);
                        ret[t++] = e;
                        cach.add(i+","+(j + n) % n);
                        cach.add((j + n) % n+","+i);
                    }
                }
            }

            for (int i = 0; i < n/2; i++) {
                ret[t++] = new Edge(v[i], v[(i+(n/2))%n]);
            }
        }

        return ret;
    }


    public Point[] getVertexPositions() {
        return PositionGenerators.circle(5, 5, 100000, 100000, n);
    }


    public String checkParameters() {
    	if ( n<=0 || deg<=0)return " Values of both M & N must be positive!";
    	else
    		return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    @Override
    public String getCategory() {
        return "Regular Graphs";
    }
}
