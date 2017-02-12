// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.platform.extension;

import graphtea.platform.core.BlackBoard;

import java.io.File;

/**
 * An ExtensionHandler which handles not .class files as extension. for exaple .m Matlab files and ...
 *
 * @author Azin Azadi
 */
public interface UnknownExtensionLoader {

    Extension load(File file, BlackBoard blackboard);

}