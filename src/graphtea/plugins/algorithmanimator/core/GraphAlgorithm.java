package graphtea.plugins.algorithmanimator.core;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.library.algorithms.Algorithm;
import graphtea.library.event.VertexRequest;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.GraphData;

/**
 * author: azin azadi
 */
public class GraphAlgorithm extends Algorithm {

    private BlackBoard blackBoard;
    protected GraphData graphData;

    public GraphAlgorithm(BlackBoard blackBoard){
        this.blackBoard = blackBoard;
        this.graphData = new GraphData(blackBoard);
    }
    
    public Vertex requestVertex(GraphModel g,String msg){
        VertexRequest vr = new VertexRequest(g, msg);
        dispatchEvent(vr);
        return (Vertex) vr.getVertex();
    }
    
    public String getMatrixHTML(GraphModel g){
        return graphData.saveLoad.Graph2MatrixHTML(g);
    }

}
