package graphlab.plugins.reports.spectralreports.maxflowmincut;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;

public abstract class MaxFlow {
	
	public double C[][]; 
	public double F[][];
	
	protected GraphModel g;
	protected int n;
	protected VertexModel source,sink;
	int s, t;
	protected boolean showResult;
	
	public  MaxFlow(GraphModel g, VertexModel source, VertexModel sink, boolean showResult) {
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
		for(EdgeModel e : g.getEdges())
		{
			e.setLabel(F[e.source.getId()][e.target.getId()] + "/" + C[e.source.getId()][e.target.getId()] );
			e.setShowWeight(false);
		}
		g.setDrawEdgeLabels(true);
	}
}
