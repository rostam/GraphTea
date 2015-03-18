// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.graphgenerator.core.extension;

import graphtea.platform.StaticUtils;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.ExtensionHandler;

/**
 * the plug in handler for graph generators, this class loads classes that are implementing
 * GraphGeneratorExtension interface,...
 *
 * @author azin azadi

 */
public class GraphGeneratorExtensionHandler implements ExtensionHandler {
    AbstractAction a = null;

    /**
     * @param b
     * @param ext
     * @return null if ext doesn't implements GraphGeneratorExtension
     */
    public AbstractAction handle(BlackBoard b, Object ext) {
        a = null;
        if (ext instanceof GraphGeneratorExtension) {
            try {
                GraphGeneratorExtension gg = (GraphGeneratorExtension) ext;
                a = new GraphGeneratorExtensionAction(b, gg);
            } catch (Exception e) {
                ExceptionHandler.catchException(e);
                StaticUtils.addExceptiontoLog(e, b);
            }
        }
        return a;
    }
}
