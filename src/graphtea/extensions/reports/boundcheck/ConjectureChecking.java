// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.plugins.reports.extension.GraphReportExtensionAction;

public class ConjectureChecking implements GraphReportExtension, Parametrizable {
    @Parameter(name = "Bound Check", description = "")
    public boolean conjCheck = false;
    @Parameter(name = "Connected", description = "")
    public boolean connected = true;
    @Parameter(name = "Size", description = "")
    public int Size = 9;
    @Parameter(name = "Up to", description = "")
    public boolean upto = false;
    @Parameter(name = "Integral", description = "")
    public boolean Integral = false;
    @Parameter(name = "Laplacian Integral", description = "")
    public boolean LaplacianIntegral = false;
    @Parameter(name = "Q-Integral", description = "")
    public boolean QIntegral = false;
    @Parameter(name="Upper Bound", description = "")
    public boolean upperBound = true;
    @Parameter(name="Strict Upper Bound", description = "")
    public boolean strictUpperBound = false;
    @Parameter(name="Lower Bound", description = "")
    public boolean lowerBound = false;
    @Parameter(name="Stric Lower Bound", description = "")
    public boolean strictLowerBound = false;
    @Parameter(name="Iterative", description = "")
    public boolean iterative = false;


    String currentType = "";

    public String getName() {
        return "Bound Check";
    }
    public String getDescription() {
        return "";
    }
    public Object calculate(GraphModel g) {
        GraphReportExtensionAction.activeConjCheck = conjCheck;
        GraphReportExtensionAction.connected = connected;
        GraphReportExtensionAction.Size = Size;
        GraphReportExtensionAction.upto = upto;
        GraphReportExtensionAction.upperBound = upperBound;
        GraphReportExtensionAction.lowerBound = lowerBound;
        GraphReportExtensionAction.strictLowerBound = strictLowerBound;
        GraphReportExtensionAction.strictUpperBound = strictUpperBound;
        GraphReportExtensionAction.iterative = iterative;
        if (Integral) currentType = "Integral";
        if (LaplacianIntegral) currentType = "LaplacianIntegral";
        if (QIntegral) currentType = "QIntegral";

        System.out.println("type " + currentType);
        AllGraphs ag = new AllGraphs(this,currentType);
        ag.filterGraphs(Size);

        GraphReportExtensionAction.currentType=currentType;

        if(conjCheck) return "Conjecture Checking is enabled.";
        return "Conjecture Checkign is disabled.";
    }

    public int getSize() {
        return Size;
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public String checkParameters() {
        return null;
    }
}