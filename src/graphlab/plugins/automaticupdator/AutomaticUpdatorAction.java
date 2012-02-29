// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.automaticupdator;

import graphlab.plugins.automaticupdator.net.interdirected.autoupdate.AutomatedUpdate;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.main.extension.GraphActionExtension;

/**
 * @author Azin Azadi
 */
public class AutomaticUpdatorAction extends AutomatedUpdate implements GraphActionExtension {
    public String getName() {
        return "Check for Updates...";
    }

    public String getDescription() {
        return "Checks for and downloads application updates.";
    }

    public void action(GraphData gd) {
        new Thread() {
            public void run() {
                AutomaticUpdatorAction.run(null);
            }
        }.start();
    }
}
