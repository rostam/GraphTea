package graphlab.plugins.reports.spectralreports.maxflowmincut;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.VertexModel;

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
		//HashMap<VertexModel, LinkedList<VertexModel>> neighbors = new HashMap<VertexModel, LinkedList<VertexModel>>();
		int size = mainGraph.numOfVertices();
		
		//first node creation
		VertexModel s1 = new VertexModel();
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
		VertexModel v, vg;
		for(int s=0; s<size; s++)
		{
			// node creation
			vg = mainGraph.getVertex(s);
			v = new VertexModel();
			v.setLabel(vg.getLabel());
			v.setLocation(vg.getLocation());
			GHTree.addVertex(v);
		}
		EdgeModel e;
		for(int i=0; i<size; i++)
		{
			e = new EdgeModel(GHTree.getVertex(i), GHTree.getVertex(neighbors[i]));
			e.setWeight(flow[i]);
			e.setShowWeight(true);
			GHTree.addEdge(e);
		}
		
	}
}



/*
 * 
 * VertexModel s,t,i;
		EdgeModel st, it, res;
		Iterator<EdgeModel> itIter;
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
						res = new EdgeModel(i, s);
						res.setLabel(label);
						GHTree.addEdge(res);
					}
				}
			}
		}

 */
