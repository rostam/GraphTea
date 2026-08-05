// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor;

import graphtea.platform.attribute.AtomAttribute;
import graphtea.platform.attribute.NotifiableAttributeSet;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.lang.BoundedInteger;
import graphtea.ui.NotifiableAttributeSetView;
import graphtea.ui.PortableNotifiableAttributeSetImpl;
import graphtea.ui.UIUtils;
import graphtea.ui.components.gpropertyeditor.editors.GColorEditor;
import graphtea.ui.components.gpropertyeditor.editors.GFileEditor;
import graphtea.ui.components.gpropertyeditor.editors.GFontEditor;
import graphtea.ui.components.gpropertyeditor.editors.GStringEditor;
import graphtea.ui.components.gpropertyeditor.editors.inplace.ArrayXEditor;
import graphtea.ui.components.gpropertyeditor.editors.inplace.BoundedIntegerEditor;
import graphtea.ui.components.gpropertyeditor.editors.inplace.GBooleanEditor;
import graphtea.ui.components.gpropertyeditor.editors.inplace.GDimensionEditor;
import graphtea.ui.components.gpropertyeditor.renderers.*;
import graphtea.ui.components.utils.GFrameLocationProvider;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * see http://graphtea.sharif.ir/trac/wiki/PropertyEditor for documentations. todo: [tea] link to be updated
 * @author  Azin Azadi
 * @email :   aazadi@gmail.com
 */

public class GPropertyEditor extends JComponent {
    GCellEditor editor = new GCellEditor();
    GCellRenderer renderer = new GCellRenderer();
    JTable table = new JTable() {

        public void paint(Graphics g) {
            super.paint(g);
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(150, 1100);
        }

        @Override
        public Dimension getPreferredSize() {
            return getMinimumSize();
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle rectangle, int i, int i2) {
            return 8;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle rectangle, int i, int i2) {
            return getVisibleRect().height;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    };
    public GPropertyTableModel model = new GPropertyTableModel();
    NotifiableAttributeSetView def = new PortableNotifiableAttributeSetImpl();

    public GPropertyEditor() {
        super();
        setMinimumSize(new Dimension(300, 200));
        setPreferredSize(new Dimension(300, 300));
        table.setModel(model);
        table.setDefaultRenderer(table.getColumnClass(1), renderer);
        table.setDefaultEditor(table.getColumnClass(1), editor);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateColumnsFromModel(true);
//        table.getTableHeader().setUpdateTableInRealTime(true);
        table.setSurrendersFocusOnKeystroke(true);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row == -1)
                    return;
                MouseListener[] mouseListeners = renderer.getLastCreatedRenderer(row).getMouseListeners();
                if (mouseListeners != null) {
                    for (MouseListener ml : mouseListeners) {
                        ml.mouseClicked(e);
                    }
                }
            }
        });

        styleTable();
        initComponents();
        l.setEditable(false);
        l.setLineWrap(true);
        l.setWrapStyleWord(true);

