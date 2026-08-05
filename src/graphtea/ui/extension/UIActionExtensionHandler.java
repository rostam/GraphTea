// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.extension;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.ExtensionHandler;

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
