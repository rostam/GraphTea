// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.platform.core;

/**
 * AbstractAction itself is a wrapper for the interface Action.
 * As you see in the source, it contains only two methods to implement. The first one is the action that must be done by this action, performAction() gets the event(key) that has caused this action to be run. This helps if an action is result of multiple events or when event handling must be coped with in the implementation itself.
 * <p/>
 * The abstract class, AbstractAction is the minimal implementation of an Action in a class. It integrates the Action with the GraphLab structure. As a result any recognized AbstractAction in the GraphLab ui is Runnable. The introducing procedure is done via Xml files.
 * <p/>
 * There is no default constructor, so every descendant of AbstractAction needs to call the super constructor in its constructor first. It is because the constructor of the AbstractAction assures that the action is registered in a BlackBoard and it is enabled. Method enable() and disable() are designated to control enablity of Action groups.
 * <p/>
 * As you can see AbstractAction has implemented The interface Listener. It is easy to see why! Every action is a reaction to event(key change) that is called from a user or program itself. so every AbstractAction is listening to it's relevant event to be fired. Methods listen4Event() and unListenEvent() are designed to handle the relevant events.
 * <p/>
 * Basically if you want to do anything in GraphLab, you need to extend AbstractAction. Looking at it's code might help.
 * <p/>
 * note: As you may see working with AbstractAction to handle UI events is not so simple, Also there are many classes in !GraphLab that use AbstractAction. But this is not advised to you, It is easier to use Extensions whenever you want to interact with user interface. (basically UIActionExtension, but almost every extensions are welcome!)
 *
 * @see graphlab.ui.extension.UIActionExtension
 * @see graphlab.platform.core.Action
 * @see graphlab.platform.core.BlackBoard
 * @author rouzbeh ebrahimi
 * @author Azin Azadi
 */
public abstract class AbstractAction implements Action, Listener {
    protected BlackBoard blackboard;
    private boolean enabled = false;
    String lastListenedEventKey = null;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public AbstractAction(BlackBoard bb) {
        setBlackBoard(bb);
        enable();
    }

    /**
     * like Action
     *
     * @param key
     * @param value
     */
    public abstract void performAction(String key, Object value);

    public final void setBlackBoard(BlackBoard t) {
        blackboard = t;
    }

    /**
     * @param key
     */
    public final void keyChanged(String key, Object value) {
        if (enabled)
            performAction(key, value);
    }


    /**
     * unlisten the event, see:listen4Event
     *
     * @param key the Event for unlisten
     */
    protected final void unListenEvent(String key) {
        blackboard.removeListener(key, this);
    }

    public String getLastListenedEventKey() {
        return lastListenedEventKey;
    }

    /**
     * listens for an event in the black board, (so multiple listening is available)
     *
     * @param e the Event for listening
     */
    protected final void listen4Event(String key) {
        blackboard.addListener(key, this);
        lastListenedEventKey = key;
    }

    /**
     * @return the blackboard of this action
     */
    public BlackBoard getBlackBoard() {
        return blackboard;
    }

    /**
     * disables the action
     * prevent it from doing anything.
     * it is used in the configuration
     */
    public void disable() {
        if (enabled) {
            enabled = false;
        }
    }

    /**
     * enables the action
     * see disable
     */
    public void enable() {
        if (!enabled) {
            enabled = true;
        }
    }

    public final boolean isEnable() {
        return enabled;
    }
}
