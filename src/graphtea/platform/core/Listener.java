// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.core;

/**
 * Listener is a kind of listener that the Black board use to notify the action, when their events occurs.
 *
 * @author Azin Azadi
 */
public interface Listener<T> {

    /**
     * Event occurred, Go and call the listeners to do the Job
     *
     * @param key The key
     */
    void keyChanged(String key, T value);


}