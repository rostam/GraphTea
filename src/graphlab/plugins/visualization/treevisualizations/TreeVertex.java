// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.visualization.treevisualizations;

import graphlab.graph.graph.Vertex;

import java.util.HashSet;

/**
 * @author Rouzbeh Ebrahimi
 */
public class TreeVertex {
    private Vertex parent;
    private HashSet<Vertex> children;

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public HashSet<Vertex> getChildren() {
        return children;
    }

    public void setChildren(HashSet<Vertex> children) {
        this.children = children;
    }
}
