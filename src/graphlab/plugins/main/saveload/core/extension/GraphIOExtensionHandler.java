// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.main.saveload.core.extension;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.extension.ExtensionHandler;
import graphlab.platform.preferences.lastsettings.StorableOnExit;
import graphlab.platform.preferences.lastsettings.UserModifiableProperty;

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
     * @param b
     * @param ext
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
