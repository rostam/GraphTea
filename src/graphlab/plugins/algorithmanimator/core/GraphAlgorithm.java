package graphlab.plugins.algorithmanimator.core;

import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.event.GraphRequest;
import graphlab.library.event.VertexRequest;

/**
 * author: azin azadi
 */
public class GraphAlgorithm extends Algorithm {

    public GraphModel requestGraph() {
        GraphRequest gr = new GraphRequest();
        dispatchEvent(gr);
        return (GraphModel) gr.getGraph();
    }
    
    public VertexModel requestVertex(GraphModel g,String msg){
        VertexRequest vr = new VertexRequest(g, msg);
        dispatchEvent(vr);
        return (VertexModel) vr.getVertex();
    }

}
