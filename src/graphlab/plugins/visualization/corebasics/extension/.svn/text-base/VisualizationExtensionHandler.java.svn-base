// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.visualization.corebasics.extension;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.extension.ExtensionHandler;

/**
 * @author Rouzbeh Ebrahimi
 * Email: ruzbehus@gmail.com
 */
public class VisualizationExtensionHandler implements ExtensionHandler {
    AbstractAction a = null;

    /**
     * @param b
     * @param ext
     * @return
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
