// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.samples.ui.texteditor.myplugin.actions;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.ui.extension.UIActionExtension;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SaveAction implements UIActionExtension {

    public void actionPerformed(BlackBoard blackBoard) {
        String path = (String) blackBoard.getData("last file");

        if (path == null || path.equals("")) {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setMultiSelectionEnabled(false);
            jfc.setDialogType(JFileChooser.SAVE_DIALOG);
            int ret = jfc.showSaveDialog(null);
            if (ret != JFileChooser.APPROVE_OPTION) {
                return;
            }
            path = jfc.getSelectedFile().getAbsolutePath();
        }

        //fetch the text area (body of application) from blackboard
        JTextArea editor = Utils.getMainEditor(blackBoard);

        try {
            System.out.println(path);

            File f = new File(path);
            if (!f.exists()) {
                f.createNewFile();
            }
            ReadWriteTextFile.setContents(f, editor.getText());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while saving file! <br> " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
            ExceptionHandler.catchException(e);
        }
    }

}
