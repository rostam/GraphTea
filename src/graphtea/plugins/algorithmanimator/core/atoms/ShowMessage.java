// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core.atoms;

import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.library.event.Event;
import graphtea.library.event.MessageEvent;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.AtomAnimator;


/**
 * @author Omid Aladini
 */
public class ShowMessage implements AtomAnimator<MessageEvent> {
    public boolean isAnimatable(Event event) {
        return event instanceof MessageEvent;
    }

    public MessageEvent animate(MessageEvent event, BlackBoard b) {
        if (event.isNotification)
            GTabbedGraphPane.showTimeNotificationMessage(event.getMessage(), b, event.durationShowTime, true);
        else GTabbedGraphPane.setMessage(event.getMessage(), b, true);
        return event;
    }
}
