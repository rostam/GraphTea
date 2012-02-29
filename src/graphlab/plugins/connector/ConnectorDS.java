// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.connector;

import graphlab.platform.attribute.AttributeSet;
import graphlab.platform.attribute.AttributeSetImpl;

import java.util.Vector;

/**
 * The base abstracting class for a connector, this class is under developement, I hope it will have a parse(file) and a
 * run(shell) method
 *
 * @author Azin Azadi , Mohammad Ali Rostami
 */
public class ConnectorDS {
    //    public String name;  //commandName used instead
    public String description;
    public AttributeSet atrs = new AttributeSetImpl();
    /**
     * provides the name of arguments of shell method!!?? it holds the order of variables in the corresponding
     * shell method call, the values are stored in atrs,
     * <p> </p>
     * see graphlab.plugins.connector.matlab.MatlabExtensionLoader.parseMatlabFile(File matlabfile)
     *
     * @see ConnectorDS.getArgs()
     */
    public Vector<String> shellMethodArgs = new Vector<String>();
    public String commandName;
    public String command;

    public String getArgs() {
        String ret = "";
        boolean b = false;
        for (String name : shellMethodArgs) {
            ret += atrs.get(name) + " ,";
            b = true;
        }
        if (b)
            ret = ret.substring(0, ret.length() - 1);
        return ret;
    }

}
