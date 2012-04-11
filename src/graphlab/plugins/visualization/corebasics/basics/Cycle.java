// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.visualization.corebasics.basics;

import graphlab.graph.graph.Vertex;

import java.util.ArrayList;

/**
 * @author Rouzbeh Ebrahimi
 */
public class Cycle {
    ArrayList<Vertex> cycleVertices;
    boolean isCycleEnded;

    public Cycle(ArrayList<Vertex> cycleVertices) {
        int cycleSize = cycleVertices.size();
        if (cycleVertices.get(0).equals(cycleVertices.get(cycleSize))) {
            this.cycleVertices = cycleVertices;
            isCycleEnded = true;
        }
    }

    public Cycle() {
        cycleVertices = new ArrayList<Vertex>();
        isCycleEnded = false;
    }

    public void add(Vertex v) {
        this.cycleVertices.add(v);
    }

    public boolean endCycle(Vertex v) {
        if (this.cycleVertices.get(0).equals(v)) {
            this.cycleVertices.add(v);
            isCycleEnded = true;
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Vertex> getCycle() {
        if (isCycleEnded) return this.cycleVertices;
        else return null;
    }
}

