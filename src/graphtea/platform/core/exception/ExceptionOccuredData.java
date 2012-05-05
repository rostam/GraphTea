// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.core.exception;

/**
 * The base class which stores information about occcured exceptions in application.
 *
 * @author  Ruzbeh ebrahimi
 */
public class ExceptionOccuredData {
    public final static String EVENT_KEY = "Bug.occured";
    public Throwable e;

    public ExceptionOccuredData(Throwable e) {
        super();
        this.e = e;
    }

}