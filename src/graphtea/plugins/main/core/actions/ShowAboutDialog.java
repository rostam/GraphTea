// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions;

import graphtea.platform.Application;
import graphtea.platform.StaticUtils;
import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

/**
 * Shows what GraphTea is and which version this is.
 *
 * <p>"About GraphTea" used to open the project's website in an external browser. That tells the
 * user nothing about the copy in front of them, needs a network connection to say anything at
 * all, and takes them out of the application to do it.
 *
 * @author azin azadi
 */
public class ShowAboutDialog extends graphtea.platform.core.AbstractAction {

    private final BlackBoard bb;

    /**
     * constructor
     *
     * @param bb the blackboard of the action
     */
    public ShowAboutDialog(BlackBoard bb) {
        super(bb);
        this.bb = bb;
        listen4Event(UIUtils.getUIEventKey("show about"));
    }

    public void performAction(String eventName, Object value) {
        if (GraphicsEnvironment.isHeadless()) {
            return;
        }
        SwingUtilities.invokeLater(this::show);
    }

    private void show() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(BorderFactory.createEmptyBorder(20, 24, 12, 24));

        JLabel title = new JLabel("GraphTea");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        body.add(title);
        body.add(Box.createVerticalStrut(4));
        body.add(new JLabel("Version " + Application.VERSION));
        body.add(Box.createVerticalStrut(14));
        body.add(new JLabel("<html><body style='width: 340px'>"
                + "A workbench for drawing graphs, generating them, and measuring their "
                + "properties.</body></html>"));
        body.add(Box.createVerticalStrut(14));
        body.add(new JLabel("<html><body style='width: 340px'>"
                + "Free software under the GNU General Public License. Built by the Graph "
                + "Theory Software Foundation and the Mathematical Science Department of "
                + "Sharif University of Technology.</body></html>"));

        JDialog dialog = new JDialog(UIUtils.getGFrame(bb), "About GraphTea", true);

        JButton website = new JButton("Visit the website");
        website.addActionListener(e -> StaticUtils.browse("https://graphtheorysoftware.com"));
        JButton close = new JButton("Close");
        close.addActionListener(e -> dialog.dispose());

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.setBorder(BorderFactory.createEmptyBorder(8, 24, 16, 24));
        buttons.add(Box.createHorizontalGlue());
        buttons.add(website);
        buttons.add(Box.createHorizontalStrut(8));
        buttons.add(close);

        JPanel content = new JPanel(new BorderLayout());
        content.add(body, BorderLayout.CENTER);
        content.add(buttons, BorderLayout.SOUTH);

        dialog.setContentPane(content);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(UIUtils.getGFrame(bb));
        dialog.getRootPane().setDefaultButton(close);
        dialog.setVisible(true);
    }
}
