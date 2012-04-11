package graphlab.plugins.reports.spectralreports.maxflowmincut;



import javax.swing.JOptionPane;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
import graphlab.library.BaseEdge;
import graphlab.library.BaseGraph;
import graphlab.library.BaseVertex;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.event.GraphRequest;
import graphlab.library.event.VertexRequest;
import graphlab.plugins.algorithmanimator.core.AlgorithmAnimator;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;

public class MinimumCut extends Algorithm implements GraphReportExtension{

	GraphModel g;
	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Connectivity";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Min Cut";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Min Cut";
	}
	

	
	
	@Override
	public Object calculate(GraphData gd) {
		g = gd.getGraph();
		AlgorithmAnimator algorithmAnimator = new AlgorithmAnimator( gd.getBlackboard());
		acceptEventDispatcher(algorithmAnimator);		
		JOptionPane.showMessageDialog(null, "Minimum cut between source and sink:" + doAlgorithm());
		return null;
	}

	public int doAlgorithm() {
		// TODO Auto-generated method stub
		resetGraphLabels();
		GraphRequest<BaseVertex, BaseEdge<BaseVertex>> gr = new GraphRequest<BaseVertex, BaseEdge<BaseVertex>>();
		try{
			dispatchEvent(gr);	
		}
		catch(Exception e){};
		
		Vertex source, sink;

		BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph  = gr.getGraph();

		VertexRequest<BaseVertex, BaseEdge<BaseVertex>> sourceReq = new VertexRequest<BaseVertex, BaseEdge<BaseVertex>>(graph, "Please choose a vertex as source.");
		try{
			dispatchEvent(sourceReq);	
		}
		catch(Exception e){};
		source = g.getVertex(sourceReq.getVertex().getId());
		source.setColor(7);
		source.setMark(true);

		VertexRequest<BaseVertex, BaseEdge<BaseVertex>> sinkReq = new VertexRequest<BaseVertex, BaseEdge<BaseVertex>>(graph, "Please choose a vertex as sink.");
		try{
			dispatchEvent(sinkReq);	
		}
		catch(Exception e){};
		sink = g.getVertex(sinkReq.getVertex().getId());
		sink.setColor(3);
		sink.setMark(true);

		MinCut MC = new MinCut(g, g.getVertex(sourceReq.getVertex().getId()), g.getVertex(sinkReq.getVertex().getId()),true);
		return MC.perform();

	}
	
	private void resetGraphLabels()
	{
		for(Vertex v: g)
		{
			v.setColor(0);
			v.setMark(false);
			
		}
		for(Edge e: g.getEdges())
		{
			e.setLabel(e.source.getId() + "" + e.target.getId());
		}
	}
	
	


}
