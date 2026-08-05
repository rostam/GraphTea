// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

/*
* InvalidEdgeException.java
*
* Created on November 14, 2004, 1:23 AM
*/

package graphtea.library.exceptions;

/**
 * @author Omid Aladini
 */
public class InvalidEdgeException extends RuntimeException {

    private static final long serialVersionUID = 3834028052299919673L;

    /**
     * Constructs an instance of <code>InvalidVertexException</code>
     */
    public InvalidEdgeException() {
        super("You are trying to access or use an edge that does not exist.");
    }
}
