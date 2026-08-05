// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.components;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Type-to-run search across every command in the menu bar.
 *
 * <p>GraphTea exposes well over three hundred reports, generators, algorithms and operators,
 * nested up to three menus deep, with names like "Eccentric Connective Co-Index". There was no
 * way to search them: finding a command meant knowing which of nine top-level menus its author
 * had filed it under. This puts all of them one keystroke away.
 *
 * <p>The index is rebuilt each time the palette opens, because extensions add their menu items
 * as they load and users can install more.
 */
public final class CommandPalette {

    /** Most matches to show; beyond this the list stops being a shortlist. */
    private static final int MAX_RESULTS = 60;

    private final JFrame owner;
    private final JMenuBar menuBar;

    private JDialog dialog;
    private JTextField query;
    private JList<Command> results;
    private DefaultListModel<Command> model;
    private List<Command> index = new ArrayList<>();

    /**
     * @param owner   the main window
     * @param menuBar the menu bar to index
     */
    public CommandPalette(JFrame owner, JMenuBar menuBar) {
        this.owner = owner;
        this.menuBar = menuBar;
    }

    /** One runnable menu item, with the menu path that led to it. */
    private static final class Command {
        private final String label;
        private final String path;
        private final JMenuItem item;
        private final String haystack;

        private Command(String label, String path, JMenuItem item) {
            this.label = label;
            this.path = path;
            this.item = item;
            this.haystack = (label + " " + path).toLowerCase(Locale.ROOT);
        }
    }

