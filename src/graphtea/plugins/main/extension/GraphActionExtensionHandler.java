// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.extension;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.ExtensionHandler;

/**
 * the plug in handler for GraphModel generators, this class loads classes that are implementing
 * GraphGeneratorExtension interface,...
 *
 * @author azin azadi

 */
public class GraphActionExtensionHandler implements ExtensionHandler {
    AbstractAction a = null;

    /**
     * @param b The blackboard
     * @param ext The extension
     * @return null if clazz doesn't implements GraphGeneratorExtension
     */
    public AbstractAction handle(BlackBoard b, Object ext) {
        a = null;
        if (ext instanceof GraphActionExtension) {
            try {
                GraphActionExtension ga = (GraphActionExtension) ext;
                a = new GraphActionExtensionAction(b, ga);
            } catch (Exception e) {
                ExceptionHandler.catchException(e);
            }
        }
        return a;
    }

}
