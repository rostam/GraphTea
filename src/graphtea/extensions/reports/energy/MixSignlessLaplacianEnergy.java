package graphtea.extensions.reports.energy;

import graphtea.extensions.reports.spectralreports.LaplacianEnergy;
import graphtea.extensions.reports.spectralreports.SignlessLaplacianEnergy;
import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.util.ArrayList;
import java.util.List;

public class MixSignlessLaplacianEnergy implements GraphReportExtension<List<String>> {

    @Override
    public List<String> calculate(GraphModel g) {
        List<String> results = new ArrayList<>();
        results.add(new LaplacianEnergy().calculate(g));
        results.add(new SignlessLaplacianEnergy().calculate(g));
        return results;
    }

    @Override
    public String getCategory() {
        return "Spectral- Energies";
    }

    @Override
    public String getName() {
        return "Mix Signless Laplacian Energy";
    }
}
