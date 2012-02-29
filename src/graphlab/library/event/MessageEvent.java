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
public class MessageEvent implements Event {

    public boolean isNotification;
    public long durationShowTime;

    public MessageEvent(String message, boolean notification, long durationShowTime) {
        this.message = message;
        isNotification = notification;
        this.durationShowTime = durationShowTime;
    }

    public MessageEvent(String message) {
        this.message = message;
        isNotification = false;
        this.durationShowTime = 0;

    }

    public String getDescription() {
        return "Sends a message to the dispatcher";
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getID() {
        return "Message Event";
    }
}
