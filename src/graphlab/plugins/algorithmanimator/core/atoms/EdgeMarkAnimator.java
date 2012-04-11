// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.core.atoms;

import graphlab.graph.graph.Edge;
import graphlab.library.event.EdgeEvent;
import graphlab.library.event.Event;
import graphlab.platform.core.BlackBoard;
import graphlab.plugins.algorithmanimator.core.AtomAnimator;

/**
 * currently not use because of automatic marking of edges in Edge
 *
 * @author Azin Azadi
 */
public class EdgeMarkAnimator implements AtomAnimator<EdgeEvent> {

    public boolean isAnimatable(Event event) {
        if (event instanceof EdgeEvent)
            if (((EdgeEvent) event).eventType == EdgeEvent.EventType.MARK)
                return true;
        return false;
    }

    public EdgeEvent animate(EdgeEvent event, BlackBoard b) {
        ((Edge) event.edge).setColor(1);
        return event;
    }
}
