package graphtea.plugins.main;

import graphtea.graph.ui.HyperlinkHandler;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.ui.UIUtils;
import graphtea.ui.extension.AbstractExtensionAction;

import java.net.URL;

/**
 *  @author Azin Azadi
 */
public class PerformExtensionLinkHandler implements HyperlinkHandler {

    public void handle(String extensionClass, BlackBoard b, URL currentURL) {
        System.out.println(extensionClass);
        System.out.println(((AbstractExtensionAction) (ExtensionLoader.loadedInstances.get(extensionClass))));
        System.out.println(ExtensionLoader.loadedInstances);
        b.setData(UIUtils.getUIEventKey(((AbstractExtensionAction) (ExtensionLoader.loadedInstances.get(extensionClass))).actionId), "click on link");
    }
}
