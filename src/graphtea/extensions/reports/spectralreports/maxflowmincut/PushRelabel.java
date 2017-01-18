package graphtea.extensions.reports.spectralreports.maxflowmincut;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;


/***
 * 
 * @author Hooman Mohajeri Moghaddam
 *	This is the implementation of the Push-Relabel algorithm by Goldberg
 *	For further detail refer to Introduction to Algorithms (Chapter 26)
 *	Throughout the class the Graph is considered connected and the weights non-negative.  
 */

public class PushRelabel extends MaxFlow{



	private int height[];
	private int excess[];
	private Integer current[];
	private HashMap <Integer, LinkedList<Integer>> neighborMap ;
	private HashMap <Integer, Iterator<Integer>> iterMap ;
	private boolean isDirecte;




	public PushRelabel(GraphModel g, Vertex source, Vertex sink, boolean showResult)
	{
		super(g,source,sink,showResult);
		isDirecte = g.isDirected();
		C = g.getWeightedAdjacencyMatrix().getArrayCopy();



		n = C.length;
		current = new Integer[n];


		// a map between the vertex id and a linked-list of simple(undirected) neighbors".
		neighborMap = new HashMap<>();
		iterMap = new HashMap<>();

		//initializing neighbor list
		for(Vertex v: g)
		{
			LinkedHashSet<Integer> simpleNeihbors = new LinkedHashSet<>();
			for (Vertex n: g.getNeighbors(v))
				simpleNeihbors.add(n.getId());	

			for (Vertex n: g.getBackNeighbours(v))
				simpleNeihbors.add(n.getId());

			LinkedList<Integer> neighbors = new LinkedList<>(simpleNeihbors);
			int id = v.getId();
			neighborMap.put(id, neighbors);

			//			iterMap.put(id , neighbors.iterator());
			//			if(!neighbors.isEmpty())
			//				current[id]=neighbors.getFirst();

		}

	}


	public void doAlgorithm()
	{
		relableToFront();
	}
	private void  initializePreflow()
	{
		F = new double[n][n];
		height = new int[n];
		excess = new int[n];
		current = new Integer[n];

		this.t = sink.getId();
		this.s = source.getId();

		height[s] = n;

		LinkedList<Integer> neighbors;
		for(int k=0; k<n ; k++)
		{
			neighbors = neighborMap.get(k);
			iterMap.put(k , neighbors.listIterator(0));
			if(!neighbors.isEmpty())
				current[k]=neighbors.getFirst();
		}

		for(int v : neighborMap.get(s))
		{
			F[s][v]=C[s][v];
			excess[v]=(int)C[s][v];
			excess[s]-=C[s][v];
		}
		//makeLabels();
	}

	private void relabel(int u)
	{ 
		LinkedList<Integer> neighbors = neighborMap.get(u);
		int min = Integer.MAX_VALUE;
		for(Integer v : neighbors)
		{
			if( Cf(u, v) > 0 && height[v] < min )
				min = height[v];

		}
		height[u] = 1+min;
		//makeLabels();
	}

	private void push(int u , int v)
	{
		int deltaF = (excess[u] < Cf(u,v) ? excess[u] : Cf(u,v));
		if(C[u][v]>0)
			F[u][v] += deltaF;
		else
			F[v][u] -= deltaF;

		excess[u] -= deltaF;
		excess[v] += deltaF;
		//makeLabels();
	}


	private int Cf(int u, int v)
	{
		return (int)(C[u][v] - F[u][v] + F[v][u]);
	}
	private void discharge(int u)
	{
		Iterator<Integer> iter = iterMap.get(u);
		Integer v;
		while (excess[u] > 0)
		{
			v = current[u];
			if (v==null)
			{
				relabel(u);
				iter=neighborMap.get(u).listIterator(0);
				iterMap.put(u, iter);
				current[u] = iter.next();
			}
			else if(Cf(u,v)>0 && height[u]==height[v]+1) 
				push(u,v);
			else
			{
				if(iter.hasNext())
					current[u]=iter.next();
				else
					current[u] = null;
			}
		}
	}

	private void relableToFront()
	{
		initializePreflow();
		LinkedList<Integer> L = new LinkedList<>();

		for(Integer i=0 ; i < n ; i++)
			if( i != s && i != t )
				L.add(i);
		Iterator<Integer> iter = L.iterator();
		int oldHeight;
		while(iter.hasNext())
		{
			int u = iter.next();
			oldHeight = height[u];
			discharge(u);
			if (height[u] > oldHeight)
			{

				iter.remove();
				L.addFirst(u);
				iter=L.listIterator(0);
				iter.next();
			}
		}
	}

	protected void makeLabels()
	{
		int s,t;
		for(Edge e : g.getEdges())
		{
			s = e.source.getId();
			t = e.target.getId();
			if(isDirecte)
				e.setLabel(F[s][t] + "/" + C[s][t] );
			else
			{
				if(s<t)
					e.setLabel((F[s][t] - F[t][s]) + "/" + C[s][t] );
				else
					e.setLabel((F[t][s] - F[s][t]) + "/" + C[s][t] );
			}
		
			e.setShowWeight(false);
		}
		for(Vertex v : g)
			//v.setLabel(""+height[v.getId()]);
		g.setDrawEdgeLabels(true);
	}

}
