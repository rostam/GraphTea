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
	public String getName() {
		return "Max Flow";
	}

	/**
	 * Computes the maximum flow between the two selected vertices.
	 *
	 * <p>This used to return null unconditionally: the only line that ran the algorithm was
	 * commented out, so the menu entry had never done anything at all. It now reads the source
	 * and sink from the selection, the one piece of user intent a report can see.
	 *
	 * @param g the graph to measure
	 * @return the maximum flow, or a sentence explaining what the user needs to select
	 */
	@Override
	public Object calculate(GraphModel g) {
		FlowEndpoints ends = FlowEndpoints.fromSelection(g);
		if (ends == null) {
			return FlowEndpoints.INSTRUCTION;
		}
		// showResult=false: the report window presents the number, so the algorithm should not
		// also relabel the graph and pop its own dialog.
		return new PushRelabel(g, ends.source(), ends.sink(), false).perform();
	}

	public int doAlgorithm() {
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
