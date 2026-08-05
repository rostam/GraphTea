// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.event;


/**
 * @author Omid
 */
public class DelayEvent implements Event {
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
