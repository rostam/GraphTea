// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.event;

import graphtea.graph.graph.GraphModel;

/**
 * occurs in black board whenever a graph tab in GTabbedPane is selected
 *
 * @author azin azadi
 */
public class GraphSelectData {
    public static final String EVENT_KEY = "Graph.Select";
    public GraphModel g;
}
