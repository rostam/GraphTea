// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.core;

/**
 * Action is the base of the actions of graphtea.
 * @author Azin Azadi
 */
public interface Action extends Listener {
    /**
     * do the job of action.
     *
     * @param key The event name
     * @param value The value
     */
    void performAction(String key, Object value);

    /**
     * each action have a black board which can interact with the world! by it.
     *
     * @param t The blackboard
     */
    void setBlackBoard(BlackBoard t);

}
