// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.core;

/**
 * Action is the base of the actions of graphlab.
 * @author Azin Azadi
 */
public interface Action extends Listener {
    /**
     * do the job of action.
     *
     * @param eventName
     * @param value
     */
    public void performAction(String key, Object value);

    /**
     * each action have a black board which can interact with the world! by it.
     *
     * @param t
     */
    public void setBlackBoard(BlackBoard t);

}
