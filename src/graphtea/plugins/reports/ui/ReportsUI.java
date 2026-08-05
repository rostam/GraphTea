// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins.reports.ui;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.extension.Extension;
import graphtea.platform.extension.ExtensionLoader;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.reports.extension.GraphReportExtension;
import graphtea.plugins.reports.extension.GraphReportExtensionHandler;
import graphtea.ui.UIUtils;
import graphtea.ui.extension.AbstractExtensionAction;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ToolTipManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * The Reports panel in the sidebar: a filterable tree of every report GraphTea can run.
 *
 * <p>This was previously a Jade template rendered to HTML and displayed in a
 * {@link javax.swing.JEditorPane}, with each report a hyperlink whose URL carried the report's
 * class name for a handler to reflect on. For a document that is a reasonable design; for a
 * list of two hundred commands it bought nothing and cost a lot. JEditorPane renders HTML 3.2,
 * so there was no hover feedback, no keyboard navigation, no selection and no way to collapse a
 * category; every keystroke in a filter box would have re-run the template and re-parsed the
 * whole document; and the panel dragged in four jars — jade4j, JEXL, commons-lang and
 * commons-logging, about 850 KB — none of which anything else in GraphTea used.
 *
 * <p>A {@link JTree} gives collapsible categories, type-ahead, arrow-key navigation and hover
 * highlighting for free, holds the extension object directly instead of reflecting on a name in
 * a URL, and re-filters by swapping a model rather than re-parsing a document.
 *
 * @author azin azadi
 */
public class ReportsUI {

    /** Category shown for reports that declare none. */
    private static final String UNCATEGORISED = "Other";

    /** Separator the extensions use inside a category name to mean "sub-category". */
    private static final String SUBCATEGORY_SEPARATOR = "-";

    private static final Color MUTED = new Color(0x7F8C8D);

    GraphData graphData;

    public static ReportsUI self = null;
    private final BlackBoard blackboard;

    /** The component handed to the sidebar. */
    public JPanel sidebar;

    private JTextField filter;
    private JLabel count;
    private JTree tree;
    private DefaultTreeModel treeModel;

    /** Every registered report, sorted by name, read once after the extensions load. */
    private final List<GraphReportExtension> allReports = new ArrayList<>();

    public ReportsUI(BlackBoard b) {
        super();
        self = this;
        this.blackboard = b;
        graphData = new GraphData(b);
        initComponents();
    }

    /** A report sitting as a leaf in the tree. Holds the extension, not a name to look up. */
    private record ReportNode(GraphReportExtension report) {
        @Override
        public String toString() {
            return displayName(report);
        }
    }

    private void initComponents() {
        filter = new JTextField();
        filter.setToolTipText("Type part of a report name");
        filter.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                rebuild();
            }

            public void removeUpdate(DocumentEvent e) {
                rebuild();
            }