    /**
     * Installs the Ctrl+K (Cmd+K on macOS) shortcut on the given root component.
     *
     * @param root component whose window-level input map should carry the shortcut
     */
    public void installShortcut(JComponent root) {
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_K,
                java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ks, "graphtea.commandPalette");
        root.getActionMap().put("graphtea.commandPalette", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show();
            }
        });
    }

    /**
     * Opens the palette, rebuilding the command index first.
     */
    public void show() {
        if (dialog == null) {
            build();
        }
        index = collect();
        query.setText("");
        refresh();
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
        query.requestFocusInWindow();
    }

    private void build() {
        query = new JTextField();
        query.setFont(query.getFont().deriveFont(Font.PLAIN, 16f));
        query.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        model = new DefaultListModel<>();
        results = new JList<>(model);
        results.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        results.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int i,
                                                          boolean selected, boolean focused) {
                super.getListCellRendererComponent(list, value, i, selected, focused);
                Command c = (Command) value;
                setText("<html>" + escape(c.label)
                        + " &nbsp;<font color='" + (selected ? "#DDDDDD" : "#888888") + "'>"
                        + escape(c.path) + "</font></html>");
                setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
                return this;
            }
        });

        JLabel hint = new JLabel("↑↓ to choose · Enter to run · Esc to close");
        hint.setBorder(BorderFactory.createEmptyBorder(6, 12, 8, 12));
        hint.setForeground(new Color(0x888888));
        hint.setFont(hint.getFont().deriveFont(Font.PLAIN, 11f));

        JPanel content = new JPanel(new BorderLayout());
        content.add(query, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(results);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(0xDDDDDD)));
        content.add(scroll, BorderLayout.CENTER);
        content.add(hint, BorderLayout.SOUTH);

        query.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                refresh();
            }

            public void removeUpdate(DocumentEvent e) {
                refresh();
            }

            public void changedUpdate(DocumentEvent e) {
                refresh();
            }
        });

        // Arrow keys and Enter belong to the list even while the text field has focus,
        // otherwise the user has to tab away from what they are typing to pick a result.
        query.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN -> move(1);
                    case KeyEvent.VK_UP -> move(-1);
                    case KeyEvent.VK_PAGE_DOWN -> move(10);
                    case KeyEvent.VK_PAGE_UP -> move(-10);
                    case KeyEvent.VK_ENTER -> run();
                    case KeyEvent.VK_ESCAPE -> dialog.setVisible(false);
                    default -> {
                        return;
                    }
                }
                e.consume();
            }
        });

        results.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 1) {
                    int i = results.locationToIndex(e.getPoint());
                    if (i >= 0) {
                        results.setSelectedIndex(i);
                        if (e.getClickCount() >= 2) {
                            run();
                        }
                    }
                }
            }
        });

        dialog = new JDialog(owner, "Run a command", true);
        dialog.setContentPane(content);
        dialog.setPreferredSize(new Dimension(620, 440));
        dialog.pack();
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                query.requestFocusInWindow();
            }
        });
    }

    private void move(int delta) {
        if (model.isEmpty()) {
            return;
        }
        int next = Math.max(0, Math.min(model.size() - 1, results.getSelectedIndex() + delta));
        results.setSelectedIndex(next);
        results.ensureIndexIsVisible(next);
    }

    private void run() {
        Command c = results.getSelectedValue();
        if (c == null) {
            return;
        }
        dialog.setVisible(false);
        // Let the palette finish closing before the command opens anything of its own.
        SwingUtilities.invokeLater(c.item::doClick);
    }

    private void refresh() {
        String q = query.getText().trim().toLowerCase(Locale.ROOT);
        model.clear();
        List<Command> ranked = new ArrayList<>();
        for (Command c : index) {
            if (q.isEmpty() || score(c, q) > 0) {
                ranked.add(c);
            }
        }
        if (!q.isEmpty()) {
            ranked.sort((a, b) -> Integer.compare(score(b, q), score(a, q)));
        }
        for (int i = 0; i < Math.min(MAX_RESULTS, ranked.size()); i++) {
            model.addElement(ranked.get(i));
        }
        if (!model.isEmpty()) {
            results.setSelectedIndex(0);
            results.ensureIndexIsVisible(0);
        }
    }

    /**
     * Ranks a command against the query: a label that starts with it beats a label that merely
     * contains it, which beats a match only in the menu path.
     *
     * @param c command to score
     * @param q lowercase query
     * @return a positive score when it matches, zero when it does not
     */
    private static int score(Command c, String q) {
        String label = c.label.toLowerCase(Locale.ROOT);
        if (label.equals(q)) {
            return 100;
        }
        if (label.startsWith(q)) {
            return 80;
        }
        for (String word : label.split("[^a-z0-9]+")) {
            if (word.startsWith(q)) {
                return 60;
            }
        }
        if (label.contains(q)) {
            return 40;
        }
        if (c.haystack.contains(q)) {
            return 20;
        }
        return subsequence(label, q) ? 10 : 0;
    }

    /** Lets "eccdist" find "Eccentric Distance Sum". */
    private static boolean subsequence(String text, String q) {
        int i = 0;
        for (int j = 0; j < text.length() && i < q.length(); j++) {
            if (text.charAt(j) == q.charAt(i)) {
                i++;
            }
        }
        return i == q.length();
    }

    private List<Command> collect() {
        List<Command> out = new ArrayList<>();
        if (menuBar == null) {
            return out;
        }
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            JMenu m = menuBar.getMenu(i);
            if (m != null) {
                walk(m, strip(m.getText()), out);
            }
        }
        return out;
    }

    private void walk(MenuElement element, String path, List<Command> out) {
        for (MenuElement child : element.getSubElements()) {
            if (child instanceof JMenu sub) {
                walk(sub, path + " ▸ " + strip(sub.getText()), out);
            } else if (child instanceof JMenuItem item) {
                String label = strip(item.getText());
                if (!label.isEmpty() && item.isEnabled()) {
                    out.add(new Command(label, path, item));
                }
            } else {
                // JPopupMenu wrappers sit between a JMenu and its items.
                walk(child, path, out);
            }
        }
    }

    private static String strip(String text) {
        if (text == null) {
            return "";
        }
        // Menu labels carry mnemonic underscores, and the XML uses a sentinel for separators.
        if ("seperator_menu".equals(text)) {
            return "";
        }
        return text.replace("_", "").trim();
    }

    private static String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
