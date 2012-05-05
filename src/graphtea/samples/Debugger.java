// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.samples;

/**
 * This is a sample class for who wants to debug his/her developing extensions.
 * Just replace your extension's class name with HelloWorldReport
 *
 * see: http://graphtea.sharif.edu/trac/wiki/ExtensionDebugging #todo:[tea] link to get updated
 * @author Azin Azadi
 */

import graphtea.platform.StaticUtils;


public class Debugger {
    public static void main(String[] args) {
        graphtea.platform.Application.main(args);
        StaticUtils.loadSingleExtension(graphtea.samples.extensions.HelloWorldReport.class);
    }
}
