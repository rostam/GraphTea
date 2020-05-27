// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.actions.G6;

import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;
import graphtea.ui.extension.AbstractExtensionAction;

import javax.swing.*;

/**
 * @author Azin Azadi
 */


public class G6Checker implements GraphActionExtension, Parametrizable {
    @Parameter(name = "G6 Checker")
    public String graphs = "";

    public String getName() {
        return "G6 Checker";
    }

    public String getDescription() {
        return "G6 Checker";
    }

    public void action(GraphData graphData) {
        if(graphs.equals("SureshWorks")) {
            for(JMenu m : AbstractExtensionAction.ourW) {
                m.setVisible(true);
            }
        } else {
            for(JMenu m : AbstractExtensionAction.ourW) {
                m.setVisible(false);
            }
        }
    }

    public String checkParameters() {
        return null;
    }

    @Override
    public String getCategory() {
        return "Other Actions";
    }
}


