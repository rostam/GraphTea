// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

import static graphtea.extensions.reports.Utils.choose;

/**
 * @author Mohammad Ali Rostami
 */

@CommandAttitude(name = "num_of_stars", abbreviation = "_noss")
public class SubTreeCounting implements GraphReportExtension<RenderTable> {
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable(2);
        Vector<String> titles = new Vector<>();
        Vector<Object> elem = new Vector<>();

        double maxDeg = 0;
        for(Vertex v : g) {
            if(g.getDegree(v) > maxDeg)
                maxDeg = g.getDegree(v);
        }

        titles.add("D(i,j)");
        ret.setTitles(titles);
        for(int i=1;i<=maxDeg;i++) {
            titles.add("" + i);
        }
        ret.setTitles(titles);

        for(int i=1;i<=maxDeg;i++) {
            Vector<Object> tmp = new Vector<>();
            tmp.add(i+"");
            for(int j=1;j<=maxDeg;j++) {
                int sum = countSubtrees(g, i, j);
                tmp.add(sum);
            }
            ret.add(tmp);
        }

        return ret;
    }

    private static int countSubtrees(GraphModel g, int i, int j) {
        int sum = 0;
        for(Edge e : g.getEdges()) {
            if (i == j) {
                sum += choose(g.getDegree(e.source)-1,i).intValue()*
                        choose(g.getDegree(e.target)-1,j).intValue();
            } else {
                sum += choose(g.getDegree(e.source)-1,i).intValue()*
                        choose(g.getDegree(e.target)-1,j).intValue();
                sum += choose(g.getDegree(e.source)-1,j).intValue()*
                        choose(g.getDegree(e.target)-1,i).intValue();
            }
        }
        return sum;
    }

    public String getName() {
        return "Number of Subtrees";
    }

    public String getDescription() {
        return "Number of Subtrees";
    }

	@Override
	public String getCategory() {
		return "Subtree Counting ";
	}

}
