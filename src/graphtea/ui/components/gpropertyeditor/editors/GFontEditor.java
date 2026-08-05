// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.ui.components.gpropertyeditor.editors;

import graphtea.ui.components.gpropertyeditor.utils.JFontChooser;

import javax.swing.*;
import java.awt.*;

/**
 * User: root
 */
public class GFontEditor extends GDialogEditor<Font> {

    JFontChooser jFontChooser;

    public JComponent getComponent(Font font) {
        jFontChooser = new JFontChooser();
        jFontChooser.setFont(font);
        return jFontChooser;
    }

    public Font getEditorValue() {
        return jFontChooser.getFont();
    }

    public void setEditorValue(Font font) {
        jFontChooser.setFont(font);
    }
}
