// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.algorithmanimator;

import graphtea.graph.ui.GHTMLPageComponent;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.components.ToolbarIcons;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Hashtable;

/**
 * The controls of the algorithm runner: play, pause, step, a speed slider, and the log of what
 * the algorithm has done.
 *
 * <p>This was IntelliJ GUI-designer output: a GridBagLayout padded with empty spacer panels, a
 * "Play One Step" button too narrow for its label, and a speed slider labelled 0 to 100 with no
 * hint of which end was fast. The output pane was built from an instance initialiser, before the
 * constructor had stored the blackboard, so it always received null.
 *
 * @author Azin Azadi
 */
public class AnimatorGUI {
    /** Action commands, so the controller does not have to match on button labels. */
    public static final String PLAY = "play";
    public static final String PAUSE = "pause";
    public static final String STEP = "step";

    public final JPanel animatorFrame = new JPanel(new BorderLayout(0, 8));
    public final JButton playButton = button("Play", "icon:play", PLAY, "Run the algorithm to the end");
    public final JButton pauseButton = button("Pause", "icon:pause", PAUSE, "Stop after the current step");
    public final JButton playOneStepButton = button("Step", "icon:step", STEP, "Run one step, then pause");
    public final JSlider speedSlider = new JSlider(0, 100, 50);
    public final GHTMLPageComponent algorithmOutputTextArea;

    public AnimatorGUI(ActionListener listener, BlackBoard blackboard) {
        playButton.addActionListener(listener);
        pauseButton.addActionListener(listener);
        playOneStepButton.addActionListener(listener);
        pauseButton.setEnabled(false);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(0, new JLabel("Slow"));
        labels.put(100, new JLabel("Fast"));
        speedSlider.setLabelTable(labels);
        speedSlider.setPaintLabels(true);
        speedSlider.setToolTipText("How long to wait between steps");

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        buttons.add(playButton);
        buttons.add(pauseButton);
        buttons.add(playOneStepButton);

        JPanel speed = new JPanel(new BorderLayout(8, 0));
        speed.add(new JLabel("Speed"), BorderLayout.WEST);
        speed.add(speedSlider, BorderLayout.CENTER);

        JPanel controls = new JPanel(new BorderLayout(0, 6));
        controls.add(buttons, BorderLayout.NORTH);
        controls.add(speed, BorderLayout.SOUTH);

        algorithmOutputTextArea = new GHTMLPageComponent(blackboard);
        algorithmOutputTextArea.setBorder(BorderFactory.createLineBorder(
                javax.swing.UIManager.getColor("Separator.foreground")));
        algorithmOutputTextArea.setHTML("<html><body><h3>Steps</h3>"
                + "<p>Press <b>Play</b> to run the algorithm, or <b>Step</b> to go one step at a time.</p>"
                + "</body></html>");
        algorithmOutputTextArea.autoScroll();

        animatorFrame.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        animatorFrame.add(controls, BorderLayout.NORTH);
        animatorFrame.add(algorithmOutputTextArea, BorderLayout.CENTER);
    }

    private static JButton button(String label, String icon, String command, String tooltip) {
        JButton b = new JButton(label, ToolbarIcons.forSpec(icon));
        b.setActionCommand(command);
        b.setToolTipText(tooltip);
        return b;
    }
}
