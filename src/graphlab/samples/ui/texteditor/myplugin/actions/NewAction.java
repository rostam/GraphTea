// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.samples.ui.texteditor.myplugin.actions;

import graphlab.platform.core.BlackBoard;
import graphlab.ui.extension.UIActionExtension;

import javax.swing.*;

public class NewAction implements UIActionExtension {
    public void actionPerformed(BlackBoard blackBoard) {
        //fetch the text area (body of application) from blackboard
        JTextArea editor = Utils.getMainEditor(blackBoard);

        //read the file and put it in editor
        editor.setText("");

        //put the file address in blackboard to use later in save
        blackBoard.setData("last file", "");

    }
}
