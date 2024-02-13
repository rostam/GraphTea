// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator;

import graphtea.extensions.algorithms.BiconnectedComponents;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;

public class BiconnectedComponentsAnim
        extends BiconnectedComponents
        implements AlgorithmExtension {

    public String getName() {
        return "Biconnected Components";
    }

    public String getDescription() {
        return "Biconnected Components";
    }
}
