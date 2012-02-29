// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gpropertyeditor.editors.inplace;

import graphlab.platform.lang.BoundedInteger;
import graphlab.ui.components.gpropertyeditor.EditingFinishedListener;
import graphlab.ui.components.gpropertyeditor.GBasicCellEditor;
import graphlab.ui.components.utils.LabledSlider;

import javax.swing.*;
import java.awt.*;

/**
 * @author azin azadi
 */
public class BoundedIntegerEditor implements GBasicCellEditor<BoundedInteger> {
    private EditingFinishedListener listener;
    private JSlider s;
    private BoundedInteger value;

    public void setEditingFinishedListener(EditingFinishedListener listener) {
        this.listener = listener;
    }

    public Component getEditorComponent(BoundedInteger value) {
        this.value = value;
        LabledSlider labledSlider = new LabledSlider(value);
        s = labledSlider.getSlider();
        return labledSlider;
    }


    public void cancelEditing() {
        listener.editingFinished(getEditorValue());
    }

    public Object getEditorValue() {
        value.setValue(s.getValue());
        return value;
    }
}
