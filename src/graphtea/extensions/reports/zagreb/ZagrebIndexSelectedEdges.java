// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.zagreb;

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

@CommandAttitude(name = "zagreb_index_edges", abbreviation = "_zie")
public class ZagrebIndexSelectedEdges implements GraphReportExtension, Parametrizable {
    public String getName() {
        return "Zagreb Indices of Selected Edges";
    }

    @Parameter(name = "Alpha", description = "")
    public Double alpha = 1.0;

    public String getDescription() {
        return "Zagreb Indices of Selected Edges";
    }

    public Object calculate(GraphData gd) {
        ArrayList<String> out = new ArrayList<String>();
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(gd);
        out.add("First General Zagreb Index : "+ zif.getFirstZagrebSelectedEdges(alpha));
        out.add("Second General Zagreb Index : "+ zif.getSecondZagrebSelectedEdges(alpha));
        out.add("First Reformulated Zagreb Index : " + zif.getFirstReZagrebSelectedEdges(alpha));
        out.add("Second Reformulated Zagreb Index : " + zif.getSecondReZagrebSelectedEdges(alpha));
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
