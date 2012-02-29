// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.tests;

import graphlab.plugins.algorithmanimator.extension.AlgorithmExtension;

public class TestInducedSubgraphs
        extends graphlab.library.algorithms.subgraphs.TestInducedSubgraphs
        implements AlgorithmExtension {

    public String getName() {
        return "Induced Subgraphs Test";
    }

    public String getDescription() {
        return "Just Induced Subgraphs Test, no input from user";
    }
}
