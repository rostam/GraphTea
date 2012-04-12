package graphlab.extensions.algorithms;

import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.Vertex;
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
        step("<h3>Source at:<a href='http://github.com/azinazadi/GraphLab/blob/master/src/graphlab/extensions/algorithms/SampleAlgorithm.java&handler=external'>SampleAlgorithm.java</a></h3>");
        step("<h3>Some helper methods at:<a href='http://github.com/azinazadi/GraphLab/blob/master/src/graphlab/plugins/main/core/AlgorithmUtils.java&handler=external'>AlgorithmUtils.java</a></h3>");

        GraphModel g = graphData.getGraph();
        Vertex v1 = requestVertex(g, "select the first vertex");
        Vertex v2 = requestVertex(g, "select the second vertex");
        step("color v1 and v2");
        v1.setColor(2);
        v2.setColor(2);
        
        step("mark v1 neighbours");
        for (Vertex v:g.neighbors(v1))
            v.setMark(true);
        
        step("color v1 edges");
        for (Edge e:g.edges(v1))
            e.setColor(3);
        
        step("connect v2 to the neighbors of its neighbours");
        Vector<Edge> toInsert = new Vector<Edge>();
        for (Vertex v:g.neighbors(v2))
            for (Vertex vv:g.neighbors(v))
                toInsert.add(new Edge(v2, vv));
        g.insertEdges(toInsert);
        
        
        step("find a path between v1 and v2");
        Path<Vertex> path = AlgorithmUtils.getPath(g, v1, v2);
        
        step("mark the path connecting v1 and v2, using helper methods in AlgorithmUtils");
        Vertex last = v2;
        for (Vertex v: path){
            Edge e = g.getEdge(v, last);
            if (e != null) e.setColor(6);
            last = v;
        }

        step("the graph matrix<br>" + getMatrixHTML(g));
        g.addEdge(new Edge(v1, v2));

        step("That's it!");
        step("Start making your algorithm by modifing this file, and running make.sh");
        step("have fun :)");
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
