// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.actions;

import graphtea.extensions.G6Format;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.main.extension.GraphActionExtension;

import java.awt.*;
import java.util.Scanner;
import java.util.Vector;

/**
 * @author Azin Azadi
 */


public class G6StringLoader implements GraphActionExtension, Parametrizable {
    @Parameter(name = "Graph G6 strings")
    public String graphs = "";

    public String getName() {
        return "G6 String Loader";
    }

    public String getDescription() {
        return "Load graphs from G6 strings";
    }

    public void action(GraphData graphData) {
        graphs.trim();
        if(graphs.contains(",")) {
            Scanner sc = new Scanner(graphs);
            sc.useDelimiter(",");
            while(sc.hasNext()) {
                GraphModel g = G6Format.stringToGraphModel(sc.next().trim());
                Point pp[] = PositionGenerators.circle(200, 400, 250, g.numOfVertices());

                int tmpcnt = 0;
                for (Vertex v : g) {
                    v.setLocation(pp[tmpcnt]);
                    tmpcnt++;
                }
                graphData.core.showGraph(g);
            }
        } else {
            GraphModel g = G6Format.stringToGraphModel(graphs);
            Point pp[] = PositionGenerators.circle(200, 400, 250, g.numOfVertices());

            int tmpcnt = 0;
            for (Vertex v : g) {
                v.setLocation(pp[tmpcnt]);
                tmpcnt++;
            }
            graphData.core.showGraph(g);
        }
    }

    public String checkParameters() {
        return null;
    }

    @Override
    public String getCategory() {
        return "Other Actions";
    }
}


