// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import graphtea.extensions.reports.boundcheck.forall.GraphFilter;
import graphtea.extensions.reports.boundcheck.forall.IterGraphs;
import graphtea.extensions.reports.boundcheck.forall.filters.*;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.Application;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.extension.Extension;
import graphtea.platform.extension.ExtensionHandler;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.plugins.reports.extension.GraphReportExtensionAction;
import graphtea.ui.extension.AbstractExtensionAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class ConjectureChecking implements GraphReportExtension, Parametrizable {
    public ConjectureChecking() {
        filter = Filters.getFilterNames();
        type = Bounds.getBoundNames();
        gens = GeneratorFilters.getGenFilters();
    }

    @Parameter(name = "Bound Check", description = "")
    public boolean conjCheck = false;
    @Parameter(name = "Connected", description = "")
    public boolean connected = true;
    @Parameter(name = "Size", description = "")
    public int Size = 9;
    @Parameter(name = "Up to", description = "")
    public boolean upto = false;
    @Parameter(name = "Filter", description = "")
    public ArrayX<String> filter;
    @Parameter(name = "Graph Generators", description = "")
    public ArrayX<String> gens;
    @Parameter(name = "Type")
    public ArrayX<String> type;
    @Parameter(name="Iterative", description = "")
    public boolean iterative = false;
    @Parameter(name="tree", description = "")
    public boolean tree = false;

    String currentType = "all";

    public String getName() {
        return "Bound Check";
    }
    public String getDescription() {
        return "";
    }

    public Object calculate(GraphModel g) {
        GraphFilter gf=Filters.getCorrectFilter(filter);
        if(gf != null) currentType=gf.getName();
        if(tree) currentType="tree";
        if(gf != null) {
            try {
                IterGraphs.filter(gf.getName(), gf, Size);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        IterGraphs.state=conjCheck+","+iterative+","+currentType+","+Size+","+type.getValue()+","+gens.getValue();
        GraphReportExtensionAction.activeConjCheck=conjCheck;
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