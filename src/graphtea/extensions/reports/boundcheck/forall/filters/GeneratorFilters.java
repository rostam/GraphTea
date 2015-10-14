package graphtea.extensions.reports.boundcheck.forall.filters;

import graphtea.extensions.reports.boundcheck.forall.ForAllParameterShower;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.extension.Extension;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.ui.ParameterShower;
import graphtea.ui.extension.AbstractExtensionAction;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by rostam on 14.10.15.
 * Generator Filters
 */
public class GeneratorFilters {
    public static HashMap<String, AbstractAction> hm = ExtensionLoader.loadedInstances;

    public static ArrayX<String> getGenFilters() {
        ArrayX ax = new ArrayX("");
        for (String s : hm.keySet()) {
            if (s.contains("graphtea.extensions.generators.")) {
                Extension ext = ((AbstractExtensionAction) hm.get(s)).getTarget();
                ax.addValidValue(ext.getName());
            }
        }
        return ax;
    }

    public static GraphModel generateGraphs(String name) {
        Extension ext = ((AbstractExtensionAction) hm.get(name)).getTarget();
        ForAllParameterShower ps = new ForAllParameterShower();
        ps.show((Parametrizable) ext);
        GraphModel g = ((AbstractExtensionAction) hm.get(name)).generateGraph();
        return new GraphModel();
    }
}
