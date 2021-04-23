// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports.boundcheck;
import graphtea.extensions.io.g6format.SaveGraph6Format;
import graphtea.extensions.reports.boundcheck.forall.IterGraphs;
import graphtea.extensions.reports.boundcheck.forall.Sizes;
import graphtea.extensions.reports.boundcheck.forall.filters.Bounds;
import graphtea.extensions.reports.boundcheck.forall.filters.Filters;
import graphtea.extensions.reports.boundcheck.forall.filters.GeneratorFilters;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.RenderTable;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.plugins.reports.extension.GraphReportExtensionAction;

import javax.swing.*;
import java.io.File;
import java.util.Vector;

public class ConjectureChecking implements GraphReportExtension, Parametrizable {
    public ConjectureChecking() {
        gfilters = Filters.getFilterNames();
        boundType = Bounds.getBoundNames();
        generators = GeneratorFilters.getGenFilters();
        PostP = new ArrayX<>("No postprocessing");
        PostP.addValidValue("Equality Filter");
        PostP.addValidValue("Max Filter");
        PostP.addValidValue("Min Filter");
    }

    @Parameter(name = "Bound Check", description = "Bound Check")
    public boolean conjCheck = false;
    @Parameter(name = "Connected", description = "Connected")
    public boolean connected = true;
    @Parameter(name = "Num Of Nodes", description = "Num Of Nodes")
    public int numOfNodes = 9;
//    @Parameter(name = "Up to", description = "")
//    public boolean upto = false;
    @Parameter(name = "Filter", description = "Filter")
    public ArrayX<String> gfilters;
    @Parameter(name = "Column ID for Filter")
    public int columnIDForFilter = 1;
    @Parameter(name = "Graph Generators", description = "Generators")
    public ArrayX<String> generators;
    @Parameter(name = "Bound Type", description = "The type of bound.")
    public ArrayX<String> boundType;
    @Parameter(name="Iterative", description = "Is it iterative?")
    public boolean iterative = false;
    @Parameter(name="Post Processing Type", description = "The type of post processing")
    public static ArrayX<String> PostP;
    @Parameter(name="Post Processing Value",description = "This is the value" +
            "which the equality post-processing filter uses to compare.")
    public static String ppvalue = "0";
    @Parameter(name="Graph Type", description = "Type of graphs")
    public ArrayX<String> GraphType=new ArrayX<>("all","tree","custom");

    String currentType = "all";
    int size = 0;

    public String getName() {
        return "Bound Check";
    }

    public String getDescription() {
        return "";
    }

    public Object calculate(GraphModel g) {
        if(PostP.getValue().equals("No postprocessing")) {
            RenderTable.noFilter=true;
        }
        if(GraphType.getValue().equals("custom")) {
            currentType=JOptionPane.showInputDialog("Please enter the cutom graph6 format file:");
            size= Integer.parseInt(JOptionPane.showInputDialog("Please enter the number of graphs in file:"));
        } else {
            currentType=GraphType.getValue();
            currentType=currentType+numOfNodes;
            size=  Sizes.sizes.get(currentType);
        }

        if(!conjCheck) {
            GraphReportExtensionAction.ig=null;
            IterGraphs itg=new IterGraphs(conjCheck,iterative,currentType,
                    size,boundType.getValue(),generators.getValue(), PostP.getValue(),
                    Filters.getCorrectFilter(gfilters), columnIDForFilter);
            Vector<GraphModel> gs = itg.wrapper_generate();
            String nameOfFile = JOptionPane.showInputDialog("Please enter tthe name of a file in which the " +
                    "graphs will be saved.:");
            SaveGraph6Format sgf = new SaveGraph6Format();
            File f = new File(nameOfFile);
            for(GraphModel gg : gs) {
                try {
                    sgf.write(f, gg);
                } catch (GraphIOException e) {
                    e.printStackTrace();
                }
            }
            return "The bound check is disabled. " +
                    "The graphs are generated and saved in the file " + nameOfFile;
        }

        GraphReportExtensionAction.ig=new IterGraphs(conjCheck,iterative,currentType,
                size,boundType.getValue(),generators.getValue(), PostP.getValue(),
                Filters.getCorrectFilter(gfilters), columnIDForFilter);

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