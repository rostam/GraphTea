// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.algorithms;

import graphtea.library.algorithms.util.EventUtils;
import graphtea.library.event.Event;
import graphtea.library.event.EventDispatcher;

/**
 * @author Omid Aladini
 *         Animated algorithms should extend this class.
 */
public abstract class Algorithm implements AlgorithmInterface {
    /**
     * Reference to the event dispatcher object.
     */
    EventDispatcher dispatcher = null;

    /**
     * Gets a reference to the dispatcher object responsible for dispatching events.
     *
     * @param D Reference to the dispatcher object.
     */
    public void acceptEventDispatcher(EventDispatcher D) {
        dispatcher = D;
    }

    /**
     * Sends the event <code>ae</code> to the dispatcher.
     *
     * @param ae The event object to be dispatched.
     */
    public void dispatchEvent(Event ae) {
        if (dispatcher != null && ae != null)
            dispatcher.dispatchEvent(ae);
    }

    protected EventDispatcher getDispatcher() {
        return dispatcher;

    }

    /**
     * defines a step on algorithm, for example visiting a vertex, or every thing which then user can 
     * pause on it.
     * @param msg
     */
    protected void step(String msg, String id){
        EventUtils.algorithmStep(this, msg,id);
    }

    protected void step(String msg){
        EventUtils.algorithmStep(this, msg);
    }
}
