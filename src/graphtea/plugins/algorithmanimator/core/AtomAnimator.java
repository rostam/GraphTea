// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core;

import graphtea.library.event.Event;
import graphtea.platform.core.BlackBoard;

/**
 * @author Azin Azadi
 */
public interface AtomAnimator<EventType extends Event> {
    /**
     * @return true if the event is animatable by this animator
     */
    public boolean isAnimatable(Event event);

    /**
     * animates event on the given blackboard as the world...
     */
    public EventType animate(EventType event, BlackBoard b);
}