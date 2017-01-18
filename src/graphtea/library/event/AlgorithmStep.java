// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/
package graphtea.library.event;


/**
 * this Event is a algorithm step, (is it a major step) -> play one step (on AnimatorGUI)
 *
 * @author Omid
 */
public class AlgorithmStep implements Event {

    public AlgorithmStep() {
        this.id = "Step";
    }

    public AlgorithmStep(String message, String id) {
        this.message = message;
        this.id = id;
    }


    public String getDescription() {
        return "Asks the handler to make a step. For animation purposes.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;
    private String id;


    public String getID() {
        return id;
    }
}