            public void changedUpdate(DocumentEvent e) {
                rebuild();
            }
        });
        // Down-arrow from the filter box moves into the results, so the whole panel is
        // reachable without touching the mouse.
        filter.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "focusResults");
        filter.getActionMap().put("focusResults", new javax.swing.AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tree.requestFocusInWindow();
                if (tree.getRowCount() > 0 && tree.getSelectionCount() == 0) {
                    tree.setSelectionRow(0);
                }
            }
        });

        count = new JLabel();
        count.setFont(count.getFont().deriveFont(Font.PLAIN, 11f));
        count.setForeground(MUTED);
        count.setBorder(BorderFactory.createEmptyBorder(5, 2, 0, 2));

        JPanel top = new JPanel(new BorderLayout(0, 0));
        top.setBorder(BorderFactory.createEmptyBorder(8, 10, 6, 10));
        top.add(filter, BorderLayout.CENTER);
        top.add(count, BorderLayout.SOUTH);

        treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("reports"));
        tree = new JTree(treeModel);
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        tree.setRowHeight(20);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setBorder(BorderFactory.createEmptyBorder(4, 6, 8, 6));
        tree.setCellRenderer(new Renderer());
        ToolTipManager.sharedInstance().registerComponent(tree);

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    runSelected();
                }
            }
        });
        tree.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "runReport");
        tree.getActionMap().put("runReport", new javax.swing.AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                runSelected();
            }
        });

        JScrollPane scroll = new JScrollPane(tree);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0xE1E6EA)));

        sidebar = new JPanel(new BorderLayout(0, 0));
        sidebar.setPreferredSize(new Dimension(400, 100));
        sidebar.add(top, BorderLayout.NORTH);
        sidebar.add(scroll, BorderLayout.CENTER);
    }

    /**
     * Reads the registered reports and builds the tree. Called once the extensions are loaded.
     */
    public void initTable() {
        allReports.clear();
        List<Extension> reports = ExtensionLoader.extensionsList.get(GraphReportExtensionHandler.class);
        if (reports != null) {
            for (Extension r : reports) {
                allReports.add((GraphReportExtension) r);
            }
        }
        allReports.sort(Comparator.comparing(r -> displayName(r).toLowerCase(Locale.ROOT)));
        rebuild();
    }

    private static String displayName(GraphReportExtension r) {
        String n = r.getName();
        return n == null || n.isBlank() ? r.getClass().getSimpleName() : n.trim();
    }

    /**
     * Turns an extension's category into a heading. Extensions encode a sub-category by joining
     * the two halves with a hyphen, which used to be shown to the user verbatim as
     * "Verification-Checking".
     *
     * @param r the report
     * @return the heading to file it under
     */
    private static String categoryOf(GraphReportExtension r) {
        String c = r.getCategory();
        if (c == null || c.isBlank()) {
            return UNCATEGORISED;
        }
        int i = c.indexOf(SUBCATEGORY_SEPARATOR);
        if (i > 0 && i < c.length() - 1) {
            return c.substring(0, i).trim() + " › " + c.substring(i + 1).trim();
        }
        return c.trim();
    }

    /** Rebuilds the tree for the current filter text. */
    private void rebuild() {
        String query = filter.getText() == null ? "" : filter.getText().trim().toLowerCase(Locale.ROOT);

        // TreeMap for a stable alphabetical order; the categories used to come out of a HashSet
        // and so appeared in a different order on every run.
        Map<String, List<GraphReportExtension>> byCategory = new TreeMap<>();
        int matches = 0;
        for (GraphReportExtension r : allReports) {
            String category = categoryOf(r);
            if (!query.isEmpty()
                    && !displayName(r).toLowerCase(Locale.ROOT).contains(query)
                    && !category.toLowerCase(Locale.ROOT).contains(query)) {
                continue;
            }
            byCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(r);
            matches++;
        }
        // "Other" reads better last than wherever O falls alphabetically.
        Map<String, List<GraphReportExtension>> ordered = new LinkedHashMap<>(byCategory);
        List<GraphReportExtension> other = ordered.remove(UNCATEGORISED);
        if (other != null) {
            ordered.put(UNCATEGORISED, other);
        }

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("reports");
        for (Map.Entry<String, List<GraphReportExtension>> e : ordered.entrySet()) {
            DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(e.getKey());
            for (GraphReportExtension r : e.getValue()) {
                categoryNode.add(new DefaultMutableTreeNode(new ReportNode(r), false));
            }
            root.add(categoryNode);
        }
        treeModel.setRoot(root);

        if (query.isEmpty()) {
            count.setText(matches + (matches == 1 ? " report" : " reports"));
            // Collapsed by default: nearly two hundred reports in one open list is the scroll
            // that made the old panel unusable.
            collapseAll();
        } else {
            count.setText(matches + " of " + allReports.size() + " reports");
            expandAll();
        }
    }

    private void collapseAll() {
        for (int i = tree.getRowCount() - 1; i >= 0; i--) {
            tree.collapseRow(i);
        }
    }

    private void expandAll() {
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
    }

    /** Runs the selected report, if the selection is a report rather than a category. */
    private void runSelected() {
        TreePath path = tree.getSelectionPath();
        if (path == null) {
            return;
        }
        Object last = path.getLastPathComponent();
        if (!(last instanceof DefaultMutableTreeNode node)
                || !(node.getUserObject() instanceof ReportNode leaf)) {
            return;
        }
        run(leaf.report());
    }

    /**
     * Fires the UI event that the report's menu item would fire, so the sidebar and the menu
     * take exactly the same path.
     *
     * @param report the report to run
     */
    private void run(GraphReportExtension report) {
        Object loaded = ExtensionLoader.loadedInstances.get(report.getClass().getName());
        if (loaded instanceof AbstractExtensionAction<?> action) {
            blackboard.setData(UIUtils.getUIEventKey(action.actionId), "reports sidebar");
        } else {
            ExceptionHandler.catchException(displayName(report),
                    new IllegalStateException("No loaded action for " + report.getClass().getName()));
        }
    }

    /** Categories muted and bold, reports plain, with the description as a tooltip. */
    private static final class Renderer extends DefaultTreeCellRenderer {

        private Renderer() {
            setLeafIcon(null);
            setOpenIcon(null);
            setClosedIcon(null);
        }

        @Override
        public Component getTreeCellRendererComponent(JTree t, Object value, boolean selected,
                                                      boolean expanded, boolean leaf, int row,
                                                      boolean focused) {
            super.getTreeCellRendererComponent(t, value, selected, expanded, leaf, row, focused);
            setToolTipText(null);
            if (value instanceof DefaultMutableTreeNode node) {
                Object user = node.getUserObject();
                if (user instanceof ReportNode reportNode) {
                    setFont(getFont().deriveFont(Font.PLAIN));
                    String description = reportNode.report().getDescription();
                    if (description != null && !description.isBlank()
                            && !description.equals(reportNode.toString())) {
                        setToolTipText(description);
                    }
                } else {
                    setFont(getFont().deriveFont(Font.BOLD));
                    if (!selected) {
                        setForeground(MUTED);
                    }
                }
            }
            return this;
        }
    }
}
