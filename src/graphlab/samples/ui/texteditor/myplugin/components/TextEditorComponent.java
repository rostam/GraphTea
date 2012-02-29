// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.samples.ui.texteditor.myplugin.components;

import graphlab.platform.core.BlackBoard;
import graphlab.ui.components.GComponentInterface;

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
