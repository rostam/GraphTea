// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.actions;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;
import graphtea.plugins.main.ui.SubGraphRenderer;

import java.util.HashMap;


/**
 * Creates a line graph from the current graph and shows it in a new tab
 *
 * @author Mohammad Ali Rostami
 * @author Azin Azadi
 */
public class ExtractSubGraph implements GraphActionExtension {

    public void action(GraphData graphData) {

        GraphModel g1 = graphData.getGraph();
        GraphModel g2 = new GraphModel(false);

        SubGraph sg = SubGraphRenderer.sgbck;
        HashMap<Integer,Integer> idid=new HashMap<Integer, Integer>();
        if(sg.vertices.size()!=0) {
            for(Vertex v : sg.vertices) {
                Vertex tmp = new Vertex();
                tmp.setLabel(v.getLabel());
                tmp.setLocation(v.getLocation());
                g2.addVertex(tmp);
                idid.put(v.getId(),tmp.getId());
            }

            for(Edge e : sg.edges) {
                Vertex vt1 = g2.getVertex(idid.get(e.source.getId()));
                Vertex vt2 = g2.getVertex(idid.get(e.target.getId()));
                g2.addEdge(new Edge(vt1,vt2));
            }
        }

       graphData.core.showGraph(g2);
    }

    public String getName() {
        return "Extract SubGraph";
    }

    public String getDescription() {
        return "";
    }

    @Override
    public String getCategory() {
        return "Basic Operations";
    }
}
