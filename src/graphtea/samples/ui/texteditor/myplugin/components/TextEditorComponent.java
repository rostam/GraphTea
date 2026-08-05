// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.ui.texteditor.myplugin.components;

import graphtea.platform.core.BlackBoard;
import graphtea.ui.components.GComponentInterface;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TextEditorComponent implements GComponentInterface {
    public Component getComponent(BlackBoard b) {
        JTextArea ed = new JTextArea();
        ed.setBorder(new LineBorder(Color.white, 10));
        return new JScrollPane(ed);
    }
}
