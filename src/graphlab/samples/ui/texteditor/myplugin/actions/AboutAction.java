// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.samples.ui.texteditor.myplugin.actions;

import graphlab.platform.core.BlackBoard;
import graphlab.ui.extension.UIActionExtension;

import javax.swing.*;

public class AboutAction implements UIActionExtension {
    public void actionPerformed(BlackBoard blackBoard) {
        JOptionPane.showMessageDialog(null, "<HTML><BODY><b>GraphLab UI<b> Text Editor Demo <br> Version 1.0 <br> Developed in Sharif University Of Technology</BODY></HTML>", "About", JOptionPane.INFORMATION_MESSAGE);
    }
}
