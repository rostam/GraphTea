// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.reports.extension;

import graphlab.platform.extension.Extension;
import graphlab.plugins.reports.GraphReportInterface;

/**
 * @author azin azadi

 */
public interface GraphReportExtension<t> extends Extension, GraphReportInterface<t> {
	public String getCategory();
}
