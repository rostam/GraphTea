// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.plugins;

import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

import javax.swing.*;
import java.io.File;

/**
 * @author Ali Ershadi
 */
public class FileOpener implements GraphActionExtension {
    public String getName() {
        return "File Opener";
    }

    public String getDescription() {
        return "Text File Opener";
    }

    public void action(GraphData gd) {
        GTabbedGraphPane gtgp = gd.getBlackboard().getData(GTabbedGraphPane.NAME);
        JFileChooser fc = new JFileChooser("");
        fc.showOpenDialog(new JFrame());
        File f = new File(fc.getSelectedFile().getAbsolutePath());
//        gtgp.add(f, f.getName());
    }
}
