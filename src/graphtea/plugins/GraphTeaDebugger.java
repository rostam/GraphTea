// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins;


//import graphtea.extensions.graphactions.GeneralizedPetersonIndepSetListerAction;
//import graphtea.extensions.connectivity.Redis;
import graphtea.extensions.io.*;
//import phylogenetic.TripNet;
import graphtea.extensions.reports.MaxIndependentSetReport;
import graphtea.extensions.actions.LineGraph;
import graphtea.extensions.actions.GraphPower;
import graphtea.platform.Application;
import graphtea.platform.GSplash;
import graphtea.platform.StaticUtils;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionOccuredData;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.Extension;
import graphtea.platform.extension.ExtensionClassLoader;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.preferences.Preferences;
import graphtea.samples.extensions.BinaryTreeGenerator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * this class creathed ONLY for test reasons, don't mistake it with main class of program which is GraphTea.
 * GraphTea loads the plugins from the jar files in the plugins directory, but in graphtea they are loaded manually
 * both of them load plugins from their setBlackBoard file setBlackBoard() method, the GraphTeaDebugger class
 * is for test purposes, so if you want to debug your plugins just load it manually like
 * other plugins in the run method,... try to load it as the last plugin for avoiding dependency problems
 *
 * @author azin azadi
 * @see graphtea.platform.Application
 */
//todo: dar paste label-ha
public class GraphTeaDebugger extends Application {
    public void run(BlackBoard b) {
//        try {
//            UIManager.setLookAndFeel();
//        } catch (Exception e) {
//            ExceptionHandler.catchException(e);
//        }
        try {
//            plugger = new Plugger(b);
            Preferences p = new Preferences(b);
            GSplash gs = new GSplash();
            gs.showMessages();
            b.setData("SETTINGS", SETTINGS);

            new graphtea.plugins.main.Init().init(b);
            new graphtea.plugins.commandline.Init().init(b);
            new graphtea.plugins.visualization.Init().init(b);
            new graphtea.plugins.algorithmanimator.Init().init(b);
            new graphtea.plugins.reports.Init().init(b);
            new graphtea.plugins.graphgenerator.Init().init(b);
            new graphtea.plugins.commonplugin.Init().init(b);
            new graphtea.plugins.connector.Init().init(b);
            loadExtensions(b);

            // loading Extensions 
            StaticUtils.loadSingleExtension(MaxIndependentSetReport.class);
//            StaticUtils.loadSingleExtension(RadiusReport.class);
            StaticUtils.loadSingleExtension(LineGraph.class);
//            StaticUtils.loadSingleExtension(TotalGraph1.class);
            StaticUtils.loadSingleExtension(GraphPower.class);
            StaticUtils.loadSingleExtension(LoadSimpleGraph.class);
            StaticUtils.loadSingleExtension(SaveSimpleGraph.class);
//            StaticUtils.loadSingleExtension(GeneralizedPetersonIndepSetListerAction.class);
            StaticUtils.loadSingleExtension(LatexWriter.class);
            StaticUtils.loadSingleExtension(LatexCAD.class);
            StaticUtils.loadSingleExtension(BinaryTreeGenerator.class);
            StaticUtils.loadSingleExtension(LoadNetGraph.class);
            //StaticUtils.loadSingleExtension(Redis.class);
            //StaticUtils.loadSingleExtension(TripNet.class);


            b.setData(POST_INIT_EVENT, "Pi");
//            UI.getGFrame(b).setTitle("GraphTea");
            gs.setVisible(false);
            gs.stopShowing();

//            Thread.sleep(1000);

        } catch (Throwable e) {
            ExceptionOccuredData bod = new ExceptionOccuredData(e);
            b.setData(ExceptionOccuredData.EVENT_KEY, bod);
        }
    }

//    public void loadExtensions(BlackBoard blackboard) {
////        try {
////            ExtensionClassLoader.cl = new URLClassLoader(new URL[]{new File("extensions").toURL()}, ClassLoader.getSystemClassLoader());// plugger.classLoader;
////        } catch (MalformedURLException e) {
////            ExceptionHandler.catchException(e);
////        }
//
////----------------
////        String dir = "extensions";
////        loadexts(dir, blackboard);
////        loadexts("graphtea\\extensions", blackboard);
//
//    }

    private void loadexts(String dir, BlackBoard blackboard) {
        ExtensionClassLoader e = new ExtensionClassLoader(dir);
        for (String c : e.classesData.keySet()) {
            try {
                Class s = ExtensionClassLoader.cl.loadClass(c);
                StaticUtils.loadSingleExtension(s);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
//        for (Class c:e.getLoadedClasses()) {
        }
        for (File f : e.getUnknownFilesFound()) {
            Extension extension = ExtensionLoader.loadUnknownExtension(f, blackboard);
            ExtensionLoader.handleExtension(blackboard, extension);
        }
    }


    protected URLClassLoader getExtensionsClassLoader() {
        try {
            return new URLClassLoader(new URL[]{new File("extensions").toURL()});
        } catch (MalformedURLException e) {
            ExceptionHandler.catchException(e);
        }
        return null;
    }

    public static void main(String[] args) {
        new GraphTeaDebugger().init();
    }
}
