// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.others;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Ali Rostami
 */
public class RevisedSzegedIndex implements GraphReportExtension<Double> {
    public String getName() { return "Revised Szeged Index"; }
    public String getCategory() { return "Topological Indices-Distance"; }

    public Double calculate(GraphModel g) {
        return EdgePartitionIndexBase.sum(g,
                (graph, e, nu, nv, nequal) -> (nu + nequal / 2.0) * (nv + nequal / 2.0));
    }
}
