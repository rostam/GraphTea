// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commonplugin.reporter.actions;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionOccuredData;
import graphtea.plugins.main.core.actions.StatusBarMessage;
import graphtea.ui.components.GComponentInterface;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExceptionReport extends graphtea.platform.core.AbstractAction implements GComponentInterface {
    String s;

    public ExceptionReport(BlackBoard bb) {
        super(bb);
        listen4Event(ExceptionOccuredData.EVENT_KEY);
    }

    @Override
    public void performAction(String eventName, Object value) {
        s = getLatestExceptionStackStrace(blackboard);
        //just for debugging reasons
        System.err.println(s);
        show.setVisible(true);
        StatusBarMessage.showQuickMessage(blackboard, "There was an error!");
        show.setBackground(Color.red.brighter());
        new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                show.setBackground(Color.white);
            }
        }).start();
//		try {
//			b.browse(new URL("http://sourceforge.net/tracker/?func=add&group_id=134117&atid=728827"));
//		} catch (MalformedURLException e) {
//			ExceptionHandler.catchException(e);
//		}
    }

    public static String getLatestExceptionStackStrace(BlackBoard blackboard) {
        String s = "";
        ExceptionOccuredData exceptionData = blackboard.getData(ExceptionOccuredData.EVENT_KEY);
        if (exceptionData != null) {
            StackTraceElement[] ee = exceptionData.e.getStackTrace();
            s = exceptionData.e.toString() + "\n";
            for (StackTraceElement _ : ee) {
                s += "\tat " + _.toString() + "\n";
            }
        }
        return s;
    }

    //    Browser b=new Browser();
    JButton show = new JButton("View Error Details");

    public Component getComponent(BlackBoard bb) {
        show.setVisible(false);
        show.setBorder(new LineBorder(Color.red.darker()));
        show.setBackground(Color.red.brighter());
        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TextBox.showTextDialog("Error Details", s);
            }
        });
        return show;
    }
}
