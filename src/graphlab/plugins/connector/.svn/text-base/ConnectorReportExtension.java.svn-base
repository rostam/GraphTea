// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.connector;



import graphlab.platform.attribute.AttributeSet;
import graphlab.platform.attribute.AttributeSetImpl;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.parameter.Parameter;
import graphlab.platform.parameter.Parametrizable;
import graphlab.plugins.commandline.Shell;
import graphlab.plugins.main.GraphData;
import graphlab.plugins.reports.extension.GraphReportExtension;

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

    public Object calculate(GraphData gd) {
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
