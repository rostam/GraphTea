// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core.atoms;

import graphtea.library.event.DelayEvent;
import graphtea.library.event.Event;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.AtomAnimator;


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
