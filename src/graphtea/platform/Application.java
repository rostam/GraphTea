// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform;


import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.Extension;
import graphtea.platform.extension.ExtensionClassLoader;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.plugin.Plugger;
import graphtea.platform.preferences.Preferences;
import graphtea.platform.preferences.lastsettings.StorableOnExit;

import java.io.File;
import java.net.URLClassLoader;


/**
 * The Main runner of program
 * Author: reza
 */
public class Application implements StorableOnExit {

    public static final String VERSION = "1.5.4";
    public static final String VERSION_NAME = "newrooz";
    public static final String WELCOME_URL = "http://graphtheorysoftware.com/v/"+VERSION_NAME;

    public static String USER_ID;

    public static final String APPLICATION_INSTANCE = "GraphTea.main";
    public Plugger plugger = null;
    public static final String POST_INIT_EVENT = "Post Initialization";

    /**
     * @param blackboard
     * @see graphtea.platform.Application#main(String[])
     */
    public void run(BlackBoard blackboard) {
        try {
            Preferences p = new Preferences(blackboard);
            GSplash gs = new GSplash();
            gs.showMessages();
            loadPlugins();
            loadExtensions(blackboard);
            gs.setVisible(false);
            gs.stopShowing();
            blackboard.setData(POST_INIT_EVENT, "Pi");
        } catch (Exception e) {
            ExceptionHandler.catchException(e);
        }
//        UI.getGFrame(blackboard).setTitle("GraphTea Graph Editor-1");
    }

    private void loadPlugins() {
        plugger.plug();

    }

    /**
     * The default BlackBoard which plugins/extensions are connected to
     */
    public static BlackBoard blackboard;

    public static BlackBoard getBlackBoard() {
        return blackboard;
    }

    /**
     * @return
     * @see graphtea.platform.Application#main(String[])
     */
    public BlackBoard init() {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(blackboard));
        blackboard = new BlackBoard();
        blackboard.setData(APPLICATION_INSTANCE, this);
        blackboard.setData("SETTINGS", SETTINGS);

        plugger = new Plugger(blackboard);
        run(blackboard);
        return blackboard;

    }

    /**
     * load all extensions from /extensions directory
     *
     * @param blackboard
     */
    public void loadExtensions(BlackBoard blackboard) {
        ExtensionClassLoader.cl = getExtensionsClassLoader();
        String path = null;
        try {
            path = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
            System.out.println(path);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        ExtensionClassLoader e = new ExtensionClassLoader(path + File.separator + "extensions");
        for (String c : e.classesData.keySet()) {
            try {
                Class s = getExtensionsClassLoader().loadClass(c);
                Object extension = ExtensionLoader.loadExtension(s);
                if (extension != null) {
                    SETTINGS.registerSetting(extension, "Extention Options");
                    ExtensionLoader.handleExtension(blackboard, extension);
                }
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
                StaticUtils.addExceptiontoLog(e1, blackboard);
            }
        }
        for (File f : e.getUnknownFilesFound()) {
            Extension extension = ExtensionLoader.loadUnknownExtension(f, blackboard);
            ExtensionLoader.handleExtension(blackboard, extension);
        }
    }

    protected URLClassLoader getExtensionsClassLoader() {
        return plugger.classLoader;
    }

    /**
     * Runs Application in these steps:
     * 0- starts GraphTea exception handler, to catch uncaught exceptions<br>
     * 1- shows a splash on the screen, and redirect System.out to the splash screen<br>
     * 2- loads plugins which are jar files located in plugins directory<br>
     * 3- loads extensions which are files located in extensions directory<br>
     * 4- fires a <code>Application#POST_INIT_EVENT</code> on blackboard<br>
     * 5- hides the splash screen<br>
     *
     * @param args
     */
    public static void main(String[] args) {
        new Application().init();
    }

}
