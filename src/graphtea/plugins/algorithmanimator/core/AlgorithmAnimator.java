// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.algorithmanimator.core;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.ui.GHTMLPageComponent;
import graphtea.library.algorithms.AutomatedAlgorithm;
import graphtea.library.event.AlgorithmStep;
import graphtea.library.event.Event;
import graphtea.library.event.EventDispatcher;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.plugins.algorithmanimator.AnimatorGUI;
import graphtea.plugins.algorithmanimator.core.atoms.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Actually the class which animates the algorithms!
 *
 * @author Azin Azadi
 */
public class AlgorithmAnimator implements EventDispatcher, ActionListener {
    static Vector<AtomAnimator> animators = new Vector<AtomAnimator>();
    BlackBoard blackboard;
    private boolean paused = true;
    private JFrame f;
    /**
     * running algorithm just one step
     */
    private boolean oneStep;

    public AlgorithmAnimator(BlackBoard blackboard) {
        this.blackboard = blackboard;
        registerAtomAnimation(new VertexSelect());
        registerAtomAnimation(new GraphSelect());
        registerAtomAnimation(new PrePostWork());
        registerAtomAnimation(new NewGraph());
        registerAtomAnimation(new DelayEventHandler());
        registerAtomAnimation(new ShowMessage());
    }

    /**
     * registers a new kind of AtomAnimator
     *
     * @param a
     */
    static public void registerAtomAnimation(AtomAnimator a) {
        animators.add(a);
    }

    public Event animateEvent(Event ae) {
        for (AtomAnimator animator : animators)
            if (animator.isAnimatable(ae)) {
                return animator.animate(ae, blackboard);
            }
        return ae;
    }

    /**
     * The main method, Animates an algorithm
     *
     * @param aa
     */
    public void animateAlgorithm(final AutomatedAlgorithm aa) {
        new Thread() {
            public void run() {
                GraphModel g = blackboard.getData(GraphAttrSet.name);
                boolean b = g.isShowChangesOnView();
                g.setShowChangesOnView(true);
                aa.acceptEventDispatcher(AlgorithmAnimator.this);
                aa.doAlgorithm();
                g.setShowChangesOnView(b);
            }
        }.start();

    }

    /**
     * dispatchs events that recieved from the algorithm
     *
     * @param event
     * @return
     */
    public Event dispatchEvent(Event event) {
        try {

            if (event instanceof AlgorithmStep) {
                if (!oneStep) {
                    double s = 100.0 - alggui.speedSlider.getValue();
                    Thread.sleep((long) (10 * s));
                }
                if (oneStep) {
                    paused = true;
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Thread sleep has error.");
        }
        GHTMLPageComponent html = alggui.algorithmOutputTextArea;

        showMessageFor(html, event);

        Event output = animateEvent(event);
        if (event.getMessage() != output.getMessage())
            showMessageFor(html, output);

        if (oneStep && event instanceof AlgorithmStep) {
            alggui.playOneStepButton.setEnabled(true);
            oneStep = false;
        }
        while (paused)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                ExceptionHandler.catchException(e);
            }

        return output;
    }

    private void showMessageFor(GHTMLPageComponent html, Event event1) {
        if (event1 != null && event1.getMessage() != null && event1.getMessage() != "") {
            html.appendHTML(event1.getMessage());
        }
    }

    /**
     * handles gui control events
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        oneStep = false;
        alggui.pauseButton.setEnabled(false);
        alggui.playButton.setEnabled(true);
        alggui.playOneStepButton.setEnabled(true);

        if (e.getActionCommand().contains("Pause")) {
            alggui.pauseButton.setEnabled(false);
            paused = true;
        } else if (e.getActionCommand().equals("Play")) {
            alggui.playButton.setEnabled(false);
            alggui.pauseButton.setEnabled(true);
            paused = false;
        } else if (e.getActionCommand().contains("One Step")) {
            alggui.playOneStepButton.setEnabled(false);
            alggui.pauseButton.setEnabled(true);
            oneStep = true;
            paused = false;
        } else System.out.println("Sooti !");
    }

    AnimatorGUI alggui;

    /**
     * creates the GUI control frame
     *
     * @param algorithmName
     */
    public void createControlDialog(String algorithmName) {
        f = new JFrame();
        f.setTitle("Algorithm Runner: " + algorithmName);
        f.setAlwaysOnTop(true);
        alggui = new AnimatorGUI(this, blackboard);
        //moves the carret to the end of text, see dispatch event
//        alggui.algorithmOutputTextArea.getCaret().setDot(alggui.algorithmOutputTextArea.getText().length());

        f.add(alggui.animatorFrame);
        f.pack();
        f.setVisible(true);
    }

}