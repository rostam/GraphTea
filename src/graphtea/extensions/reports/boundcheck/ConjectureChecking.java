// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;
import graphtea.extensions.reports.boundcheck.forall.IterGraphs;
import graphtea.extensions.reports.boundcheck.forall.filters.Bounds;
import graphtea.extensions.reports.boundcheck.forall.filters.Filters;
import graphtea.extensions.reports.boundcheck.forall.filters.GeneratorFilters;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.plugins.reports.extension.GraphReportExtensionAction;

public class ConjectureChecking implements GraphReportExtension, Parametrizable {
    public ConjectureChecking() {
        gfilters= Filters.getFilterNames();
        type = Bounds.getBoundNames();
        generators = GeneratorFilters.getGenFilters();
        PostP = new ArrayX<>("No postprocessing");
        PostP.addValidValue("Equality Filter");
        PostP.addValidValue("Max Filter");
        PostP.addValidValue("Min Filter");
    }

    @Parameter(name = "Bound Check", description = "")
    public boolean conjCheck = false;
    @Parameter(name = "Connected", description = "")
    public boolean connected = true;
    @Parameter(name = "Size", description = "")
    public int Size = 9;
    @Parameter(name = "Partition", description = "")
    public boolean part = false;
//    @Parameter(name = "Up to", description = "")
//    public boolean upto = false;
    @Parameter(name = "Filters", description = "")
    public ArrayX<String> gfilters;
    @Parameter(name = "Graph Generators", description = "")
    public ArrayX<String> generators;
    @Parameter(name = "Type")
    public ArrayX<String> type;
    @Parameter(name="Iterative", description = "")
    public boolean iterative = false;
    @Parameter(name="Tree", description = "")
    public boolean tree = false;
    @Parameter(name="Post Processing Type", description = "")
    public static ArrayX<String> PostP;
    @Parameter(name="Post Processing Value",description = "This is the value" +
            "which the equality post-processing filter uses to compare.")
    public static String ppvalue = "0";

    String currentType = "all";

    public String getName() {
        return "Bound Check";
    }

    public String getDescription() {
        return "";
    }

    public Object calculate(GraphModel g) {
        if(!conjCheck) {
            GraphReportExtensionAction.ig=null;
            return "Conjecture Checkign is disabled.";
        }
        if(PostP.getValue().equals("No postprocessing")) RenderTable.noFilter=true;
        if(tree) currentType="tree";
        if(!tree) currentType="all";
        GraphReportExtensionAction.ig=new IterGraphs(conjCheck,iterative,currentType,
                Size,type.getValue(),generators.getValue(),part, PostP.getValue(),
                Filters.getCorrectFilter(gfilters));

        if(conjCheck) return "Conjecture Checking is enabled.";
        return "Conjecture Checkign is disabled.";
    }

    @Override
    public String getCategory() {
        return null;
    }

    @Override
    public String checkParameters() {
        return null;
    }
}