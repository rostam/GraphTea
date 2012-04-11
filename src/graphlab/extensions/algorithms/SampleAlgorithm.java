package graphlab.extensions.algorithms;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;
import graphlab.library.Path;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.GraphAlgorithm;
import graphlab.plugins.algorithmanimator.extension.AlgorithmExtension;
import graphlab.plugins.main.core.AlgorithmUtils;

import java.util.Vector;

/**
 * author: rostam
 * author: azin
 */
public class SampleAlgorithm extends GraphAlgorithm implements AlgorithmExtension {
    public SampleAlgorithm(BlackBoard blackBoard) {
        super(blackBoard);
    }

    @Override
    public void doAlgorithm() {
        step("<h3>Source at: <a href='http://github.com/azinazadi/GraphLab/blob/master/src/graphlab/extensions/algorithms/SampleAlgorithm.java&handler=external'>SampleAlgorithm.java</a></h3>");
        step("<h3>Some helper methods at:<a href='http://github.com/azinazadi/GraphLab/blob/master/src/graphlab/plugins/main/core/AlgorithmUtils.java&handler=external'>AlgorithmUtils.java</a></h3>");

        GraphModel g = graphData.getGraph();
        VertexModel v1 = requestVertex(g, "select the first vertex");
        VertexModel v2 = requestVertex(g, "select the second vertex");
        step("check the first vertex");
        v1.setColor(2);
        step("check the second vertex");
        v2.setColor(3);
        step("have a look<br>" + getMatrixHTML(g));
        g.addEdge(new EdgeModel(v1, v2));
        step("add an edge between them<br>" + getMatrixHTML(g));
        
        
        step("mark v1 neighbours");
        for (VertexModel v:g.neighbors(v1))
            v.setMark(true);
        
        step("color v1 edges");
        for (EdgeModel e:g.edges(v1))
            e.setColor(3);
        
        step("connect v2 to the neighbors of its neighbours");
        Vector<EdgeModel> toInsert = new Vector<EdgeModel>();
        for (VertexModel v:g.neighbors(v2))
            for (VertexModel vv:g.neighbors(v))
                toInsert.add(new EdgeModel(v2, vv));
        g.insertEdges(toInsert);
        
        
        step("find a path between v1 and v2");
        Path<VertexModel> path = AlgorithmUtils.getPath(g, v1, v2);
        
        step("mark the path connecting v1 and v2, using helper methods in AlgorithmUtils");
        VertexModel last = v2;
        for (VertexModel v: path){
            EdgeModel e = g.getEdge(v, last);
            if (e != null) e.setColor(6);
            last = v;
        }

        step("goodbye");
    }

    @Override
    public String getName() {
        return "Just a sample ";
    }

    @Override
    public String getDescription() {
        return "This is just a show case for developers to see how they can make new algorithms";
    }
}
