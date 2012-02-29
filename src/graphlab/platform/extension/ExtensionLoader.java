// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.extension;

import graphlab.platform.Application;
import graphlab.platform.StaticUtils;
import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.preferences.lastsettings.StorableOnExit;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashSet;

/**
 * The base class for loading extensions.
 * This class performs loading and reloading of extensions,  (from their source (.class or anyother format) files)
 *
 * @author Azin Azadi
 */
public class ExtensionLoader implements StorableOnExit {
    private static HashSet<ExtensionHandler> registeredExtensionHandlers = new HashSet<ExtensionHandler>();
    private static HashSet<UnknownExtensionLoader> registeredUnknownExtensionLoaders = new HashSet<UnknownExtensionLoader>();

    /**
     * Registers extHandler as an extension handler, so after this new extension that are loaded
     * to GraphLab will be given to handler.
     * <p/>
     * calling this method will not affect previously loaded extensions.
     *
     * @param extHandler
     */
    public static void registerExtensionHandler(ExtensionHandler extHandler) {
        registeredExtensionHandlers.add(extHandler);
    }

    public static HashSet<ExtensionHandler> getRegisteredExtensionHandlers() {
        return registeredExtensionHandlers;
    }

    /**
     * register e as an unKnownExtensionLoader, which GraphLab will try to load "not .class files" with them
     *
     * @param e
     */
    public static void registerUnknownExtensionLoader(UnknownExtensionLoader e) {
        registeredUnknownExtensionLoaders.add(e);
    }

    /**
     * gets e as an extension and tries to create Its relating AbstractAction
     * using registered ExtensionHandlers
     */
    public static AbstractAction handleExtension(BlackBoard b, Object e) {
        AbstractAction a = null;
        for (ExtensionHandler _ : registeredExtensionHandlers) {
            if (a == null)
                a = _.handle(b, e);
            else
                _.handle(b, e);
        }
        return a;
    }

    /**
     * returns an instance of extensionClass if the given extensionClass implements Extension (BasicExtension)
     * or has default constructor, otherwise it returns null
     *
     * @param extensionClass
     * @return
     */
    public static Object loadExtension(Class extensionClass) {
        Object ret = null;
        try {
            if (BasicExtension.class.isAssignableFrom(extensionClass)) {
                Constructor[] cs = extensionClass.getConstructors();
                for (Constructor c : cs) {
                    Class[] p = c.getParameterTypes();
                    if (p.length == 1 && p[0].equals(BlackBoard.class)) {
                        ret = extensionClass.getConstructor(BlackBoard.class).newInstance(Application.blackboard);
                        break;
                    }
                }
                if (ret == null && extensionClass.getConstructor() != null) {
                    ret = extensionClass.newInstance();
                }
            }
            if (ret != null) {
                SETTINGS.registerSetting(ret, "Extention Options");
            }
            return ret;
        } catch (Exception e) {
            System.err.println(extensionClass);
            ExceptionHandler.catchException(e);
            StaticUtils.addExceptiontoLog(e, Application.blackboard);
        }
        return ret;
    }

    /**
     * returns an instance of extensionClsas if the given extensionClass implements Extension, otherwise it returns null
     *
     * @param unknownFile
     */
    public static Extension loadUnknownExtension(File unknownFile, BlackBoard blackboard) {
        for (UnknownExtensionLoader _ : registeredUnknownExtensionLoaders) {
            Extension extension = _.load(unknownFile, blackboard);
            if (extension != null)
                return extension;
        }
        return null;
    }


}