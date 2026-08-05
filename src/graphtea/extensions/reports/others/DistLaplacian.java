package graphtea.extensions.reports.others;

import Jama.Matrix;
import graphtea.extensions.AlgorithmUtils;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistLaplacian implements GraphReportExtension<RenderTable> {

    public String getName() {
        return "Distance Signless laplacian";
    }

    public String getDescription() {
        return "Distance calculations";
    }

    @Override
    public RenderTable calculate(GraphModel g) {
        RenderTable ret = new RenderTable();
        ret.setTitles(Arrays.asList("m ", "n ", "Eigen Values"));

        Matrix DLS = AlgorithmUtils.getDistanceSignlessLaplacianMatrix(g);

        List<Object> v = new ArrayList<>();
        v.add(g.getEdgesCount());
        v.add(g.getVerticesCount());
        v.add(AlgorithmUtils.getEigenValues(DLS));
        ret.add(v);
        return ret;
    }

    @Override
    public String getCategory() {
        return "Verification-Checking";
    }
}
