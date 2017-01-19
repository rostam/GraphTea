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
 * Author: M. Ali Rostami
 * 
 */
@CommandAttitude(name = "generate_webgraph", abbreviation = "_g_webg", description = "generates a Web graph of order n")
public class WebGraph implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {

	@Parameter(name = "n")
	public static int n = 4;
    @Parameter(name = "t")
    public static int t = 2;

	GraphModel g;

	public void setWorkingGraph(GraphModel g)
	{
		this.g = g;
	}

	public String getName()
	{
		return "Web Graph";
	}

	public String getDescription()
	{
		return "Generate Web Graph";
	}

	Vertex[] v;

	public Vertex[] getVertices()
	{
		Vertex[] result = new Vertex[((t+1)*n)+1];
		for (int i = 0; i < (t+1)*n+1; i++)
			result[i] = new Vertex();
		v = result;
		return result;
	}

    public Edge[] getEdges()
    {
        Edge[] result = new Edge[(2*t*n)+n];
        int ecnt = 0;

        for(int j=1;j < t+1;j++) {
            for (int i = 0; i < n - 1; i++) {
                    result[ecnt] = new Edge(v[j * n + i], v[(j * n + i + 1)]);
                    ecnt++;
            }
                result[ecnt] = new Edge(v[j * n + n - 1], v[j * n]);
                ecnt++;
        }

        for(int j=0;j < t;j++) {
            for (int i = 0; i < n; i++) {
                result[ecnt] = new Edge(v[j * n + i], v[j * n + i + n]);
                ecnt++;
            }
        }

        for(int i=0;i < n;i++) {
            result[ecnt]=new Edge(v[t*n + i],v[(t+1)*n]);
            ecnt++;
        }

        return result;
    }

    public Point[] getVertexPositions() {
        Point[] r = new Point[((t+1)*n)+1];

        for(int i=t; i >=0 ;i--) {
            Point p[] = PositionGenerators.circle((t-(i-1))*10000, 10000, 10000, n);
            for(int j=0;j < n;j++) {
                r[(i*n)+j] = p[j];
            }
        }
        r[(t+1)*n] = new Point(10000,10000);

        return r;
    }

	public String checkParameters(){
		if(n<3)return "n must be higher than 2!";
        else
			return null;
	}

	public GraphModel generateGraph()
	{
		return GraphGenerator.getGraph(false, this);
	}

	/**
	 * generates a Web Graph with given parameters
	 */
	public static GraphModel generateWeb(int n)
	{
		WebGraph.n = n;
		return GraphGenerator.getGraph(false, new WebGraph());
    }

    @Override
    public String getCategory() {
        return "Web Class Graphs";
    }
}