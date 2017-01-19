package graphtea.extensions.actions.art;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.main.extension.GraphActionExtension;

/**
 * Created with IntelliJ IDEA.
 * User: rostam
 * Date: 9/18/14
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphArtActionExtendTree
    implements GraphActionExtension {
        static final String CURVE_WIDTH = "Curve Width";
        @Override
        public String getName() {
        return "GraphTea Art Tree Extender";
    }

        @Override
        public String getDescription() {
        return "GraphTea Art";
    }

        @Override
        public void action(GraphData graphData) {
            final Vertex parent[] = new Vertex[graphData.getGraph().getVerticesCount()];
            //consider the hole structure as a tree
            AlgorithmUtils.BFSrun(graphData.getGraph(), graphData.getGraph().getVertex(0), (v, p) -> parent[v.getId()] = p);
            for (Vertex v : graphData.select.getSelectedVertices()) {
                if(graphData.getGraph().getDegree(v)==1) {
                    if(v.getId()==0) continue;
                    addChildren(graphData, parent[v.getId()], v, 1.7);
                    addChildren(graphData, parent[v.getId()], v, 0);
                    addChildren(graphData, parent[v.getId()], v, -1.7);
                }

            }
        }

    private void addChildren(GraphData graphData, Vertex p, Vertex v, double degree) {
        AlgorithmUtils.getDistance(v, p);
        GPoint gv = GPoint.sub(p.getLocation(),v.getLocation());
        gv = PositionGenerators.rotate(gv, degree);
        gv = GPoint.div(gv,1.7);
        gv = GPoint.add(gv, v.getLocation());
        Vertex vv = new Vertex();
        vv.setLocation(gv);

        graphData.getGraph().addVertex(vv);
        graphData.getGraph().addEdge(new Edge(v,vv));
    }

    @Override
    public String getCategory() {
        return "Graph-based Visualization";
    }
}
