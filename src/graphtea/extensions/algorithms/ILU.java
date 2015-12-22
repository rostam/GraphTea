package graphtea.extensions.algorithms;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.GraphAlgorithm;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;

import java.util.Vector;

/**
 * author: rostam
 * author: azin
 */
public class ILU extends GraphAlgorithm implements AlgorithmExtension {
    public ILU(BlackBoard blackBoard) {
        super(blackBoard);
    }

    @Override
    public void doAlgorithm() {
        GraphModel g = graphData.getGraph();
        boolean cont = true;
        int fillin = 0;
        while(cont) {
            Vertex v1 = requestVertex(g, "select a vertex");
            Vector<Vertex> InV = new Vector<>();
            Vector<Vertex> OutV = new Vector<>();
            for(Vertex v : g) {
                if(g.isEdge(v,v1)) InV.add(v);
                if(g.isEdge(v1,v)) OutV.add(v);
            }

            for(Vertex iv : InV) {
                for(Vertex ov : OutV) {
                    if(!g.isEdge(iv,ov)) {
                        g.addEdge(new Edge(iv,ov));
                        fillin++;
                    }
                }
            }

            System.out.println("Fillin" + fillin);

            graphData.getGraphRenderer().repaint();
            graphData.getGraphRenderer().repaintGraph();
            graphData.getGraphRenderer().validate();
            g.removeVertex(v1);
            if(g.numOfVertices()==0) cont = false;
            graphData.getGraphRenderer().repaint();
            graphData.getGraphRenderer().repaintGraph();
            graphData.getGraphRenderer().validate();
       }
    }

    @Override
    public String getName() {
        return "ILU";
    }

    @Override
    public String getDescription() {
        return "ILU";
    }
}
