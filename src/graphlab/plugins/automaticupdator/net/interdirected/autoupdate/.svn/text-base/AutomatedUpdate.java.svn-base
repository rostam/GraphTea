// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.automaticupdator.net.interdirected.autoupdate;

import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import java.io.*;
import java.util.prefs.Preferences;

import graphlab.platform.core.exception.ExceptionHandler;

/**
 * Downloaded from: http://sourceforge.net/projects/javaautoupdater/
 *
 * @author Michael Quattlebaum
 */
public class AutomatedUpdate {

    public static Preferences prefs;
    private static SVNClientManager ourClientManager;
    private static UpdateEventHandler myEventHandler;
    private static GuiStatusScreen gss;
    private static boolean usegui;

    private static final AntLauncher ant = new AntLauncher();

    /**
     * @param args Not currently used. All parameters are stored in the
     *             conf/autoupdate.xml file.
     */
    public static void main(String[] args) {
        System.out.println("Starting AutomatedUpdate.java");
        // So that we can execute this by instantiating the class if we want
        // later on.
        run(args);
    }

    /**
     * @param args Not currently used. All parameters are stored in the
     *             conf/autoupdate.xml file.
     */
    public static void run(String[] args) {
        // Get all of our properties and prepare for our update.
        LoadPrefsFromFile();
        SoftwareUpdateable upd = null;
        String url = "http://graphlab.sharif.edu/svn";
//        String url = prefs.get("repositoryurl", null);
        String module_url = "head/binary";
//        String module_url = prefs.get("moduleurl", null);
        String name = "";
//        String name = prefs.get("repositoryusername", null);
        String password = "";
//        String password = prefs.get("repositorypassword", null);
        String applicationdirectory = "";
//        String applicationdirectory = prefs.get("applicationdirectory", null);
        String buildfile = prefs.get("antbuildfile", "build.xml");
        String antlocation = prefs.get("antlocation", ".");
        String updatecheckclass = prefs.get("updatecheckclass", ".");
        usegui = prefs.getBoolean("usegui", true);
        boolean autoclose = prefs.getBoolean("autoclose", false);
        boolean showbutton = !autoclose;
//        try {
//            upd = getSoftwareUpdateObject(updatecheckclass);
//            System.out.println("Update Object:" + upd);
//        }
//        catch (Exception e) {
//            ExceptionHandler.catchException(e);
//        }
        if (true) {
//        if (upd.isUpdatable()) {
            System.out.println("isUpdatable is true");
            // Display the GUI status screen
            if (usegui) {
                gss = new GuiStatusScreen(prefs.get("windowtitle", ""), prefs.get(
                        "instructions", ""), prefs.getInt("screenwidth", 500),
                        prefs.getInt("screenheight", 600), prefs.get("buttontext",
                        "Update is Complete"), showbutton, "", prefs.getInt("imagewidth", 0),
                        prefs.getInt("imageheight", 0));
                gss.appendText("Checking for file update.");
            } else System.out.println("Checking for file update.");
            // Setup the respository connections.
            DAVRepositoryFactory.setup();
            SVNRepository repository = null;
            ISVNOptions options = SVNWCUtil.createDefaultOptions(true);

            try {
                SVNRepositoryFactoryImpl.setup();
                repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(url + "/" + module_url));
                ISVNAuthenticationManager authManager = SVNWCUtil
                        .createDefaultAuthenticationManager(name, password);
                repository.setAuthenticationManager(authManager);

                ourClientManager = SVNClientManager.newInstance(options,
                        authManager);
                SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
                updateClient.setIgnoreExternals(false);
                myEventHandler = new UpdateEventHandler();
                if (usegui) myEventHandler.setGuiStatusScreen(gss);
                updateClient.setEventHandler(myEventHandler);
                // Determine if we have a directory for our update as defined in the
                // properties
                File outdirectory = new File(applicationdirectory).getAbsoluteFile();
                if (!outdirectory.exists()) {
                    File parentdir = outdirectory.getParentFile();
                    if (parentdir.exists())
                        outdirectory.mkdir();
                    else {
                        parentdir.mkdir();
                        outdirectory.mkdir();
                    }
                }
                // Actually check out or update the files from the SVN Server.
                // The UpdateEventHandler class will handle the status updates
                // during the download.
                updateClient.doCheckout(SVNURL.parseURIDecoded(url + "/"
                        + module_url), outdirectory, SVNRevision.HEAD,
                        SVNRevision.HEAD, true); // Recursive
            } catch (org.tmatesoft.svn.core.SVNAuthenticationException ae) {
                System.out.println("Cannot authorize user login.");
                ShowException(ae);
            } catch (org.tmatesoft.svn.core.SVNException e) {
                System.out.println(e.getLocalizedMessage());
                ShowException(e);
            }
            if (usegui) {
                gss.appendText("File check and download is complete.");
                gss.appendText("Now restart GraphLab.");
            } else System.out.println("File check and download is complete.");

            if (prefs.getBoolean("launchant", false)) {
                if (usegui) gss.appendText("Running update scripts.");
                else System.out.println("Running update scripts.");
                try {
                    ExecuteAntLauncher(antlocation, applicationdirectory, buildfile);
                }
                catch (Exception e) {
                    ShowException(e);
                }
                if (usegui) gss.appendText("Update scripts complete.");
                else System.out.println("Update scripts complete.");
            }
            // When we're finished, either display the status button or exit.
            if (usegui) gss.appendText("Update is now complete.");
            else System.out.println("Update is now complete.");
            if (usegui && showbutton)
                gss.enableButton();
//		else
//			System.exit(0);
        }
    }

    /**
     * @return Returns a GuiStatusScreen if "usegui" is true. Otherwise returns
     *         null.
     */
    public static GuiStatusScreen getGuiStatusScreen() {
        if (usegui)
            return gss;
        else
            return null;
    }

    /**
     * Static method to load the preferences for the updater from the
     * conf/autoupdate.xml file.
     */
    private static void LoadPrefsFromFile() {
        prefs = null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(
                    "prefs" + File.separator + "autoupdate.xml"));
        } catch (FileNotFoundException e) {
            System.out
                    .println("You must have a conf/autoupdate.xml configuration file.");
        }
        try {
            Preferences.importPreferences(is);
        } catch (Exception e) {
            ExceptionHandler.catchException(e);
        }
        prefs = Preferences.systemNodeForPackage(AutomatedUpdate.class);
    }

    private static void ShowException(Exception e) {
        if (usegui) gss.appendText("ERROR: " + e.getMessage());
        else System.out.println("ERROR: " + e.getMessage());
        StackTraceElement[] trace = e.getStackTrace();
        gss.appendText("TRACE:");
        for (int i = 0; i < trace.length; i++) {
            StackTraceElement stacke = trace[i];
            if (usegui)
                gss.appendText("   " + stacke.getClassName() + "(" + stacke.getMethodName() + ") line " + stacke.getLineNumber());
            else
                System.out.println("   " + stacke.getClassName() + "(" + stacke.getMethodName() + ") line " + stacke.getLineNumber());
        }
    }

    private static void ExecuteAntLauncher(String antlocation, String run_dir, String buildfile) throws Exception {
        String separator = File.separator;
        String cpseparator = File.pathSeparator;
        ant.SetAntLocation(antlocation);
        ant.SetRunDirectory(run_dir);
        ant.SetBuildFile(run_dir + separator + buildfile);
        String cp = System.getenv("CLASSPATH");
        cp = cp +
                cpseparator + antlocation + separator + "lib" + separator + "ant.jar" + cpseparator +
                cpseparator + antlocation + separator + "lib" + separator + "ant-launcher.jar";
        ant.SetClassPath(cp);

        try {
            BufferedReader inr = ant.run();
            String line = "";
            while ((line = inr.readLine()) != null) {
                if (usegui) {
                    if (line.indexOf("tools.jar") < 0)
                        gss.appendText(line);
                } else System.out.println(line);
            }
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
            ShowException(ioe);
        }
    }

    private static SoftwareUpdateable getSoftwareUpdateObject(String classname) {
        try {
            Class cl = Class.forName(classname);
            java.lang.reflect.Constructor co = cl.getConstructor(new Class[]{});
            return (SoftwareUpdateable) co.newInstance(new Object[]{});
        }
        catch (ClassNotFoundException badClass) {
            badClass.printStackTrace();
        }
        catch (NoSuchMethodException badConst) {
            badConst.printStackTrace();
        }
        catch (IllegalAccessException badPerm) {
            badPerm.printStackTrace();
        }
        catch (IllegalArgumentException badParms) {
            badParms.printStackTrace();
        }
        catch (InstantiationException abstractClass) {
            abstractClass.printStackTrace();
        }
        catch (java.lang.reflect.InvocationTargetException ite) {
            ite.printStackTrace();
        }
        return null;
    }
}