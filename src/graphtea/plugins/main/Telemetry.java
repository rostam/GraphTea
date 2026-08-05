// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.main;

import graphtea.platform.ui.UserNotifier;

import java.util.prefs.Preferences;

/**
 * Whether GraphTea may report usage back to its authors.
 *
 * <p>GraphTea sends the names of actions the user runs, plus the stack trace of any failure,
 * to an analytics endpoint, keyed by the machine's public IP address. That is a reasonable
 * thing to want and an unreasonable thing to do without asking, so the answer is asked for
 * once and remembered.
 *
 * <p>The choice is stored outside the {@code Settings} mechanism deliberately: it has to be
 * readable before plugins finish loading, and it must survive a corrupt preferences file by
 * defaulting to <em>off</em> rather than to on.
 */
public final class Telemetry {

    private static final String NODE = "graphtea";
    private static final String KEY_ASKED = "usageReportingAsked";
    private static final String KEY_ENABLED = "usageReportingEnabled";

    private static Boolean cached;

    private Telemetry() {
    }

    private static Preferences prefs() {
        return Preferences.userRoot().node(NODE);
    }

    /**
     * Returns whether usage reporting is on, asking the user the first time it is called.
     *
     * @return true when the user has agreed to usage reporting
     */
    public static synchronized boolean isEnabled() {
        if (cached != null) {
            return cached;
        }
        Preferences p;
        try {
            p = prefs();
        } catch (RuntimeException e) {
            // No usable preference store: stay silent rather than transmit by default.
            cached = Boolean.FALSE;
            return false;
        }
        if (!p.getBoolean(KEY_ASKED, false)) {
            boolean agreed = UserNotifier.ask("Help improve GraphTea?",
                    "<html><body style='width: 380px'>"
                            + "<p>GraphTea can report which features you use, and the technical "
                            + "detail of any errors, to the people who develop it. This includes "
                            + "your computer's public IP address.</p>"
                            + "<p>Nothing about your graphs is sent. You can change this later "
                            + "under <b>File &rsaquo; Settings</b>.</p></body></html>",
                    "Send usage reports", "No thanks", false);
            p.putBoolean(KEY_ASKED, true);
            p.putBoolean(KEY_ENABLED, agreed);
            cached = agreed;
            return agreed;
        }
        cached = p.getBoolean(KEY_ENABLED, false);
        return cached;
    }

    /**
     * Turns usage reporting on or off. Takes effect for events raised from now on; a running
     * session keeps whichever sender thread it already started.
     *
     * @param enabled true to report usage
     */
    public static synchronized void setEnabled(boolean enabled) {
        cached = enabled;
        Preferences p = prefs();
        p.putBoolean(KEY_ASKED, true);
        p.putBoolean(KEY_ENABLED, enabled);
    }
}
