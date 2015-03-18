// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.core.extension;

import graphtea.graph.graph.GraphModel;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.StaticUtils;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.plugins.main.saveload.ExampleFileFilter;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.ui.extension.AbstractExtensionAction;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * @author azin azadi

 */
public class GraphReaderExtensionAction extends AbstractExtensionAction {
    private GraphReaderExtension gi;
    private String prefix;

    public GraphReaderExtensionAction(BlackBoard bb, GraphReaderExtension gi) {
        super(bb, gi);
        this.gi = gi;
    }

    public String getMenuNamePrefix() {
        return "";
    }

    public String getParentMenuName() {
        return "File.Load Graph From";
    }

    public void performExtension() {
        try {
            importGraph();
        }
        catch (Exception e) {
            ExceptionHandler.catchException(e);
            StaticUtils.addExceptiontoLog(e, blackboard);
        }
    }

    String ss;
    GraphModel g;
    IOException ee;
    GraphIOException gioe;

    private void importGraph() throws GraphIOException {
        JFileChooser fileChooser = new JFileChooser();
        ExampleFileFilter fileFilter = new ExampleFileFilter(gi.getExtension(), gi.getName());
        fileFilter.setDescription(gi.getDescription());
        fileChooser.setFileFilter(fileFilter);
        if (GraphIOExtensionHandler.defaultFile != null)
            fileChooser.setCurrentDirectory(new File(GraphIOExtensionHandler.defaultFile));
        int l = fileChooser.showOpenDialog(null);
        if (l == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser
                    .getSelectedFile();
            GraphIOExtensionHandler.defaultFile = selectedFile.getPath();

            if (gi.accepts(selectedFile)) {
                GraphModel g = gi.read(selectedFile);
                GTabbedGraphPane.getCurrentGTabbedGraphPane(blackboard).addGraph(g);
            }
        }

    }

}
