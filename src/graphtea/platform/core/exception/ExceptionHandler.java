// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.platform.core.exception;

import graphtea.platform.Application;
import graphtea.platform.StaticUtils;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.ui.UserNotifier;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Central handler for failures that escape the code that caused them.
 *
 * <p>Every path here does three things: log to the console for developers, record on the
 * blackboard for anything listening, and &mdash; the part that used to be missing &mdash;
 * tell the user, via {@link UserNotifier}. A failure the user cannot see is a failure they
 * will report as "nothing happens when I click it".
 */
public class ExceptionHandler implements UncaughtExceptionHandler {

    BlackBoard blackBoard;

    public ExceptionHandler(BlackBoard bb) {
        super();
        blackBoard = bb;
    }

    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("Exception Occurred: " + e);
        e.printStackTrace();
        StaticUtils.addExceptiontoLog(e, blackBoard);
        UserNotifier.report(null, e);
    }

    /**
     * Records a failure whose context is not known.
     *
     * <p>Prefer {@link #catchException(String, Throwable)} wherever you can name what the user
     * was trying to do &mdash; "Chromatic Number" is a far better message than
     * "ArrayIndexOutOfBoundsException".
     *
     * @param e the failure
     */
    public static void catchException(Throwable e) {
        catchException(null, e);
    }

    /**
     * Records a failure, attributing it to a named user action.
     *
     * @param context what the user was doing, in their words; may be null
     * @param e       the failure
     */
    public static void catchException(String context, Throwable e) {
        e.printStackTrace();
        if (Application.blackboard != null) {
            StaticUtils.addExceptiontoLog(e, Application.blackboard);
        }
        UserNotifier.report(context, e);
    }

    /**
     * Records a failure the user does not need to hear about &mdash; one that is expected,
     * already handled, and has no bearing on what they asked for. Logs only.
     *
     * @param e the failure
     */
    public static void catchExceptionQuietly(Throwable e) {
        e.printStackTrace();
        if (Application.blackboard != null) {
            StaticUtils.addExceptiontoLog(e, Application.blackboard);
        }
    }
}
