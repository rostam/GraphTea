// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.algorithmanimator;

import graphtea.library.event.EventDispatcher;
import graphtea.plugins.algorithmanimator.extension.AlgorithmExtension;

import javax.swing.*;


public class More implements AlgorithmExtension {

    public String getName() {
        return "more";
    }

    public String getDescription() {
        return "more";
    }

    public void doAlgorithm() {
        JOptionPane.showMessageDialog(null, "There are more algorithms available in GraphTea Library that do not have an extension \nin GraphTea GUI yet. You are welcome to explore.");
    }

    public void acceptEventDispatcher(EventDispatcher e) {
    }

}
