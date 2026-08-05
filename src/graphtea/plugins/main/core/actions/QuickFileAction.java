// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.core.actions;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.platform.ui.UserNotifier;
import graphtea.ui.UIUtils;
import graphtea.ui.extension.AbstractExtensionAction;

/**
 * Plain <b>Open</b> and <b>Save</b> for GraphTea's own file format.
 *
 * <p>Loading and saving were reachable only through <b>File &rsaquo; Load Graph From &rsaquo;
 * …</b> and <b>File &rsaquo; Save Graph To &rsaquo; …</b>, one entry per format, with no
 * shortcut on any of them. Someone who just wants to save their work had to know which of seven
 * formats was the native one. These two entries do the obvious thing under the keys everybody
 * already presses; the format submenus stay for everything else.
 */
public abstract class QuickFileAction extends AbstractAction {

    private final String extensionClassName;

    /**
     * @param bb                 the blackboard of the action
     * @param eventId            the UI event id this action listens for
     * @param extensionClassName the reader or writer extension to delegate to
     */
    protected QuickFileAction(BlackBoard bb, String eventId, String extensionClassName) {
        super(bb);
        this.extensionClassName = extensionClassName;
        listen4Event(UIUtils.getUIEventKey(eventId));
    }

    public void performAction(String eventName, Object value) {
        Object loaded = ExtensionLoader.loadedInstances.get(extensionClassName);
        if (!(loaded instanceof AbstractExtensionAction<?> action)) {
            // The format extension did not load; say so rather than doing nothing.
            UserNotifier.report("Opening or saving a graph",
                    new IllegalStateException("The GraphTea file format extension ("
                            + extensionClassName + ") is not available."));
            return;
        }
        blackboard.setData(UIUtils.getUIEventKey(action.actionId), "quick file action");
    }

    /** File &rsaquo; Open: reads a {@code .tea} file. */
    public static class Open extends QuickFileAction {
        /**
         * @param bb the blackboard of the action
         */
        public Open(BlackBoard bb) {
            super(bb, "open graph", "graphtea.extensions.io.LoadGraph");
        }
    }

    /** File &rsaquo; Save: writes a {@code .tea} file. */
    public static class Save extends QuickFileAction {
        /**
         * @param bb the blackboard of the action
         */
        public Save(BlackBoard bb) {
            super(bb, "save graph", "graphtea.extensions.io.SaveGraph");
        }
    }
}
