// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.reports.extension;

import graphtea.platform.extension.Extension;
import graphtea.plugins.reports.GraphReportInterface;

/**
 * @author azin azadi
 */
public interface GraphReportExtension<t> extends Extension, GraphReportInterface<t> {
	public String getCategory();
}
