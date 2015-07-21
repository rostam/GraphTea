package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.GraphModel;
import graphtea.library.algorithms.util.BipartiteChecker;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;



/**
 * Description here.
 *
 * @author Hooman Mohajeri Moghaddam
 */
public class IsBipartite implements GraphReportExtension  {



	public String getName() {
		return "Is Bipartite";
	}

	public String getDescription() {
		return "Is the graph Bipartite?";
	}

	public Object calculate(GraphModel g) {
		if(g.numOfVertices()==0)
			return new String("Graph empty");
		return new String(BipartiteChecker.isBipartite(g)+"");

	}

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "General";
	}

}
