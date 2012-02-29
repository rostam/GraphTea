// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commonplugin.reporter.actions;

import graphlab.platform.core.BlackBoard;
import graphlab.plugins.commonplugin.reporter.Browser;
import graphlab.ui.extension.UIActionExtension;

public class Report implements UIActionExtension {

//    public static final String EVENT_KEY = UIUtils.getUIEventKey("Bug Buddy");

//    public Report(BlackBoard bb) {
//        super(bb);
//        listen4Event(EVENT_KEY);
//    }

//    public void performAction(String eventName, Object value) {
//        Browser b = new Browser();
//        b.pack();
//        b.setVisible(true);
//    }

    public static void showBugBuddy() {
        Browser.browse(Browser.header + Browser.footer);
    }

    public void actionPerformed(BlackBoard blackBoard) {
//        try {
        String st = ExceptionReport.getLatestExceptionStackStrace(blackBoard);
        if (!st.equals(""))
            TextBox.showTextDialog("Error Details", "To report a bug go to http://graphlab.sharif.edu/trac , register, login and then create a ticket, last stack trace is: \n"+st);
        else
            TextBox.showTextDialog("Error Details", "To report a bug go to http://graphlab.sharif.edu/trac , register, login and then create a ticket.");
//            Browser.browse(new URL("http://graphlab.sharif.ir/trac/newticket"));
//        } catch (MalformedURLException e) {
//            ExceptionHandler.catchException(e);
//        }
    }
}