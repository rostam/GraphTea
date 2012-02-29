// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main;

import graphlab.graph.GraphUtils;
import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.AbstractGraphRenderer;
import graphlab.graph.graph.GraphModel;
import graphlab.library.algorithms.util.LibraryUtils;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.StaticUtils;
import graphlab.plugins.main.core.AlgorithmUtils;
import graphlab.plugins.main.core.CorePluginMethods;
import graphlab.plugins.main.saveload.SaveLoadPluginMethods;
import graphlab.plugins.main.select.SelectPluginMethods;
import graphlab.ui.UIUtils;

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