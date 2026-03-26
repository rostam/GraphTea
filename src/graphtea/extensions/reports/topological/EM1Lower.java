// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.reports.topological;

import java.util.List;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.CommandAttitude;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Ali Rostami

 */

@CommandAttitude(name = "EM1Lower", abbreviation = "_EM1Lower")
public class EM1Lower implements GraphReportExtension<RenderTable> {
    public String getName() {
        return "EM1 Low";
    }

    public String getDescription() {
        return "EM1 Low";
    }

    public RenderTable calculate(GraphModel g) {
        ZagrebIndexFunctions zif = new ZagrebIndexFunctions(g);
        ZagrebIndexFunctions zifL = new ZagrebIndexFunctions(
                AlgorithmUtils.createLineGraph(g)
        );

        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList(" EM1(G) ", "rhs"));
        double maxDeg = 0;
        double minDeg = Integer.MAX_VALUE;

        ArrayList<Integer> al = AlgorithmUtils.getDegreesList(g);
        Collections.sort(al);
        maxDeg = al.get(al.size()-1);
        minDeg = al.get(0);

        double m = g.getEdgesCount();

        double M12=zif.getSecondZagreb(1);
        double M21=zif.getFirstZagreb(1);
        double EM1=zifL.getFirstZagreb(1);


        List<Object> v = new ArrayList<>();
        v.add(EM1);
        v.add((M21*M21/(2*m))+(4*m)+(2*M12)-(4*M21)+((m*(maxDeg-minDeg)*(maxDeg-minDeg))/2));



        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Degree";
    }
}