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
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.awt.*;

/**
 * Author: Mohsen Khaki
 * 
 */
@CommandAttitude(name = "generate_helmn", abbreviation = "_g_helmn", description = "generates a Helm graph of order n")
public class HelmGraph implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {

	@Parameter(name = "n")
	public static int n = 3;

    @Parameter(name = "closed")
    public static boolean closed = false;

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

	Vertex[] v;

	public Vertex[] getVertices()
	{
		Vertex[] result = new Vertex[2*n+1];
		for (int i = 0; i < 2*n+1; i++)
			result[i] = new Vertex();
		v = result;
		return result;
	}

	public Edge[] getEdges()
	{
		Edge[] result;
        if(!closed) result = new Edge[3*n];
        else result = new Edge[3*n+n];
        int ecnt = 0;
		for (int i = 0; i < n; i++)
		{
			result[ecnt] = new Edge(v[i], v[n+i]);
            ecnt++;
			result[ecnt] = new Edge(v[n+i], v[2*n]);
            ecnt++;
			result[ecnt] = new Edge(v[n+i],v[n+((i+1)%n)]);
            ecnt++;
            if(closed) {
                result[ecnt] = new Edge(v[i],v[((i+1)%n)]);
                ecnt++;
            }
		}

        return result;
	}

	public Point[] getVertexPositions()
	{
		int w = 1000;
		double mw = ((double)w)/2.0, qw = ((double)w)/4.0;
		Point result[] = new Point[2*n+1];
		result[2*n] = new Point(w/2, w/2);
		double ang = Math.PI*2.0/n;
		double offset = 0.0;
		if ((n % 2) == 0)
			offset = ang/2.0; 
		for ( int i = 0 ; i < n ; i++ )
		{
			double angle = offset + i * ang;
			result[i] = new Point((int)(mw + Math.sin(angle)* mw), (int)(mw - Math.cos(angle)* mw));
			result[n+i] = new Point((int)(mw + Math.sin(angle)* qw), (int)(mw - Math.cos(angle)* qw));
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
        graphtea.platform.Application.main(args);
//        StaticUtils.loadSingleExtension(graphtea.samples.extensions.HelmGraph.class);
    }

    @Override
    public String getCategory() {
        return "Web Class Graphs";
    }
}