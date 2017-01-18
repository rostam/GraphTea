// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.algorithmanimator;

import graphtea.library.algorithms.coloring.SampleColoring;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;

public class SampleColoringExtension extends SampleColoring
        implements AlgorithmExtension {

    public String getName() {
        return "Sample Coloring";
    }

    public String getDescription() {
        return "Colors vertices by their in-degrees";
    }

}
