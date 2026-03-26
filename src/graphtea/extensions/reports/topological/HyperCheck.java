// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ali Rostami
 */

@CommandAttitude(name = "HyperCheck", abbreviation = "_HyperCheck")
public class HyperCheck implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "Hyper Check";
    }

    public String getDescription() {
        return "Hyper Check";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" Hyper ", " T-1 ", " T-2-HyHyper "));

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        double maxDeg = al.get(al.size() - 1);
        double minDeg = al.get(0);

        double m = g.getEdgesCount();
        double M21 = zif.getFirstZagreb(1);
        double H = zif.getHarmonicIndex();
        double chi = zif.getGeneralSumConnectivityIndex(2);

        List<Object> v = new ArrayList<>();
        v.add(chi);
        v.add((2 * (maxDeg + minDeg) * M21) - (4 * m * maxDeg * minDeg));
        v.add((2 * (maxDeg + minDeg) * M21) - (4 * m * maxDeg * minDeg) + M21 + (2 * maxDeg * minDeg * H) - (2 * m * (maxDeg + minDeg)));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
