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
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

/**
 * Author: Ali Rostami
 *
 * https://mathworld.wolfram.com/CrownGraph.html
 * 
 */
@CommandAttitude(name = "generate_crown", abbreviation = "_g_crown",
        description = "generates a Crown graph of order n")
public class CrownGraph implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {

	@Parameter(name = "n")
	public static int n = 3;

	public String getName()
	{
		return "Crown Graph";
	}

	public String getDescription()
	{
		return "Generate Crown Graph";
	}

	Vertex[] v;

	public Vertex[] getVertices()
	{
		Vertex[] ret = new Vertex[n + n];
		for (int i = 0; i < n + n; i++)
			ret[i] = new Vertex();
		v = ret;
		return ret;
	}

	public Edge[] getEdges() {
		Edge[] ret = new Edge[n * (n-1)];
		int cnt = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (i != j) {
					ret[cnt++] = new Edge(v[i], v[n + j]);
				}
			}
		return ret;
	}

	public GPoint[] getVertexPositions()
	{
		int w = 100;
		int h = 100;
		GPoint[] ret = new GPoint[n + n];
		GPoint[] np = PositionGenerators.line(5, h / 4, w, 0, n);
		GPoint[] mp = PositionGenerators.line(5, 3 * h / 4, w, 0, n);
		System.arraycopy(np, 0, ret, 0, n);
		System.arraycopy(mp, 0, ret, n, n);
		return ret;
	}

	public String checkParameters() {
		if (n < 3) return "n must be higher than 2!";
		else
			return null;
	}

	public GraphModel generateGraph()
	{
		return GraphGenerator.getGraph(false, this);
	}

	/**
	 * generates a Helm Graph with given parameters
	 */
	public static GraphModel generateCrown(int n)
	{
		CrownGraph.n = n;
		return GraphGenerator.getGraph(false, new CrownGraph());
    }

public static void main(String[] args) {
        graphtea.platform.Application.main(args);
//        StaticUtils.loadSingleExtension(graphtea.samples.extensions.HelmGraph.class);
    }

    @Override
    public String getCategory() {
        return "Web Class Graphs";
    }
}