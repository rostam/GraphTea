// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.ui.extension;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.extension.ExtensionHandler;

/**
 * @author azin azadi

 */
public class UIActionExtensionHandler implements ExtensionHandler {
    AbstractAction a = null;

    public AbstractAction handle(BlackBoard b, Object ext) {
        a = null;
        if (ext instanceof UIActionExtension) {
            try {
                UIActionExtension vm = (UIActionExtension) ext;
                a = new UIActionExtensionAction(b, vm);
            } catch (Exception e) {
                ExceptionHandler.catchException(e);
            }
        }
        return a;
    }
}
