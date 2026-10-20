// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.reports.ui;

import graphtea.extensions.reports.boundcheck.Expression;
import graphtea.extensions.reports.boundcheck.Invariants;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * A searchable list of the invariants a formula can use. Choosing one types its name into the
 * formula at the caret, so nobody has to guess that "Wiener Index" is written
 * {@code wiener_index}.
 *
 * @author M. Ali Rostami
 */
final class InvariantPicker extends JDialog {
    private static final long serialVersionUID = 1L;

    private final transient Invariants invariants;
    private final DefaultListModel<Invariants.Invariant> model = new DefaultListModel<>();
    private final JList<Invariants.Invariant> list = new JList<>(model);
    private final JTextField search = new JTextField();
    private transient JTextComponent target;

    InvariantPicker(Window owner, Invariants invariants) {
        super(owner, "Insert an Invariant", ModalityType.MODELESS);
        this.invariants = invariants;

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> l, Object value, int index, boolean selected,
                                                          boolean focus) {
                Invariants.Invariant inv = (Invariants.Invariant) value;
                String html = "<html><b>" + inv.id() + "</b>&nbsp;&nbsp;<span style='color:"
                        + hex(selected ? null : CheckerStyle.muted()) + "'>" + escape(inv.title())
                        + " · " + escape(inv.group()) + "</span></html>";
                return super.getListCellRendererComponent(l, html, index, selected, focus);
            }
        });
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    insert();
                }
            }
        });

        search.putClientProperty("JTextField.placeholderText", "Search, e.g. wiener, energy, degree");
        search.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            public void changedUpdate(DocumentEvent e) {
                filter();
            }
        });
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                moveSelection(e);
            }
        });

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(search, BorderLayout.NORTH);
        content.add(new JScrollPane(list), BorderLayout.CENTER);
        content.add(functionsHint(), BorderLayout.SOUTH);
        setContentPane(content);

        bind("ENTER", this::insert);
        bind("ESCAPE", this::dispose);
        setSize(new Dimension(560, 460));
        filter();
    }

    /**
     * Shows the picker for a formula field.
     *
     * @param field  the field to type into
     * @param anchor the component to open next to
     */
    void showFor(JTextComponent field, Component anchor) {
        this.target = field;
        setLocationRelativeTo(anchor);
        setVisible(true);
        search.requestFocusInWindow();
        search.selectAll();
    }

    private static JLabel functionsHint() {
        return CheckerStyle.hint("<html>Functions: " + String.join(", ", Expression.functionNames())
                + ".<br>Constants pi and e. Operators + − * / ^, comparisons, and, or, not.</html>");
    }

    private void bind(String key, Runnable r) {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        getRootPane().getActionMap().put(key, new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                r.run();
            }
        });
    }

    private void filter() {
        String q = search.getText().trim().toLowerCase(Locale.ROOT);
        model.clear();
        List<Invariants.Invariant> hits = new ArrayList<>();
        for (Invariants.Invariant inv : invariants.all()) {
            String hay = (inv.id() + " " + inv.title() + " " + inv.group()).toLowerCase(Locale.ROOT);
            if (q.isEmpty() || hay.contains(q)) {
                hits.add(inv);
            }
        }
        // Names starting with the search come first, then names containing it, then the rest.
        hits.sort(Comparator.comparingInt(inv -> inv.id().startsWith(q) ? 0 : inv.id().contains(q) ? 1 : 2));
        hits.forEach(model::addElement);
        if (!model.isEmpty()) {
            list.setSelectedIndex(0);
        }
    }

    private void moveSelection(KeyEvent e) {
        int i = list.getSelectedIndex();
        if (e.getKeyCode() == KeyEvent.VK_DOWN && i < model.size() - 1) {
            list.setSelectedIndex(i + 1);
        } else if (e.getKeyCode() == KeyEvent.VK_UP && i > 0) {
            list.setSelectedIndex(i - 1);
        } else {
            return;
        }
        list.ensureIndexIsVisible(list.getSelectedIndex());
        e.consume();
    }

    private void insert() {
        Invariants.Invariant inv = list.getSelectedValue();
        if (inv == null || target == null) {
            return;
        }
        try {
            int at = target.getCaretPosition();
            target.getDocument().insertString(at, inv.id(), null);
        } catch (BadLocationException e) {
            target.setText(target.getText() + inv.id());
        }
        dispose();
        target.requestFocusInWindow();
    }

    private static String hex(java.awt.Color c) {
        return c == null ? "inherit" : String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    private static String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
