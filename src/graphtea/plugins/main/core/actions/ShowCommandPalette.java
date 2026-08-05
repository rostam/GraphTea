// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main.core.actions;

import graphtea.platform.core.AbstractAction;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;
import graphtea.ui.components.GFrame;

/**
 * Opens the command palette from the menu and toolbar.
 *
 * <p>Ctrl+K works on its own, but a shortcut nobody is told about is a shortcut nobody uses,
 * so the palette also has a visible way in.
 */
public class ShowCommandPalette extends AbstractAction {

    /**
     * @param bb the blackboard of the action
     */
    public ShowCommandPalette(BlackBoard bb) {
        super(bb);
        listen4Event(UIUtils.getUIEventKey("command palette"));
    }

    public void performAction(String eventName, Object value) {
        GFrame frame = UIUtils.getGFrame(blackboard);
        if (frame != null && frame.getCommandPalette() != null) {
            frame.getCommandPalette().show();
        }
    }
}
