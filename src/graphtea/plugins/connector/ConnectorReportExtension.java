// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.connector;


import graphtea.graph.graph.GraphModel;
import graphtea.platform.attribute.AttributeSet;
import graphtea.platform.attribute.AttributeSetImpl;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.commandline.Shell;
import graphtea.plugins.reports.extension.GraphReportExtension;

import java.io.File;

/**
 * @author Mohammad Ali Rostami
 * @email ma.rostami@yahoo.com
 */

public class ConnectorReportExtension implements GraphReportExtension, Parametrizable {
    File matlabFile;
    private BlackBoard blackboard;
    private ConnectorDS connectorDs;


    @Parameter
    public AttributeSet atrs = new AttributeSetImpl();


    public ConnectorReportExtension(File matlabFile, BlackBoard blackboard, ConnectorDS connectorDs) {
        this.matlabFile = matlabFile;
        this.blackboard = blackboard;
        this.connectorDs = connectorDs;
        this.atrs = connectorDs.atrs;
    }

    public String getName() {
        return connectorDs.commandName;
    }

    public String getDescription() {
        return connectorDs.description;
    }

    public Object calculate(GraphModel g) {
        Shell currentShell = Shell.getCurrentShell(blackboard);
        String args = connectorDs.getArgs(); //this works becase atrs in this class points to atrs in connectorDS (they are the same!)
        return currentShell.evaluate(connectorDs.commandName + "(" + args + ")");
    }

    public String checkParameters() {
        return null;
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}
}
