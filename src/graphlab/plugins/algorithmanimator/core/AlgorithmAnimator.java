// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.algorithmanimator.core;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.ui.GHTMLPageComponent;
import graphlab.library.algorithms.AutomatedAlgorithm;
import graphlab.library.event.AlgorithmStep;
import graphlab.library.event.Event;
import graphlab.library.event.EventDispatcher;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.plugins.algorithmanimator.AnimatorGUI;
import graphlab.plugins.algorithmanimator.core.atoms.*;

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
        for (AtomAnimator _ : animators)
            if (_.isAnimatable(ae)) {
                return _.animate(ae, blackboard);
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
        Event event1 = animateEvent(event);

        GHTMLPageComponent html = alggui.algorithmOutputTextArea;
//        JTextArea txt = alggui.algorithmOutputTextArea;
//        int cursorDot = txt.getCaret().getDot();
//        int cursorMark = txt.getCaret().getMark();
//        boolean isCursorInEnd = cursorDot == txt.getText().length();
        if (event1 != null && event1.getMessage() != null && event1.getMessage() != "") {
            html.appendHTML(event1.getMessage());
//            if (isCursorInEnd)
//                txt.getCaret().setDot(txt.getText().length());
//            else {
//                txt.getCaret().setDot(cursorMark);
//                txt.getCaret().moveDot(cursorDot);
//            }
//            txt.getCaret().setVisible(true);

        }

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

        return event1;
    }

    /**
     * handles gui control events
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        oneStep = false;
        alggui.pauseButton.setEnabled(true);
        alggui.playButton.setEnabled(true);
        alggui.playOneStepButton.setEnabled(true);

        if (e.getActionCommand().contains("Pause")) {
            alggui.pauseButton.setEnabled(false);
            paused = true;
        } else if (e.getActionCommand().equals("Play")) {
            alggui.playButton.setEnabled(false);
            paused = false;
        } else if (e.getActionCommand().contains("One Step")) {
            alggui.playOneStepButton.setEnabled(false);
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