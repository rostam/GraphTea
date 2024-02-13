package graphtea.extensions.reports.boundcheck.forall.filters;

import graphtea.platform.lang.ArrayX;

/**
 * Created by rostam on 14.10.15.
 * @author M. Ali Rostami
 */
public class Bounds {
    public static final String Upper = "Upper";
    public static final String Lower = "Lower";
    public static final String StrictLower = "Strict Lower";
    public static final String StrictUpper = "Strict Upper";

    public static ArrayX<String> getBoundNames() {
        return new ArrayX<>(
                Bounds.Upper,
                Bounds.Lower,
                Bounds.StrictLower,
                Bounds.StrictUpper
        );
    }
}
