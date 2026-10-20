// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.utils;

import graphtea.platform.attribute.NotifiableAttributeSet;
import graphtea.platform.extension.Extension;
import graphtea.ui.components.gpropertyeditor.GPropertyEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A dialog that edits a set of attributes in a property table: the parameters of a generator,
 * report or operator before it runs, or the properties of a vertex or edge.
 */
public class GAttrFrame extends JDialog {
    private static final long serialVersionUID = 722213016758300314L;

    /** Width the explanatory text wraps at. */
    private static final int INTRO_WIDTH = 340;

    private final NotifiableAttributeSet atr;
    private GPropertyEditor table;
    private boolean status = false;

    /**
     * @param parent the window the dialog belongs to
     * @param atr    the attributes to edit
     * @param modal  whether the dialog blocks until it is closed
     */
    public GAttrFrame(Frame parent, NotifiableAttributeSet atr, boolean modal) {
        this(parent, atr, modal, null);
    }

    /**
     * @param owner the window the dialog belongs to, or null
     * @param atr   the attributes to edit
     * @param modal whether the dialog blocks until it is closed
     * @param intro a sentence or two shown above the table, or null
     */
    public GAttrFrame(Window owner, NotifiableAttributeSet atr, boolean modal, String intro) {
        super(owner, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
        this.atr = atr;
        initComponents(intro);
    }

    /**
     * @return true if the dialog was closed with OK, false if it was cancelled
     */
    public boolean getReturnStatus() {
        return status;
    }

    private void initComponents(String intro) {
        table = new GPropertyEditor();
        table.connect(atr);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        okButton.addActionListener(evt -> finished(true));
        cancelButton.addActionListener(evt -> finished(false));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        if (intro != null && !intro.isBlank()) {
            JLabel introLabel = new JLabel("<html><div style='width:" + INTRO_WIDTH + "px'>"
                    + escape(intro.strip()) + "</div></html>");
            introLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
            content.add(introLabel, BorderLayout.NORTH);
        }
        content.add(table, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(content);

        // Enter confirms and Escape cancels, as in every other dialog. The old key listener
        // only fired while OK itself had focus, and tested a key code inside keyTyped, where
        // it is always VK_UNDEFINED, so Enter never worked at all.
        getRootPane().setDefaultButton(okButton);
        getRootPane().registerKeyboardAction(e -> finished(false),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                finished(false);
            }
        });
        pack();
        setLocationRelativeTo(getOwner());
    }

    private static String escape(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private void finished(boolean status) {
        this.status = status;
        if (status && table.getTable().isEditing()) {
            table.getTable().getCellEditor().stopCellEditing();
        }
        dispose();
    }

    public static GAttrFrame showEditDialog(NotifiableAttributeSet input) {
        return showEditDialog(input, true);
    }

    /**
     * Shows a Property editor to edit the attributes in the input.
     * the modal is like the modal in JDialog
     */
    public static GAttrFrame showEditDialog(NotifiableAttributeSet input, boolean modal) {
        String title = input instanceof Extension extension ? extension.getName() : "Properties";
        return showEditDialog(input, modal, title, null);
    }

    /**
     * Shows a property editor over the active window.
     *
     * @param input the attributes to edit
     * @param modal whether to block until the dialog is closed
     * @param title the dialog title: what is being edited, not "Edit Attributes"
     * @param intro a sentence or two shown above the table, or null
     * @return the dialog, closed already if it was modal
     */
    public static GAttrFrame showEditDialog(NotifiableAttributeSet input, boolean modal, String title, String intro) {
        // Previously parented to a new, never-shown JFrame on every call: the dialog opened at
        // the screen's corner instead of over GraphTea, and each call leaked a frame.
        Window owner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
        GAttrFrame gAttrFrame = new GAttrFrame(owner, input, modal, intro);
        gAttrFrame.setTitle(title);
        gAttrFrame.setVisible(true);
        return gAttrFrame;
    }

    /**
     * return the GPropertyEditor which is the main editor of NotifiableAttributeSet
     */
    public GPropertyEditor getPropertyEditor() {
        return table;
    }
}
