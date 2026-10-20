// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.ui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * A graph canvas with a message strip ({@link GHTMLPageComponent}) underneath it.
 *
 * <p>The strip sizes itself to its message: it disappears when there is nothing to say and
 * grows, up to {@link #MAX_HELPER_HEIGHT}, for the multi-line messages algorithms write into
 * it. It used to be fixed at 75 pixels, which was too tall for the one-line status messages
 * that make up most of its traffic and too short for an algorithm's step description.
 *
 * @author Azin Azadi
 */
public class GSplitedPane extends JSplitPane {
    /** Tallest the strip grows before its message scrolls. */
    private static final int MAX_HELPER_HEIGHT = 180;
    private static final int DIVIDER_SIZE = 1;

    public GHTMLPageComponent helper;
    public JComponent main;

    public GSplitedPane(GHTMLPageComponent helper, JComponent main) {
        super(JSplitPane.VERTICAL_SPLIT, main, helper);
        this.helper = helper;
        this.main = main;

        helper.setMinimumSize(new Dimension(0, 0));
        // Extra height on resize goes to the canvas; the strip keeps the height it was fitted to.
        setResizeWeight(1.0);
        setDividerSize(DIVIDER_SIZE);
        setBorder(BorderFactory.createEmptyBorder());

        helper.addPropertyChangeListener(GHTMLPageComponent.CONTENT_PROPERTY,
                e -> SwingUtilities.invokeLater(this::fitHelper));
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                fitHelper();
            }
        });
        fitHelper();
    }

    /** Moves the divider so the strip is exactly as tall as its message needs. */
    private void fitHelper() {
        boolean empty = isBlank(helper.getEditorPane().getDocument());
        helper.setVisible(!empty);
        setDividerSize(empty ? 0 : DIVIDER_SIZE);
        int height = getHeight();
        if (empty || height <= 0) {
            return;
        }
        JEditorPane pane = helper.getEditorPane();
        Insets insets = helper.getInsets();
        // An HTML pane's preferred height depends on the width it is laid out at.
        pane.setSize(Math.max(1, getWidth() - insets.left - insets.right), Short.MAX_VALUE);
        int wanted = Math.min(MAX_HELPER_HEIGHT, pane.getPreferredSize().height + insets.top + insets.bottom);
        setDividerLocation(Math.max(0, height - getDividerSize() - wanted));
    }

    private static boolean isBlank(Document doc) {
        try {
            return doc.getText(0, doc.getLength()).isBlank();
        } catch (BadLocationException e) {
            return true;
        }
    }
}
