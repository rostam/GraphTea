// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "zagreb_coindex", abbreviation = "_zci")
public class ZagrebCoindex implements GraphReportExtension, Parametrizable {
    public String getName() {
        return "All Zagreb Coindices";
    }

    @Parameter(name = "Alpha", description = "")
    public Double alpha = 1.0;

    public String getDescription() {
        return "All Zagreb Coindices";
    }

    public Object calculate(GraphData gd) {
        ArrayList<String> out = new ArrayList<String>();

        double first_zagreb = 0;
        double second_zagreb = 0;

        for(Vertex v1 : gd.getGraph().getVertexArray()) {
            for(Vertex v2 : gd.getGraph().getVertexArray()) {
                if(v1.getId()!=v2.getId()) {
                    if(!gd.getGraph().isEdge(v1,v2)) {
                        first_zagreb += Math.pow(gd.getGraph().getDegree(v1), alpha - 1) +
                                Math.pow(gd.getGraph().getDegree(v2), alpha - 1);
                        second_zagreb += Math.pow(gd.getGraph().getDegree(v1)*
                                                  gd.getGraph().getDegree(v2), alpha );

                    }
                }
            }
        }

        first_zagreb/=2;
        second_zagreb/=2;
        out.add("First Zagreb Coindex : "+ first_zagreb);
        out.add("Second Zagreb Coindex : "+ second_zagreb);
        return out;
    }

    private boolean edge_adj(Edge e1,Edge e2) {
        if(e1.source.getId()==e2.source.getId()  &&
                e1.target.getId()==e2.target.getId()) return false;
        else if(e1.target.getId()==e2.source.getId() &&
                e1.source.getId()==e2.target.getId()) return false;
        else if(e1.source.getId() == e2.source.getId()) return true;
        else if(e1.source.getId() == e2.target.getId()) return true;
        else if(e1.target.getId() == e2.source.getId()) return true;
        else if(e1.target.getId() == e2.target.getId()) return true;
        return false;
    }

    public String checkParameters() {
        return null;
    }

    @Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Topological Indices";
	}
}
