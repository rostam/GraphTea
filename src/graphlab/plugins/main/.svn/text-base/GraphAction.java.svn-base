// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main;

import graphlab.platform.core.AbstractAction;
import graphlab.platform.core.BlackBoard;

/**
 * @author Rouzbeh Ebrahimi
 */
public abstract class GraphAction extends AbstractAction {
    private GraphData graphData;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */

    public GraphAction(BlackBoard bb) {
        super(bb);
        graphData = new GraphData(bb);
    }

    /**
     * this method is the basic structure for performing a job in the AbstractAction structure,But Here we needed more
     * simple structure ;so we defined GraphAction and so there was no need of this signature!
     *
     * @param eventName
     * @param value
     */
    public final void performAction(String eventName, Object value) {
        performJob(graphData);
    }

    public abstract void performJob(GraphData graphData);
}
