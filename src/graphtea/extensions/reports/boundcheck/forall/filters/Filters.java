package graphtea.extensions.reports.boundcheck.forall.filters;


import graphtea.extensions.reports.boundcheck.forall.GraphFilter;
import graphtea.platform.lang.ArrayX;

/**
 * Created by rostam on 14.10.15.
 * @author M. Ali Rostami
 */
public class Filters {
    public final static String Integral = "Integral";
    public final static String LaplacianIntegral = "Laplacian Integral";
    public final static String QIntegral = "Q-Integral";
    public final static String NoFilter = "No Filter";
    public final static String ChemTree = "Chemical Tree";
    public final static String MinDeg2 = "Minimum Degree 2";
    public final static String Unicyclic = "Unicyclic Graphs";
    public final static String Bicyclic = "Bicyclic Graphs";
    public final static String Pentacyclic = "Pentacyclic Graphs";
    public final static String Tricyclic = "Tricyclic Graphs";

    public static ArrayX<String> getFilterNames() {
        return new ArrayX<>(
                Filters.Unicyclic,
                Filters.Bicyclic,
                Filters.Tricyclic,
                Filters.Pentacyclic,
                Filters.NoFilter,
                Filters.Integral,
                Filters.LaplacianIntegral,
                Filters.QIntegral,
                Filters.ChemTree,
                Filters.MinDeg2
                );
    }

    public static GraphFilter getCorrectFilter(ArrayX<String> filter) {
        GraphFilter gf = null;
        switch (filter.getValue()) {
            case Filters.Integral:
                gf = new IntegralFilter();
                break;
            case Filters.LaplacianIntegral:
                gf = new LaplacianIntegralFilter();
                break;
            case Filters.QIntegral:
                gf = new QIntegralFilter();
                break;
            case Filters.ChemTree:
                gf = new ChemTreeFilter();
                break;
            case Filters.MinDeg2:
                gf = new MinDeg2Filter();
                break;
            case Filters.Unicyclic:
                gf = new UnicyclicGraph();
                break;
            case Filters.Bicyclic:
                gf = new BicyclicGraph();
                break;
            case Filters.Tricyclic:
                gf = new TricyclicGraph();
                break;
            case Filters.Pentacyclic:
                gf = new PentacyclicGraph();
                break;
        }
        return gf;
    }
}
