// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.platform.core;

/**
 * Typed constants for every key used on the {@link BlackBoard}.
 *
 * <p>Use these instead of raw string literals so that typos are caught at compile time
 * and every publish/subscribe pair is easy to discover via "Find Usages".
 *
 * <pre>
 * // publish
 * blackboard.setData(BlackBoardKeys.UNDO_POINT, null);
 *
 * // subscribe
 * blackboard.addListener(BlackBoardKeys.UNDO_POINT, (key, value) -> { ... });
 * </pre>
 */
public final class BlackBoardKeys {

    private BlackBoardKeys() {}

    /**
     * Fired once after all plugins and extensions have initialised.
     * Value is always the string {@code "Pi"}.
     *
     * @see graphtea.platform.Application#POST_INIT_EVENT
     */
    public static final String POST_INIT = "Post Initialization";

    /**
     * The application-wide {@link graphtea.platform.preferences.Preferences} object.
     * Written once during startup by {@code Preferences}; read by preference dialogs.
     */
    public static final String PREFERENCES = "Preferences";

    /**
     * The application-wide settings object ({@code SETTINGS} instance from
     * {@link graphtea.platform.extension.ExtensionLoader}).
     * Written once during startup by {@code Application}.
     */
    public static final String SETTINGS = "SETTINGS";

    /**
     * Set to {@code null} whenever an action that modifies the graph completes,
     * signalling the undo stack to capture the current state.
     *
     * @see graphtea.plugins.commonplugin.undo.UndoAction
     */
    public static final String UNDO_POINT = "undo point";

    /**
     * Analytics / activity tracking. Value is an {@code AEvent} describing the
     * action that just ran. Subscribers (e.g. the main plugin) may log or display it.
     *
     * @see graphtea.platform.core.AEvent
     */
    public static final String ATRACK = "ATrack";

    /**
     * Emitted by {@code MoveSelected} while the user is dragging vertices.
     * Value is {@code "yes"} when a drag starts and {@code "no"} when it ends.
     */
    public static final String MOVE_SELECTED_MOVING = "MoveSelected.moving";

    /**
     * Absolute path of the most recently opened or saved file.
     * Empty string when no file has been opened in this session.
     */
    public static final String LAST_FILE = "last file";

    /**
     * Emitted when the user clicks a hyperlink inside an extension action.
     * Value is the constant string {@code "click on link"}.
     */
    public static final String CLICK_ON_LINK = "click on link";
}
