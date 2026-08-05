// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
 * InvalidVertexException.java
 *
 * Created on November 14, 2004, 1:23 AM
 */

package graphtea.library.exceptions;

/**
 * @author Omid Aladini
 */
public class InvalidVertexException extends RuntimeException {

    private static final long serialVersionUID = 3545512928372405814L;

    /**
     * Constructs an instance of <code>InvalidVertexException</code>
     */
    public InvalidVertexException() {
        super("You are trying to access or use a vertex that does not exist.");
    }

    public InvalidVertexException(String additional) {
        super("You are trying to access or use a vertex that does not exist:" + additional);
    }

}
