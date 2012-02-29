// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/**
 *
 */
package graphlab.library.event;


/**
 * @author Omid
 */
public class DelayEvent implements Event {

    public int millisecond;

    public DelayEvent(int millisecond) {
        this.millisecond = millisecond;

    }

    public String getDescription() {
        return "Asks the handler to make a algorithmStep. For animation purposes.";
    }

    public String getID() {
        return "DelayEvent";

    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
