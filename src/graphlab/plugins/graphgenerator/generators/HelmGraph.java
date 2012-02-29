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
import graphlab.platform.StaticUtils;
import graphlab.plugins.graphgenerator.GraphGenerator;
import graphlab.plugins.graphgenerator.core.PositionGenerators;
import graphlab.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphlab.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.awt.*;

/**
 * Author: Mohsen Khaki
 * 
 */
@CommandAttitude(name = "generate_helmn", abbreviation = "_g_helmn", description = "generates a Helm graph of order n")
public class HelmGraph implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {

	@Parameter(name = "n")
	public static int n = 3;

	GraphModel g;

	public void setWorkingGraph(GraphModel g)
	{
		this.g = g;
	}

	public String getName()
	{
		return "Helm Graph";
	}

	public String getDescription()
	{
		return "Generate Helm Graph";
	}

	VertexModel[] v;

	public VertexModel[] getVertices()
	{
		VertexModel[] result = new VertexModel[2*n+1];
		for (int i = 0; i < 2*n+1; i++)
			result[i] = new VertexModel();
		v = result;
		return result;
	}

	public EdgeModel[] getEdges()
	{
		EdgeModel[] result = new EdgeModel[3*n];
		for (int i = 0; i < n; i++)
		{
			result[i] = new EdgeModel(v[i], v[n+i]);
			result[n+i] = new EdgeModel(v[n+i], v[2*n]);
			result[2*n+i] = new EdgeModel(v[n+i],v[n+((i+1)%n)]);
		}
		return result;
	}

	public Point[] getVertexPositions()
	{
		int w = 1000;
		double mw = ((double)w)/2.0, qw = ((double)w)/4.0;
		Point result[] = new Point[2*n+1];
		result[2*n] = new Point((int)(w/2), (int)(w/2));
		double r1 = mw;
		double r2 = qw;
		double ang = Math.PI*2.0/n;
		double offset = 0.0;
		if ((n % 2) == 0)
			offset = ang/2.0; 
		for ( int i = 0 ; i < n ; i++ )
		{
			double angle = offset + i * ang;
			result[i] = new Point((int)(mw + Math.sin(angle)*r1), (int)(mw - Math.cos(angle)*r1));
			result[n+i] = new Point((int)(mw + Math.sin(angle)*r2), (int)(mw - Math.cos(angle)*r2));
		}
		return result;
	}

	public String checkParameters(){
		if( n<3)return "n must be higher than 2!";
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
	public static GraphModel generateHelm(int n)
	{
		HelmGraph.n = n;
		return GraphGenerator.getGraph(false, new HelmGraph());
    }

public static void main(String[] args) {
        graphlab.platform.Application.main(args);
//        StaticUtils.loadSingleExtension(graphlab.samples.extensions.HelmGraph.class);
    }
}