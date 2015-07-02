// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.StaticUtils;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.awt.*;

/**
 * Author: Saman Feghhi
 * 
 */
@CommandAttitude(name = "generate_kmno", abbreviation = "_g_kmno", description = "generates a Tripartite complete graph")
public class KmnoGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface
{

	@Parameter(name = "M")
	public static int m = 3;

	@Parameter(name = "N")
	public static int n = 3;

	@Parameter(name = "O")
	public static int o = 3;

	GraphModel g;

	public void setWorkingGraph(GraphModel g)
	{
		this.g = g;
	}

	public String getName()
	{
		return "Km,n,o";
	}

	public String getDescription()
	{
		return "Generate Km,n,o";
	}

	Vertex[] v;

	public Vertex[] getVertices()
	{
		Vertex[] ret = new Vertex[m + n + o];
		for (int i = 0; i < m + n + o; i++)
			ret[i] = new Vertex();
		v = ret;
		return ret;
	}

	public Edge[] getEdges()
	{
		Edge[] ret = new Edge[m * n + m * o + n * o];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n + o; j++)
				ret[i * ( n + o ) + j] = new Edge(v[i], v[m + j]);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < o; j++)
				ret[m * ( n + o ) + o * i + j] = new Edge(v[m + i], v[m + n + j]);
		return ret;
	}

	public Point[] getVertexPositions()
	{
		Point ret[] = new Point[m + n + o];
		int dx, dy;

		if(m == 1)
			ret[0] = new Point(150, 260);
		else
		{
			dx = -300/(m - 1);
			dy = 520/(m - 1);
			for (int i = 0; i < m; i++)
				ret[i] = new Point(300 + i * dx, i * dy);
		}

		if(n == 1)
			ret[m] = new Point(850, 260);
		else
		{
			dx = 300/(n - 1);
			dy = 520/(n - 1);
			for (int i = 0; i < n; i++)
				ret[m + i] = new Point(700 + i * dx, i * dy);
		}

		if (o == 1)
			ret[m + n] = new Point(500, 800);
		else
		{
			dx = 600/(o - 1);
			dy = 0;
			for (int i = 0; i < o; i++)
				ret[m + n + i] = new Point(200 + i * dx, 800 + i * dy);
		}

		return ret;
	}

	public String checkParameters(){
		if (n<0 || m<0 || o<0)return "All values must be positive!";
		else
			return null;
	}

	public GraphModel generateGraph()
	{
		return GraphGenerator.getGraph(false, this);
	}

	/**
	 * generates a Km,n,o Graph with given parameters
	 */
	public static GraphModel generateKmno(int m, int n, int o)
	{
		KmnoGenerator.m = m;
		KmnoGenerator.n = n;
		KmnoGenerator.o = o;
		return GraphGenerator.getGraph(false, new KmnoGenerator());
	}

    public static void main(String[] args) {
        graphtea.platform.Application.main(args);
        StaticUtils.loadSingleExtension(KmnoGenerator.class);

    }

    @Override
    public String getCategory() {
        return "Multipartite Graphs";
    }
}