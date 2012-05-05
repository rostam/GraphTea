// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.extensions;

import java.io.File;

import graphtea.plugins.connector.matlab.MatlabRunner;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;

public class HelloWorldReport implements GraphReportExtension {
    public String getName() {
        return "hello world";
    }

    public String getDescription() {
        return "just a hello world extension!";
    }

    public Object calculate(GraphData gd) {
    	return "Hello World2!";
    }

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}
}