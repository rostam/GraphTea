// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.ui.components.gpropertyeditor.editors;

import javax.swing.*;
import java.io.File;

/**
 * @author azin azadi

 */
public class GFileEditor extends GDialogEditor<File> {
    JFileChooser jf = new JFileChooser();
    boolean selectDirectory;
    File lastFile = null;

    public GFileEditor() {
        jf.setControlButtonsAreShown(false);
    }

    public JComponent getComponent(File initialValue) {
        lastFile = initialValue;
        selectDirectory = initialValue.isDirectory();
        jf.setCurrentDirectory(initialValue);
        return jf;
    }

    public File getEditorValue() {
        File selectedFile = jf.getSelectedFile();
        File ret = selectDirectory ? jf.getCurrentDirectory() : selectedFile;
        if (ret == null)
            ret = lastFile;
        return ret;
    }

    public void setEditorValue(File value) {
        jf.setSelectedFile(value);
    }

}
