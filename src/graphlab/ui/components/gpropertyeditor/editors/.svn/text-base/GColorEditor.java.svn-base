// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.ui.components.gpropertyeditor.editors;

import javax.swing.*;
import java.awt.*;

/**
 * author: Azin Azadi
 * Email:
 */
public class GColorEditor extends GDialogEditor<Color> {
    private JColorChooser jc;

    public JComponent getComponent(Color initialValue) {
        jc = new JColorChooser(initialValue);
        return jc;
    }

    public Color getEditorValue() {
        return jc.getColor();
    }

    public void setEditorValue(Color value) {
        jc.setColor(value);
    }
}