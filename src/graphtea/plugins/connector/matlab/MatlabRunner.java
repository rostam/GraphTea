// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.connector.matlab;

import jmatlink.JMatLink;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

/**
 * @author Azin Azadi , Mohammad Ali Rostami
 * @email ma.rostami@yahoo.com
 */
public class MatlabRunner {

    public static jmatlink.JMatLink engine;

    /**
     * runs the given matlab (.m) file as a function with the given params as the
     * inputs of function in the Matlab environment.
     *
     * @param matlabfile
     * @param params
     * @return the result of function
     * @throws FileNotFoundException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static String matlabRunner(File matlabfile, Object[] params) throws FileNotFoundException, IllegalAccessException, InvocationTargetException {
        try {
            engine = new JMatLink();
            engine.engOpen();
            engine.setDebug(false);
            engine.engOutputBuffer();
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(null, "Matlab Engine could not be oppened!", "Error while starting MatLab", JOptionPane.ERROR_MESSAGE);
            return "Matlab Engine could not be oppened!";
        }
        String command = "";
        String path = matlabfile.getParentFile().getPath();
        path = path.replaceAll("%20", " ");
        engine.engEvalString("cd ('" + path + "')");
        command = "varvar = " + matlabfile.getName().substring(0, matlabfile.getName().length() - 2) + "(";
        for (Object p : params)
            command += p + ",";
        if (params.length > 0)
            command = command.substring(0, command.length() - 1);
        command += ")";
//        command = "aa = h";
        //   engine.engO
        System.out.println(MessageFormat.format("command:{0}", command));
        engine.engEvalString(command);
//        engine.eng
//        return engine.engGetCharArray("aa")[0];

        String ret = engine.engGetOutputBuffer();
        if (ret.startsWith("???")) {
            JOptionPane.showMessageDialog(null, ret, "Error while running MatLab", JOptionPane.ERROR_MESSAGE);
            return ret;
        }
        ret = ret.replaceAll("\n", " ");
        ret = ret.substring(ret.indexOf("=") + 1).trim();
        return ret;
    }
}