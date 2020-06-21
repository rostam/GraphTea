package graphtea.extensions.reports.clique;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.Vector;

public class MaxCliqueSize implements GraphReportExtension<Integer> {
    public static int maxCliqueSize(GraphModel g) {
        MaxCliqueAlg mca = new MaxCliqueAlg(g);
        Vector<Vector<Vertex>> mcs = mca.allMaxCliques();
        int maxSize = 0;
        for(Vector<Vertex> vv : mcs) {
            if(maxSize < vv.size()) {
                maxSize = vv.size();
            }
        }
        return  maxSize;
    }

    @Override
    public Integer calculate(GraphModel g) {
        return maxCliqueSize(g);
    }

    @Override
    public String getCategory() {
        return "General";
    }

    @Override
    public String getName() {
        return "Max Clique Size";
    }

    @Override
    public String getDescription() {
        return "Max Clique Size";
    }
}
