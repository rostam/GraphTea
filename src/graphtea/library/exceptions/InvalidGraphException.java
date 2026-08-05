// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.exceptions;

/**
 * @author Omid Aladini
 */
public class InvalidGraphException extends RuntimeException {

    private static final long serialVersionUID = 4121131454852772150L;

    /**
     * Constructs an instance of <code>InvalidGraphException</code>
     */
    public InvalidGraphException() {
        super("Operation on an invalid graph object.");
    }

    public InvalidGraphException(Throwable cause) {
        super(cause);
    }

    public InvalidGraphException(String string) {
        super(string);
    }

}
