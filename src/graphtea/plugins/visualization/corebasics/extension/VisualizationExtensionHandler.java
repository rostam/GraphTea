// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.visualization.corebasics.extension;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.ExtensionHandler;

/**
 * @author Rouzbeh Ebrahimi
 * Email: ruzbehus@gmail.com
 */
public class VisualizationExtensionHandler implements ExtensionHandler {
    AbstractAction a = null;

    /**
     * @param b The blackboard
     * @param ext The extension
     * @return The abstract action
     */
    public AbstractAction handle(BlackBoard b, Object ext) {
        a = null;
        if (ext instanceof VisualizationExtension) {
            try {
                VisualizationExtension gg = (VisualizationExtension) ext;
                a = new VisualizationExtensionAction(b, gg);
            } catch (Exception e) {
                ExceptionHandler.catchException(e);
            }
        }
        return a;
    }
}
