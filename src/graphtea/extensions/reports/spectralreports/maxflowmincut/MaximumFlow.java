package graphtea.extensions.reports.spectralreports.maxflowmincut;


import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.BaseEdge;
import graphtea.library.BaseGraph;
import graphtea.library.BaseVertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.event.GraphRequest;
import graphtea.library.event.VertexRequest;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.reports.extension.GraphReportExtension;

public class MaximumFlow extends Algorithm implements GraphReportExtension{

	GraphModel g;
	BlackBoard b;
	@Override
	public String getCategory() {
		return "Connectivity";
	}

	@Override
	public String getDescription() {
		return "Max Flow";
	}

	@Override
	public String getName() {
		return "Max Flow";
	}


	@Override
	public Object calculate(GraphModel g) {
		g = g;
		//AlgorithmAnimator algorithmAnimator = new AlgorithmAnimator( gd.getBlackboard());
		//acceptEventDispatcher(algorithmAnimator);
		//JOptionPane.showMessageDialog(null, "Maxmimum flow between source and sink:" + doAlgorithm());
		return null;
	}

	public int doAlgorithm() {
		// TODO Auto-generated method stub
		resetGraphTeaels();
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

		PushRelabel PR = new PushRelabel(g, g.getVertex(sourceReq.getVertex().getId()), g.getVertex(sinkReq.getVertex().getId()),true);
		return PR.perform();

	}

	private void resetGraphTeaels()
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
