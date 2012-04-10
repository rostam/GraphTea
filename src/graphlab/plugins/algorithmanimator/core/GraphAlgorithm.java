package graphlab.plugins.algorithmanimator.core;

import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.library.algorithms.Algorithm;
import graphlab.library.event.GraphRequest;
import graphlab.library.event.VertexRequest;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.main.GraphData;

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
    
    public VertexModel requestVertex(GraphModel g,String msg){
        VertexRequest vr = new VertexRequest(g, msg);
        dispatchEvent(vr);
        return (VertexModel) vr.getVertex();
    }
    
    public String getMatrixHTML(GraphModel g){
        return graphData.saveLoad.Graph2MatrixHTML(g);
    }

}
