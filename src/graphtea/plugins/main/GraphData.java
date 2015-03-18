// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main;

import graphtea.graph.GraphUtils;
import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.AbstractGraphRenderer;
import graphtea.graph.graph.GraphModel;
import graphtea.library.algorithms.util.LibraryUtils;
import graphtea.platform.StaticUtils;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.main.core.AlgorithmUtils;
import graphtea.plugins.main.core.CorePluginMethods;
import graphtea.plugins.main.saveload.SaveLoadPluginMethods;
import graphtea.plugins.main.select.SelectPluginMethods;
import graphtea.ui.UIUtils;

/**
 * This class provides usefull information and methods all in one place
 * @author azin azadi
 */
public class GraphData {
    BlackBoard blackboard;
    public SelectPluginMethods select;
    public SaveLoadPluginMethods saveLoad;
    AlgorithmUtils algorithmUtils = new AlgorithmUtils();
    StaticUtils platformUtils = new StaticUtils();
    GraphUtils graphUtils = new GraphUtils();
    UIUtils uiUtils = new UIUtils();
    LibraryUtils libraryUtils = new LibraryUtils();
//    public RightClickPluginMethods rightclick;
//    public ReporterPluginMethods browser;
//    public PreviewPluginMethods preview;
    //    public HelpPluginMethods help;
    public CorePluginMethods core;

    public GraphData(BlackBoard blackboard) {
        this.blackboard = blackboard;
        select = new SelectPluginMethods(blackboard);
        saveLoad = new SaveLoadPluginMethods(blackboard);
//        rightclick = new RightClickPluginMethods();
//        browser = new ReporterPluginMethods();
//        preview = new PreviewPluginMethods();
//        help = new HelpPluginMethods(blackboard);
        core = new CorePluginMethods(blackboard);

    }

    /**
     * @return returns the current graph
     */
    public GraphModel getGraph() {
        return blackboard.getData(GraphAttrSet.name);
    }

    public AbstractGraphRenderer getGraphRenderer() {
        return AbstractGraphRenderer.getCurrentGraphRenderer(blackboard);
    }

    public BlackBoard getBlackboard() {
        return blackboard;
    }

}