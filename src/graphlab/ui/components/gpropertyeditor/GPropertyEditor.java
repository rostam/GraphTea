// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gpropertyeditor;

import graphlab.platform.attribute.AtomAttribute;
import graphlab.platform.attribute.NotifiableAttributeSet;
import graphlab.platform.lang.ArrayX;
import graphlab.platform.lang.BoundedInteger;
import graphlab.ui.NotifiableAttributeSetView;
import graphlab.ui.PortableNotifiableAttributeSetImpl;
import graphlab.ui.UIUtils;
import graphlab.ui.components.gpropertyeditor.editors.GColorEditor;
import graphlab.ui.components.gpropertyeditor.editors.GFileEditor;
import graphlab.ui.components.gpropertyeditor.editors.GFontEditor;
import graphlab.ui.components.gpropertyeditor.editors.GStringEditor;
import graphlab.ui.components.gpropertyeditor.editors.inplace.ArrayXEditor;
import graphlab.ui.components.gpropertyeditor.editors.inplace.BoundedIntegerEditor;
import graphlab.ui.components.gpropertyeditor.editors.inplace.GBooleanEditor;
import graphlab.ui.components.gpropertyeditor.editors.inplace.GDimensionEditor;
import graphlab.ui.components.gpropertyeditor.renderers.*;
import graphlab.ui.components.utils.GFrameLocationProvider;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * see http://graphlab.sharif.ir/trac/wiki/PropertyEditor for documentations.
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

////        table.setGridColor(Color.lightGray);
//        table.setRowHeight(30);
//        table.getColumnModel().getColumn(1).setWidth(50);
        initComponents();
        l.setEditable(false);
	l.setLineWrap(true);
	l.setWrapStyleWord(true);

//        l.setLineWrap(true);
//        l.setPreferredSize(new Dimension(300,300));
    }

    JTextArea l = new JTextArea();

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