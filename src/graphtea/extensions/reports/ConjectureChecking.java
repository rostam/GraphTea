// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports;

import graphtea.graph.graph.GraphModel;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.plugins.reports.extension.GraphReportExtensionAction;

public class ConjectureChecking implements GraphReportExtension, Parametrizable {
    @Parameter(name = "Conjecture Checking", description = "")
    public boolean conjCheck = false;
    @Parameter(name = "All Connected Graphs", description = "")
    public boolean connected = true;
    @Parameter(name = "Size", description = "")
    public int Size = 9;
    @Parameter(name = "Up to", description = "")
    public boolean upto = false;
    @Parameter(name = "Integral", description = "")
    public boolean Integral = false;
    @Parameter(name = "Laplacian Integral", description = "")
    public boolean LaplacianIntegral = false;
    @Parameter(name = "Signless Laplacian Integral", description = "")
    public boolean SignlessLaplacianIntegral = false;

    public String getName() {
        return "Conjecture Checking";
    }
    public String getDescription() {
        return "";
    }
    public Object calculate(GraphModel g) {
        GraphReportExtensionAction.activeConjCheck = conjCheck;
        GraphReportExtensionAction.connected = connected;
        GraphReportExtensionAction.Size = Size;
        GraphReportExtensionAction.upto = upto;
        GraphReportExtensionAction.Integral = Integral;
        GraphReportExtensionAction.LaplacianIntegral = LaplacianIntegral;
        GraphReportExtensionAction.SignlessLaplacianIntegral = SignlessLaplacianIntegral;

        if(conjCheck) return "Conjecture Checking is enabled.";
        return "Conjecture Checkign is disabled.";
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