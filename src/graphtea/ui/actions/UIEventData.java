// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.actions;

/**
 * This kind of Object will be send to blackboard whenever any ui action's(i.e. menu pressing) occurred
 *
 * @author Azin Azadi
 */

public class UIEventData {
    public static final String name = "UIEventData";

    public static String name(String id) {
        //chon tooie blCK BOARD DAR HALE HAzer nemishe be hameie eventa addListener kard pas felan majbooram in eventa ro hamashopono ba ie esm dashte basha ta betoonam addListener konam
        return name + "." + id;
    }

    String event(String id) {
        return name(id);
    }

    public static final int PREF = 0;
    public static final int ACTION = 1;

    public int eventType;
    /**
     * the name of the action, this event should be passed to, the action should be loaded via XML to be known to UI.
     */
    public String action;
}