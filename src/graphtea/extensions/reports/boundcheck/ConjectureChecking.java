// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;

import graphtea.extensions.reports.boundcheck.forall.GraphFilter;
import graphtea.extensions.reports.boundcheck.forall.IterGraphs;
import graphtea.extensions.reports.boundcheck.forall.filters.IntegralFilter;
import graphtea.extensions.reports.boundcheck.forall.filters.LaplacianIntegralFilter;
import graphtea.extensions.reports.boundcheck.forall.filters.QIntegralFilter;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.plugins.reports.extension.GraphReportExtensionAction;

import java.io.IOException;

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

    String currentType = "all";
    String bound="no";

    public static String state = "";

    public String getName() {
        return "Bound Check";
    }
    public String getDescription() {
        return "";
    }
    public Object calculate(GraphModel g) {
        GraphFilter gf=null;
        if (Integral) {gf=new IntegralFilter();}
        else if (LaplacianIntegral) {gf=new LaplacianIntegralFilter();}
        else if (QIntegral) {gf=new QIntegralFilter();}
        if(gf != null) currentType=gf.getName();

        if(upperBound) bound="upper";
        else if(lowerBound) bound="lower";
        else if(strictUpperBound) bound="strictUpper";
        else if(strictLowerBound) bound="strictLower";

        if(gf != null) {
            try {
                IterGraphs.filter(gf.getName(), gf, Size);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        state = ""+conjCheck+" "+iterative+" "+currentType+" "+Size+" "+bound;
        GraphReportExtensionAction.state=state;

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