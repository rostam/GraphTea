package graphtea.extensions.reports.boundcheck.forall.filters;


import graphtea.extensions.reports.boundcheck.forall.GraphFilter;
import graphtea.platform.lang.ArrayX;

/**
 * Created by rostam on 14.10.15.
 */
public class Filters {
    public final static String Integral = "Integral";
    public final static String LaplacianIntegral = "Laplacian Integral";
    public final static String QIntegral = "Q-Integral";
    public final static String NoFilter = "No Filter";
    public final static String ChemTree = "Chemical Tree";
    public final static String MinDeg2 = "Minimum Degree 2";

    public static ArrayX<String> getFilterNames() {
        return new ArrayX<>(
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
        if (filter.getValue().equals(Filters.Integral)) {
            gf = new IntegralFilter();
        } else if (filter.getValue().equals(Filters.LaplacianIntegral)) {
            gf = new LaplacianIntegralFilter();
        } else if (filter.getValue().equals(Filters.QIntegral)) {
            gf = new QIntegralFilter();
        } else if (filter.getValue().equals(Filters.ChemTree)) {
            gf = new ChemTreeFilter();
        } else if (filter.getValue().equals(Filters.MinDeg2)) {
            gf = new MinDeg2Filter();
        }
        return gf;
    }
}
