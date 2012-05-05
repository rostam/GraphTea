// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core.atoms;

import graphtea.graph.graph.Edge;
import graphtea.library.event.EdgeEvent;
import graphtea.library.event.Event;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.core.AtomAnimator;

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
