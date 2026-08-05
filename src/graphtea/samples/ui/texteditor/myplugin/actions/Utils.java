// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.ui.texteditor.myplugin.actions;

import graphtea.platform.core.BlackBoard;
import graphtea.ui.UIUtils;

import javax.swing.*;

public class Utils {
    public static JTextArea getMainEditor(BlackBoard blackBoard) {
        JScrollPane editor = (JScrollPane) UIUtils.getComponent(blackBoard, "body editor");
        return (JTextArea) editor.getViewport().getView();
    }

}
