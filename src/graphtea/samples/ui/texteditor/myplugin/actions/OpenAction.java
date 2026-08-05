// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.ui.texteditor.myplugin.actions;

import graphtea.platform.core.BlackBoard;
import graphtea.ui.extension.UIActionExtension;

import javax.swing.*;
import java.io.File;

public class OpenAction implements UIActionExtension {

    public void actionPerformed(BlackBoard blackBoard) {
        //Show the file editor
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setMultiSelectionEnabled(false);

        int ret = jfc.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();

            //fetch the text area (body of application) from blackboard
            JTextArea editor = Utils.getMainEditor(blackBoard);

            //read the file and put it in editor
            editor.setText(ReadWriteTextFile.getContents(selectedFile));

            //put the file address in blackboard to use later in save
            blackBoard.setData("last file", selectedFile.getAbsolutePath());
        }
    }

}
