// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.automaticupdator.net.interdirected.autoupdate;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * AntLauncher  This is basically a copy of org.apache.tools.ant.launch.Launcher.  I needed the
 * run method to be public, and I needed the parameters to be assigned in the instantiation of the
 * class instead.  This just seemed to make more sense to me than statically running the MAIN from the
 * original Launcher, or executing it as an exec from a batch file.
 *
 * @author Michael Quattlebaum
 * @see org.apache.tools.ant.launch.Launcher
 */
public class AntLauncher {
    private String[] args;
    private File runDir = new File(".");
    private String cp = "";
    private String antloc = ".";

    /**
     * AppendArgs  This method is used to add arguments to the arguments array
     * used in ANT.  Any argument that could be passed on the command line of
     * an ant launch can be added as an argument here.  If a value is to be passed
     * with the argument, the user must pass the argument flag first
     * (with the &quot;-&quot; character included).  For example, to add the -lib
     * parameter, you must call this method twice, once with &quot;-lib&quot; and
     * once with the actual value of &quot;./lib&quot;.
     *
     * @param add_argument Argument to add to the arguments array.
     */
    public void AppendArgs(String add_argument) {
        int argslen = (args == null) ? 0 : args.length;
        String[] newArgs = new String[argslen + 1];
        int i = 0;
        for (; i < argslen; i++) {
            newArgs[i] = args[i];
        }
        newArgs[i] = add_argument;
        args = newArgs;
    }

    /**
     * AppendArgs  This is a shortcut to the AppendArgs(String) method in case
     * you have a parameter/value pair that need to be added.  It just calls
     * AppendArgs(String) twice.
     *
     * @param add_argument
     * @param add_value
     */
    public void AppendArgs(String add_argument, String add_value) {
        this.AppendArgs(add_argument);
        this.AppendArgs(add_value);
    }

    /**
     * ResetArgs  This method resets the arguments array to a null value.  This should
     * not be used unless you need to eliminate the default library location (-lib ./lib)
     * from the parameters.
     */
    public void ResetArgs() {
        args = null;
    }

    /**
     * SetRunDirectory is used to set the base run directory.  From the AutomatedUpdate, it
     * should under normal conditions be set to the application directory.
     *
     * @param run_dir The directory that ANT should launch from.
     */
    public void SetRunDirectory(String run_dir) {
        runDir = new File(run_dir);
        if (!(runDir.exists() && runDir.isDirectory())) {
            System.out.println("Run directory does not exist. Setting to current directory instead.");
            runDir = new File(".");
        }
    }

    /**
     * SetListenerClass sets the listener class file for the ANT build.  The listener class will
     * then be used to set the output stream so that it is used by the status window.
     *
     * @param listener A String with the name of the listening class.
     */
    public void SetListenerClass(String listener) {
        AppendArgs("-listener", listener);
    }

    public void SetBuildFile(String buildfile) {
        AppendArgs("-buildfile", buildfile);
    }

    public void SetAntLocation(String antlocation) {
        antloc = antlocation;
        AppendArgs("-lib", antlocation + File.separator + "lib");
    }

    public String GetAntLocation() {
        return antloc;
    }

    public void SetClassPath(String classpath) {
        if (classpath.substring(0, 0) != "\"") classpath = "\"" + classpath;
        if (classpath.substring(classpath.length() - 1) != "\"") classpath = classpath + "\"";
        cp = classpath;
    }

    /**
     * run This method is used to create a new Java JVM to handle org.apache.tools.ant.launch.Launcher
     * and capture standard out and redirect it to the command line.
     */
    public BufferedReader run() throws Exception {
        String commandStr = "java";
        Runtime rt = Runtime.getRuntime();
        String[] javaargs = {
                "-classpath",
                GetAntLocation() + File.separator + "ant-launcher.jar",
                "-cp",
                cp,
                "org.apache.tools.ant.launch.Launcher"
        };
        for (int i = 0; i < javaargs.length; i++) {
            commandStr = commandStr + " " + javaargs[i];
        }
        for (int i = 0; i < args.length; i++) {
            commandStr = commandStr + " " + args[i];
        }
        Process process = rt.exec(commandStr);
        InputStreamReader reader= new InputStreamReader(process.getInputStream());
		return new BufferedReader(reader);
	}
}