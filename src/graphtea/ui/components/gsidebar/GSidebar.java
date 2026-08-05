// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gsidebar;


import graphtea.platform.core.BlackBoard;
import graphtea.ui.components.gbody.GBody;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * The strip of vertical tabs down the left edge of the main window.
 *
 * <p>Each tab used to be a small GIF with its label baked into the image, rotated and rendered
 * at around eight pixels; "Properties", "Shell" and "Reports" were all but unreadable, and
 * adding a tab meant drawing a new picture. The labels are now drawn with Java2D at a legible
 * size, which is what the long comment at the bottom of this file was waiting for.
 *
 * Author: Azin Azadi
 */
public class GSidebar extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -599129598320886343L;
    private final GBody targetBody;

    /** Keeps exactly one tab looking selected. */
    private final ButtonGroup group = new ButtonGroup();

    /**
     * constructor
     */
    public GSidebar(GBody targetBody, BlackBoard blackboard) {
        this.targetBody = targetBody;
        initComponents();
    }

    private void initComponents() {
        BoxLayout mgr = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(mgr);
    }

    /**
     * adds a button to side bar, and match it to component
     */
    public void addButton(String iconFileName, Component component, String label) {
        addButton(new ImageIcon(iconFileName), component, label);
    }

    public void addButton(URL iconURl, Component component, String label) {
        addButton(new ImageIcon(iconURl), component, label);
    }

    public void addButton(Icon icon, Component c, String label) {
        GSidebarButton b = new GSidebarButton(icon, c, this, label);
        group.add(b);
        if (label.equalsIgnoreCase("shell"))
            add(b,1);
        else add(b);
        validate();
    }

    /**
     * Shows a panel, or hides the sidebar if that panel is already the one showing.
     *
     * @param sidePanel the panel behind the clicked tab
     * @param label     its title
     */
    public void setPanel(Component sidePanel, String label) {
        if (targetBody.isShowingSideBarPane(sidePanel, label)) {
            // Clicking the open tab again closes the sidebar and hands the width back to the
            // canvas. ButtonGroup will not let a selected toggle deselect itself, so clear it.
            targetBody.hideSideBar();
            group.clearSelection();
            repaint();
        } else {
            targetBody.showSideBarPane(sidePanel, label);
        }
    }

    /**
     * hides the panel from the screen
     */
    public void hidePanel() {
        targetBody.hideSideBar();
    }
}

/**
 * A vertical tab: its label drawn rotated a quarter turn anticlockwise, so it reads bottom-to-top
 * down the side of the window.
 */
class GSidebarButton extends JToggleButton implements ActionListener {

    private static final long serialVersionUID = -3299575618889083096L;

    /** Width of the strip. Wide enough for a 12pt label plus breathing room. */
    private static final int WIDTH = 26;

    /** Padding above and below the text within the tab. */
    private static final int PADDING = 14;

    private static final Color SELECTED_BG = new Color(0xE3E9EF);
    private static final Color HOVER_BG = new Color(0xF1F4F7);
    private static final Color EDGE = new Color(0xCCD3DA);
    private static final Color TEXT = new Color(0x33404D);

    private final Component sidePanel;
    private final GSidebar sidebar;
    private final String label;

    GSidebarButton(Icon icon, Component sidepanel, GSidebar sidebar, String label) {
        this.sidePanel = sidepanel;
        this.sidebar = sidebar;
        this.label = label;
        setToolTipText(label);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setRolloverEnabled(true);
        addActionListener(this);

        int height = PADDING * 2 + textLength();
        Dimension size = new Dimension(WIDTH, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setAlignmentX(CENTER_ALIGNMENT);
    }

    private int textLength() {
        return getFontMetrics(getFont()).stringWidth(label);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            if (isSelected()) {
                g2.setColor(SELECTED_BG);
                g2.fillRect(0, 0, w, h);
            } else if (getModel().isRollover()) {
                g2.setColor(HOVER_BG);
                g2.fillRect(0, 0, w, h);
            }
            g2.setColor(EDGE);
            g2.drawLine(w - 1, 0, w - 1, h - 1);

            // Rotate a quarter turn anticlockwise about the centre, then draw the label
            // horizontally: it comes out reading bottom-to-top.
            g2.translate(w / 2.0, h / 2.0);
            g2.rotate(-Math.PI / 2);
            g2.setColor(TEXT);
            g2.setFont(getFont());
            int textWidth = g2.getFontMetrics().stringWidth(label);
            int baseline = g2.getFontMetrics().getAscent() / 2 - 1;
            g2.drawString(label, -textWidth / 2f, baseline);
        } finally {
            g2.dispose();
        }
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        sidebar.setPanel(sidePanel, label);
    }
}
