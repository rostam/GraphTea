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
import java.util.Map.Entry;
import java.util.StringTokenizer;
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
            System.out.println(directory);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        File f = new File(directory, "plugins");
        if (f.isDirectory() && f.canRead()) {
            for (File ff : f.listFiles()) {
                if (ff.isFile() && "jar".equalsIgnoreCase(getExtension(ff))) {
                    init(ff);
                }
            }
            System.out.println("------------------------------------------------------------");
            verify();
            System.out.println("------------------------------------------------------------");
            if (first != null) {
                int libCount = 0;
                File libf = new File(directory, "lib");
                ArrayList<URL> libURLs = new ArrayList<>();
                if (libf.isDirectory() && libf.canRead()) {
                    for (File ff : libf.listFiles()) {
                        if (ff.isFile() && "jar".equalsIgnoreCase(getExtension(ff))) {
                            try {
                                libURLs.add(ff.toURI().toURL());
                                System.out.println("Library file " + ff + " added.");
                            } catch (MalformedURLException e) {
                                ExceptionHandler.catchException(e);
                            }
                            libCount++;
                        }
                    }
                }
                URL[] urls = new URL[activePlugins + libCount + 1];
                int i = 0;
                for (URL libURL : libURLs)
                    urls[i++] = libURL;
                for (String name : files.keySet()) {
                    if (mark.get(name) == 0)
                        try {
                            urls[i] = files.get(name).toURI().toURL();
                        } catch (MalformedURLException e) {
                            System.out.println(name + " [" + files.get(name).getPath() + "]");
                            ExceptionHandler.catchException(e);
                        }
                    i++;
                }
                try {
                    urls[i] = new File(directory, "extensions").toURI().toURL();
                } catch (MalformedURLException e) {
                    ExceptionHandler.catchException(e);
                }
                classLoader = new URLClassLoader(urls);
                System.out.println("" + i + " jar file(s) loaded.");
                System.out.println("------------------------------------------------------------");
                dfs(first);
            } else
                System.out.println("Can't Load Any Plugin!");
            System.out.println("------------------------------------------------------------");
        } else {
            System.out.println("There is no directory named \"plugins\" in " +
                    directory.getAbsolutePath());
        }
    }

    /**
     * Read manifest of a jar file and make that plugin candidate
     * to be loaded.
     *
     * @param ff file object
     */
    public void init(File ff) {
        try {
            JarFile jf = new JarFile(ff);
            System.out.println("------------------------------------------------------------");
            String name = jf.getManifest().getMainAttributes().getValue("plugin-name");
            String verStr = jf.getManifest().getMainAttributes().getValue("plugin-version");
            String dependsStr = jf.getManifest().getMainAttributes().getValue("plugin-depends");
            if (name == null || verStr == null) {
                System.out.println("Skipping " + name + "(" + verStr + ")");
                return;
            }
            Long ver = Long.parseLong(verStr);
            System.out.println("Detected " + name + "(" + ver + ") ...");
            for (Entry<Object, Object> s : jf.getManifest().getMainAttributes().entrySet()) {
                if (!"plugin-name".equals(s.getKey()) && !"plugin-version".equals(s.getKey()))
                    System.out.println(s.getKey() + " : " + s.getValue());
            }
            ArrayList<Object[]> dependsArray = new ArrayList<>();
            if (dependsStr != null) {
                StringTokenizer st = new StringTokenizer(dependsStr);
                while (st.hasMoreElements()) {
                    String depName = st.nextToken();
                    String depVerStr = st.nextToken();
                    long depVer = Long.parseLong(depVerStr);
                    dependsArray.add(new Object[]{depName, depVer});
                }
            }
            versions.put(name, ver);
            mark.put(name, -1);
            files.put(name, ff);
            childs.put(name, new ArrayList<>());
            depends.put(name, dependsArray);
            initializer.put(name, jf.getManifest().getMainAttributes().getValue("plugin-initializer"));
            configxml.put(name, jf.getManifest().getMainAttributes().getValue("plugin-configxml"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Remove a "plugin candidate" because of lack of dependencies.
     *
     * @param name Name of plugin
     */
    public void remove(String name) {
        if (mark.get(name) != -2) {
            System.out.println("Removing " + name + " because of lack of dependencies...");
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
            System.out.println("Plugin " + name + "(" + mark.get(name) + ") has " + childs.get(name).size() + " child(ren)!");
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
        System.out.println("DFS on " + name + " ...");
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
                PluginInterface init = (PluginInterface) classLoader.loadClass(cName).newInstance();
                init.init(blackboard);
            } catch (ClassNotFoundException cnfe) {
                String confixml = configxml.get(name);
                if (confixml == null)
                    confixml = "/graphtea/plugins/" + name + "/config.xml";
                Iterator<Object[]> it = depends.get(name).iterator();
                PluginHandlerInterface init = null;
                while (init == null) {
                    try {
                        init = (PluginHandlerInterface) classLoader.loadClass(prefix + it.next()[0].toString() + handler_postfix).newInstance();
                        init.init(confixml, blackboard);
                    } catch (ClassNotFoundException cnfe2) {
                        StaticUtils.addExceptiontoLog(cnfe2, blackboard);
                    }
                }
            }
            System.out.println("Loaded : " + name + ".");
        } catch (Exception ex) {
            ex.printStackTrace();
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
