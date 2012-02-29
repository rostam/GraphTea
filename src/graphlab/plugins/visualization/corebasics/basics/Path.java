// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.visualization.corebasics.basics;

import graphlab.graph.graph.VertexModel;

import java.util.ArrayList;

/**
 * @author Rouzbeh Ebrahimi
 */
public class Path {
    ArrayList<VertexModel> pathVertices;

    public Path() {
        pathVertices = new ArrayList<VertexModel>();
    }

    public boolean add(VertexModel v) {
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
