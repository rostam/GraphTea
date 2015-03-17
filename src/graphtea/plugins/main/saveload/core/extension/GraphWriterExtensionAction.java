// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.core.extension;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.plugins.main.core.actions.StatusBarMessage;
import graphtea.plugins.main.saveload.ExampleFileFilter;
import graphtea.plugins.main.saveload.SaveLoadPluginMethods;
import graphtea.plugins.main.saveload.core.GraphIOException;
import graphtea.ui.extension.AbstractExtensionAction;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * @author azin azadi

 */
public class GraphWriterExtensionAction extends AbstractExtensionAction {

    private GraphWriterExtension ge;
    private String prefix;

    public GraphWriterExtensionAction(BlackBoard bb, GraphWriterExtension ge) {
        super(bb, ge);
        this.ge = ge;
    }

    public String getParentMenuName() {
        return "File.Save Graph To";
    }

    public String getMenuNamePrefix() {
        return "";
    }

    public void performExtension() {
        try {
            if (ge != null)
                exportGraph();
        }
        catch (Exception e) {
            ExceptionHandler.catchException(e);
        }
    }

    String ss;
    GraphModel g;
    IOException ee;
    GraphIOException gioe;

    private void exportGraph() throws IOException, GraphIOException {
        g = ((GraphModel) (blackboard.getData(GraphAttrSet.name)));
        JFileChooser fileChooser = new JFileChooser();
        ExampleFileFilter fileFilter = new ExampleFileFilter(ge.getExtension(), ge.getName());
        fileFilter.setDescription(ge.getDescription());
        fileChooser.setFileFilter(fileFilter);
        if (GraphIOExtensionHandler.defaultFile != null)
            fileChooser.setCurrentDirectory(new File(GraphIOExtensionHandler.defaultFile));

        int l = fileChooser.showSaveDialog(null);
        if (l == JFileChooser.APPROVE_OPTION) {
            ss = fileChooser.getSelectedFile().getAbsolutePath();
            if (!ge.getExtension().equalsIgnoreCase(SaveLoadPluginMethods.getExtension(fileChooser.getSelectedFile())))
                ss += "." + ge.getExtension();
            GraphIOExtensionHandler.defaultFile = ss;
            if (!((new File(ss)).isFile())
                    || JOptionPane.showConfirmDialog(
                    null,
                    "A file with name "
                            + ss
                            + " exists! Do you want to rewrite it?",
                    "GraphTea Saving ...",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                GTabbedGraphPane.showNotificationMessage("Saving in the file ...", blackboard, true);
                new Thread() {
                    public void run() {
                        try {
                            ge.write(new File(ss), g);
                        } catch (GraphIOException e) {
                            //todo: handle exceptions
                            gioe = e;
                        }
                        StatusBarMessage.setMessage(blackboard, "");
                        GTabbedGraphPane.showTimeNotificationMessage("File Saved!", blackboard, 5000, true);
                    }
                }.start();

            }
        }
    }
}