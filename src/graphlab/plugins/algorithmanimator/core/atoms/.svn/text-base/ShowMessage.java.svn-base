// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.core.atoms;

import graphlab.graph.ui.GTabbedGraphPane;
import graphlab.library.event.Event;
import graphlab.library.event.MessageEvent;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.AtomAnimator;


/**
 * @author Omid Aladini
 */
public class ShowMessage implements AtomAnimator<MessageEvent> {
    public boolean isAnimatable(Event event) {
        if (event instanceof MessageEvent)
            return true;
        else
            return false;
    }

    public MessageEvent animate(MessageEvent event, BlackBoard b) {
        if (event.isNotification)
            GTabbedGraphPane.showTimeNotificationMessage(event.getMessage(), b, event.durationShowTime, true);
        else GTabbedGraphPane.setMessage(event.getMessage(), b, true);
        return event;
    }
}
