// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

/*
 * Splash.java
 *
 * Created on September 19, 2005, 8:09 PM
 */
package graphtea.platform;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.URL;

/**
 * The window shown while GraphTea starts up.
 *
 * <p>This used to redirect {@code System.err} into a text area, so the first thing a new user
 * ever saw was two hundred lines of {@code Adding action graphtea.plugins.…(do bfs,null)}.
 * It now shows the name of the phase in progress and nothing else; the console still gets the
 * full detail for anyone debugging a startup problem.
 */
public class GSplash extends JFrame {

    private static final long serialVersionUID = 7023272493672748876L;

    private JLabel status;
    private JProgressBar progress;

    GSplash() {
        initComponents();
    }

    /**
     * Shows the splash and starts the progress indicator.
     */
    void showMessages() {
        setVisible(true);
        toFront();
    }

    /**
     * Updates the single line of text under the logo.
     *
     * @param message a short phase description in the user's language, e.g. "Loading extensions"
     */
    public void setStatus(String message) {
        Runnable r = () -> {
            status.setText(message);
            status.repaint();
        };
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    /**
     * Hides the splash. Safe to call more than once.
     */
    void stopShowing() {
        SwingUtilities.invokeLater(() -> {
            progress.setIndeterminate(false);
            setVisible(false);
            dispose();
        });
    }

    private void initComponents() {
        JLabel bg = new JLabel();
        getContentPane().setLayout(null);
        setResizable(false);
        setUndecorated(true);

        status = new JLabel("Starting…", SwingConstants.LEFT);
        status.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        status.setForeground(new Color(0x2C3E50));
        add(status);

        progress = new JProgressBar();
        progress.setIndeterminate(true);
        add(progress);

        bg.setHorizontalAlignment(SwingConstants.LEFT);
        Class<? extends GSplash> clazz = getClass();
        URL resource = clazz.getResource("splash.jpg");
        if (resource == null) {
            resource = clazz.getResource("splash.gif");
        }
        if (resource == null) {
            resource = clazz.getResource("splash.png");
        }
        if (resource == null) {
            resource = clazz.getResource("splash.bmp");
        }
        int w = 480;
        int h = 320;
        if (resource != null) {
            ImageIcon icon = new ImageIcon(resource);
            bg.setIcon(icon);
            w = icon.getIconWidth();
            h = icon.getIconHeight();
            bg.setBounds(0, 0, w, h);
        }

        // Sit the status line and bar just above the bottom edge of the artwork.
        status.setBounds(40, h - 62, w - 80, 18);
        progress.setBounds(40, h - 40, w - 80, 10);

        bg.setVerticalAlignment(SwingConstants.TOP);
        add(bg);
        Dimension size = new Dimension(w, h);
        super.setPreferredSize(size);
        super.setSize(size);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - w) / 2, (screen.height - h) / 2);
    }
}
