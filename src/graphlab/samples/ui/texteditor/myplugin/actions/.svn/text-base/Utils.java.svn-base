// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.samples.ui.texteditor.myplugin.actions;

import graphlab.platform.core.BlackBoard;
import graphlab.ui.UIUtils;

import javax.swing.*;

public class Utils {
    public static JTextArea getMainEditor(BlackBoard blackBoard) {
        JScrollPane editor = (JScrollPane) UIUtils.getComponent(blackBoard, "body editor");
        return (JTextArea) editor.getViewport().getView();
    }

}
