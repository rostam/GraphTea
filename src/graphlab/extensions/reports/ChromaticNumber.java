// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.extensions.reports;

import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;

/**
 * @author Azin Azadi
 */
public class ChromaticNumber implements GraphReportExtension, ColoringListener {

    public String getName() {
        return "Chromatic Number";
    }

    public String getDescription() {
        return "The chromatic number of graph";
    }

    Partitioner p;
    int ct;
    boolean found;

    public Object calculate(GraphData gd) {
        p = new Partitioner(gd.getGraph());
        ct = 1;
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
		// TODO Auto-generated method stub
		return "Property";
	}
}
