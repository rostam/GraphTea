// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.connector;

import graphtea.platform.plugin.PluginMethods;
import graphtea.plugins.connector.matlab.MatlabRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Azin Azadi
 */
public class Connector implements PluginMethods {
    /**
     * @see graphtea.plugins.connector.matlab.MatlabRunner#matlabRunner(java.io.File,Object[])
     */
    public static String matlabRunner(File matlabfile, Object[] params) throws FileNotFoundException, IllegalAccessException, InvocationTargetException {
        return MatlabRunner.matlabRunner(matlabfile, params);
    }
}
