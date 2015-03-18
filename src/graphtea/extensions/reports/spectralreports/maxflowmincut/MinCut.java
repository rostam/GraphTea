package graphtea.extensions.reports.spectralreports.maxflowmincut;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.LinkedList;

public class MinCut{

	public double C[][]; 
	public double F[][];
	public Vertex source,sink;
	public LinkedList<Integer> cut = new LinkedList<Integer>();
	public boolean showResult;
	
	
	protected int n;
	protected GraphModel g;
	protected double Cf[][];
	protected PushRelabel PR;
		
	
	public  MinCut(GraphModel g, Vertex source, Vertex sink, boolean showResult) {
		this.g = g;
		this.source = source;
		this.sink = sink;
		this.showResult = showResult;
		PR = new PushRelabel(g, source, sink, showResult);
	}
	
	public int perform()
	{
		int res = doAlgorithm();
		if(showResult)
			makeLabels();
		return res;
	}
	
	public int calcMinCutVal()
	{
		// TO DO: Modify this not to instantiate a new PR every time but
		// to use the same PR with new source and sinks.
		//PushRelabel PR = new PushRelabel(g, source, sink, false);
		reinitialize();
		return(PR.perform());
	}
	
	
	private void reinitialize()
	{
		PR.source = this.source;
		PR.sink = this.sink;
		PR.showResult = this.showResult;
		cut.clear();
	}
	protected int doAlgorithm()
	{
		reinitialize();
		int res = PR.perform();
		C = PR.C;
		F = PR.F;
		n = C.length;
		Cf = new double[n][n];
		for(int i=0; i<n ; i++)
			for(int j=0; j<n ; j++)
				Cf[i][j] = C[i][j] - F[i][j] + F[j][i];
		findCut();
		return res;
	}
	
	
	private void findCut()
	{
		//Using BFS we find all the reachable vertices from source
		// on the residual network
		boolean[] seen = new boolean[n];
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
			
		int s = source.getId();
		seen[s]=true;
		queue.add(s);
		
		cut.add(s);
		
		int v;
		while(!queue.isEmpty())
		{
			v=queue.removeFirst();
			for(int i=0 ; i < n ; i++)
			{
				if(Cf[v][i]>0)
					if(!seen[i])
					{
						seen[i]=true;
						queue.add(i);
						cut.add(i);
					}
			}
		}
	}
	
	protected void makeLabels()
	{
		for(int t=0; t<n ; t++)
		{
			if(cut.contains(t))
				g.getVertex(t).setColor(7);
			
			else
				g.getVertex(t).setColor(3);
		}
	}
}
