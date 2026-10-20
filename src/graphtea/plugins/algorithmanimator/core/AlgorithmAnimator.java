// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core;

import graphtea.library.event.AlgorithmStep;
import graphtea.library.event.Event;
import graphtea.library.event.EventDispatcher;
import graphtea.platform.core.BlackBoard;
import graphtea.plugins.algorithmanimator.AnimatorGUI;
import graphtea.plugins.algorithmanimator.core.atoms.DelayEventHandler;
import graphtea.plugins.algorithmanimator.core.atoms.GraphSelect;
import graphtea.plugins.algorithmanimator.core.atoms.NewGraph;
import graphtea.plugins.algorithmanimator.core.atoms.PrePostWork;
import graphtea.plugins.algorithmanimator.core.atoms.ShowMessage;
import graphtea.plugins.algorithmanimator.core.atoms.VertexSelect;
import graphtea.ui.UIUtils;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Runs an algorithm step by step under the control of the runner window.
 *
 * <p>The algorithm runs on its own thread and reports each step through
 * {@link #dispatchEvent}, which waits there while the user has paused. Everything that touches
 * the window is passed to the event thread; previously the algorithm thread appended to the log
 * and enabled buttons itself. Closing the window stops the algorithm, where before a paused
 * algorithm thread went on waiting for a Play that could no longer be pressed.
 *
 * @author Azin Azadi
 */
public class AlgorithmAnimator implements EventDispatcher, ActionListener {

    /** Thrown on the algorithm thread to unwind it when the user closes the runner window. */
    public static final class AlgorithmCancelled extends RuntimeException {
        private static final long serialVersionUID = 1L;

        AlgorithmCancelled() {
            super("The algorithm was stopped.");
        }
    }

    // Registered once. The constructor used to add these six to the static list on every run,
    // so the list grew by six each time an algorithm was started.
    static final List<AtomAnimator<Event>> animators = new CopyOnWriteArrayList<>();

    static {
        registerAtomAnimation(new VertexSelect());
        registerAtomAnimation(new GraphSelect());
        registerAtomAnimation(new PrePostWork());
        registerAtomAnimation(new NewGraph());
        registerAtomAnimation(new DelayEventHandler());
        registerAtomAnimation(new ShowMessage());
    }

    /** Longest wait between steps, at the slow end of the speed slider. */
    private static final int MAX_STEP_DELAY_MS = 1000;

    private static final Dimension WINDOW_SIZE = new Dimension(380, 520);

    final BlackBoard blackboard;
    private volatile boolean paused = true;
    /** Run one step, then pause again. */
    private volatile boolean oneStep;
    private volatile boolean cancelled;
    /** Read by the algorithm thread, written on the event thread. */
    private volatile int speed = 50;

    private AnimatorGUI alggui;
    private JDialog window;

    public AlgorithmAnimator(BlackBoard blackboard) {
        this.blackboard = blackboard;
    }

    /**
     * registers a new kind of AtomAnimator
     *
     * @param a The animator
     */
    @SuppressWarnings("unchecked")
    public static void registerAtomAnimation(AtomAnimator a) {
        animators.add(a);
    }

    public Event animateEvent(Event ae) {
        for (AtomAnimator<Event> animator : animators) {
            if (animator.isAnimatable(ae)) {
                return animator.animate(ae, blackboard);
            }
        }
        return ae;
    }

    /**
     * Called on the algorithm thread for every event the algorithm reports.
     *
     * @param event The event
     * @return The event, as transformed by the animator that handled it
     */
    public Event dispatchEvent(Event event) {
        checkCancelled();
        if (event instanceof AlgorithmStep) {
            if (oneStep) {
                paused = true;
            } else {
                sleep((long) ((100 - speed) / 100.0 * MAX_STEP_DELAY_MS));
            }
        }

        log(event);
        Event output = animateEvent(event);
        if (output != event && output != null && event.getMessage() != output.getMessage()) {
            log(output);
        }

        if (oneStep && event instanceof AlgorithmStep) {
            oneStep = false;
            onEventThread(() -> setControls(true, false, true));
        }
        while (paused) {
            checkCancelled();
            sleep(100);
        }
        return output;
    }

    private void checkCancelled() {
        if (cancelled) {
            throw new AlgorithmCancelled();
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            cancelled = true;
            throw new AlgorithmCancelled();
        }
    }

    private void log(Event event) {
        String message = event == null ? null : event.getMessage();
        if (message != null && !message.isEmpty() && alggui != null) {
            onEventThread(() -> alggui.algorithmOutputTextArea.appendHTML(message));
        }
    }

    /**
     * Handles the runner's buttons. Called on the event thread.
     *
     * @param e The action event
     */
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case AnimatorGUI.PLAY -> {
                oneStep = false;
                paused = false;
                setControls(false, true, false);
            }
            case AnimatorGUI.PAUSE -> {
                oneStep = false;
                paused = true;
                setControls(true, false, true);
            }
            case AnimatorGUI.STEP -> {
                oneStep = true;
                paused = false;
                setControls(false, true, false);
            }
            default -> {
            }
        }
    }

    private void setControls(boolean play, boolean pause, boolean step) {
        alggui.playButton.setEnabled(play);
        alggui.pauseButton.setEnabled(pause);
        alggui.playOneStepButton.setEnabled(step);
    }

    /**
     * Opens the runner window beside the main window. May be called from any thread.
     *
     * @param algorithmName The algorithm name
     */
    public void createControlDialog(String algorithmName) {
        onEventThreadAndWait(() -> {
            alggui = new AnimatorGUI(this, blackboard);
            alggui.speedSlider.addChangeListener(e -> speed = alggui.speedSlider.getValue());
            speed = alggui.speedSlider.getValue();

            window = new JDialog(UIUtils.getGFrame(blackboard), algorithmName, false);
            window.setContentPane(alggui.animatorFrame);
            window.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            window.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    cancelled = true;
                    paused = false;
                }
            });
            window.setSize(WINDOW_SIZE);
            placeBesideOwner(window);
            window.setVisible(true);
        });
    }

    /**
     * Tells the window the algorithm has run to the end, or was stopped.
     *
     * @param completed false if the algorithm was stopped or failed
     */
    public void algorithmEnded(boolean completed) {
        onEventThread(() -> {
            if (alggui == null || !window.isDisplayable()) {
                return;
            }
            setControls(false, false, false);
            alggui.algorithmOutputTextArea.appendHTML(completed
                    ? "<p><b>Finished.</b></p>" : "<p><b>Stopped.</b></p>");
            window.setTitle(window.getTitle() + (completed ? " — finished" : " — stopped"));
        });
    }

    /** Puts the window at the right-hand edge of the main window, where it covers least. */
    private static void placeBesideOwner(JDialog d) {
        if (d.getOwner() == null) {
            d.setLocationRelativeTo(null);
            return;
        }
        Rectangle o = d.getOwner().getBounds();
        d.setLocation(o.x + o.width - d.getWidth() - 24, o.y + 120);
    }

    private static void onEventThread(Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    private static void onEventThreadAndWait(Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
            return;
        }
        try {
            SwingUtilities.invokeAndWait(r);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e.getCause());
        }
    }
}
