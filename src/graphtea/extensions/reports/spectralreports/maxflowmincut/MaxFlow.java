package graphtea.extensions.reports.spectralreports.maxflowmincut;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

public abstract class MaxFlow {
	
	public double C[][]; 
	public double F[][];
	
	protected GraphModel g;
	protected int n;
	protected Vertex source,sink;
	int s, t;
	protected boolean showResult;
	
	public  MaxFlow(GraphModel g, Vertex source, Vertex sink, boolean showResult) {
		this.g = g;
		this.source = source;
		this.sink = sink;
		this.showResult = showResult;
	}
	
	public int perform()
	{
		doAlgorithm();
		if(showResult)
			makeLabels();
		int res=0;
		for(int i=0; i<n ; i++)
		{
			res+= F[i][t];
		}
		return res;
			
	}

	protected abstract void doAlgorithm();
	protected void makeLabels()
	{
		for(Edge e : g.getEdges())
		{
			e.setLabel(F[e.source.getId()][e.target.getId()] + "/" + C[e.source.getId()][e.target.getId()] );
			e.setShowWeight(false);
		}
		g.setDrawEdgeLabels(true);
	}
}
