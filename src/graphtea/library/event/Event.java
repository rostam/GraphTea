// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.library.event;


/**
 * It's just a tagging interface.
 *
 * @author Omid Aladini
 */
public interface Event {
    String getID();

    String getDescription();

    /**
     * @return a message if the algorithm want to send any of them. this message will be loged and shown to the user
     */
    String getMessage();

}
