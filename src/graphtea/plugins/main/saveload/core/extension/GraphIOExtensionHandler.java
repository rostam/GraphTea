// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.saveload.core.extension;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.ExtensionHandler;
import graphtea.platform.preferences.lastsettings.StorableOnExit;
import graphtea.platform.preferences.lastsettings.UserModifiableProperty;

/**
 * the plug in handler for graph Input and Output, this class loads classes that are implementing
 * GraphReaderExtension interface,...
 *
 * @author azin azadi

 */
public class GraphIOExtensionHandler implements ExtensionHandler, StorableOnExit {
    private boolean handlingReaders;
    private AbstractAction a = null;

    {
        SETTINGS.registerSetting(this, "Only Storable");
    }

    /**
     * @param isHandlingReaders indicates that if this Object should handle GraphReaders or GraphWriters
     */
    public GraphIOExtensionHandler(boolean isHandlingReaders) {
        handlingReaders = isHandlingReaders;
    }

    @UserModifiableProperty(displayName = "default Directory Path")
    public static String defaultFile = ".";

    /**
     * @param b The blackboard
     * @param ext The extension
     * @return null if ext doesn't implements GraphReaderExtension
     */
    public AbstractAction handle(BlackBoard b, Object ext) {
        a = null;
        if (!handlingReaders) {
            if (ext instanceof GraphWriterExtension) {
                try {
                    GraphWriterExtension gg = (GraphWriterExtension) ext;
                    a = new GraphWriterExtensionAction(b, gg);
                } catch (Exception e) {
                    ExceptionHandler.catchException(e);

                }
            }
        } else {
            if (ext instanceof GraphReaderExtension) {
                try {
                    GraphReaderExtension gg = (GraphReaderExtension) ext;
                    a = new GraphReaderExtensionAction(b, gg);
                } catch (Exception e) {
                    ExceptionHandler.catchException(e);
                }
            }
        }
        return a;
    }
}
