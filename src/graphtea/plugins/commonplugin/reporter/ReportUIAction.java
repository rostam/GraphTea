// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.reporter;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionOccuredData;
import graphtea.ui.extension.UIActionExtension;

public class ReportUIAction implements UIActionExtension {

    public void actionPerformed(BlackBoard blackBoard) {
        String st = getLatestExceptionStackStrace(blackBoard);
        if (!st.equals(""))
            TextBox.showTextDialog("Error Details", "To report a bug go to https://github.com/graphtheorysoftware/GraphTea/issues , please put this stack trace in there: \n" + st);
        else
            TextBox.showTextDialog("Error Details", "To report a bug go to https://github.com/graphtheorysoftware/GraphTea/issues.");
    }

    public static String getLatestExceptionStackStrace(BlackBoard blackboard) {
        String s = "";
        ExceptionOccuredData exceptionData = blackboard.getData(ExceptionOccuredData.EVENT_KEY);
        if (exceptionData != null) {
            StackTraceElement[] ee = exceptionData.e.getStackTrace();
            s = exceptionData.e.toString() + "\n";
            for (StackTraceElement element : ee) {
                s += "\tat " + element.toString() + "\n";
            }
        }
        return s;
    }


}