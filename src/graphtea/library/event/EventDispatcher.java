// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.event;


/**
 * EventDispatcher is designed to receive notification of algorithm events.
 * One who implements this class may take different decision on different types of Event object.
 *
 * @author Omid Aladini
 */
public interface EventDispatcher {
    /**
     * Should take reactions (possibly to the gui) upon call by the algorithm.
     *
     * @param ae Event object.
     */
    Event dispatchEvent(Event ae);
}
