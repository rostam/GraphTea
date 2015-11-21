// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import graphtea.extensions.reports.ChromaticNumber;
import graphtea.extensions.reports.boundcheck.forall.GraphFilter;
import graphtea.extensions.reports.boundcheck.forall.IterGraphs;
import graphtea.extensions.reports.boundcheck.forall.Sizes;
import graphtea.extensions.reports.boundcheck.forall.filters.Bounds;
import graphtea.extensions.reports.boundcheck.forall.filters.Filters;
import graphtea.extensions.reports.boundcheck.forall.filters.GeneratorFilters;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.ui.GraphColoringRenderer;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.plugins.reports.extension.GraphReportExtensionAction;

import java.io.IOException;

public class ConjectureChecking implements GraphReportExtension, Parametrizable {

    public ConjectureChecking() {
        filters = Filters.getFilterNames();
        type = Bounds.getBoundNames();
        generators = GeneratorFilters.getGenFilters();
        part=new ArrayX<>(0);
        for(int i=0;i<14;i++) {
            part.addValidValue(i+1);
            if(i==13)   Sizes.sizes.put("all10"+(i+1),9);
            else        Sizes.sizes.put("all10"+(i+1),901274);
        }

        postproc = new ArrayX<>("No postprocessing");
        postproc.addValidValue("1000 biggest values on the second column");
        postproc.addValidValue("1000 smallest values on the second column");
    }

    @Parameter(name = "Bound Check", description = "")
    public boolean conjCheck = false;
    @Parameter(name = "Connected", description = "")
    public boolean connected = true;
    @Parameter(name = "Size", description = "")
    public int Size = 9;
    @Parameter(name = "Partitions", description = "")
    public ArrayX<Integer> part;
    @Parameter(name = "Up to", description = "")
    public boolean upto = false;
    @Parameter(name = "Filter", description = "")
    public ArrayX<String> filters;
    @Parameter(name = "Graph Generators", description = "")
    public ArrayX<String> generators;
    @Parameter(name = "Type")
    public ArrayX<String> type;
    @Parameter(name="Iterative", description = "")
    public boolean iterative = false;
    @Parameter(name="Tree", description = "")
    public boolean tree = false;
    @Parameter(name="Chemical tree", description = "")
    public boolean chemtree = false;
    @Parameter(name="Post Processing", description = "")
    public ArrayX<String> postproc;

    String currentType = "all";

    public String getName() {
        return "Bound Check";
    }
    public String getDescription() {
        return "";
    }

    public Object calculate(GraphModel g) {
        GraphFilter gf;
        if(chemtree) gf=Filters.getCorrectFilter(filters,Filters.ChemTree);
        else gf=Filters.getCorrectFilter(filters,null);

        if(gf != null) currentType=gf.getName();
        if(tree) currentType="tree";
        if(chemtree) currentType="chemtree";
        if(!tree && !chemtree) currentType="all";
        GraphReportExtensionAction.ig=new IterGraphs(conjCheck,iterative,currentType,
                Size,type.getValue(),generators.getValue(),part.getValue(),postproc.getValue());
        if(gf != null) {
            try {
                if(tree) {
                    GraphReportExtensionAction.ig.filter("tree", Size, gf);
                } else {
                    GraphReportExtensionAction.ig.filter("all", Size, gf);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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