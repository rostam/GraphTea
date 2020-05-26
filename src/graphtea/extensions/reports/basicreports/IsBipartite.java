package graphtea.extensions.reports.basicreports;

import graphtea.graph.graph.GraphModel;
import graphtea.library.algorithms.util.BipartiteChecker;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * Description here.
 *
 * @author Hooman Mohajeri Moghaddam, Ali Rostami
 */
public class IsBipartite implements GraphReportExtension  {

	public String getName() {
		return "Is Bipartite";
	}

	public String getDescription() {
		return "Is the graph Bipartite?";
	}

	public Object calculate(GraphModel g) {
		return BipartiteChecker.isBipartite(g);
	}

	@Override
	public String getCategory() {
		return "General";
	}

}
