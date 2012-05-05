// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator;

import graphtea.library.algorithms.spanningtree.Kruskal;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;

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
