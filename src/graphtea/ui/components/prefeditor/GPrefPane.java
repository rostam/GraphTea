// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.components.prefeditor;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.lang.NaturalOrder;
import graphtea.platform.preferences.AbstractPreference;
import graphtea.ui.UIUtils;
import graphtea.ui.components.gpropertyeditor.GPropertyEditor;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

/**
 * The Settings window: a list of categories beside the settings in the selected one.
 *
 * <p>This used to extend GFrame, the main window class, so opening Settings built a second,
 * untitled copy of the application frame. Its category list was cut to a fixed width, and its
 * "Ok" and "Cancel" buttons sat at opposite edges and did the same thing: every edit is applied
 * the moment it is made, so there was never anything to cancel. It is now a dialog over the main
 * window with a single Close button, and says that changes apply immediately.
 *
 * @author Rouzbeh Ebrahimi
 */
public class GPrefPane extends JDialog {
    /** Category holding values that are stored but not meant to be edited. */
    private static final String HIDDEN_CATEGORY = "Only Storable";

    private static final Dimension SIZE = new Dimension(760, 460);

    public final Map<String, AbstractPreference> tabs;
    private final GPropertyEditor editor = new GPropertyEditor();

    /**
     * @param bb    the main blackboard
     * @param items the settings, by category name
     */
    public GPrefPane(BlackBoard bb, Map<String, AbstractPreference> items) {
        super(UIUtils.getGFrame(bb), "Settings", ModalityType.MODELESS);
        this.tabs = items;

        List<String> categories = items.keySet().stream()
                .filter(s -> !HIDDEN_CATEGORY.equals(s))
                .sorted(NaturalOrder.INSTANCE)
                .toList();
        JList<String> list = new JList<>(categories.toArray(new String[0]));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && list.getSelectedValue() != null) {
                editor.connect(items.get(list.getSelectedValue()).attributeSet);
            }
        });
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setPreferredSize(new Dimension(Math.max(170, list.getPreferredSize().width + 24), 0));

        JLabel note = new JLabel("Changes take effect immediately.");
        note.setForeground(UIManager.getColor("Label.disabledForeground"));
        JButton close = new JButton("Close");
        close.addActionListener(e -> dispose());

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.add(note, BorderLayout.CENTER);
        buttons.add(close, BorderLayout.EAST);

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(listScroll, BorderLayout.WEST);
        content.add(editor, BorderLayout.CENTER);
        content.add(buttons, BorderLayout.SOUTH);
        setContentPane(content);

        getRootPane().setDefaultButton(close);
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        if (!categories.isEmpty()) {
            list.setSelectedIndex(0);
        }
        setSize(SIZE);
        setLocationRelativeTo(getOwner());
    }
}
