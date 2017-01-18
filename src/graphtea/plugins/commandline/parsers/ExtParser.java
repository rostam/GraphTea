// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.commandline.parsers;

/**
 * @author Mohammad Ali Rostami
 * @email rostamiev@gmail.com
 */

public interface ExtParser {
    String getName();

    String parse(String line);
}
