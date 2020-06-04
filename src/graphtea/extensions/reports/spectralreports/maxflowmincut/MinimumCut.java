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
import graphtea.plugins.reports.extension.GraphReportExtension;

public class MinimumCut extends Algorithm implements GraphReportExtension{

	GraphModel g;
	@Override
	public String getCategory() {
		return "Connectivity";
	}

	@Override
	public String getDescription() {
		return "Min Cut";
	}

	@Override
	public String getName() {
		return "Min Cut";
	}
	

	@Override
	public Object calculate(GraphModel g) {
		//AlgorithmAnimator algorithmAnimator = new AlgorithmAnimator( gd.getBlackboard());
		//acceptEventDispatcher(algorithmAnimator);
		//JOptionPane.showMessageDialog(null, "Minimum cut between source and sink:" + doAlgorithm());
		return null;
	}

	public int doAlgorithm() {
		// TODO Auto-generated method stub
		resetGraphTeaels();
		GraphRequest<BaseVertex, BaseEdge<BaseVertex>> gr = new GraphRequest<>();
		try{
			dispatchEvent(gr);	
		}
		catch(Exception ignored){}

        Vertex source, sink;

		BaseGraph<BaseVertex, BaseEdge<BaseVertex>> graph  = gr.getGraph();

		VertexRequest<BaseVertex, BaseEdge<BaseVertex>> sourceReq = new VertexRequest<>(graph, "Please choose a vertex as source.");
		try{
			dispatchEvent(sourceReq);	
		}
		catch(Exception ignored){}
        source = g.getVertex(sourceReq.getVertex().getId());
		source.setColor(7);
		source.setMark(true);

		VertexRequest<BaseVertex, BaseEdge<BaseVertex>> sinkReq = new VertexRequest<>(graph, "Please choose a vertex as sink.");
		try{
			dispatchEvent(sinkReq);	
		}
		catch(Exception ignored){}
        sink = g.getVertex(sinkReq.getVertex().getId());
		sink.setColor(3);
		sink.setMark(true);

		MinCut MC = new MinCut(g, g.getVertex(sourceReq.getVertex().getId()), g.getVertex(sinkReq.getVertex().getId()),true);
		return MC.perform();

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
