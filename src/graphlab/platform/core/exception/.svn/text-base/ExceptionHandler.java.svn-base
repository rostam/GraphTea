// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.core.exception;

import graphlab.platform.StaticUtils;
import graphlab.platform.Application;
import graphlab.platform.core.BlackBoard;

import java.lang.Thread.UncaughtExceptionHandler;

public class ExceptionHandler implements UncaughtExceptionHandler {

    BlackBoard blackBoard;

    public ExceptionHandler(BlackBoard bb) {
        super();
        blackBoard = bb;
    }

    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("Exception Occured: " + e.toString());
        e.printStackTrace();
        StaticUtils.addExceptiontoLog(e, blackBoard);
    }

    public static void catchException(Exception e) {
        e.printStackTrace();
        if (Application.blackboard != null)
            StaticUtils.addExceptiontoLog(e, Application.blackboard);
    }
}
