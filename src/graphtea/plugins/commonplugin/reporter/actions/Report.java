// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.reporter.actions;

import graphtea.platform.core.BlackBoard;
import graphtea.ui.extension.UIActionExtension;

public class Report implements UIActionExtension {


    public void actionPerformed(BlackBoard blackBoard) {
        String st = ExceptionReport.getLatestExceptionStackStrace(blackBoard);
        if (!st.equals(""))
            TextBox.showTextDialog("Error Details", "To report a bug go to https://github.com/graphtheorysoftware/GraphTea/issues , please put this stack trace in there: \n"+st);
        else
            TextBox.showTextDialog("Error Details", "To report a bug go to https://github.com/graphtheorysoftware/GraphTea/issues.");
    }
}