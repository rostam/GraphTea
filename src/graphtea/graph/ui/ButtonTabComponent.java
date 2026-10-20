// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.function.IntConsumer;

/**
 * A tab's title with a button that closes the tab.
 *
 * <p>There used to be two copies of this class. The one in use, in {@code graphtea.graph.old},
 * asked "Do you want to exit this tab?" before every close, even of an empty graph, while
 * <b>File &rsaquo; Close Tab</b> closed without asking; the other copy, here, was never used,
 * yet it was the one {@link GTabbedGraphPane} checked for when a graph was renamed. Closing is
 * now left to the owner of the tabs, so both ways of closing a tab behave the same.
 */
public class ButtonTabComponent extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int BUTTON_SIZE = 16;

    private final JTabbedPane pane;
    private final JLabel label;

    /**
     * @param pane    the tabbed pane the component sits in
     * @param onClose called with the tab's index when its close button is pressed
     */
    public ButtonTabComponent(JTabbedPane pane, IntConsumer onClose) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.pane = pane;
        setOpaque(false);

        label = new JLabel() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getText() {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                return i == -1 ? null : pane.getTitleAt(i);
            }
        };
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 6));
        add(label);

        JButton close = new CloseButton();
        close.addActionListener(e -> {
            int i = pane.indexOfTabComponent(this);
            if (i != -1) {
                onClose.accept(i);
            }
        });
        add(close);
    }

    /** Lays the title out again after the tab's title has changed. */
    public void titleChanged() {
        label.revalidate();
        label.repaint();
    }

    /** A small borderless button painted as a cross, darker while the pointer is over it. */
    private final class CloseButton extends JButton {
        private static final long serialVersionUID = 1L;

        CloseButton() {
            setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
            setToolTipText("Close this tab");
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusable(false);
            setRolloverEnabled(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color idle = UIManager.getColor("Label.disabledForeground");
            Color hover = UIManager.getColor("Label.foreground");
            g2.setColor(getModel().isRollover() || idle == null ? hover : idle);
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int d = 5;
            int w = getWidth();
            int h = getHeight();
            g2.drawLine(d, d, w - d - 1, h - d - 1);
            g2.drawLine(w - d - 1, d, d, h - d - 1);
            g2.dispose();
        }
    }
}
