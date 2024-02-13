// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.actions;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

/**
 * Creates a line graph from the current graph and shows it in a new tab
 *
 * @author Mohammad Ali Rostami
 * @author Azin Azadi
 */
public class ComplementGraph implements GraphActionExtension {

    public void action(GraphData graphData) {

        GraphModel g1 = graphData.getGraph();
        GraphModel g2 = new GraphModel(false);//

        for(Vertex v : g1.getVertexArray()) {
            Vertex tmp = new Vertex();
            tmp.setLocation(v.getLocation());
            g2.addVertex(tmp);
        }


       for(Vertex v1 : g1.getVertexArray()) {
           for(Vertex v2 : g1.getVertexArray()) {
               if(v1.getId() != v2.getId()) {
                   if (!g1.isEdge(v1, v2)) {
                       g2.addEdge(new Edge(g2.getVertex(v1.getId()),
                               g2.getVertex(v2.getId())));
                   }
               }
           }
       }

       graphData.core.showGraph(g2);
    }

    public String getName() {
        return "Complement Graph";
    }

    public String getDescription() {
        return "Makes a graph including those edges which" +
                "are not existent in the initial graph.";
    }

    @Override
    public String getCategory() {
        return "Transformations";
    }
}
