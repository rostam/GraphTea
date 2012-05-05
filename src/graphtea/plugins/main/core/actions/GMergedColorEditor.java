// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core.actions;

import graphtea.graph.graph.GraphModel;
import graphtea.ui.components.gpropertyeditor.EditingFinishedListener;
import graphtea.ui.components.gpropertyeditor.GBasicCellEditor;
import graphtea.ui.components.gpropertyeditor.editors.GColorEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * author: Azin Azadi
 * Email:
 */
public class GMergedColorEditor implements GBasicCellEditor<Integer>, ActionListener, EditingFinishedListener, MouseListener, ListCellRenderer {
    JComboBox c;
    private Integer curVal;
    private EditingFinishedListener listener;
    boolean general = true;

    public GMergedColorEditor(boolean vertexColor) {
        isVertexColor = vertexColor;
        general = false;
    }

    boolean isVertexColor;

    public GMergedColorEditor() {
        general = true;
    }

    private void createCombo() {
        c = new JComboBox();
        c.addItem("Int Editor");
        c.addItem("Color Editor");

        for (int i = 0; i < 20; i++) {
            c.addItem(i);
        }
        c.addItem(curVal);
        c.setRenderer(this);
        c.addActionListener(this);
        c.setSelectedIndex(c.getItemCount() - 1);
    }

    public void setEditingFinishedListener(EditingFinishedListener listener) {
        this.listener = listener;
    }

    public Component getEditorComponent(Integer value) {
        this.curVal = value;
        createCombo();
        return c;
    }


    public void cancelEditing() {

    }

    public Object getEditorValue() {
        if (c.getSelectedItem() instanceof String) {
            String val = (String) c.getSelectedItem();
            if (val.equals("Int Editor")) {
                try {
                    int v = Integer.parseInt(comp.getText());
                    return v;
                }
                catch (Exception ee) { //it was not a number
                    return curVal;
                }

            } else if (val.equals("Color Editor")) {
                Color editorValue = gc.getEditorValue();
                return editorValue.getRGB();
            }
        }
        return c.getSelectedItem();
    }


    GColorEditor gc;
    JTextField comp;

    public void actionPerformed(ActionEvent e) { //on combo
        if (c.getSelectedItem() instanceof String) {
            String val = (String) c.getSelectedItem();
            if (val.equals("Int Editor")) {
                showIntEditor();
            } else if (val.equals("Color Editor")) {
                showRGBEditor();
            }
        } else {
            Integer i = (Integer) c.getSelectedItem();
            listener.editingFinished(i);
        }
    }

    private void showRGBEditor() {
        gc = new GColorEditor();
        gc.getEditorComponent(GraphModel.getColor(curVal)).setVisible(true);
        gc.setEditingFinishedListener(this);
    }

    private void showIntEditor() {
        final JFrame f = new JFrame();
        comp = new JTextField(7);
        f.add(comp);
        f.pack();
        f.setVisible(true);
        comp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = comp.getText();
                try {
                    int v = Integer.parseInt(text);
                    listener.editingFinished(v);
                    f.setVisible(false);
                }
                catch (Exception ee) { //it was not a number
                    comp.setText("" + curVal);
                }
            }
        });
    }

    public void editingFinished(Object editorValue) {
        listener.editingFinished(((Color) editorValue).getRGB());
    }

    public void mouseClicked(MouseEvent e) {//this is done because of the bug:4135029 of jdk. see: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4135029
        if (c.getSelectedIndex() == 0) {
            showIntEditor();
        }
        if (c.getSelectedIndex() == 1) {
            showRGBEditor();
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof String) {
            DefaultListCellRenderer d = new DefaultListCellRenderer();
            Component c = d.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            return c;
            //                    String v = (String) value;
            //                    if (v.equals("Color Editor")) {
            //
            //                    } else if (v.equals("Int Editor")) {
            //
            //                    }
        }
        if (!general)
            return new GMergedColorRenderer().getRendererComponent(value, isVertexColor);
        else
            return new GMergedColorRenderer().getRendererComponent(value);
    }
}
