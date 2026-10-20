// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

/*
 * Pluger.java
 *
 * Created on March 22, 2005, 3:17 PM
 */
package graphtea.platform.plugin;

import java.util.List;
import graphtea.platform.StaticUtils;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * GraphTea plugging functionality is provided here.
 * <p/>
 * Class Plugger, is the heart of plugin structure. In order to gather all the plugins, you need to gather all the possible libraries(.jar) files to the project. The method plug() does this using Java Reflection API and Java Class Loader.
 * <p/>
 * After this, you need to read the manifest files. This is done via Java JarFile class. other properties of the plugin like its version , the dependencies and the answer to the question that this plugin should be loaded to GraphTea should be answered here. Method init(File) gets a jar file and checks all the mentioned actions using helper methods verify(), dfs(), load() and remove().
 * <p/>
 * verify() reads a HashMap called depends. This Map is filled in the init() method using the property reader of JarFile class. It reads all the dependencies that are mentioned in the jar file. These are only related to version dependencies.
 * <p/>
 * dfs() tries a DFS algorithm to topologically sort the dependencies tree and then use the sorted trees to give priority to plugin loads.
 *
 * @author Reza Mohammadi
 */
public class Plugger {

    public final String prefix = "graphtea.plugins.";
    public final String postfix = ".Init";
    public final String handler_postfix = ".HandlerInit";
    public static final String PLUGGER_INSTANCE = "main.Plugger";
    public HashMap<String, Long> versions = new HashMap<>();
    public HashMap<String, File> files = new HashMap<>();
    public HashMap<String, Integer> mark = new HashMap<>();
    public HashMap<String, String> initializer = new HashMap<>();
    public HashMap<String, String> configxml = new HashMap<>();
    public HashMap<String, ArrayList<Object[]>> depends = new HashMap<>();
    public HashMap<String, ArrayList<String>> childs = new HashMap<>();
    public URLClassLoader classLoader = null;
    public int activePlugins = 0;

    private BlackBoard blackboard = null;
    private String first = null;

    public Plugger(BlackBoard blackboard) {
        this.blackboard = blackboard;
        blackboard.setData(PLUGGER_INSTANCE, this);
    }

    /**
     * Search plugins directory and add jar files to GraphTea.
     * And add all jar files in plgins directory and lib directory
     * to <code>classLoader</code>
     */
    public void plug() {
        File directory = null;
        try {
            directory = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile();
        } catch (Exception e1) {
            ExceptionHandler.catchException(e1);
        }

        File f = new File(directory, "plugins");
        if (f.isDirectory() && f.canRead()) {
            for (File ff : f.listFiles()) {
                if (ff.isFile() && "jar".equalsIgnoreCase(getExtension(ff))) {
                    init(ff);
                }
            }
            verify();
            if (first != null) {
                // The array this replaced was sized for active plugins but advanced its index
                // for every plugin, so one plugin dropped for a missing dependency overflowed it.
                List<URL> urls = new ArrayList<>();
                File libf = new File(directory, "lib");
                if (libf.isDirectory() && libf.canRead()) {
                    for (File ff : libf.listFiles()) {
                        if (ff.isFile() && "jar".equalsIgnoreCase(getExtension(ff))) {
                            addURL(urls, ff);
                        }
                    }
                }
                for (String name : files.keySet()) {
                    if (mark.get(name) == 0) {
                        addURL(urls, files.get(name));
                    }
                }
                addURL(urls, new File(directory, "extensions"));
                classLoader = new URLClassLoader(urls.toArray(new URL[0]));
                dfs(first);
            } else
                System.err.println("No plugin could be loaded.");
        } else {
            System.err.println("There is no directory named \"plugins\" in " + directory);
        }
    }

    /**
     * Read manifest of a jar file and make that plugin candidate
     * to be loaded.
     *
     * @param ff file object
     */
    public void init(File ff) {
        try (JarFile jf = new JarFile(ff)) {
            Attributes manifest = jf.getManifest().getMainAttributes();
            String name = manifest.getValue("plugin-name");
            String verStr = manifest.getValue("plugin-version");
            String dependsStr = manifest.getValue("plugin-depends");
            if (name == null || verStr == null) {
                System.err.println("Skipping " + ff.getName() + ": no plugin-name/plugin-version in its manifest");
                return;
            }
            Long ver = Long.parseLong(verStr);
            ArrayList<Object[]> dependsArray = new ArrayList<>();
            if (dependsStr != null) {
                String[] depTokens = dependsStr.trim().split("\\s+");
                for (int di = 0; di + 1 < depTokens.length; di += 2) {
                    String depName = depTokens[di];
                    long depVer = Long.parseLong(depTokens[di + 1]);
                    dependsArray.add(new Object[]{depName, depVer});
                }
            }
            versions.put(name, ver);
            mark.put(name, -1);
            files.put(name, ff);
            childs.put(name, new ArrayList<>());
            depends.put(name, dependsArray);
            initializer.put(name, manifest.getValue("plugin-initializer"));
            configxml.put(name, manifest.getValue("plugin-configxml"));
        } catch (Exception ex) {
            ExceptionHandler.catchException(ex);
        }

    }

