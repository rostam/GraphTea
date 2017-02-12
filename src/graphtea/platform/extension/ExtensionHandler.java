// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.extension;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;

/**
 * The base class to create new types of extensions. It can be done by this:
 * <pre>
 * class MyExtensionHandler implements ExtensionHandler{
 *   ...
 * }
 *
 * and in you plugin Init file:
 *
 * public class Init{
 *      static{
 *          ExtensionLoader.registerExtensionHandler(new MyExtensionHandler());
 *      }
 * }
 * @author azin azadi
 */
public interface ExtensionHandler {
    /**
     * tries to handle the given Object as a known Extension,...
     * normally, Extensions are interfaces that can be sandwiched in an AbstractAction Automatically,
     * there is options for generating menus automatically after loading it (By returning AbstractExtensionAction) .
     *
     * @param b         the blackboard as the environment...
     * @param extension the extension which we want to create the AbstractAction from
     * @return not null if it was a valid case and the operation was successfull, null if it was not a valid case or it
     *         is done without creating any actions(in some cases)
     * @see graphtea.ui.extension.AbstractExtensionAction
     */
    AbstractAction handle(BlackBoard b, Object extension);
}
