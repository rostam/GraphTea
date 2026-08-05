// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.extensions.reports;

import graphtea.graph.graph.GraphModel;
import graphtea.plugins.reports.extension.GraphReportExtension;

/**
 * @author Azin Azadi
 */
public class ChromaticNumber implements GraphReportExtension<Integer>, ColoringListener {

    public String getName() {
        return "Chromatic Number";
    }

    public String getDescription() {
        return "The chromatic number of graph";
    }

    Partitioner p;
    int ct;
    boolean found;

    /**
     * @param g the graph to colour
     * @return the least number of colours needed to properly colour {@code g}
     */
    public Integer calculate(GraphModel g) {
        if (g == null || g.getVerticesCount() == 0) {
            return 0;
        }
        p = new Partitioner(g);
        // Partitioner.findAllPartitionings(t) hands out colours t, t-1, … 1 and 0, so it
        // answers "can this be coloured with t + 1 colours?". Starting the search at 1 meant
        // the smallest answer it could ever give was 2, and every edgeless graph — which needs
        // exactly one colour — was reported as needing two.
        ct = 0;
        found = false;
        while (!found) {
            found = isColorable(ct++);
        }
        return ct;
    }

    public boolean isColorable(int t) {
        return p.findAllPartitionings(t, this);
    }

    public boolean coloringFound(final int t) {
        found = true;
        return true;
    }

    @Override
    public String getCategory() {
        return "Coloring";
    }

    public static int getChromaticNumber(GraphModel g) {
        ChromaticNumber cn = new ChromaticNumber();
        return cn.calculate(g);
    }
}
