package graphtea.extensions.reports.spectralreports.maxflowmincut;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;

public class GusfieldGomoryHuTree {

	private MinCut MC;
	public GraphModel mainGraph, GHTree;
	private int[] flow, neighbors;
	public GusfieldGomoryHuTree(GraphModel g) throws Exception
	{
		if (g.isDirected())
			throw new Exception("Gomory-Hu for directed Graphs may not exist");

		this.mainGraph = g;
		GHTree = new GraphModel(false);
		GHTree.setLabel("Gomory-Hu Tree");
		GHTree.setDrawEdgeLabels(true);
		MC = new MinCut(g, null, null, false);
	}
	public GraphModel perform()
	{
		//HashMap<Vertex, LinkedList<Vertex>> neighbors = new HashMap<Vertex, LinkedList<Vertex>>();
		int size = mainGraph.numOfVertices();
		
		//first node creation
		Vertex s1 = new Vertex();
		s1.setLabel(mainGraph.getVertex(0).getLabel());
		s1.setLocation(mainGraph.getVertex(0).getLocation());
		GHTree.addVertex(s1);
		
		
		MC  = new MinCut(mainGraph, null, null, true);
		neighbors = new int[size];
		flow = new int[size];
		int t, temp;
		

		

		for(int s=1; s<size; s++)
		{			
			
			t = neighbors[s];
			MC.source = mainGraph.getVertex(s);
			MC.sink = mainGraph.getVertex(t);
			
			flow[s] = MC.perform();
			for (int i=1; i < size ; i++)
			{
				if (i!=s && MC.cut.contains(i) && neighbors[i]==t)
					neighbors[i]=s;
			}
			if(MC.cut.contains(neighbors[t]))
			{
				neighbors[s] = neighbors[t];
				neighbors[t]=s;
				temp = flow[t];
				flow[t] = flow[s];
				flow[s] = temp;
				
			}
		}
		
		showResult();
		return GHTree;


	}

	public void showResult()
	{
		GHTree.clear();
		int size = mainGraph.numOfVertices();
		Vertex v, vg;
		for(int s=0; s<size; s++)
		{
			// node creation
			vg = mainGraph.getVertex(s);
			v = new Vertex();
			v.setLabel(vg.getLabel());
			v.setLocation(vg.getLocation());
			GHTree.addVertex(v);
		}
		Edge e;
		for(int i=0; i<size; i++)
		{
			e = new Edge(GHTree.getVertex(i), GHTree.getVertex(neighbors[i]));
			e.setWeight(flow[i]);
			e.setShowWeight(true);
			GHTree.addEdge(e);
		}
		
	}
}



/*
 * 
 * Vertex s,t,i;
		Edge st, it, res;
		Iterator<Edge> itIter;
		Collection<Integer> Cut;
		boolean isSource;
		String label;
		for(int sn=1; sn<size; sn++)
		{
			s = GHTree.getVertex(sn);
			st = GHTree.lightEdgeIterator(s).next();
			t = (st.source==s? st.target : st.source);

			MC.source = mainGraph.getVertex(sn);
			MC.sink = mainGraph.getVertex(t.getId());

			st.setWeight(MC.perform());
			Cut = MC.cut;

			itIter = GHTree.lightEdgeIterator(t);
			while(itIter.hasNext())
			{
				it = itIter.next();

				if(it.source==t)
				{
					i=it.target;
					isSource = false;
				}
				else
				{
					i = it.source;
					isSource = true;
				}
				//TODO: modify this for efficiency 
				if(Cut.contains(i))
				{
					{
						if(isSource)
						{
							label = GHTree.getEdge(i, t).getLabel();
							GHTree.removeEdge(GHTree.getEdge(i, t));
						}
						else
						{
							label = GHTree.getEdge(t, i).getLabel();
							GHTree.removeEdge(GHTree.getEdge(t, i));
						}
						res = new Edge(i, s);
						res.setLabel(label);
						GHTree.addEdge(res);
					}
				}
			}
		}

 */
