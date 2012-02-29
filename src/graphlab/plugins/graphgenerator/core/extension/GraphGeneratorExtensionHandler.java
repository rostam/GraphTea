// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.graphgenerator.core.extension;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.extension.ExtensionHandler;
import graphlab.platform.StaticUtils;

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
