// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ali Rostami
 */
public class InverseDegree implements GraphReportExtension<RenderTable> {

    public String getName() {
        return "Inverse Degree";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" M^-1_1(G) ", " S3 Max ", " S3 Min ", " S2 Max ", " S2 Min ", " Base "));

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        double maxDeg = al.get(al.size() - 1);
        double maxDeg2 = al.size() >= 2 ? al.get(al.size() - 2) : maxDeg;
        double minDeg = al.get(0);
        if (maxDeg2 == 0) maxDeg2 = maxDeg;

        double m = g.getEdgesCount();
        double n = g.getVerticesCount();
        double M21 = zif.getFirstZagreb(1);
        double Mm11 = zif.getFirstZagreb(-2);

        List<Object> v = new ArrayList<>();
        v.add(Mm11);
        v.add((1 / maxDeg) + (1 / maxDeg2) + (Math.pow(n - 2, 2) / (2 * m - maxDeg - maxDeg2)));
        v.add((1 / maxDeg) + (1 / minDeg) + (Math.pow(n - 2, 2) / (2 * m - maxDeg - minDeg)));
        v.add((1 / maxDeg) + (1 / maxDeg2)
                + ((n - 2) * (2 * m - maxDeg - maxDeg2) / (M21 - maxDeg * maxDeg - maxDeg2 * maxDeg2)));
        v.add((1 / maxDeg) + (1 / minDeg)
                + ((n - 2) * (2 * m - maxDeg - minDeg) / (M21 - maxDeg * maxDeg - minDeg * minDeg)));
        v.add((2 * m * n) / M21);
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}
