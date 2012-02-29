// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.core;

import graphlab.library.event.Event;
import graphlab.platform.core.BlackBoard;

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