    private static void addURL(List<URL> urls, File f) {
        try {
            urls.add(f.toURI().toURL());
        } catch (MalformedURLException e) {
            ExceptionHandler.catchException(e);
        }
    }

    /**
     * Remove a "plugin candidate" because of lack of dependencies.
     *
     * @param name Name of plugin
     */
    public void remove(String name) {
        if (mark.get(name) != -2) {
            System.err.println("Removing " + name + " because of lack of dependencies...");
            mark.put(name, -2);
            for (String ch : childs.get(name))
                remove(ch);
            for (Object[] dep : depends.get(name))
                childs.get(dep[0]).remove(name);
        }
    }

    /**
     * Check dependencies
     */
    public void verify() {
        for (String name : versions.keySet()) {
            for (Object[] dep : depends.get(name)) {
                if (mark.get(name) == -1) {
                    String depName = (String) dep[0];
                    Long depVer = (Long) dep[1];
                    Long depPlugVer = versions.get(depName);
                    if (depPlugVer == null || depPlugVer < depVer || mark.get(depName) == -2) {
                        remove(name);
                    } else {
                        childs.get(depName).add(name);
                    }
                }
            }
            if (mark.get(name) == -1)
                mark.put(name, 0);
        }
        activePlugins = 0;
        for (String name : versions.keySet()) {
            if (mark.get(name) == 0) {
                activePlugins++;
                if (first == null && childs.get(name).isEmpty())
                    first = name;
            }
        }
    }

    /**
     * DFS to find a topological sort of plugins.
     *
     * @param name Name of plugin
     */
    public void dfs(String name) {
        mark.put(name, 1);
        for (Object[] dep : depends.get(name)) {
            if (mark.get(dep[0]) == 0)
                dfs((String) dep[0]);
        }
        mark.put(name, 2);
        load(name);
        mark.put(name, 3);
        for (String chstr : childs.get(name)) {
            if (mark.get(chstr) == 0)
                dfs(chstr);
        }
    }

    /**
     * Load and initialize a plugin.
     * if plugin has defined plugin-initializer or has
     * graphtea.plugins.<i>pluginname</i>.Init, Then the
     * init class of plugin will be loaded.
     * Else if plugin has defined plugin-configxml
     * (or using default =
     * "/graphtea/gui/plugin/<i>pluginname</i>/config.xml"),
     * then this function search parents of this plugin
     * to find a <code>PluginHandlerInterface</code> and
     * send path of config.xml to that handler.
     *
     * @param name Name of plugin
     * @see PluginInterface#init(graphtea.platform.core.BlackBoard)
     * @see PluginHandlerInterface#init(String,graphtea.platform.core.BlackBoard)
     */
    public void load(String name) {
        try {
            System.out.println("Loading " + name + " ...");
            try {
                String cName = initializer.get(name);
                if (cName == null)
                    cName = prefix + name + postfix;
                PluginInterface init = (PluginInterface) classLoader.loadClass(cName).getDeclaredConstructor().newInstance();
                init.init(blackboard);
            } catch (ClassNotFoundException cnfe) {
                String confixml = configxml.get(name);
                if (confixml == null)
                    confixml = "/graphtea/plugins/" + name + "/config.xml";
                Iterator<Object[]> it = depends.get(name).iterator();
                PluginHandlerInterface init = null;
                while (init == null) {
                    try {
                        init = (PluginHandlerInterface) classLoader
                                .loadClass(prefix + it.next()[0].toString() + handler_postfix)
                                .getDeclaredConstructor().newInstance();
                        init.init(confixml, blackboard);
                    } catch (ClassNotFoundException cnfe2) {
                        StaticUtils.addExceptiontoLog(cnfe2, blackboard);
                    }
                }
            }
        } catch (Exception ex) {
            ExceptionHandler.catchException(ex);
        }
    }

    /**
     * Return the extension portion of the file's name .
     *
     * @see #getExtension
     * @see javax.swing.filechooser.FileFilter#accept
     */
    public static String getExtension(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }
}
