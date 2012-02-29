// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator;

import graphlab.library.algorithms.spanningtree.Kruskal;
import graphlab.plugins.algorithmanimator.extension.AlgorithmExtension;

public class KruskalAnim
        extends Kruskal
        implements AlgorithmExtension {

    public String getName() {
        return "Kruskal";
    }

    public String getDescription() {
        return "Kruskal Minimum Spanning Tree";
    }
}
