// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.connector.matlab;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.Extension;
import graphtea.platform.extension.UnknownExtensionLoader;
import graphtea.platform.StaticUtils;
import graphtea.platform.plugin.Plugger;
import graphtea.plugins.commandline.Shell;
import graphtea.plugins.connector.ConnectorDS;
import graphtea.plugins.connector.ConnectorReportExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MatlabExtensionLoader implements UnknownExtensionLoader {
    public Extension load(File file, BlackBoard blackboard) {
        try {
            if ("m".equals(Plugger.getExtension(file))) {
                System.err.println("loding MatLab extension: " + file.getName());
                ConnectorDS cds = parseMatlabFile(file);
//            System.err.println(cds.command);
                if (cds == null)
                    return null;
                Shell.getCurrentShell(blackboard).evaluate(cds.command);
                return new ConnectorReportExtension(file, blackboard, cds);
            } else
                return null;
        } catch (Exception e) {
            ExceptionHandler.catchException(e);
            return null;
        }
    }

    //todo: to move this method to ConnectorDS and make it a helper method for all external connections
    protected ConnectorDS parseMatlabFile(File matlabfile) {
        ConnectorDS ret = new ConnectorDS();
        String args = "";
        ret.commandName = matlabfile.getName().substring(0, matlabfile.getName().lastIndexOf("."));
        Scanner fileScanner = new Scanner("");
        try {
            fileScanner = new Scanner(matlabfile);
        }
        catch (FileNotFoundException e) {
            ExceptionHandler.catchException(e);
        }
        String funcBody = "new String[]{";

        String line = fileScanner.nextLine();

        //read arguments
        int counter = 0;
//        while (line.startsWith("%")) {
        if (line.contains("description:"))
            ret.description = line.substring(line.indexOf(":") + 2, line.length());
        else
            return null;

        line = fileScanner.nextLine();
        if (line.contains("args:")) {
            args = line.substring(line.indexOf(":") + 2, line.length());
            StringTokenizer argsTok = new StringTokenizer(args, ",");
            ret.command = ret.commandName + "(";
            while (argsTok.hasMoreTokens()) {
                String temp = argsTok.nextToken().trim();
                if (temp.startsWith("@")) {
                    Scanner sc = new Scanner(temp);
                    String clazz = sc.next().substring(1); //variable class
                    String name = sc.next();
                    ret.command += clazz + " " + name + " ,";
                    sc.next(); //skips the " = "
                    String defVal = sc.next();
                    funcBody += "String.valueOf(" + name + "),";
                    ret.atrs.put(name, StaticUtils.fromString(clazz, defVal));
                    ret.shellMethodArgs.add(name);
                    counter++;
                } else {
                    funcBody += "String.valueOf(" + temp + "),";
                }
            }
        }
        //prepare the command

        ret.command = "public Object " + (counter != 0 ? ret.command.substring(0, ret.command.length() - 1) : ret.command) + ")";
        funcBody = funcBody.substring(0, funcBody.length() - 1) + "}";
        try

        {
            ret.command += "{return graphtea.plugins.connector.matlab.MatlabRunner.matlabRunner(new File(\"" + matlabfile.toURI().toURL().getPath() + "\") , " + funcBody + ");}";
        }

        catch (MalformedURLException e) {
            ExceptionHandler.catchException(e);
        }

        return ret;
    }
}