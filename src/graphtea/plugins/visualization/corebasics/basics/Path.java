// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.visualization.corebasics.basics;

import graphtea.graph.graph.Vertex;

import java.util.ArrayList;

/**
 * @author Rouzbeh Ebrahimi
 */
public class Path {
    ArrayList<Vertex> pathVertices;

    public Path() {
        pathVertices = new ArrayList<Vertex>();
    }

    public boolean add(Vertex v) {
        if (!pathVertices.contains(v)) {
            pathVertices.add(v);
            return true;
        } else {
            return false;
        }
    }

    public void removeLastVertex() {
        this.pathVertices.remove(pathVertices.size());
    }

    public void removeFirstVertex() {
        this.pathVertices.remove(0);
    }

}
