// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.help;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.StaticUtils;
import graphtea.platform.plugin.Plugger;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Utils {

    public static String helpPreDestDir = "Doc/";

    public static Hashtable<String, URL> pluginHelps = new Hashtable<String, URL>();

    /**
     * Register a help entry on help menu.
     *
     * @param blackboard blackboard!
     * @param pluginName name of plugin (= jar file) containing help files.
     * @param title      Title that will be shown to user for help entry
     * @param filter     Prefix path of help content in jar file
     * @return <code>URL</code> of index of registered help.
     *         This url will be opened when user selects the registered
     *         title of help.
     */
    public static URL registerHelpPlugin(BlackBoard blackboard,
                                         String pluginName, String title, String filter) {
        try {
            Plugger plugger = blackboard.getData(Plugger.PLUGGER_INSTANCE);
            if (plugger == null)
                return null;
            String index = "index" + plugger.versions.get(pluginName) + ".html";
            String dest = Utils.helpPreDestDir + pluginName;
            File f = new File(helpPreDestDir);
            if (!f.isDirectory())
                f.mkdir();
            f = new File(dest);
            if (!f.isDirectory())
                f.mkdir();
            f = new File(dest + "/" + index);
            if (!f.isFile()) {
                JarFile jarFile = new JarFile(plugger.files.get(pluginName));
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry je = entries.nextElement();

                    if (!je.getName().startsWith(filter))
                        continue;

                    System.out.println("Extracting " + je.getName());
                    String fname = je.getName().substring(filter.length());

                    if (je.isDirectory())
                        (new File(dest + "/" + fname)).mkdir();
                    else {
                        File efile = new File(dest, fname);

                        InputStream in = new BufferedInputStream(jarFile
                                .getInputStream(je));
                        OutputStream out = new BufferedOutputStream(
                                new FileOutputStream(efile));

                        StaticUtils.copyStream(in, out);

                        out.flush();
                        out.close();
                        in.close();
                    }
                }
            }
            URL url = f.toURL();
            pluginHelps.put(title, url);
            System.out.println("Help of plugin '" + pluginName
                    + "' with name '" + title + "' Registered.");
            return url;
        } catch (Exception e) {
            StaticUtils.addExceptiontoLog(e, blackboard);
        }
        return null;
    }

}
