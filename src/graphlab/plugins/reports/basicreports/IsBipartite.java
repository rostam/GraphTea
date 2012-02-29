package graphlab.plugins.reports.basicreports;

import graphlab.library.algorithms.util.BipartiteChecker;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;



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

	public Object calculate(GraphData gd) {
		if(gd.getGraph().numOfVertices()==0)
			return new String("Graph empty");
		return new String(BipartiteChecker.isBipartite(gd.getGraph())+"");

	}

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Property";
	}

}
