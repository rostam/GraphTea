package graphlab.library.algorithms.util;

import java.lang.reflect.Array;
import java.util.LinkedList;

import graphlab.graph.graph.VertexModel;
import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.algorithms.AutomatedAlgorithm;
import graphlab.library.event.MessageEvent;
import graphlab.library.event.typedef.BaseGraphRequest;

/**
 * The details of the algorithm used here can be found at:
 * http://www.cs.auckland.ac.nz/~ute/220ft/graphalg/node15.html#SECTION00033000000000000000
 * 
 * @author Hooman Mohajeri Moghaddam
 *
 */
public class BipartiteChecker extends Algorithm implements AutomatedAlgorithm {

	@SuppressWarnings("unchecked")
	public static <VertexType extends BaseVertex, EdgeType extends BaseEdge<VertexType>>
	boolean isBipartite(BaseGraph<VertexType, EdgeType> graph) {

		//Copying to ColorableVertex
		ColorableVertex<VertexType>[] CVArray =  (ColorableVertex<VertexType>[])Array.newInstance(ColorableVertex.class , graph.getVerticesCount());
		int i=0;
		for (VertexType vm : graph)
		{
			CVArray[i] = new ColorableVertex<VertexType>(vm);
			i++;
		}



		LinkedList<ColorableVertex<VertexType>> queue = new LinkedList<ColorableVertex<VertexType>>();
		ColorableVertex<VertexType> cv = CVArray[0];

		for (ColorableVertex<VertexType> v : CVArray)
		{
			if (v.color>0) continue;

			v.color = 1;
			queue.offer(v);

			while (queue.size() != 0) {	
				cv = queue.poll();
				for (ColorableVertex<VertexType> next : CVArray) {
					if (graph.isEdge(cv.vm, next.vm)) 
					{
						if(next.color == 0)
						{
							next.color = 3 - cv.color;
							queue.offer(next);
						}
						else if(cv.color == next.color)
							return false;
					}
				}

			}
		}

		return true;
	}

	@Override
	public void doAlgorithm() {
		BaseGraphRequest gr = new BaseGraphRequest();
		dispatchEvent(gr);
		BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph = gr.getGraph();

		if (isBipartite(graph)) {
			dispatchEvent(new MessageEvent("Graph is bipartite."));
		} else
			dispatchEvent(new MessageEvent("Graph is NOT bipartite."));

	}

}





class ColorableVertex <VertexType extends BaseVertex>
{
	VertexType vm;
	ColorableVertex(VertexType v)
	{
		vm = v;
		this.color=0;
	}
	ColorableVertex(VertexType v,int color)
	{
		vm = v;
		this.color=0;
	}
	int color;
}
