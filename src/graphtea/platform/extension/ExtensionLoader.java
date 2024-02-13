// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.extension;

import graphtea.platform.Application;
import graphtea.platform.StaticUtils;
import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.preferences.lastsettings.StorableOnExit;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

/**
 * The base class for loading extensions.
 * This class performs loading and reloading of extensions,  (from their source (.class or another format) files)
 *
 * @author Azin Azadi
 */
public class ExtensionLoader implements StorableOnExit {
    private static final HashSet<ExtensionHandler> registeredExtensionHandlers = new HashSet<>();
    private static final HashSet<UnknownExtensionLoader> registeredUnknownExtensionLoaders = new HashSet<>();
    // categorises the known extensions on their type. The type (eg report, generator, ...) is identified
    // by the respective ExtensionHandler
    public static HashMap<Class<? extends ExtensionHandler>, Vector<Extension>> extensionsList = new HashMap<>();
    // maps an extension class (eg GeneratePath), to the loaded AbstractAction
    public static HashMap<String, AbstractAction> loadedInstances = new HashMap<>();

    /**
     * Registers extHandler as an extension handler, so after this new extension that are loaded
     * to GraphTea will be given to handler.
     * <p/>
     * calling this method will not affect previously loaded extensions.
     *
     * @param extHandler The extension handler
     */
    public static void registerExtensionHandler(ExtensionHandler extHandler) {
        registeredExtensionHandlers.add(extHandler);
    }

    public static HashSet<ExtensionHandler> getRegisteredExtensionHandlers() {
        return registeredExtensionHandlers;
    }

    /**
     * register e as an unKnownExtensionLoader, which GraphTea will try to load "not .class files" with them
     *
     * @param e The extension loader
     */
    public static void registerUnknownExtensionLoader(UnknownExtensionLoader e) {
        registeredUnknownExtensionLoaders.add(e);
    }

    /**
     * gets e as an extension and tries to create Its relating AbstractAction
     * using registered ExtensionHandlers
     *
     * This should be the only place to handle extensions otherwise we are in problem,
     * because here we keep list of handled extensions for further uses. everything else will
     * not be in this list.
     */
    public static AbstractAction handleExtension(BlackBoard b, Extension e) {
        AbstractAction a = null;
        for (ExtensionHandler handler : registeredExtensionHandlers) {
            AbstractAction ret = handler.handle(b, e);
            if (ret != null){
                if (a == null) {
                    a = ret;
                }
                if (!extensionsList.containsKey(handler.getClass())){
                    extensionsList.put(handler.getClass(), new Vector<>());
                }
                extensionsList.get(handler.getClass()).add(e);
                loadedInstances.put(e.getClass().getName(), a);
            }
        }
        return a;
    }

    /**
     * returns an instance of extensionClass if the given extensionClass implements Extension (BasicExtension)
     * or has default constructor, otherwise it returns null
     *
     * @param extensionClass the extension class
     * @return an object of the given extension
     */
    public static Extension loadExtension(Class<Extension> extensionClass) {
        Extension ret = null;
        try {
            if (Extension.class.isAssignableFrom(extensionClass)) {
                Constructor<Extension>[] cs = (Constructor<Extension>[]) extensionClass.getConstructors();
                for (Constructor<Extension> c : cs) {
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
                SETTINGS.registerSetting(ret, "Extension Options");
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
     * returns an instance of extensionClass if the given extensionClass implements Extension, otherwise it returns null
     *
     * @param unknownFile The given unknown extension
     */
    public static Extension loadUnknownExtension(File unknownFile, BlackBoard blackboard) {
        for (UnknownExtensionLoader loader : registeredUnknownExtensionLoaders) {
            Extension extension = loader.load(unknownFile, blackboard);
            if (extension != null)
                return extension;
        }
        return null;
    }


}