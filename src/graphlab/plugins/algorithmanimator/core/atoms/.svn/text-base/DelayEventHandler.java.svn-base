// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.core.atoms;

import graphlab.library.event.DelayEvent;
import graphlab.library.event.Event;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.AtomAnimator;


/**
 * @author Omid Aladini
 */
public class DelayEventHandler implements AtomAnimator<DelayEvent> {
    public boolean isAnimatable(Event event) {
        if (event instanceof DelayEvent)
            return true;
        else
            return false;
    }

    public DelayEvent animate(DelayEvent event, BlackBoard b) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return event;
    }
}