//        l.setLineWrap(true);
//        l.setPreferredSize(new Dimension(300,300));
    }

    /** Row height. The default 16 leaves no air around the text or the in-place editors. */
    private static final int ROW_HEIGHT = 22;

    private static final Color GRID = new Color(0xE8ECEF);
    private static final Color HEADER_BG = new Color(0xF5F7F9);
    private static final Color HEADER_FG = new Color(0x7F8C8D);
    private static final Color NAME_FG = new Color(0x44515E);

    /**
     * Makes the property list legible.
     *
     * <p>It rendered as a dense spreadsheet: 16-pixel rows with the text jammed against the
     * cell edge, a full grid of dark lines around every cell, and column headers that the look
     * and feel drew as if they were editable text fields. The settings that would have fixed
     * it were present in the source but commented out.
     */
    private void styleTable() {
        table.setRowHeight(ROW_HEIGHT);
        table.setGridColor(GRID);
        // Horizontal rules only: a full grid on a two-column list is all boxes and no content.
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setReorderingAllowed(false);
            header.setPreferredSize(new Dimension(0, 24));
            TableCellRenderer base = header.getDefaultRenderer();
            header.setDefaultRenderer((t, value, selected, focused, row, column) -> {
                Component c = base.getTableCellRendererComponent(t, value, selected, focused, row, column);
                if (c instanceof JLabel headerLabel) {
                    headerLabel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0, 0, 1, 0, GRID),
                            new EmptyBorder(0, 8, 0, 8)));
                    headerLabel.setFont(headerLabel.getFont().deriveFont(Font.PLAIN, 11f));
                    headerLabel.setForeground(HEADER_FG);
                    headerLabel.setBackground(HEADER_BG);
                    headerLabel.setOpaque(true);
                }
                return c;
            });
        }

        // The value column has its own renderer; the name column was using the bare default,
        // with the text hard against the left edge.
        TableCellRenderer nameBase = new DefaultTableCellRenderer();
        table.getColumnModel().getColumn(0).setCellRenderer((t, value, selected, focused, row, column) -> {
            Component c = nameBase.getTableCellRendererComponent(t, value, selected, focused, row, column);
            if (c instanceof JLabel nameLabel) {
                nameLabel.setBorder(new EmptyBorder(0, 8, 0, 8));
                if (!selected) {
                    nameLabel.setForeground(NAME_FG);
                }
            }
            return c;
        });
    }

    private final JTextArea l = new JTextArea();

    private void initComponents() {
        JSplitPane jSplitPane1 = new JSplitPane();
        JScrollPane jScrollPane1 = new JScrollPane();
        JScrollPane jScrollPane2 = new JScrollPane();

        jScrollPane1.setBorder(new EmptyBorder(0, 0, 0, 0));
        jScrollPane2.setBorder(new EmptyBorder(0, 0, 0, 0));
        jSplitPane1.setBorder(new EmptyBorder(0, 0, 0, 0));

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerSize(2);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.8);
        jScrollPane1.setViewportView(table);
        jScrollPane2.setViewportView(l);
        table.setBackground(Color.white);
        jScrollPane1.setBackground(Color.white);
        jScrollPane2.setBackground(Color.white);
        jSplitPane1.setLeftComponent(jScrollPane1);

//        jPanel1.add(l);
//        jPanel1.setMinimumSize(new Dimension(10, 20));
//        jPanel1.setMaximumSize(new Dimension(10, 20));
        jSplitPane1.setRightComponent(jScrollPane2);
//        l.setBackground(Color.white);
//        l.setOpaque(true);
        add(jSplitPane1, java.awt.BorderLayout.CENTER);

        validate();
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                updateDescription();
            }
        });
    }

    private void updateDescription() {
        if (target != null) {
            int scc = table.getSelectedRow();
            if (scc != -1) {
                String name = target.getView().getNameAt(scc);
                String desc = target.getView().getDescription(name);
                if (desc == null || desc.equals("")) {
                    desc = name;
                }
                desc += ": " + target.getView().getAttribute().get(name);
                setDescription(desc);
            }
        } else {
            setDescription("");
        }
    }

    private void setDescription(String desc) {
        l.setText(desc);
    }

    static {
        UIUtils.registerRenderer(Dimension.class, new GDimensionRenderer());
        UIUtils.registerEditor(Dimension.class, new GDimensionEditor());
//        registerEditor(String.class, new GStringEditor());
        UIUtils.registerEditor(Color.class, new GColorEditor());
        UIUtils.registerRenderer(Color.class, new GColorRenderer());
        UIUtils.registerRenderer(Boolean.class, new GBooleanRenderer());
        UIUtils.registerEditor(Boolean.class, new GBooleanEditor());
        UIUtils.registerEditor(Font.class, new GFontEditor());
        UIUtils.registerRenderer(Font.class, new GFontRenderer());
        UIUtils.registerEditor(ArrayX.class, new ArrayXEditor(null));
//        registerEditor(DefaultOAtr.class, new DefaultOAtrEditor());
        UIUtils.registerRenderer(AtomAttribute.class, new OAtrRenderer());
        UIUtils.registerEditor(String.class, new GStringEditor());
        UIUtils.registerEditor(BoundedInteger.class, new BoundedIntegerEditor());
        UIUtils.registerRenderer(BoundedInteger.class, new BoundedIntegerRenderer());
        UIUtils.registerRenderer(Iterable.class, new IterableRenderer());
        UIUtils.registerEditor(File.class, new GFileEditor());
    }

    public ProEditor2NotifiableAttributeSetConnector conector = new ProEditor2NotifiableAttributeSetConnector(this);

    public void connect(NotifiableAttributeSet x) {
        conector.connect(x);

        int h = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            h += table.getRowHeight(i);
        }
        h += table.getRowCount() * table.getRowMargin();
        table.setPreferredSize(GFrameLocationProvider.getPopUpSize());
        table.validate();
    }

    NotifiableAttributeSetView target;
    public JTable getTable() {
        return table;
    }
}